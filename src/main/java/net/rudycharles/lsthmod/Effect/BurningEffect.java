package net.rudycharles.lsthmod.Effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.rudycharles.lsthmod.Potion.ModPotion;

import java.util.function.Predicate;


public class BurningEffect extends MobEffect {

    public static final Predicate<FluidState> liquid = FluidState::isEmpty;

    protected BurningEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        livingEntity.setRemainingFireTicks(200*(1+amplifier));
        if (level instanceof ServerLevel serverLevel) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(serverLevel, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            areaEffectCloud.setDuration(100);
            areaEffectCloud.setRadius(2);
            areaEffectCloud.setPotionContents(new PotionContents(ModPotion.BURNING_POTION));
            serverLevel.addFreshEntity(areaEffectCloud);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 40 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
