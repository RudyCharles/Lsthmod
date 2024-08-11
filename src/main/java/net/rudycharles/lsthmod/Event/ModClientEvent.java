package net.rudycharles.lsthmod.Event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.rudycharles.lsthmod.Client.ModTooltip;
import net.rudycharles.lsthmod.Lsthmod;

@EventBusSubscriber(modid = Lsthmod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvent {
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ModTooltip.UpdateTooltip(event.getItemStack(), event.getToolTip());
    }
}
