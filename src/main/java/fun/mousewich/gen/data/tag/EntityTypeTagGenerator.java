package fun.mousewich.gen.data.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import static fun.mousewich.ModBase.NAMESPACE;

public class EntityTypeTagGenerator extends FabricTagProvider<EntityType<?>> {
	public EntityTypeTagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ENTITY_TYPE, "entity_types", NAMESPACE + ":entity_type_tag_generator");
	}
	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(ModEntityTypeTags.FROG_FOOD).add(EntityType.SLIME, EntityType.MAGMA_CUBE);
	}
}
