package net.rudycharles.lsthmod.Potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Effect.ModEffect;

import java.util.function.Supplier;

public class ModPotion {
    public static final DeferredRegister<Potion> POTION =
            DeferredRegister.create(Registries.POTION, Lsthmod.MODID);

    public static final Holder<Potion> BURNING_POTION =
            POTION.register("burning", () -> new Potion(new MobEffectInstance(ModEffect.BURNING_EFFECT,
                    600,
                    0,
                    true,
                    true,
                    false)));

    public static void register(IEventBus eventBus) {
        POTION.register(eventBus);
    }
}
