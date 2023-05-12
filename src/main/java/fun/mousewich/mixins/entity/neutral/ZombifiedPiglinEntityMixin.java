package fun.mousewich.mixins.entity.neutral;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin extends ZombieEntity implements Angerable, EntityWithBloodType {
	public ZombifiedPiglinEntityMixin(World world) { super(world); }
	public ZombifiedPiglinEntityMixin(EntityType<? extends ZombieEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_ZOMBIFIED_PIGLIN_STEP, 0.15f, 1.0f);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.ZOMBIFIED_PIGLIN_BLOOD_TYPE; }
}
