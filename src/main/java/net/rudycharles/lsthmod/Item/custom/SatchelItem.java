package net.rudycharles.lsthmod.Item.custom;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.rudycharles.lsthmod.Entity.attribute.ModAttribute;
import net.rudycharles.lsthmod.Entity.custom.SatchelPotion;
import net.rudycharles.lsthmod.Enchantment.ModEnchantment;
import net.rudycharles.lsthmod.Item.ModItem;
import net.rudycharles.lsthmod.Util.ModTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SatchelItem extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> POTION = itemStack -> itemStack.is(ModTag.Items.POTION_AMMO);

    public SatchelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return POTION;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 5;
    }

    @Override
    protected void shootProjectile(LivingEntity pShooter, Projectile pProjectile, int pIndex, float pVelocity, float pInaccuracy, float pAngle, @Nullable LivingEntity pTarget) {
            pProjectile.shootFromRotation(pShooter,pShooter.getXRot(),pShooter.getYRot(),0,pVelocity,pInaccuracy);
    }

    @Override
    protected Projectile createProjectile(Level pLevel, LivingEntity pShooter, ItemStack pWeapon, ItemStack pAmmo, boolean pIsCrit) {
        SatchelPotion thrownpotion = new SatchelPotion(pLevel, pShooter);
        if ((pAmmo.is(ModTag.Items.POTION_AMMO) && EnchantmentHelper.hasTag(pWeapon,ModTag.Enchantments.FIELD_PREP))) {
            if (pAmmo != ItemStack.EMPTY) {
                thrownpotion.setItem(pAmmo);
            }
            return thrownpotion;
        }
        return thrownpotion;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        ItemStack stack = pPlayer.getProjectile(itemstack);
        var enchantment = pLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            if (pLevel instanceof ServerLevel serverlevel && !stack.isEmpty()) {
                pLevel.playSound(null,
                        pPlayer.getX(),
                        pPlayer.getY(),
                        pPlayer.getZ(),
                        SoundEvents.BUNDLE_REMOVE_ONE,
                        pPlayer.getSoundSource(),
                        1.0F,
                        1.0f);
                List<ItemStack> list = draw(itemstack, stack, pPlayer);
                if (EnchantmentHelper.hasTag(itemstack, ModTag.Enchantments.CLUSTER_BOMB)) {
                    this.shoot(serverlevel,
                        pPlayer,
                        pPlayer.getUsedItemHand(),
                        itemstack,
                        list,
                            (float) (1 - (EnchantmentHelper.getTagEnchantmentLevel(enchantment.getHolderOrThrow(ModEnchantment.CLUSTER_BOMB), itemstack)* 0.08)),
                            EnchantmentHelper.getTagEnchantmentLevel(enchantment.getHolderOrThrow(ModEnchantment.CLUSTER_BOMB), itemstack)* 10,
                        false,
                        null);

                } else {
                    this.shoot(serverlevel,
                            pPlayer,
                            pPlayer.getUsedItemHand(),
                            itemstack,
                            list,
                            0.75F,
                            0.0F,
                            false,
                            null);
                }
                if (!pPlayer.getAbilities().instabuild) {
                    pPlayer.getCooldowns().addCooldown(this, 10);
                }
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
        if (!pPlayer.hasInfiniteMaterials() && stack.isEmpty()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide);
        }
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }

    @Override
    protected int getDurabilityUse(ItemStack pStack) {
        return 2;
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return ModItem.MAGIC_POTION.get().getDefaultInstance();
    }
}
