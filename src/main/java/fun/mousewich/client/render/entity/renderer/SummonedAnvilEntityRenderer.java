package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.entity.SummonedAnvilEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@Environment(value= EnvType.CLIENT)
public class SummonedAnvilEntityRenderer extends EntityRenderer<SummonedAnvilEntity> {
	public SummonedAnvilEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.shadowRadius = 0.5f;
	}

	@Override
	public void render(SummonedAnvilEntity fallingBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		BlockState blockState = Blocks.ANVIL.getDefaultState();
		if (blockState.getRenderType() != BlockRenderType.MODEL) return;
		World world = fallingBlockEntity.getWorld();
		if (blockState == world.getBlockState(fallingBlockEntity.getBlockPos()) || blockState.getRenderType() == BlockRenderType.INVISIBLE) return;
		matrixStack.push();
		BlockPos blockPos = new BlockPos(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
		matrixStack.translate(-0.5, 0.0, -0.5);
		BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
		blockRenderManager.getModelRenderer().render(world, blockRenderManager.getModel(blockState), blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, new Random(), blockState.getRenderingSeed(fallingBlockEntity.getFallingBlockPos()), OverlayTexture.DEFAULT_UV);
		matrixStack.pop();
		super.render(fallingBlockEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Identifier getTexture(SummonedAnvilEntity fallingBlockEntity) { return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE; }
}