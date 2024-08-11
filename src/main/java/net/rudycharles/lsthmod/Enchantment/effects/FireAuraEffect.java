package net.rudycharles.lsthmod.Enchantment.effects;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record FireAuraEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<FireAuraEffect> CODEC = MapCodec.unit(FireAuraEffect::new);

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity livingEntity) {
            var owner = enchantedItemInUse.owner();
            assert owner != null;
            double d3 = owner.position().x - livingEntity.position().x;
            double d4 = owner.position().z - livingEntity.position().z ;
            if (livingEntity.distanceToSqr(owner) >= 6) return;
            livingEntity.igniteForSeconds(60);
            livingEntity.knockback(0.2, d3, d4);
        }
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
