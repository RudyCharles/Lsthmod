package net.rudycharles.lsthmod.Entity.attribute;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;

public class ModAttribute {

    public static final DeferredRegister<Attribute> ATTRIBUTE =
            DeferredRegister.create(Registries.ATTRIBUTE, Lsthmod.MODID);

    public static final Holder<Attribute> POTION_SPLASH =
            ATTRIBUTE.register("player.splash_radius", () ->
                    new RangedAttribute("player.name.splash_radius",4.0f,0f,Double.MAX_VALUE).setSyncable(true));

    public static final Holder<Attribute> POTION_VELOCITY =
            ATTRIBUTE.register("player.velocity", () ->
                    new RangedAttribute("player.name.velocity",4.0f,0f,Double.MAX_VALUE).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTE.register(eventBus);
    }
}
