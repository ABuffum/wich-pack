package fun.mousewich.mixins.client.render.model;

import fun.mousewich.client.render.entity.animation.AnimationUtils;
import fun.mousewich.client.render.entity.animation.ModAnimation;
import fun.mousewich.client.render.entity.animation.ModAnimationState;
import fun.mousewich.client.render.entity.model.ExpandedModelPart;
import fun.mousewich.client.render.entity.model.ExpandedSinglePartEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(SinglePartEntityModel.class)
public abstract class SinglePartEntityModelMixin implements ExpandedSinglePartEntityModel {
    @Override
    public Optional<ModelPart> getChild(String string) {
        return this.getPart().traverse().filter(modelPart -> ((ExpandedModelPart) modelPart).hasChild(string)).findFirst().map(modelPart -> modelPart.getChild(string));
    }

    @Shadow
    public abstract ModelPart getPart();

    private static final Vec3f field_39195 = new Vec3f();

    public void updateAnimation(ModAnimationState animationState, ModAnimation animation, float f) {
        this.updateAnimation(animationState, animation, f, 1.0F);
    }

    public void updateAnimation(ModAnimationState animationState, ModAnimation animation, float f, float g) {
        SinglePartEntityModel singlePartEntityModel = SinglePartEntityModel.class.cast(this);
        animationState.update(f, g);
        animationState.run((animationStatex) -> {
            AnimationUtils.animate(singlePartEntityModel, animation, animationState.getTimeRunning(), 1.0F, field_39195);
        });
    }
}
