package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.ModDatagen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Set;

import static fun.mousewich.ModBase.*;

public class EntityTypeTagGenerator extends FabricTagProvider<EntityType<?>> {
	public EntityTypeTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ENTITY_TYPE, "entity_types", NAMESPACE + ":entity_type_tag_generator");
	}
	private static Identifier betterend(String path) { return new Identifier("betterend", path); }
	private static Identifier betternether(String path) { return new Identifier("betternether", path); }
	private static Identifier graveyard(String path) { return new Identifier("graveyard", path); }
	@Override
	protected void generateTags() {
		for (Map.Entry<TagKey<EntityType<?>>, Set<EntityType<?>>> entry : ModDatagen.Cache.Tag.ENTITY_TYPE_TAGS.entrySet()) {
			getOrCreateTagBuilder(entry.getKey()).add(entry.getValue().toArray(EntityType<?>[]::new));
			entry.getValue().clear();
		}
		ModDatagen.Cache.Tag.ENTITY_TYPE_TAGS.clear();

		getOrCreateTagBuilder(ModEntityTypeTags.FROG_FOOD)
				.add(EntityType.SLIME, EntityType.MAGMA_CUBE, TROPICAL_SLIME_ENTITY, PINK_SLIME_ENTITY)
				.addOptional(betterend("end_slime"));
		getOrCreateTagBuilder(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
				.add(MELON_GOLEM_ENTITY)
				.add(FROZEN_ZOMBIE_ENTITY);
		getOrCreateTagBuilder(EntityTypeTags.SKELETONS)
				.add(MOSSY_SKELETON_ENTITY, SUNKEN_SKELETON_ENTITY)
				.addOptional(betternether("jungle_skeleton"))
				.addOptional(graveyard("revenant"));
		getOrCreateTagBuilder(ModEntityTypeTags.SPIDERS).add(EntityType.SPIDER, EntityType.CAVE_SPIDER, BONE_SPIDER_ENTITY, JUMPING_SPIDER_ENTITY);
		getOrCreateTagBuilder(ModEntityTypeTags.ZOMBIES)
				.add(EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.HUSK, EntityType.DROWNED, EntityType.GIANT)
				.add(FROZEN_ZOMBIE_ENTITY)
				.addOptional(graveyard("ghoul"));
	}
}
