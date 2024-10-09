package net.rudycharles.lsthmod.Registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,Lsthmod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_POTION =
            register("stored_potion", integerBuilder -> integerBuilder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> TICK =
            register("satchel_tick", integerBuilder -> integerBuilder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_XP =
            register("stored_xp", integerBuilder -> integerBuilder.persistent(ExtraCodecs.POSITIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>>
    register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPE.register(name, () -> builder.apply(DataComponentType.builder()).build());
    };

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPE.register(eventBus);
    }
}
