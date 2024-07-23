package net.rudycharles.lsthmod.enchantment;

import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.ApplyMobEffect;
import net.minecraft.world.item.enchantment.effects.SetValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Util.ModTag;

public class ModEnchantment {

    public static final ResourceKey<Enchantment> MAGIC_PROTECTION = key("magic_protection");
    public static final ResourceKey<Enchantment> POWERFUL_POTION = key("powerful_potion");
    public static final ResourceKey<Enchantment> LARGER_POTION = key("larger_potion");
    public static final ResourceKey<Enchantment> CLUSTER_BOMB = key("cluster_bomb");
    public static final ResourceKey<Enchantment> FIELD_PREP = key("field_prep");
    public static final ResourceKey<Enchantment> DEEPER_RESERVE = key("deeper_pocket");
    public static final ResourceKey<Enchantment> ENFORCER = key("enforcer");
    public static final ResourceKey<Enchantment> SLAYER = key("slayer");

    public static void bootstrap(BootstrapContext<Enchantment> bootstrapContext) {
        register(bootstrapContext, MAGIC_PROTECTION,
                Enchantment.enchantment(Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                                        10,
                                        4,
                                        Enchantment.dynamicCost(1, 11),
                                        Enchantment.dynamicCost(12, 11),
                                        1,
                                        EquipmentSlotGroup.ARMOR
                                )
                        )
                        .exclusiveWith(bootstrapContext.lookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.ARMOR_EXCLUSIVE))
                        .withEffect(EnchantmentEffectComponents.DAMAGE_PROTECTION,
                                new AddValue(LevelBasedValue.perLevel(2.0f)),
                                DamageSourceCondition.hasDamageSource(
                                        DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.WITCH_RESISTANT_TO))
                                                .tag(TagPredicate.isNot(DamageTypeTags.BYPASSES_INVULNERABILITY)))
                        )
        );
        register(bootstrapContext, ENFORCER,
                Enchantment.enchantment(Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(25, 8),
                                        2,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(bootstrapContext.lookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(2.5F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityTypeTags.RAIDERS))
                                )
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffect(
                                        HolderSet.direct(MobEffects.WEAKNESS),
                                        LevelBasedValue.constant(1.5F),
                                        LevelBasedValue.perLevel(1.5F, 0.5F),
                                        LevelBasedValue.constant(3.0F),
                                        LevelBasedValue.constant(3.0F)
                                ),
                                LootItemEntityPropertyCondition.hasProperties(
                                                LootContext.EntityTarget.THIS,
                                                EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityTypeTags.RAIDERS))
                                        )
                                        .and(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true)))
                        )
        );
        register(bootstrapContext, SLAYER,
                Enchantment.enchantment(Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                        5,
                                        5,
                                        Enchantment.dynamicCost(5, 8),
                                        Enchantment.dynamicCost(25, 8),
                                        2,
                                        EquipmentSlotGroup.MAINHAND
                                )
                        )
                        .exclusiveWith(bootstrapContext.lookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.DAMAGE,
                                new AddValue(LevelBasedValue.perLevel(1.5F)),
                                LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(ModTag.EntityTypes.BOSS))
                                )
                        )
        );
        register(bootstrapContext, DEEPER_RESERVE,
                Enchantment.enchantment(Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ModTag.Items.POTION_SLINGERS),
                                        1,
                                        1,
                                        Enchantment.constantCost(20),
                                        Enchantment.constantCost(50),
                                        8,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.AMMO_USE,
                                new SetValue(LevelBasedValue.constant(0)),
                                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SPLASH_POTION).of(Items.LINGERING_POTION)
                                ))
        );
        register(bootstrapContext, CLUSTER_BOMB,
                Enchantment.enchantment(Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ModTag.Items.POTION_SLINGERS),
                                        5,
                                        5,
                                        Enchantment.constantCost(20),
                                        Enchantment.constantCost(50),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_COUNT,
                                new AddValue(LevelBasedValue.perLevel(1.0F)))
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_SPREAD,
                                new AddValue(LevelBasedValue.perLevel(100.0f)))
        );
        register(bootstrapContext, FIELD_PREP,
                Enchantment.enchantment(Enchantment.definition(
                                bootstrapContext.lookup(Registries.ITEM).getOrThrow(ModTag.Items.POTION_SLINGERS),
                                5,
                                1,
                                Enchantment.constantCost(20),
                                Enchantment.constantCost(50),
                                4,
                                EquipmentSlotGroup.HAND
                        )
                )
        );
        register(bootstrapContext, POWERFUL_POTION,
                Enchantment.enchantment(Enchantment.definition(
                                bootstrapContext.lookup(Registries.ITEM).getOrThrow(ModTag.Items.POTION_SLINGERS),
                                5,
                                3,
                                Enchantment.dynamicCost(5, 8),
                                Enchantment.dynamicCost(25, 8),
                                4,
                                EquipmentSlotGroup.HAND
                        )
                )
        );
        register(bootstrapContext, LARGER_POTION,
                Enchantment.enchantment(Enchantment.definition(
                                bootstrapContext.lookup(Registries.ITEM).getOrThrow(ModTag.Items.POTION_SLINGERS),
                                5,
                                3,
                                Enchantment.dynamicCost(5, 8),
                                Enchantment.dynamicCost(25, 8),
                                4,
                                EquipmentSlotGroup.HAND
                        )
                )
        );
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, name));
    }
}
