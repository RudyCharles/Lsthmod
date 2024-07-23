package net.rudycharles.lsthmod.Item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Item.custom.MultitoolItem;
import net.rudycharles.lsthmod.Item.custom.SabreItem;
import net.rudycharles.lsthmod.Item.custom.SatchelItem;
import net.rudycharles.lsthmod.Potion.ModPotion;

import java.util.function.Supplier;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Lsthmod.MODID);

    public static final Supplier<Item> OPAL =
            ITEMS.register("opal",() -> new Item(new Item.Properties()));

    public static final Supplier<Item> CONDENSED_OPAL =
            ITEMS.register("condensed_opal", () -> new Item(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)) );
    public static final Supplier<Item> ENCHANTED_CLOTH =
            ITEMS.register("enchanted_cloth", () -> new Item(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)) );

    public static final Supplier<Item> SATCHEL =
            ITEMS.register("satchel", () -> new SatchelItem(new Item.Properties()
                    .durability(456)
                    .rarity(Rarity.EPIC)
                    .stacksTo(1)
            ));
    public static final Supplier<Item> DESERT_WANDERER_PICKAXE =
            ITEMS.register("desert_wanderer_pickaxe", () -> new PickaxeItem(
                    ModTier.DESERT_WANDERER,
                    new Item.Properties()
                            .attributes(DiggerItem.createAttributes(ModTier.DESERT_WANDERER, 2,-2.4f)
                                    .withModifierAdded(Attributes.MOVEMENT_SPEED,
                                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, "base_movement_speed"),
                                            0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
                            )
                            .rarity(Rarity.EPIC)
            ));
    public static final Supplier<Item> DESERT_WANDERER_MULTITOOL =
            ITEMS.register("desert_wanderer_multitool", () -> new MultitoolItem(
                    ModTier.DESERT_WANDERER,
                    new Item.Properties()
                            .attributes(DiggerItem.createAttributes(ModTier.DESERT_WANDERER, 0,-3.6f)
                                    .withModifierAdded(Attributes.SUBMERGED_MINING_SPEED,
                                            new AttributeModifier(ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, "base_mining_submerge"),
                                                    0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), EquipmentSlotGroup.MAINHAND)
                            )
                            .rarity(Rarity.EPIC).setNoRepair()
            ));
    public static final Supplier<Item> DESERT_WANDERER_SWORD =
            ITEMS.register("desert_sabre", () -> new SabreItem(
                    ModTier.DESERT_WANDERER,
                    new Item.Properties()
                            .attributes(SabreItem.createAttributes(ModTier.DESERT_WANDERER, 4, -2.0f, 1.5f))
                            .rarity(Rarity.EPIC)
            ));

    public static final Supplier<Item> LIGHTNING_BOTTLE =
            ITEMS.register("lightning_bottle", () -> new SplashPotionItem(new Item.Properties()
                    .stacksTo(16)
                    .component(DataComponents.POTION_CONTENTS,
                            new PotionContents(ModPotion.LIGHTNING_POTION))
            ));

    public static final Supplier<Item> MAGIC_POTION =
            ITEMS.register("magic_potion", () -> new SplashPotionItem(new Item.Properties()
                    .stacksTo(16)
                    .component(DataComponents.POTION_CONTENTS,
                            PotionContents.EMPTY)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
