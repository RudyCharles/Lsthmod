package net.rudycharles.lsthmod.Client;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.rudycharles.lsthmod.Item.ModItem;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class ModTooltip {
    public static void UpdateTooltip(ItemStack stack, List<Component> list) {
        Consumer<Component> consumer = list::add;
        addAttributeTooltips(stack, consumer);
    }

    private static void addAttributeTooltips(ItemStack stack, Consumer<Component> tooltipAdder) {
        ItemAttributeModifiers itemattributemodifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        if (itemattributemodifiers.showInTooltip()) {
            for (EquipmentSlotGroup equipmentslotgroup : EquipmentSlotGroup.values()) {
                MutableBoolean mutableboolean = new MutableBoolean(true);
                stack.forEachModifier(equipmentslotgroup, (p_348379_, p_348380_) -> {
                    if (mutableboolean.isTrue()) {
                        tooltipAdder.accept(CommonComponents.EMPTY);
                        tooltipAdder.accept(Component.translatable("item.modifiers." + equipmentslotgroup.getSerializedName()).withStyle(ChatFormatting.GRAY));
                        mutableboolean.setFalse();
                    }

                    BluetoGreen(tooltipAdder, null, p_348379_, p_348380_);
                });
            }
        }
    }

    private static void BluetoGreen(Consumer<Component> tooltipAdder, @Nullable Player player, Holder<Attribute> attribute, AttributeModifier modifier) {
        double d0 = modifier.amount();
        boolean flag = false;
        if (player != null) {
            if (modifier.is(ModItem.BASE_SWEEPING_RATIO)) {
                d0 += player.getAttributeBaseValue(Attributes.SWEEPING_DAMAGE_RATIO);
                flag = true;
            }
        }

        double d1;
        if (modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                || modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
            d1 = d0 * 100.0;
        } else if (attribute.is(Attributes.KNOCKBACK_RESISTANCE)) {
            d1 = d0 * 10.0;
        } else {
            d1 = d0;
        }

        if (flag) {
            tooltipAdder.accept(
                    CommonComponents.space()
                            .append(
                                    Component.translatable(
                                            "attribute.modifier.equals." + modifier.operation().id(),
                                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d1),
                                            Component.translatable(attribute.value().getDescriptionId())
                                    )
                            )
                            .withStyle(ChatFormatting.DARK_GREEN)
            );
        }
    }
}
