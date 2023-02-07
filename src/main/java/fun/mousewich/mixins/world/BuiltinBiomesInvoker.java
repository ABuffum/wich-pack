package fun.mousewich.mixins.world;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BuiltinBiomes.class)
public interface BuiltinBiomesInvoker {
	@Invoker("register")
	static void Register(RegistryKey<Biome> key, Biome biome) { }
}
