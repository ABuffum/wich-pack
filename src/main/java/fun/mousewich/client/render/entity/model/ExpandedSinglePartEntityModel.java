package fun.mousewich.client.render.entity.model;

import fun.mousewich.client.render.entity.animation.ModAnimation;
import fun.mousewich.client.render.entity.animation.ModAnimationState;
import net.minecraft.client.model.ModelPart;

import java.util.Optional;

public interface ExpandedSinglePartEntityModel {
    Optional<ModelPart> getChild(String string);

    void updateAnimation(ModAnimationState animationState, ModAnimation animation, float f);

    void updateAnimation(ModAnimationState animationState, ModAnimation animation, float f, float g);
}
