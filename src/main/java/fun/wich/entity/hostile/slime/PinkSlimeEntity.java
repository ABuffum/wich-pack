package fun.wich.entity.hostile.slime;

import fun.wich.ModBase;
import fun.wich.entity.ai.SlimeMoveControl;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.projectile.PinkSlimeBallEntity;
import fun.wich.particle.ModParticleTypes;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class PinkSlimeEntity extends ModSlimeEntity implements RangedAttackMob, EntityWithBloodType {
	public PinkSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new SlimeMoveControl(this);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.remove(this.swimmingGoal);
		this.goalSelector.add(0, this.swimmingGoal);
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 1, 40, 10.0f));
	}

	@Override
	protected ParticleEffect getParticles() { return ModParticleTypes.ITEM_PINK_SLIME; }

	@Override
	public void attack(LivingEntity target, float pullProgress) {
		PinkSlimeBallEntity projectile = new PinkSlimeBallEntity(this.world, this);
		double e = target.getX() - this.getX();
		double f = target.getBodyY(0.3333333333333333) - projectile.getY();
		double g = target.getZ() - this.getZ();
		double h = Math.sqrt(e * e + g * g) * (double)0.2f;
		projectile.setVelocity(e, f + h, g, 1.6f, 12.0f);
		this.playSound(ModSoundEvents.ENTITY_PINK_SLIME_THROW, 1.0f, 0.4f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		this.world.spawnEntity(projectile);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.PINK_SLIME_BLOOD_TYPE; }
}
