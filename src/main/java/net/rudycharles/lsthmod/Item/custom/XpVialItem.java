package net.rudycharles.lsthmod.Item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.rudycharles.lsthmod.Registries.ModDataComponents;

import java.util.List;

public class XpVialItem extends Item {

    public static final int MAX_XP = 5000;

    public XpVialItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        int XP = player.totalExperience;
        ItemStack stack = player.getItemInHand(usedHand);
        Integer stored_xp = stack.getComponents().get(ModDataComponents.STORED_XP.get());

        if (player.isCrouching()) {
            player.giveExperiencePoints(stored_xp);
            level.playSound(null, player, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1, 3);
            return InteractionResultHolder.consume(stack);
        } else {
            switch ((XP < MAX_XP) ? 0 :
                    (stored_xp + XP >= MAX_XP && stored_xp < MAX_XP) ? 1 : 2) {
                case 0:
                    level.playSound(null, player, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 1);
                    stack.set(ModDataComponents.STORED_XP, stored_xp + XP);
                    player.giveExperiencePoints(-XP);
                case 1:
                    level.playSound(null, player, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 1);
                    stack.set(ModDataComponents.STORED_XP, MAX_XP);
                    player.giveExperiencePoints(-(MAX_XP - stored_xp));
                case 2:
            }
            return InteractionResultHolder.success(stack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.get(ModDataComponents.STORED_XP.get()) != null) {
            tooltipComponents.add(Component.literal("Stored Experience: " + stack.get(ModDataComponents.STORED_XP) + "/5000").withStyle(ChatFormatting.WHITE));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

