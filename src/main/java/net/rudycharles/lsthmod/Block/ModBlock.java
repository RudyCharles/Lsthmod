package net.rudycharles.lsthmod.Block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.*;
import net.rudycharles.lsthmod.Item.ModItem;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.function.Supplier;

public class ModBlock {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, Lsthmod.MODID);

    public static final Supplier<Block> OPAL_ORE =
            registerBlock("opal_ore", () -> new DropExperienceBlock(
                    UniformInt.of(12,25),
                    BlockBehaviour.Properties.of()
                    .destroyTime(3)
                    .explosionResistance(6.0f)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()
            ));

    private static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        Supplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(String name, Supplier<T> block) {
        return ModItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
