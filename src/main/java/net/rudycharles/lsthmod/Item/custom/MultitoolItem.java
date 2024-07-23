package net.rudycharles.lsthmod.Item.custom;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.rudycharles.lsthmod.Util.ModTag;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class MultitoolItem extends DiggerItem {
    public MultitoolItem(Tier pTier, Properties pProperties) {
        super(pTier, ModTag.Blocks.MINEABLE_MULTITOOL, pProperties);
    }
}
