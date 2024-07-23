package net.rudycharles.lsthmod.Entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Entity.custom.SatchelPotion;

import java.util.function.Supplier;

public class ModEntity {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(Registries.ENTITY_TYPE, Lsthmod.MODID);

    public static final Supplier<EntityType<SatchelPotion>> SATCHEL_POTION =
            ENTITY_TYPE.register("satchel_potion", () -> EntityType.Builder.<SatchelPotion>of(SatchelPotion::new, MobCategory.MISC)
                    .sized(0.25f,0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("satchel_potion"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}
