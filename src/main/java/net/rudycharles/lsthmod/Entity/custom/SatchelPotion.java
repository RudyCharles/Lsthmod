package net.rudycharles.lsthmod.Entity.custom;

import it.unimi.dsi.fastutil.doubles.DoubleDoubleImmutablePair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.rudycharles.lsthmod.Datagen.ModDatapackProvider;
import net.rudycharles.lsthmod.Enchantment.ModEnchantment;
import net.rudycharles.lsthmod.Entity.ModEntity;
import net.rudycharles.lsthmod.Item.ModItem;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.Math.sqrt;

public class SatchelPotion extends ThrowableItemProjectile {

    public double baseDamage = 10.0;
    public double baseRadiusSqr = 16.0;

    public static final Predicate<LivingEntity> WATER_SENSITIVE_OR_ON_FIRE = p_350140_ -> p_350140_.isSensitiveToWater() || p_350140_.isOnFire();

    public SatchelPotion(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SatchelPotion(Level pLevel, LivingEntity pShooter) {
        super(ModEntity.SATCHEL_POTION.get(), pShooter, pLevel);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItem.MAGIC_POTION.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            Direction direction = pResult.getDirection();
            BlockPos blockpos = pResult.getBlockPos();
            BlockPos blockpos1 = blockpos.relative(direction);
            PotionContents potioncontents = (PotionContents)itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (potioncontents.is(Potions.WATER)) {
                this.dowseFire(blockpos1);
                this.dowseFire(blockpos1.relative(direction.getOpposite()));
                Iterator var7 = Direction.Plane.HORIZONTAL.iterator();

                while(var7.hasNext()) {
                    Direction direction1 = (Direction)var7.next();
                    this.dowseFire(blockpos1.relative(direction1));
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            PotionContents potioncontents = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (potioncontents.is(Potions.WATER)) {
                this.applyWater();
            } else if (potioncontents.hasEffects()) {
                if (this.isLingering()) {
                    this.makeAreaOfEffectCloud(potioncontents);
                } else {
                    this.applySplash(
                            potioncontents.getAllEffects(), pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)pResult).getEntity() : null
                    );
                }
            } else if (itemstack.is(ModItem.MAGIC_POTION.get())){
                this.applyDamage(pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)pResult).getEntity() : null);
            }

            int i = potioncontents.potion().isPresent() && potioncontents.potion().get().value().hasInstantEffects() ? 2007 : 2002;
            if (potioncontents.hasEffects() || potioncontents.is(Potions.WATER) ) {
                this.level().levelEvent(i, this.blockPosition(), potioncontents.getColor());
            } else if (itemstack.is(ModItem.MAGIC_POTION.get())) {
                this.level().levelEvent(i, this.blockPosition(), 6684876);
            }
            this.discard();
        }
    }

    private void applyWater() {
        AABB aabb = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
        var enchantmentRegistry = this.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        var rad = this.baseRadiusSqr;

        for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, aabb, WATER_SENSITIVE_OR_ON_FIRE)) {
            double d0 = this.distanceToSqr(livingentity);
            if (this.getOwner() instanceof LivingEntity livingEntity2) {
                var level = EnchantmentHelper.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ModEnchantment.LARGER_POTION), livingEntity2);
                if (d0 <= rad + (level * 3)) {
                    if (livingentity.isSensitiveToWater()) {
                        livingentity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 12.0F);
                    }

                    if (livingentity.isOnFire() && livingentity.isAlive()) {
                        livingentity.extinguishFire();
                    }
                }
            }
        }

        for (Axolotl axolotl : this.level().getEntitiesOfClass(Axolotl.class, aabb)) {
            axolotl.rehydrate();
        }
    }

    private void applyDamage(@Nullable Entity pEntity) {
        AABB aabb = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
        double dmg = this.baseDamage;
        DamageSource damagesource = this.damageSources().magic();
        var enchantments = this.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        Entity owner = this.getOwner();
        var rad = this.baseRadiusSqr;

        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
        if (!list.isEmpty()) {
            for (LivingEntity livingentity : list) {
                double d0 = this.distanceToSqr(livingentity);
                if (owner instanceof LivingEntity livingEntity2) {
                    var level = EnchantmentHelper.getEnchantmentLevel(enchantments.getHolderOrThrow(ModEnchantment.LARGER_POTION), livingEntity2);
                    if (d0 <= rad + (level*3)) {
                        double d1 = 1.0 - sqrt(d0) / 5.0;
                        double d3 = this.position().x - livingentity.position().x;
                        double d4 = this.position().z - livingentity.position().z;
                        int i = EnchantmentHelper.getEnchantmentLevel(enchantments.getHolderOrThrow(ModEnchantment.POWERFUL_POTION), livingEntity2);
                        livingentity.hurt(damagesource, (float) ((dmg + (i * 2)) * d1));
                        livingentity.knockback(0.4*d1,d3,d4);
                    }
                }

            }
        }
    }

    private void applySplash(Iterable<MobEffectInstance> pEffects, @Nullable Entity pEntity) {
        AABB aabb = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
        var enchantments = this.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        var rad = this.baseRadiusSqr;

        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);
        if (!list.isEmpty()) {
            Entity entity = this.getEffectSource();

            for (LivingEntity livingentity : list) {
                double d0 = this.distanceToSqr(livingentity);
                if (this.getOwner() instanceof LivingEntity livingEntity2) {
                    var level = EnchantmentHelper.getEnchantmentLevel(enchantments.getHolderOrThrow(ModEnchantment.LARGER_POTION), livingEntity2);
                    if (livingentity.isAffectedByPotions()) {
                        if (d0 <= rad + (level*3)) {
                            double d1;
                            if (livingentity == pEntity) {
                                d1 = 1.0;
                            } else {
                                d1 = 1.0 - sqrt(d0) / 5.0;
                            }

                            for (MobEffectInstance mobeffectinstance : pEffects) {
                                Holder<MobEffect> holder = mobeffectinstance.getEffect();
                                if (holder.value().isInstantenous()) {
                                    holder.value().applyInstantenousEffect(this, this.getOwner(), livingentity, mobeffectinstance.getAmplifier(), d1);
                                } else {
                                    int i = mobeffectinstance.mapDuration(p_267930_ -> (int) (d1 * (double) p_267930_ + 0.5));
                                    MobEffectInstance mobeffectinstance1 = new MobEffectInstance(
                                            holder, i, mobeffectinstance.getAmplifier(), mobeffectinstance.isAmbient(), mobeffectinstance.isVisible()
                                    );
                                    if (!mobeffectinstance1.endsWithin(20)) {
                                        livingentity.addEffect(mobeffectinstance1, entity);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private void makeAreaOfEffectCloud(PotionContents pPotionContents) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
        var enchantments = this.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

        if (this.getOwner() instanceof LivingEntity livingentity) {
            areaeffectcloud.setOwner(livingentity);
            var level = EnchantmentHelper.getEnchantmentLevel(enchantments.getHolderOrThrow(ModEnchantment.LARGER_POTION), livingentity);
            areaeffectcloud.setRadius((float) (3.0F + sqrt(level*3)/3));
        } else {
            areaeffectcloud.setRadius(3.0f);
        }
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.setPotionContents(pPotionContents);
        this.level().addFreshEntity(areaeffectcloud);
    }

    private boolean isLingering() {
        return this.getItem().is(Items.LINGERING_POTION);
    }

    private void dowseFire(BlockPos pPos) {
        BlockState blockstate = this.level().getBlockState(pPos);
        if (blockstate.is(BlockTags.FIRE)) {
            this.level().destroyBlock(pPos, false, this);
        } else if (AbstractCandleBlock.isLit(blockstate)) {
            AbstractCandleBlock.extinguish(null, blockstate, this.level(), pPos);
        } else if (CampfireBlock.isLitCampfire(blockstate)) {
            this.level().levelEvent(null, 1009, pPos, 0);
            CampfireBlock.dowse(this.getOwner(), this.level(), pPos, blockstate);
            this.level().setBlockAndUpdate(pPos, blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false)));
        }
    }

    @Override
    public DoubleDoubleImmutablePair calculateHorizontalHurtKnockbackDirection(LivingEntity p_345103_, DamageSource p_345887_) {
        double d0 = p_345103_.position().x - this.position().x;
        double d1 = p_345103_.position().z - this.position().z;
        return DoubleDoubleImmutablePair.of(d0, d1);
    }
}
