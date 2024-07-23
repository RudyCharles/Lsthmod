package net.rudycharles.lsthmod.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGen generator = event.getGenerator();

    }
}
