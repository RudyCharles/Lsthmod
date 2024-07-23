package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Block.ModBlock;
import net.rudycharles.lsthmod.Item.ModItem;
import net.rudycharles.lsthmod.Loot.AddItemModifier;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Lsthmod.MODID);
    }

    @Override
    protected void start() {
        add("opal_from_stone", new AddItemModifier(
               new LootItemCondition[] {
                       LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.STONE).build(),
                       LootItemRandomChanceCondition.randomChance(0.1f).build()
               },
                ModItem.OPAL.get()
        ));
        add("copal_from_ore", new AddItemModifier(
                new LootItemCondition[] {
                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlock.OPAL_ORE.get()).build(),
                        LootItemRandomChanceCondition.randomChance(0.1f).build()
                },
                ModItem.CONDENSED_OPAL.get()
        ));
        add("opal_from_treasure", new AddItemModifier(
                new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/desert_pyramid")).build(),
                        LootItemRandomChanceCondition.randomChance(0.2f).build()
                },
                ModItem.CONDENSED_OPAL.get()
        ));
        add("multitool_from_treasure", new AddItemModifier(
                new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/desert_pyramid")).build(),
                        LootItemRandomChanceCondition.randomChance(0.1f).build()
                },
                ModItem.DESERT_WANDERER_MULTITOOL.get()
        ));
        add("cloth_from_treasure", new AddItemModifier(
                new LootItemCondition[]{
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/desert_pyramid")).build(),
                        LootItemRandomChanceCondition.randomChance(0.75f).build()
                },
                ModItem.ENCHANTED_CLOTH.get()
        ));
    }
}
