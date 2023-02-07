package fun.mousewich.mixins.entity;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
	protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="interact", at = @At("HEAD"), cancellable = true)
	public final void interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (this.isAlive()) {
			MobEntity me = (MobEntity)(Object)this;
			MobEntityInvoker mei = (MobEntityInvoker)me;
			if (me.getHoldingEntity() != player) {
				ActionResult actionResult = mei.InvokeInteractWithItem(player, hand);
				if (actionResult.isAccepted()) cir.setReturnValue(actionResult);
				else {
					actionResult = mei.InvokeInteractMob(player, hand);
					if (actionResult.isAccepted()) {
						this.emitGameEvent(ModGameEvent.ENTITY_INTERACT);
						cir.setReturnValue(actionResult);
					}
					cir.setReturnValue(super.interact(player, hand));
				}
			}
		}
	}
}
