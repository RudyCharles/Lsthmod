package net.rudycharles.lsthmod.Potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Effect.ModEffect;

public class ModPotion {
    public static final DeferredRegister<Potion> POTION =
            DeferredRegister.create(Registries.POTION, Lsthmod.MODID);

    public static void register(IEventBus eventBus) {
        POTION.register(eventBus);
    }
}
