package net.rudycharles.lsthmod.Event;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.rudycharles.lsthmod.Enchantment.ModEnchantment;
import net.rudycharles.lsthmod.Lsthmod;

@EventBusSubscriber(modid = Lsthmod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvent {
    @SubscribeEvent
    public static void gliding(LivingFallEvent event) {
    }
}
