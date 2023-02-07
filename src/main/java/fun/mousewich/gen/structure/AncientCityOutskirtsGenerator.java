package fun.mousewich.gen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fun.mousewich.ModBase;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.structure.processor.StructureProcessorList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;

public class AncientCityOutskirtsGenerator {
	public static void Initialize() {
		RegistryEntry<StructureProcessorList> ANCIENT_CITY_GENERIC_DEGRADATION = ModBase.ANCIENT_CITY_GENERIC_DEGRADATION.get();
		RegistryEntry<StructureProcessorList> ANCIENT_CITY_WALLS_DEGRADATION = ModBase.ANCIENT_CITY_WALLS_DEGRADATION.get();
		StructurePools.register(
				new StructurePool(
						ModBase.ID("ancient_city/structures"),
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(StructurePoolElement.ofEmpty(), 7),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/barracks", ANCIENT_CITY_GENERIC_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/chamber_1", ANCIENT_CITY_GENERIC_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/chamber_2", ANCIENT_CITY_GENERIC_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/chamber_3", ANCIENT_CITY_GENERIC_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/sauna_1", ANCIENT_CITY_GENERIC_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/small_statue", ANCIENT_CITY_GENERIC_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/large_ruin_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/tall_ruin_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/tall_ruin_2", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/tall_ruin_3", ANCIENT_CITY_GENERIC_DEGRADATION), 2),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/tall_ruin_4", ANCIENT_CITY_GENERIC_DEGRADATION), 2),
								Pair.of(StructurePoolElement.ofList(ImmutableList.of(
										StructurePoolElement.ofProcessedSingle( "ancient_city/structures/camp_1", ANCIENT_CITY_GENERIC_DEGRADATION),
										StructurePoolElement.ofProcessedSingle( "ancient_city/structures/camp_2", ANCIENT_CITY_GENERIC_DEGRADATION),
										StructurePoolElement.ofProcessedSingle( "ancient_city/structures/camp_3", ANCIENT_CITY_GENERIC_DEGRADATION))), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/medium_ruin_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/medium_ruin_2", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/small_ruin_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/small_ruin_2", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/large_pillar_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/structures/medium_pillar_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofList(ImmutableList.of(StructurePoolElement.ofSingle("ancient_city/structures/ice_box_1"))), 1)),
						StructurePool.Projection.RIGID));
		StructurePools.register(
				new StructurePool(
						ModBase.ID("ancient_city/sculk"),
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(StructurePoolElement.ofFeature(ModBase.SCULK_PATCH_ANCIENT_CITY_PLACED.getRegistryEntry()), 6), Pair.of(StructurePoolElement.ofEmpty(), 1)),
						StructurePool.Projection.RIGID));
		StructurePools.register(
				new StructurePool(
						ModBase.ID("ancient_city/walls"),
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_corner_wall_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_intersection_wall_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_lshape_wall_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_2", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_2", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_3", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_4", ANCIENT_CITY_WALLS_DEGRADATION), 4),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_passage_1", ANCIENT_CITY_WALLS_DEGRADATION), 3),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/ruined_corner_wall_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/ruined_corner_wall_2", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/ruined_horizontal_wall_stairs_1", ANCIENT_CITY_WALLS_DEGRADATION), 2),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/ruined_horizontal_wall_stairs_2", ANCIENT_CITY_WALLS_DEGRADATION), 2),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/ruined_horizontal_wall_stairs_3", ANCIENT_CITY_WALLS_DEGRADATION), 3),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/ruined_horizontal_wall_stairs_4", ANCIENT_CITY_WALLS_DEGRADATION), 3)),
						StructurePool.Projection.RIGID));
		StructurePools.register(
				new StructurePool(
						ModBase.ID("ancient_city/walls/no_corners"),
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_2", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_1", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_2", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_3", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_4", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_stairs_5", ANCIENT_CITY_WALLS_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/walls/intact_horizontal_wall_bridge", ANCIENT_CITY_WALLS_DEGRADATION), 1)),
						StructurePool.Projection.RIGID));
		StructurePools.register(
				new StructurePool(
						ModBase.ID("ancient_city/city_center/walls"),
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/bottom_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/bottom_2", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/bottom_left_corner", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/bottom_right_corner_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/bottom_right_corner_2", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/left", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/right", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/top", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/top_right_corner", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city_center/walls/top_left_corner", ANCIENT_CITY_GENERIC_DEGRADATION), 1)),
						StructurePool.Projection.RIGID));
		StructurePools.register(
				new StructurePool(
						ModBase.ID("ancient_city/city/entrance"),
						new Identifier("empty"),
						ImmutableList.of(
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city/entrance/entrance_connector", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city/entrance/entrance_path_1", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city/entrance/entrance_path_2", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city/entrance/entrance_path_3", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city/entrance/entrance_path_4", ANCIENT_CITY_GENERIC_DEGRADATION), 1),
								Pair.of(StructurePoolElement.ofProcessedSingle("ancient_city/city/entrance/entrance_path_5", ANCIENT_CITY_GENERIC_DEGRADATION), 1)),
						StructurePool.Projection.RIGID));
	}
}
