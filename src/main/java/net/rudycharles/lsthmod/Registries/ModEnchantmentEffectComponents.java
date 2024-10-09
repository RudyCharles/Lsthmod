package net.rudycharles.lsthmod.Registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModEnchantmentEffectComponents {

    public static final DeferredRegister<DataComponentType<?>> ENCHANTMENT_COMPONENT_TYPE =
            DeferredRegister.createDataComponents(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Lsthmod.MODID);

    public static final DeferredHolder<DataComponentType<?>,DataComponentType<EnchantmentValueEffect>> MAX_POTION =
            register("max_potion", p_346197_ -> p_346197_.persistent(EnchantmentValueEffect.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> POTION_RADIUS = register(
        "potion_radius",
        p_345532_ -> p_345532_.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf())
            );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>>
    register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return ENCHANTMENT_COMPONENT_TYPE.register(name, () -> builder.apply(DataComponentType.builder()).build());
    };

    public static void register(IEventBus eventBus) {
        ENCHANTMENT_COMPONENT_TYPE.register(eventBus);
    }
}
