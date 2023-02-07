package fun.mousewich.mixins.world;

import fun.mousewich.ModBase;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSupplier;
import net.minecraft.world.chunk.BelowZeroRetrogen;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(BelowZeroRetrogen.class)
public class BelowZeroRetrogenMixin {
	@Inject(method="getBiomeSupplier", at = @At("HEAD"), cancellable = true)
	private static void GetBiomeSupplier(BiomeSupplier biomeSupplier, Chunk chunk, CallbackInfoReturnable<BiomeSupplier> cir) {
		if (!chunk.hasBelowZeroRetrogen()) return;
		Predicate<RegistryKey<Biome>> predicate = ModBase.CAVE_BIOMES::contains;
		cir.setReturnValue((x, y, z, noise) -> {
			RegistryEntry<Biome> registryEntry = biomeSupplier.getBiome(x, y, z, noise);
			if (registryEntry.matches(predicate)) return registryEntry;
			return chunk.getBiomeForNoiseGen(x, 0, z);
		});
	}
}
