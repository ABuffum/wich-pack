package fun.mousewich.gen.data;

import fun.mousewich.gen.data.advancement.AdvancementsGenerator;
import fun.mousewich.gen.data.loot.BlockLootGenerator;
import fun.mousewich.gen.data.model.ModelGenerator;
import fun.mousewich.gen.data.recipe.RecipeGenerator;
import fun.mousewich.gen.data.tag.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(ModelGenerator::new);
		fabricDataGenerator.addProvider(BlockLootGenerator::new);
		fabricDataGenerator.addProvider(BiomeTagGenerator::new);
		fabricDataGenerator.addProvider(BlockTagGenerator::new);
		fabricDataGenerator.addProvider(EntityTypeTagGenerator::new);
		fabricDataGenerator.addProvider(GameEventTagGenerator::new);
		fabricDataGenerator.addProvider(ItemTagGenerator::new);
		fabricDataGenerator.addProvider(RecipeGenerator::new);
		fabricDataGenerator.addProvider(AdvancementsGenerator::new);
	}
}
