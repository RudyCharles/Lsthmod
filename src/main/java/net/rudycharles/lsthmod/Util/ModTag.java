package net.rudycharles.lsthmod.Util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.rudycharles.lsthmod.Lsthmod;

public class ModTag {
    public static class Items {
        public static final TagKey<Item> POTION_SLINGERS = Tag("potion_slingers");
        public static final TagKey<Item> POTION_AMMO = Tag("potion_ammo");

        private static TagKey<Item> Tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> RARE_ORE = Tag("rare_ore");
        public static final TagKey<Block> NEEDS_DESERT_WANDERER = Tag("needs_desert_wanderer");
        public static final TagKey<Block> INCORRECT_FOR_DESERT_WANDERER = Tag("incorrect_for_desert_wanderer");
        public static final TagKey<Block> MINEABLE_MULTITOOL = Tag("mineable/multitool");

        private static TagKey<Block> Tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, name));
        }
    }

    public static class Enchantments {
        public static final TagKey<Enchantment> CLUSTER_BOMB = Tag("cluster_bomb");
        public static final TagKey<Enchantment> FIELD_PREP = Tag("field_prep");

        private static TagKey<Enchantment> Tag(String name) {
            return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> BOSS = Tag("boss_enemies");

        private static TagKey<EntityType<?>> Tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Lsthmod.MODID, name));
        }
    }
}
