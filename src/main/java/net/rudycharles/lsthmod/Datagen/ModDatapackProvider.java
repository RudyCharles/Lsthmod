package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.rudycharles.lsthmod.Enchantment.ModEnchantment;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Registries.ModDataComponents;
import net.rudycharles.lsthmod.Registries.ModEnchantmentEffectComponents;
import net.rudycharles.lsthmod.Worldgen.ModBiomeModifier;
import net.rudycharles.lsthmod.Worldgen.ModConfiguredFeatures;
import net.rudycharles.lsthmod.Worldgen.ModPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Lsthmod.MODID));
    }

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, ModEnchantment::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifier::bootstrap);

    @Override
    public CompletableFuture<HolderLookup.Provider> getRegistryProvider() {
        return super.getRegistryProvider();
    }
}
