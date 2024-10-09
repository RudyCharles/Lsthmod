package net.rudycharles.lsthmod.Item.custom;

import net.minecraft.advancements.critereon.MobEffectsPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.rudycharles.lsthmod.Effect.ModEffect;
import net.rudycharles.lsthmod.Entity.custom.SatchelPotion;
import net.rudycharles.lsthmod.Enchantment.ModEnchantment;
import net.rudycharles.lsthmod.Item.ModItem;
import net.rudycharles.lsthmod.Potion.ModPotion;
import net.rudycharles.lsthmod.Registries.ModDataComponents;
import net.rudycharles.lsthmod.Util.ExtraEnchantmentHelper;
import net.rudycharles.lsthmod.Util.ModTag;
import org.apache.commons.lang3.math.Fraction;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SatchelItem extends ProjectileWeaponItem {

    public SatchelItem(Properties pProperties) {
        super(pProperties);
    }

    public int default_amount = 4;

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return null;
    }

    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon) {
        var var1 = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        int elevel1 = EnchantmentHelper.getEnchantmentLevel(var1.getOrThrow(ModEnchantment.FIERY_PREP), shooter);
        SatchelPotion satchelPotion = new SatchelPotion(level, shooter);
        if (elevel1 > 0) {
            ItemStack stack = PotionContents.createItemStack(Items.SPLASH_POTION, ModPotion.BURNING_POTION);
            satchelPotion.setItem(stack);
            return satchelPotion;
        }
        return satchelPotion;
    }

    protected void shoot(ServerLevel level, LivingEntity shooter, ItemStack weapon,
                         float velocity, float inaccuracy,
                         @Nullable LivingEntity target) {
        int amount = weapon.getOrDefault(ModDataComponents.STORED_POTION, 0);
        float f = EnchantmentHelper.processProjectileSpread(level, weapon, shooter, 0.0F);
        var var10000 = EnchantmentHelper.processProjectileCount(level, weapon, shooter, 1);
        float f1 = 2.0F * f / 4;
        float f2 = (float)((amount - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;
        if (amount >= 1) {
            for (int i = 0; i < var10000; ++i) {
                float f4 = f2 + f3 * (float) ((i + 1) / 2) * f1;
                f3 = -f3;
                Projectile projectile = this.createProjectile(level, shooter, weapon);
                this.shootProjectile(shooter, projectile, i, velocity, inaccuracy, f4, target);
                level.addFreshEntity(projectile);
                level.playSound(null,
                        shooter.getX(),
                        shooter.getY(),
                        shooter.getZ(),
                        SoundEvents.BUNDLE_REMOVE_ONE,
                        shooter.getSoundSource(),
                        1.0F,
                        1.0f);
            }
        }
    }

    private static Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
        Vector3f vector3f = distance.toVector3f().normalize();
        Vector3f vector3f1 = new Vector3f(vector3f).cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double)vector3f1.lengthSquared() <= 1.0E-7) {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            vector3f1 = new Vector3f(vector3f).cross(vec3.toVector3f());
        }

        Vector3f vector3f2 = new Vector3f(vector3f).rotateAxis((float) (Math.PI / 2), vector3f1.x, vector3f1.y, vector3f1.z);
        return new Vector3f(vector3f).rotateAxis(angle * (float) (Math.PI / 180.0), vector3f2.x, vector3f2.y, vector3f2.z);
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        Vector3f vector3f;
        if (target != null) {
            double d0 = target.getX() - shooter.getX();
            double d1 = target.getZ() - shooter.getZ();
            double d2 = Math.sqrt(d0 * d0 + d1 * d1);
            double d3 = target.getY(0.3333333333333333) - projectile.getY() + d2 * 0.2F;
            vector3f = getProjectileShotVector(shooter, new Vec3(d0, d3, d1), angle);
        } else {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = new Quaternionf().setAngleAxis((double)(angle * (float) (Math.PI / 180.0)), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);
            vector3f = vec31.toVector3f().rotate(quaternionf);
        }

        projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocity, inaccuracy);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pHand) {
        ItemStack itemstack = player.getItemInHand(pHand);
        int amount = itemstack.getOrDefault(ModDataComponents.STORED_POTION, 0);
        if (level instanceof ServerLevel serverlevel) {
            if (amount >= 1) {
                this.shoot(serverlevel,
                        player,
                        itemstack,
                        0.75F,
                        0.0f,
                        null);
                itemstack.hurtAndBreak(this.getDurabilityUse(itemstack), player, player.getEquipmentSlotForItem(itemstack));
                if (!player.hasInfiniteMaterials()) {
                    itemstack.set(ModDataComponents.STORED_POTION, amount - 1);
                }
            } else {
                level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.CONDUIT_ACTIVATE,
                        player.getSoundSource(),
                        2.0F,
                        1.0f);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 0;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        int amount = stack.getOrDefault(ModDataComponents.STORED_POTION, 0);
        return amount > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int amount = stack.getOrDefault(ModDataComponents.STORED_POTION, 0);
        Fraction fraction = switch (amount) {
            case 1,5 -> Fraction.ONE_QUARTER;
            case 2,6 -> Fraction.ONE_HALF;
            case 3,7 -> Fraction.THREE_QUARTERS;
            case 4,8 -> Fraction.ONE;
            default -> Fraction.ZERO;
        };
        return Math.min(1 + Mth.mulAndTruncate(fraction, 12), 13);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return stack.get(ModDataComponents.STORED_POTION) > 4 ? 13107200 : 13133055;
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 4;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity, InteractionHand hand) {
        return super.onEntitySwing(stack, entity, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        int tick = stack.getOrDefault(ModDataComponents.TICK, 1);
        int count = stack.getOrDefault(ModDataComponents.STORED_POTION, 0);
        stack.set(ModDataComponents.TICK, tick + 1);
        if (entity instanceof LivingEntity livingEntity) {
            int elevel = ExtraEnchantmentHelper.getMaximumCharge(stack, entity);
            if (count < default_amount + elevel && tick % 120 == 0) {
                stack.set(ModDataComponents.STORED_POTION, count + 1);
                stack.set(ModDataComponents.TICK, 1);
            }
        }
    }
}
