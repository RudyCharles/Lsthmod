package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Lsthmod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ModBlockTagGenerator blockTagsProvider =
                new ModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper);
        ModDatapackProvider modDataPackProvider =
                new ModDatapackProvider(packOutput, lookupProvider);

        generator.addProvider(event.includeServer(), modDataPackProvider);

        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModEnchantmentTagGenerator(packOutput, modDataPackProvider.getRegistryProvider(),existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagGenerator(packOutput,lookupProvider, blockTagsProvider.contentsGetter(),existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagGenerator(packOutput,lookupProvider,existingFileHelper));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput,lookupProvider));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput, lookupProvider));

        generator.addProvider(event.includeClient(),new ModItemModelProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeClient(),new ModBlockStateProvider(packOutput,existingFileHelper));

        generator.addProvider(event.includeClient(), new ModGlobalLootModifierProvider(packOutput, lookupProvider));
    }
}
