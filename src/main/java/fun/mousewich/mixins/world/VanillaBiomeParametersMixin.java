package fun.mousewich.mixins.world;

import com.mojang.datafixers.util.Pair;
import fun.mousewich.ModBase;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(VanillaBiomeParameters.class)
public abstract class VanillaBiomeParametersMixin {
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange defaultParameter;
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange[] erosionParameters;
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange[] temperatureParameters;
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange nearInlandContinentalness;
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange farInlandContinentalness;
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange riverContinentalness;
	@Shadow @Final
	private MultiNoiseUtil.ParameterRange nonFrozenTemperatureParameters;

	private void writeDeepDarkParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
		parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(1.1f), weirdness, offset), biome));
	}

	@Inject(method="writeCaveBiomes", at = @At("TAIL"))
	private void WriteCaveBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, CallbackInfo ci) {
		this.writeDeepDarkParameters(parameters, this.defaultParameter, this.defaultParameter, this.defaultParameter, MultiNoiseUtil.ParameterRange.combine(this.erosionParameters[0], this.erosionParameters[1]), this.defaultParameter, 0.0f, ModBase.DEEP_DARK);
	}

	private void writeBiomeParameters(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
		parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(0.0f), weirdness, offset), biome));
		parameters.accept(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.ParameterRange.of(1.0f), weirdness, offset), biome));
	}

	@Inject(method="writeMixedBiomes", at = @At("HEAD"))
	private void WriteMixedBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
		writeBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[3], this.temperatureParameters[4]), this.defaultParameter, MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosionParameters[6], weirdness, 0.0f, ModBase.MANGROVE_SWAMP);
	}

	@Inject(method="writeBiomesNearRivers", at = @At("HEAD"))
	private void WriteBiomesNearRivers(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
		writeBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[3], this.temperatureParameters[4]), this.defaultParameter, MultiNoiseUtil.ParameterRange.combine(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosionParameters[6], weirdness, 0.0f, ModBase.MANGROVE_SWAMP);
	}
	@Inject(method="writeRiverBiomes", at = @At("HEAD"))
	private void WriteRiverBiomes(Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness, CallbackInfo ci) {
		writeBiomeParameters(parameters, MultiNoiseUtil.ParameterRange.combine(this.temperatureParameters[3], this.temperatureParameters[4]), this.defaultParameter, MultiNoiseUtil.ParameterRange.combine(this.riverContinentalness, this.farInlandContinentalness), this.erosionParameters[6], weirdness, 0.0f, ModBase.MANGROVE_SWAMP);
	}

	private static final RegistryKey<Biome>[][] SpecialNearMountainBiomes = new RegistryKey[][]{
			{ BiomeKeys.ICE_SPIKES, null, null, null, null },
			{ ModBase.CHERRY_GROVE, null, BiomeKeys.MEADOW, BiomeKeys.MEADOW, BiomeKeys.OLD_GROWTH_PINE_TAIGA },
			{ ModBase.CHERRY_GROVE, ModBase.CHERRY_GROVE, BiomeKeys.FOREST, BiomeKeys.BIRCH_FOREST, null },
			{ null, null, null, null, null },
			{ BiomeKeys.ERODED_BADLANDS, BiomeKeys.ERODED_BADLANDS, null, null, null }
	};

	@Inject(method="getNearMountainBiome", at=@At("HEAD"), cancellable = true)
	private void getNearMountainBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness, CallbackInfoReturnable<RegistryKey<Biome>> cir) {
		RegistryKey<Biome> registryKey = SpecialNearMountainBiomes[temperature][humidity];
		if (registryKey == ModBase.CHERRY_GROVE) cir.setReturnValue(registryKey);
	}
}
