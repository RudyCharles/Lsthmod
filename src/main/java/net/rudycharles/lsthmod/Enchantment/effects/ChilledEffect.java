package net.rudycharles.lsthmod.Enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.rudycharles.lsthmod.Lsthmod;

import java.util.function.Supplier;

public record ChilledEffect(LevelBasedValue Duration) implements EnchantmentEntityEffect {

    public static final MapCodec<ChilledEffect> CODEC = RecordCodecBuilder.mapCodec((p_346379_) ->
            p_346379_.group(LevelBasedValue.CODEC.fieldOf("duration").forGetter(ChilledEffect::Duration))
            .apply(p_346379_, ChilledEffect::new));

    @Override
    public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        entity.setTicksFrozen((int) Math.ceil(Duration.calculate(i)));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }

}
