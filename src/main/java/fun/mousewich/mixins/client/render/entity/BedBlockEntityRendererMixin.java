package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.block.basic.ModBedBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlockEntityRenderer.class)
public abstract class BedBlockEntityRendererMixin {
	@Inject(method="render(Lnet/minecraft/block/entity/BedBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at=@At("HEAD"), cancellable = true)
	private void Render(BedBlockEntity bedBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
		if (_render(bedBlockEntity, matrixStack, vertexConsumerProvider, i, j)) ci.cancel();
	}

	private boolean _render(BedBlockEntity bedBlockEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
		World world = bedBlockEntity.getWorld();
		Block block = (world != null ? world.getBlockState(bedBlockEntity.getPos()) : bedBlockEntity.getCachedState()).getBlock();
		SpriteIdentifier spriteIdentifier;
		if (block instanceof ModBedBlock bed) spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, bed.GetTexture());
		else return false;
		BedBlockEntityRenderer bber = (BedBlockEntityRenderer)(Object)this;
		BedBlockEntityRendererAccessor bbera = (BedBlockEntityRendererAccessor)bber;
		BedBlockEntityRendererInvoker bberi = (BedBlockEntityRendererInvoker)bber;
		if (world != null) {
			BlockState blockState = bedBlockEntity.getCachedState();
			DoubleBlockProperties.PropertySource<? extends BedBlockEntity> propertySource = DoubleBlockProperties.toPropertySource(BlockEntityType.BED, BedBlock::getBedPart, BedBlock::getOppositePartDirection, ChestBlock.FACING, blockState, world, bedBlockEntity.getPos(), (worldx, pos) -> false);
			int k = propertySource.apply(new LightmapCoordinatesRetriever<BedBlockEntity>()).get(i);
			bberi.InvokeRenderPart(matrixStack, vertexConsumerProvider, blockState.get(BedBlock.PART) == BedPart.HEAD ? bbera.getBedHead() : bbera.getBedFoot(), blockState.get(BedBlock.FACING), spriteIdentifier, k, j, false);
		}
		else {
			bberi.InvokeRenderPart(matrixStack, vertexConsumerProvider, bbera.getBedHead(), Direction.SOUTH, spriteIdentifier, i, j, false);
			bberi.InvokeRenderPart(matrixStack, vertexConsumerProvider, bbera.getBedFoot(), Direction.SOUTH, spriteIdentifier, i, j, true);
		}
		return true;
	}
}
