package net.rudycharles.lsthmod.Enchantment;

import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.rudycharles.lsthmod.Enchantment.effects.ChilledEffect;
import net.rudycharles.lsthmod.Enchantment.effects.FireAuraEffect;
import net.rudycharles.lsthmod.Entity.ModEntity;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.Util.ModTag;

public class ModEnchantment {

    public static final ResourceKey<Enchantment> MAGIC_PROTECTION = key("magic_protection");
    public static final ResourceKey<Enchantment> POWERFUL_POTION = key("powerful_potion");
    public static final ResourceKey<Enchantment> LARGER_POTION = key("larger_potion");
    public static final ResourceKey<Enchantment> FAN_FIRE = key("cluster_bomb");
    public static final ResourceKey<Enchantment> FIERY_PREP = key("field_prep");
    public static final ResourceKey<Enchantment> DEEPER_RESERVE = key("deeper_pocket");
    public static final ResourceKey<Enchantment> ENFORCER = key("enforcer");
    public static final ResourceKey<Enchantment> SLAYER = key("slayer");
    public static final ResourceKey<Enchantment> CHILLED = key("chilled");
    public static final ResourceKey<Enchantment> FIRE_AURA = key("fire_aura");

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
        register(bootstrapContext, CHILLED,
                Enchantment.enchantment(
                        Enchantment.definition(
                                bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.FIRE_ASPECT_ENCHANTABLE),
                                bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                2,
                                2,
                                Enchantment.dynamicCost(10, 20),
                                Enchantment.dynamicCost(60, 20),
                                4,
                                EquipmentSlotGroup.MAINHAND)
                        )
                        .exclusiveWith(bootstrapContext.lookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.SMELTS_LOOT))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                AllOf.entityEffects(new ChilledEffect(LevelBasedValue.perLevel(210))),
                                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new ApplyMobEffect(HolderSet.direct(MobEffects.MOVEMENT_SLOWDOWN),
                                        LevelBasedValue.constant(1.5F),
                                        LevelBasedValue.perLevel(1.5F, 0.5F),
                                        LevelBasedValue.constant(3.0F),
                                        LevelBasedValue.constant(3.0F)
                                ),
                                DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().isDirect(true))
                        )
        );
        EntityPredicate.Builder entitypredicate$builder = EntityPredicate.Builder.entity()
                .periodicTick(5)
                .flags(EntityFlagsPredicate.Builder.flags().setIsFlying(false).setOnGround(true))
                .moving(MovementPredicate.horizontalSpeed(MinMaxBounds.Doubles.atLeast(0)));
        register(bootstrapContext, FIRE_AURA,
                Enchantment.enchantment(
                                Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.CHEST_ARMOR),
                                        1,
                                        1,
                                        Enchantment.dynamicCost(10, 20),
                                        Enchantment.dynamicCost(60, 20),
                                        4,
                                        EquipmentSlotGroup.CHEST)
                        )
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.VICTIM,
                                EnchantmentTarget.ATTACKER,
                                new FireAuraEffect()
                        )
                        .withEffect(
                                EnchantmentEffectComponents.TICK,
                                new SpawnParticlesEffect(
                                        ParticleTypes.FLAME,
                                        SpawnParticlesEffect.inBoundingBox(),
                                        SpawnParticlesEffect.offsetFromEntityPosition(0.5F),
                                        SpawnParticlesEffect.movementScaled(-0.2F),
                                        SpawnParticlesEffect.fixedVelocity(ConstantFloat.of(0.15F)),
                                        ConstantFloat.of(1.0F)
                                ),
                                LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, entitypredicate$builder)
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
        );
        register(bootstrapContext, FAN_FIRE,
                Enchantment.enchantment(Enchantment.definition(
                                        bootstrapContext.lookup(Registries.ITEM).getOrThrow(ModTag.Items.POTION_SLINGERS),
                                        5,
                                        2,
                                        Enchantment.constantCost(20),
                                        Enchantment.constantCost(50),
                                        4,
                                        EquipmentSlotGroup.HAND
                                )
                        )
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_COUNT,
                                new AddValue(LevelBasedValue.perLevel(2.0F)))
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_SPREAD,
                                new AddValue(LevelBasedValue.perLevel(15.0f)))
        );
        register(bootstrapContext, FIERY_PREP,
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
