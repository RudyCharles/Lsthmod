package net.rudycharles.lsthmod.Util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.rudycharles.lsthmod.Registries.ModDataComponents;
import net.rudycharles.lsthmod.Registries.ModEnchantmentEffectComponents;
import org.apache.commons.lang3.mutable.MutableFloat;

import static net.minecraft.world.item.enchantment.EnchantmentHelper.runIterationOnItem;

public class ExtraEnchantmentHelper {
    public static int getMaximumCharge(ItemStack itemstack, Entity entity) {
        MutableFloat mutablefloat = new MutableFloat(0.0F);
        runIterationOnItem(itemstack, (holder, level) ->
            holder.value().modifyUnfilteredValue(ModEnchantmentEffectComponents.MAX_POTION.get(), entity.getRandom(), level, mutablefloat));
        return Math.max(0, mutablefloat.intValue());
    }

    public static int getPotionRadius(ItemStack itemstack, Entity entity, ServerLevel serverLevel) {
        MutableFloat mutablefloat = new MutableFloat(0.0F);
        runIterationOnItem(itemstack, (holder, level) ->
                holder.value().modifyEntityFilteredValue(ModEnchantmentEffectComponents.POTION_RADIUS.get(),serverLevel, level, itemstack, entity, mutablefloat));
        return Math.max(0, mutablefloat.intValue());
    }
}
