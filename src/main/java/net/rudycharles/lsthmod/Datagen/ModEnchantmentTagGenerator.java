package net.rudycharles.lsthmod.Datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rudycharles.lsthmod.Lsthmod;
import net.rudycharles.lsthmod.enchantment.ModEnchantment;
import net.rudycharles.lsthmod.Util.ModTag;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagGenerator extends EnchantmentTagsProvider {
    public ModEnchantmentTagGenerator(PackOutput p_341044_, CompletableFuture<HolderLookup.Provider> p_341146_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_341044_, p_341146_, Lsthmod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTag.Enchantments.CLUSTER_BOMB).add(ModEnchantment.CLUSTER_BOMB);
        this.tag(ModTag.Enchantments.FIELD_PREP).add(ModEnchantment.FIELD_PREP);

        this.tag(EnchantmentTags.NON_TREASURE)
                .add(ModEnchantment.CLUSTER_BOMB,
                        ModEnchantment.MAGIC_PROTECTION,
                        ModEnchantment.DEEPER_RESERVE,
                        ModEnchantment.ENFORCER,
                        ModEnchantment.SLAYER,
                        ModEnchantment.FIELD_PREP,
                        ModEnchantment.POWERFUL_POTION,
                        ModEnchantment.LARGER_POTION
                        );
        this.tag(EnchantmentTags.DAMAGE_EXCLUSIVE).add(
                ModEnchantment.ENFORCER,
                ModEnchantment.SLAYER
        );

        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .remove(Enchantments.SHARPNESS);
        this.tag(EnchantmentTags.TRADEABLE).remove(Enchantments.SHARPNESS);
        this.tag(EnchantmentTags.ON_RANDOM_LOOT).remove(Enchantments.SHARPNESS);
        this.tag(EnchantmentTags.ON_TRADED_EQUIPMENT).remove(Enchantments.SHARPNESS);
    }

    @Override
    public String getName() {
        return "Enchantment Tags";
    }
}