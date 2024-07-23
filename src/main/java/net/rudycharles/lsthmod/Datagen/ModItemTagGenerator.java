package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Item.ModItem;
import net.rudycharles.lsthmod.Util.ModTag;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, Lsthmod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTag.Items.POTION_SLINGERS).add(ModItem.SATCHEL.get());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(ModItem.SATCHEL.get());
        this.tag(ItemTags.VANISHING_ENCHANTABLE).add(ModItem.SATCHEL.get());
        this.tag(ModTag.Items.POTION_AMMO)
                .add(Items.SPLASH_POTION)
                .add(Items.LINGERING_POTION)
                .add(ModItem.LIGHTNING_BOTTLE.get());

        this.tag(ItemTags.PICKAXES).add(ModItem.DESERT_WANDERER_PICKAXE.get());
        this.tag(ItemTags.SHOVELS).add(ModItem.DESERT_WANDERER_MULTITOOL.get());
        this.tag(ItemTags.SWORDS).add(ModItem.DESERT_WANDERER_SWORD.get());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
