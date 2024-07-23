package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Block.ModBlock;
import net.rudycharles.lsthmod.Util.ModTag;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Lsthmod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTag.Blocks.RARE_ORE).add(ModBlock.OPAL_ORE.get())
                .addTag(Tags.Blocks.ORES_DIAMOND)
                .addTag(Tags.Blocks.ORES_EMERALD)
                .addTag(Tags.Blocks.ORES_NETHERITE_SCRAP)
                .addTag(Tags.Blocks.ORES_GOLD);

        this.tag(ModTag.Blocks.NEEDS_DESERT_WANDERER).addTag(ModTag.Blocks.RARE_ORE);

        this.tag(ModTag.Blocks.INCORRECT_FOR_DESERT_WANDERER)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTag.Blocks.RARE_ORE);

        this.tag(ModTag.Blocks.MINEABLE_MULTITOOL).addTags(
                BlockTags.MINEABLE_WITH_PICKAXE,
                BlockTags.MINEABLE_WITH_AXE,
                BlockTags.MINEABLE_WITH_SHOVEL);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlock.OPAL_ORE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlock.OPAL_ORE.get());
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
