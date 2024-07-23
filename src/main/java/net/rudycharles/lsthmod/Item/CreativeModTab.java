package net.rudycharles.lsthmod.Item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Block.ModBlock;

import java.util.function.Supplier;

public class CreativeModTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lsthmod.MODID);

    public static final Supplier<CreativeModeTab> MOD_TAB_ONE =
            CREATIVE_MODE_TABS.register("first_tab",() ->
                    CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItem.OPAL.get()))
                            .title(Component.translatable("creativetab.tabone"))
                            .displayItems((pParameters, pOutput) -> {
                                pOutput.accept(ModItem.OPAL.get());
                                pOutput.accept(ModItem.CONDENSED_OPAL.get());

                                pOutput.accept(ModItem.DESERT_WANDERER_PICKAXE.get());
                                pOutput.accept(ModItem.DESERT_WANDERER_MULTITOOL.get());
                                pOutput.accept(ModItem.DESERT_WANDERER_SWORD.get());

                                pOutput.accept(ModBlock.OPAL_ORE.get());

                                pOutput.accept(ModItem.ENCHANTED_CLOTH.get());
                                pOutput.accept(ModItem.SATCHEL.get());
                                pOutput.accept(ModItem.LIGHTNING_BOTTLE.get());
                            })
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
