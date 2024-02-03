package fun.wich.mixins.client.render.item;

import fun.wich.ModBase;
import fun.wich.client.render.item.CompassAnglePredicateProvider;
import fun.wich.entity.LastDeathPositionStoring;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPredicateProviderRegistry.class)
public class ModelPredicateProviderRegistryMixin {
	@Inject(method="<clinit>", at = @At("TAIL"))
	private static void RegisterRecoveryCompass(CallbackInfo ci) {
		ModelPredicateProviderRegistry.register(ModBase.RECOVERY_COMPASS, new Identifier("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
			if (entity instanceof LastDeathPositionStoring deathStoring) return deathStoring.getLastDeathPos().orElse(null);
			return null;
		}));
	}
}
