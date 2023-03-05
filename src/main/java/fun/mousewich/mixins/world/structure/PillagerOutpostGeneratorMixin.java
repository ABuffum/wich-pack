package fun.mousewich.mixins.world.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.PillagerOutpostGenerator;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PillagerOutpostGenerator.class)
public class PillagerOutpostGeneratorMixin {
	@ModifyArg(method="<clinit>", at = @At(value = "INVOKE", target="Lnet/minecraft/structure/pool/StructurePools;register(Lnet/minecraft/structure/pool/StructurePool;)Lnet/minecraft/util/registry/RegistryEntry;"))
	private static StructurePool RegisterAllayCage(StructurePool templatePool) {
		if (templatePool.getId().getPath().endsWith("features")) {
			return new StructurePool(new Identifier("pillager_outpost/features"), new Identifier("empty"),
					ImmutableList.of(
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_cage1"), 1),
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_cage2"), 1),
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_cage_with_allays"), 1),
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_logs"), 1),
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_tent1"), 1),
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_tent2"), 1),
							Pair.of(StructurePoolElement.ofLegacySingle("pillager_outpost/feature_targets"), 1),
							Pair.of(StructurePoolElement.ofEmpty(), 6)),
					StructurePool.Projection.RIGID);
		}
		return templatePool;
	}
}
