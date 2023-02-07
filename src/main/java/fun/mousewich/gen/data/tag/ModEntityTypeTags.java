package fun.mousewich.gen.data.tag;

import fun.mousewich.ModBase;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityTypeTags {
	public static final TagKey<EntityType<?>> FROG_FOOD = createMinecraftTag("frog_food");

	private static TagKey<EntityType<?>> createTag(String name) { return TagKey.of(Registry.ENTITY_TYPE_KEY, ModBase.ID(name)); }
	private static TagKey<EntityType<?>> createTag(String namespace, String path) { return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(namespace, path)); }
	private static TagKey<EntityType<?>> createCommonTag(String name) { return TagKey.of(Registry.ENTITY_TYPE_KEY,new Identifier("c", name)); }
	private static TagKey<EntityType<?>> createMinecraftTag(String name) { return TagKey.of(Registry.ENTITY_TYPE_KEY,new Identifier(name)); }
}
