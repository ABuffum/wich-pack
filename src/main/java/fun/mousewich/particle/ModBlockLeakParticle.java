package fun.mousewich.particle;

import fun.mousewich.ModBase;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

@Environment(value=EnvType.CLIENT)
public class ModBlockLeakParticle extends SpriteBillboardParticle {
	private final Fluid fluid;
	protected boolean obsidianTear;
	ModBlockLeakParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
		super(world, x, y, z);
		this.setBoundingBoxSpacing(0.01f, 0.01f);
		this.gravityStrength = 0.06f;
		this.fluid = fluid;
	}
	protected Fluid getFluid() { return this.fluid; }

	@Override
	public ParticleTextureSheet getType() { return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE; }

	@Override
	public int getBrightness(float tint) {
		if (this.obsidianTear) return 240;
		return super.getBrightness(tint);
	}

	@Override
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		this.updateAge();
		if (this.dead) return;
		this.velocityY -= this.gravityStrength;
		this.move(this.velocityX, this.velocityY, this.velocityZ);
		this.updateVelocity();
		if (this.dead) return;
		this.velocityX *= 0.98f;
		this.velocityY *= 0.98f;
		this.velocityZ *= 0.98f;
		if (this.fluid == Fluids.EMPTY) return;
		BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
		FluidState fluidState = this.world.getFluidState(blockPos);
		if (fluidState.getFluid() == this.fluid && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos))) this.markDead();
	}
	protected void updateAge() { if (this.maxAge-- <= 0) this.markDead(); }
	protected void updateVelocity() { }

	@Environment(EnvType.CLIENT)
	public static class LandingObsidianBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;
		public LandingObsidianBloodFactory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			ModBlockLeakParticle blockLeakParticle = new ModBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
			blockLeakParticle.obsidianTear = true;
			blockLeakParticle.maxAge = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class FallingObsidianBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;
		public FallingObsidianBloodFactory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			ModBlockLeakParticle blockLeakParticle = new ModBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, ModBase.LANDING_OBSIDIAN_BLOOD);
			blockLeakParticle.obsidianTear = true;
			blockLeakParticle.gravityStrength = 0.01F;
			blockLeakParticle.setColor(1F, 0F, 0F);
			blockLeakParticle.setSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

	@Environment(EnvType.CLIENT)
	public static class DrippingObsidianBloodFactory implements ParticleFactory<DefaultParticleType> {
		protected final SpriteProvider spriteProvider;
		public DrippingObsidianBloodFactory(SpriteProvider spriteProvider) { this.spriteProvider = spriteProvider; }
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			ModBlockLeakParticle.Dripping dripping = new ModBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, ModBase.FALLING_OBSIDIAN_BLOOD);
			dripping.obsidianTear = true;
			dripping.gravityStrength *= 0.01F;
			dripping.maxAge = 100;
			dripping.setColor(1F, 0F, 0F);
			dripping.setSprite(this.spriteProvider);
			return dripping;
		}
	}

	private static ModBlockLeakParticle setCherryLeavesColor(ModBlockLeakParticle particle) {
		particle.setColor(0.937f, 0.655f, 0.804f);
		return particle;
	}

	public static SpriteBillboardParticle createDrippingCherryLeaves(DefaultParticleType ignoredType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return setCherryLeavesColor(new Dripping(world, x, y, z, Fluids.EMPTY, ModBase.FALLING_CHERRY_LEAVES_PARTICLE));
	}

	public static SpriteBillboardParticle createFallingCherryLeaves(DefaultParticleType ignoredType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		ContinuousFalling continuousFalling = new ContinuousFalling(world, x, y, z, Fluids.EMPTY, ModBase.LANDING_CHERRY_LEAVES_PARTICLE);
		continuousFalling.gravityStrength = 0.005f;
		return setCherryLeavesColor(continuousFalling);
	}

	public static SpriteBillboardParticle createLandingCherryLeaves(DefaultParticleType ignoredType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		return setCherryLeavesColor(new Landing(world, x, y, z, Fluids.EMPTY));
	}

	@Environment(value= EnvType.CLIENT)
	static class Dripping extends ModBlockLeakParticle {
		private final ParticleEffect nextParticle;

		Dripping(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
			super(world, x, y, z, fluid);
			this.nextParticle = nextParticle;
			this.gravityStrength *= 0.02f;
			this.maxAge = 40;
		}

		@Override
		protected void updateAge() {
			if (this.maxAge-- <= 0) {
				this.markDead();
				this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
			}
		}

		@Override
		protected void updateVelocity() {
			this.velocityX *= 0.02;
			this.velocityY *= 0.02;
			this.velocityZ *= 0.02;
		}
	}
	@Environment(value=EnvType.CLIENT)
	static class ContinuousFalling extends Falling {
		protected final ParticleEffect nextParticle;

		ContinuousFalling(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
			super(world, x, y, z, fluid);
			this.nextParticle = nextParticle;
		}

		@Override
		protected void updateVelocity() {
			if (this.onGround) {
				this.markDead();
				this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
			}
		}
	}
	@Environment(value=EnvType.CLIENT)
	static class Landing extends ModBlockLeakParticle {
		Landing(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
			super(clientWorld, d, e, f, fluid);
			this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
		}
	}
	@Environment(value=EnvType.CLIENT)
	static class Falling extends ModBlockLeakParticle {
		Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
			this(clientWorld, d, e, f, fluid, (int)(64.0 / (Math.random() * 0.8 + 0.2)));
		}

		Falling(ClientWorld world, double x, double y, double z, Fluid fluid, int maxAge) {
			super(world, x, y, z, fluid);
			this.maxAge = maxAge;
		}

		@Override
		protected void updateVelocity() {
			if (this.onGround) {
				this.markDead();
			}
		}
	}
}