package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Item.ModItem;

import java.util.Objects;
import java.util.function.Supplier;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Lsthmod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItem.CONDENSED_OPAL.get());
        basicItem(ModItem.OPAL.get());
        basicItem(ModItem.MAGIC_POTION.get());
        basicItem(ModItem.SATCHEL.get());
        basicItem(ModItem.ENCHANTED_CLOTH.get());
        basicItem(ModItem.LIGHTNING_BOTTLE.get());

        HandHeldItem(ModItem.DESERT_WANDERER_PICKAXE.get());
        HandHeldItem(ModItem.DESERT_WANDERER_MULTITOOL.get());
        HandHeldItem(ModItem.DESERT_WANDERER_SWORD.get());
    }

    public ItemModelBuilder HandHeldItem(Item item) {
        return HandHeldItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }

    public ItemModelBuilder HandHeldItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()));
    }
}
