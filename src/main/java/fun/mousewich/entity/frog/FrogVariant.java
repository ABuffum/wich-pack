package fun.mousewich.entity.frog;

import fun.mousewich.ModBase;
import net.minecraft.util.Identifier;

public enum FrogVariant {
    TEMPERATE("temperate", "minecraft:textures/entity/frog/temperate_frog.png"),
    WARM("warm", "minecraft:textures/entity/frog/warm_frog.png"),
    COLD("cold", "minecraft:textures/entity/frog/cold_frog.png");

    public final String name;
    public final Identifier texture;
    FrogVariant(String name, String texture) {
        this.name = name;
        this.texture = ModBase.ID(texture);
    }
}