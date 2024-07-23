package net.rudycharles.lsthmod.Item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.rudycharles.lsthmod.Util.ModTag;

public class ModTier {
    public static final Tier DESERT_WANDERER =
            new SimpleTier(
                    ModTag.Blocks.INCORRECT_FOR_DESERT_WANDERER,
                    230,
                    15,
                    1.0f,
                    22,
                    () -> Ingredient.of(ModItem.CONDENSED_OPAL.get())
            );
}
