package fun.mousewich.mixins.world;

import com.mojang.datafixers.util.Pair;
import fun.mousewich.ModBase;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
}
