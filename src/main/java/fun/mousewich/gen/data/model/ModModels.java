package fun.mousewich.gen.data.model;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;

import java.util.Optional;

import static fun.mousewich.ModBase.ID;

public class ModModels {
	public static final Model CUBE_NORTH_WEST_MIRRORED_ALL = Make("minecraft:block/cube_north_west_mirrored_all", "_north_west_mirrored", TextureKey.ALL);
	public static final Model TEMPLATE_BARS_CAP = Make("block/_template_bars_cap", "_cap", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_CAP_ALT = Make("block/_template_bars_cap_alt", "_cap_alt", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_POST = Make("block/_template_bars_post", "_post", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_POST_ENDS = Make("block/_template_bars_post_ends", "_post_ends", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_SIDE = Make("block/_template_bars_side", "_side", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BARS_SIDE_ALT = Make("block/_template_bars_side_alt", "_side_alt", TextureKey.PARTICLE, ModTextureKeys.BARS, TextureKey.EDGE);
	public static final Model TEMPLATE_BONE_TORCH = Make("block/_template_bone_torch", TextureKey.TORCH);
	public static final Model TEMPLATE_BONE_TORCH_WALL = Make("block/_template_bone_torch_wall", TextureKey.TORCH);
	public static final Model TEMPLATE_CAMPFIRE_OFF = Make("block/_template_campfire_off", ModTextureKeys.LOG);
	public static final Model TEMPLATE_CHAIN = Make("block/_template_chain", TextureKey.ALL);
	public static final Model TEMPLATE_CUSTOM_FENCE_GATE = Make("minecraft:block/template_custom_fence_gate", TextureKey.PARTICLE, TextureKey.TEXTURE);
	public static final Model TEMPLATE_GLASS_TRAPDOOR_BOTTOM = Make("block/_template_glass_trapdoor_bottom", "_bottom", TextureKey.TOP, TextureKey.SIDE);
	public static final Model TEMPLATE_GLASS_TRAPDOOR_OPEN = Make("block/_template_glass_trapdoor_open", "_open", TextureKey.TOP, TextureKey.SIDE);
	public static final Model TEMPLATE_GLASS_TRAPDOOR_TOP = Make("block/_template_glass_trapdoor_top", "_top", TextureKey.TOP, TextureKey.SIDE);
	public static final Model TEMPLATE_LADDER = Make("block/_template_ladder", TextureKey.TEXTURE);
	public static final Model TEMPLATE_LECTERN = Make("block/_template_lectern", TextureKey.BOTTOM, ModTextureKeys.BASE, TextureKey.FRONT, ModTextureKeys.SIDES, TextureKey.TOP);
	public static final Model TEMPLATE_MULTI_TEXTURE_CARPET = Make("block/_template_multi_texture_carpet", TextureKey.PARTICLE, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST, TextureKey.UP, TextureKey.DOWN);
	public static final Model TEMPLATE_MULTI_TEXTURE_SLAB = Make("block/_template_multi_texture_slab", TextureKey.PARTICLE, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST, TextureKey.UP, TextureKey.DOWN);
	public static final Model TEMPLATE_MULTI_TEXTURE_SLAB_TOP = Make("block/_template_multi_texture_slab_top", "_top", TextureKey.PARTICLE, TextureKey.NORTH, TextureKey.SOUTH, TextureKey.EAST, TextureKey.WEST, TextureKey.UP, TextureKey.DOWN);
	public static final Model TEMPLATE_WOODCUTTER = Make("block/_template_woodcutter", ModTextureKeys.PLANKS);

	public static Model Make(String path, TextureKey... requiredTextureKeys) {
		return new Model(Optional.of(ID(path)), Optional.empty(), requiredTextureKeys);
	}
	public static Model Make(String path, String variant, TextureKey... requiredTextureKeys) {
		return new Model(Optional.of(ID(path)), Optional.of(variant), requiredTextureKeys);
	}
}
