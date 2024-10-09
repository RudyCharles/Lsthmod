package net.rudycharles.lsthmod.Enchantment.effects;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.rudycharles.lsthmod.Loot.AddItemModifier;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.function.Supplier;

public class ModEnchantmentEffect {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_EFFECT =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Lsthmod.MODID);
    public static final DeferredRegister<MapCodec<? extends EnchantmentLocationBasedEffect>> LOCATION_EFFECT =
            DeferredRegister.create(Registries.ENCHANTMENT_LOCATION_BASED_EFFECT_TYPE, Lsthmod.MODID);
    public static final DeferredRegister<MapCodec<? extends EnchantmentValueEffect>> VALUE_EFFECT =
            DeferredRegister.create(Registries.ENCHANTMENT_VALUE_EFFECT_TYPE, Lsthmod.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> CHILLED =
            ENTITY_EFFECT.register("chilled",() -> ChilledEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> FIRE_AURA =
            ENTITY_EFFECT.register("fire_aura", () -> FireAuraEffect.CODEC);

    public static void register(IEventBus eventBus) {
        ENTITY_EFFECT.register(eventBus);
        LOCATION_EFFECT.register(eventBus);
        VALUE_EFFECT.register(eventBus);
    }
}
