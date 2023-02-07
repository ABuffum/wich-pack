package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends SpiderEntity {
	public CaveSpiderEntityMixin(EntityType<? extends CaveSpiderEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_CAVE_SPIDER_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_CAVE_SPIDER_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_CAVE_SPIDER_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_CAVE_SPIDER_STEP, 0.15f, 1.0f);
	}
}
