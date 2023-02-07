package fun.mousewich.client.render.entity.model;

import fun.mousewich.client.render.entity.animation.AnimationUtils;
import fun.mousewich.client.render.entity.animation.FrogAnimations;
import fun.mousewich.client.render.entity.animation.ModAnimation;
import fun.mousewich.client.render.entity.animation.ModAnimationState;
import fun.mousewich.entity.frog.FrogEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class FrogEntityModel<T extends FrogEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart eyes;
    private final ModelPart leftEye;
    private final ModelPart rightEye;
    private final ModelPart tongue;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart croakingBody;

    public FrogEntityModel(ModelPart root) {
        this.root = root.getChild(ModEntityModelPartNames.ROOT);
        this.body = this.root.getChild(EntityModelPartNames.BODY);
        this.head = this.body.getChild(EntityModelPartNames.HEAD);
        this.eyes = this.head.getChild(ModEntityModelPartNames.EYES);
        this.leftEye = this.eyes.getChild(EntityModelPartNames.LEFT_EYE);
        this.rightEye = this.eyes.getChild(EntityModelPartNames.RIGHT_EYE);
        this.tongue = this.body.getChild(ModEntityModelPartNames.TONGUE);
        this.leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftLeg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.croakingBody = this.body.getChild(ModEntityModelPartNames.CROAKING_BODY);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild(ModEntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData2.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(3, 1).cuboid(-3.5F, -2.0F, -8.0F, 7.0F, 3.0F, 9.0F).uv(23, 22).cuboid(-3.5F, -1.0F, -8.0F, 7.0F, 0.0F, 9.0F), ModelTransform.pivot(0.0F, -2.0F, 4.0F));
        ModelPartData modelPartData4 = modelPartData3.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(23, 13).cuboid(-3.5F, -1.0F, -7.0F, 7.0F, 0.0F, 9.0F).uv(0, 13).cuboid(-3.5F, -2.0F, -7.0F, 7.0F, 3.0F, 9.0F), ModelTransform.pivot(0.0F, -2.0F, -1.0F));
        ModelPartData modelPartData5 = modelPartData4.addChild(ModEntityModelPartNames.EYES, ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 0.0F, 2.0F));
        modelPartData5.addChild(EntityModelPartNames.RIGHT_EYE, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F), ModelTransform.pivot(-1.5F, -3.0F, -6.5F));
        modelPartData5.addChild(EntityModelPartNames.LEFT_EYE, ModelPartBuilder.create().uv(0, 5).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F), ModelTransform.pivot(2.5F, -3.0F, -6.5F));
        modelPartData3.addChild(ModEntityModelPartNames.CROAKING_BODY, ModelPartBuilder.create().uv(26, 5).cuboid(-3.5F, -0.1F, -2.9F, 7.0F, 2.0F, 3.0F, new Dilation(-0.1F)), ModelTransform.pivot(0.0F, -1.0F, -5.0F));
        ModelPartData modelPartData6 = modelPartData3.addChild(ModEntityModelPartNames.TONGUE, ModelPartBuilder.create().uv(17, 13).cuboid(-2.0F, 0.0F, -7.1F, 4.0F, 0.0F, 7.0F), ModelTransform.pivot(0.0F, -1.01F, 1.0F));
        ModelPartData modelPartData7 = modelPartData3.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 32).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F), ModelTransform.pivot(4.0F, -1.0F, -6.5F));
        modelPartData7.addChild(ModEntityModelPartNames.LEFT_HAND, ModelPartBuilder.create().uv(18, 40).cuboid(-4.0F, 3.01F, -5.0F, 8.0F, 0.0F, 8.0F), ModelTransform.pivot(4.0F, -1.0F, -6.5F));
        ModelPartData modelPartData8 = modelPartData3.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 38).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 3.0F), ModelTransform.pivot(-4.0F, -1.0F, -6.5F));
        modelPartData8.addChild(ModEntityModelPartNames.RIGHT_HAND, ModelPartBuilder.create().uv(2, 40).cuboid(-4.0F, 3.01F, -5.0F, 8.0F, 0.0F, 8.0F), ModelTransform.pivot(-4.0F, -1.0F, -6.5F));
        ModelPartData modelPartData9 = modelPartData2.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(14, 25).cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F), ModelTransform.pivot(3.5F, -3.0F, 4.0F));
        modelPartData9.addChild(ModEntityModelPartNames.LEFT_FOOT, ModelPartBuilder.create().uv(2, 32).cuboid(-2.0F, 3.01F, -4.0F, 8.0F, 0.0F, 8.0F), ModelTransform.pivot(3.5F, -3.0F, 4.0F));
        ModelPartData modelPartData10 = modelPartData2.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 25).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 3.0F, 4.0F), ModelTransform.pivot(-3.5F, -3.0F, 4.0F));
        modelPartData10.addChild(ModEntityModelPartNames.RIGHT_FOOT, ModelPartBuilder.create().uv(18, 32).cuboid(-6.0F, 3.01F, -4.0F, 8.0F, 0.0F, 8.0F), ModelTransform.pivot(-3.5F, -3.0F, 4.0F));
        return TexturedModelData.of(modelData, 48, 48);
    }

    public void setAngles(T frogEntity, float f, float g, float h, float i, float j) {
        this.getPart().traverse().forEach(modelPart -> ((ExpandedModelPart) modelPart).resetTransform());
        float k = Math.min((float) frogEntity.getVelocity().lengthSquared() * 200.0F, 8.0F);
        this.setPivots();
        ((ExpandedSinglePartEntityModel)this).updateAnimation(frogEntity.longJumpingAnimationState, FrogAnimations.LONG_JUMPING, h);
        ((ExpandedSinglePartEntityModel)this).updateAnimation(frogEntity.croakingAnimationState, FrogAnimations.CROAKING, h);
        ((ExpandedSinglePartEntityModel)this).updateAnimation(frogEntity.usingTongueAnimationState, FrogAnimations.USING_TONGUE, h);
        ((ExpandedSinglePartEntityModel)this).updateAnimation(frogEntity.walkingAnimationState, FrogAnimations.WALKING, h, k);
        ((ExpandedSinglePartEntityModel)this).updateAnimation(frogEntity.swimmingAnimationState, FrogAnimations.SWIMMING, h);
        ((ExpandedSinglePartEntityModel)this).updateAnimation(frogEntity.idlingInWaterAnimationState, FrogAnimations.IDLING_IN_WATER, h);
        this.croakingBody.visible = frogEntity.croakingAnimationState.isRunning();
    }

    private void setPivots() {
        this.root.pivotY = 24.0F;

        this.body.pivotX = 0.0F;
        this.body.pivotY = -2.0F;
        this.body.pivotZ = 4.0F;

        this.head.pivotX = 0.0F;
        this.head.pivotY = -2.0F;
        this.head.pivotZ = -1.0F;

        this.eyes.pivotX = 0.5F;
        this.eyes.pivotY = 0.0F;
        this.eyes.pivotZ = 2.0F;

        this.leftEye.pivotX = 1.5F;
        this.leftEye.pivotY = -3.0F;
        this.leftEye.pivotZ = -6.5F;

        this.rightEye.pivotX = -2.5F;
        this.rightEye.pivotY = -3.0F;
        this.rightEye.pivotZ = -6.5F;

        this.croakingBody.pivotX = 0.0F;
        this.croakingBody.pivotY = 1.0F;
        this.croakingBody.pivotZ = -5.0F;

        this.tongue.pivotX = 0.0F;
        this.tongue.pivotY = -1.1F;
        this.tongue.pivotZ = 1.0F;

        this.rightArm.pivotX = -4.0F;
        this.rightArm.pivotY = -1.0F;
        this.rightArm.pivotZ = -6.5F;

        this.leftArm.pivotX = 4.0F;
        this.leftArm.pivotY = -1.0F;
        this.leftArm.pivotZ = -6.5F;

        this.rightLeg.pivotX = -3.5F;
        this.rightLeg.pivotY = -3.0F;
        this.rightLeg.pivotZ = 4.0F;

        this.leftLeg.pivotX = 3.5F;
        this.leftLeg.pivotY = -3.0F;
        this.leftLeg.pivotZ = 4.0F;

    }

    public ModelPart getPart() { return this.root; }
}
