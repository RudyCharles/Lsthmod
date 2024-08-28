package net.rudycharles.lsthmod.Registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE =
            DeferredRegister.createDataComponents(Lsthmod.MODID);

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
