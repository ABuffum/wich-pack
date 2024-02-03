package fun.wich.mixins.entity;

import fun.wich.ModBase;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.village.VillagerType;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerType.class)
public class VillagerTypeMixin {
	@Inject(method="forBiome", at=@At("HEAD"), cancellable = true)
	private static void forBiome(RegistryEntry<Biome> biomeEntry, CallbackInfoReturnable<VillagerType> cir) {
		if (biomeEntry.matchesKey(ModBase.MANGROVE_SWAMP)) cir.setReturnValue(VillagerType.SWAMP);
	}
}
