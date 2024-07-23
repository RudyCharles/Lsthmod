package net.rudycharles.lsthmod.Effect;

import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ChargedEffect extends MobEffect {
    protected ChargedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        this.summonLighttning(pLivingEntity.level(), pLivingEntity.getX(), pLivingEntity.getY() + 0.5, pLivingEntity.getZ());
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int p_295629_, int p_295734_) {
        int i = 60 >> p_295734_;
        return i <= 0 || p_295629_ % i == 0;
    }

    private void summonLighttning(Level pLevel, double pX, double pY, double pZ) {
        LightningBolt thunder = EntityType.LIGHTNING_BOLT.create(pLevel);
        assert thunder != null;
        thunder.setDamage(4.0f);
        thunder.moveTo(pX, pY, pZ, pLevel.getRandom().nextFloat() * 360.0F, 0.0F);
        pLevel.addFreshEntity(thunder);
    }
}
