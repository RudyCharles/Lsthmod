package net.rudycharles.lsthmod.Datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Item.ModItem;
import net.rudycharles.lsthmod.Util.ModTag;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItem.CONDENSED_OPAL.get(), 2)
                .pattern("###")
                .pattern("#E#")
                .pattern("###")
                .define('#', ModItem.OPAL.get())
                .define('E', ModItem.CONDENSED_OPAL.get())
                .unlockedBy("has_opal", inventoryTrigger(ItemPredicate.Builder.item().of(ModItem.OPAL.get()).build()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItem.DESERT_WANDERER_PICKAXE.get(), 1)
                .pattern("GGO")
                .pattern(" PG")
                .pattern("S G")
                .define('O', ModItem.CONDENSED_OPAL.get())
                .define('P', Items.GOLDEN_PICKAXE)
                .define('G', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_opal", inventoryTrigger(ItemPredicate.Builder.item().of(ModItem.CONDENSED_OPAL.get()).build()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItem.DESERT_WANDERER_SWORD.get(), 1)
                .pattern("  G")
                .pattern("OG ")
                .pattern("SO ")
                .define('O', ModItem.CONDENSED_OPAL.get())
                .define('G', Items.GOLD_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_opal", inventoryTrigger(ItemPredicate.Builder.item().of(ModItem.CONDENSED_OPAL.get()).build()))
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItem.SATCHEL.get())
                .pattern("LCO")
                .pattern("C C")
                .pattern("CCC")
                .define('L', Items.LEAD)
                .define('C', ModItem.ENCHANTED_CLOTH.get())
                .define('O',ModItem.CONDENSED_OPAL.get())
                .unlockedBy("has_condensed_opal", inventoryTrigger(ItemPredicate.Builder.item().of(ModItem.CONDENSED_OPAL.get()).build()))
                .showNotification(true)
                .save(pRecipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ModItem.LIGHTNING_BOTTLE.get(), 4)
                .pattern(" C ")
                .pattern("CBC")
                .pattern(" C ")
                .define('C', Items.COPPER_INGOT)
                .define('B', Items.GLASS_BOTTLE)
                .showNotification(true)
                .unlockedBy("has_copper", inventoryTrigger(ItemPredicate.Builder.item().of(Items.COPPER_INGOT).build()))
                .save(pRecipeOutput);
    }
}
