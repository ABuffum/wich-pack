package fun.wich.haven.block.tnt;

import fun.wich.block.tnt.ModTntBlock;
import fun.wich.haven.HavenMod;
import fun.wich.haven.entity.tnt.SharpTntEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class SharpTntBlock extends ModTntBlock {
	public SharpTntBlock(Settings settings) { super(settings); }
	@Override
	public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClient) {
			SharpTntEntity tntEntity = new SharpTntEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, explosion.getCausingEntity());
			int i = tntEntity.getFuse();
			tntEntity.setFuse((short)(world.random.nextInt(i / 4) + i / 8));
			world.spawnEntity(tntEntity);
		}
	}
	@Override
	protected void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter) {
		if (!world.isClient) {
			SharpTntEntity tntEntity = new SharpTntEntity(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, igniter);
			world.spawnEntity(tntEntity);
			world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), HavenMod.SHARP_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
		}
	}
}
