package fun.mousewich.client.render.entity.animation;

import fun.mousewich.client.render.entity.model.ModEntityModelPartNames;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import fun.mousewich.client.render.entity.animation.ModAnimation.Builder;
import fun.mousewich.client.render.entity.animation.ModTransformation.Interpolations;
import fun.mousewich.client.render.entity.animation.ModTransformation.Targets;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class FrogAnimations {
    public static final ModAnimation CROAKING = Builder.create(3.0F)
            .addBoneAnimation(
                    ModEntityModelPartNames.CROAKING_BODY,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.375F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4167F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41823(0.0F, 1.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(2.9583F, AnimationUtils.method_41823(0.0F, 1.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(3.0F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )/*
            .addBoneAnimation(
                    ModEntityModelPartNames.CROAKING_BODY,
                    new ModTransformation(
                            Targets.SCALE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41822(0.0, 0.0, 0.0), Interpolations.field_37884),
                            new ModKeyframe(0.375F, AnimationUtils.method_41822(0.0, 0.0, 0.0), Interpolations.field_37884),
                            new ModKeyframe(0.4167F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(0.5417F, AnimationUtils.method_41822(1.3F, 2.1F, 1.6F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41822(1.3F, 2.1F, 1.6F), Interpolations.field_37884),
                            new ModKeyframe(0.7083F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(2.25F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(2.3333F, AnimationUtils.method_41822(1.3F, 2.1F, 1.6F), Interpolations.field_37884),
                            new ModKeyframe(2.4167F, AnimationUtils.method_41822(1.3F, 2.1F, 1.6F), Interpolations.field_37884),
                            new ModKeyframe(2.5F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(2.5833F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(2.6667F, AnimationUtils.method_41822(1.3F, 2.1F, 1.6F), Interpolations.field_37884),
                            new ModKeyframe(2.875F, AnimationUtils.method_41822(1.3F, 2.1F, 1.6F), Interpolations.field_37884),
                            new ModKeyframe(2.9583F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(3.0F, AnimationUtils.method_41822(0.0, 0.0, 0.0), Interpolations.field_37884))
            )*/
            .build();
    public static final ModAnimation WALKING = Builder.create(1.25F)
            .looping()
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, -5.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.2917F, AnimationUtils.method_41829(7.5F, -2.67F, -7.5F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41829(22.5F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(1.125F, AnimationUtils.method_41829(-45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41829(0.0F, -5.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.1F, -2.0F), Interpolations.field_37884),
                            new ModKeyframe(0.2917F, AnimationUtils.method_41823(-0.5F, -0.25F, -0.13F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41823(-0.5F, 0.1F, 2.0F), Interpolations.field_37884),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41823(0.5F, 1.0F, -0.11F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41823(0.0F, 0.1F, -2.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.125F, AnimationUtils.method_41829(22.5F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41829(-45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41829(0.0F, 5.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(7.5F, 2.33F, 7.5F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.5F, 0.1F, 2.0F), Interpolations.field_37884),
                            new ModKeyframe(0.2917F, AnimationUtils.method_41823(-0.5F, 1.0F, 0.12F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41823(0.0F, 0.1F, -2.0F), Interpolations.field_37884),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41823(0.5F, -0.25F, -0.13F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41823(0.5F, 0.1F, 2.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.1667F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.2917F, AnimationUtils.method_41829(45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41829(-45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.1F, 1.2F), Interpolations.field_37884),
                            new ModKeyframe(0.1667F, AnimationUtils.method_41823(0.0F, 0.1F, 2.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41823(0.0F, 2.0F, 1.06F), Interpolations.field_37884),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41823(0.0F, 0.1F, -1.0F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41823(0.0F, 0.1F, 1.2F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(-33.75F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.0417F, AnimationUtils.method_41829(-45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.1667F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41829(-33.75F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 1.14F, 0.11F), Interpolations.field_37884),
                            new ModKeyframe(0.1667F, AnimationUtils.method_41823(0.0F, 0.1F, -1.0F), Interpolations.field_37884),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41823(0.0F, 0.1F, 2.0F), Interpolations.field_37884),
                            new ModKeyframe(1.125F, AnimationUtils.method_41823(0.0F, 2.0F, 0.95F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41823(0.0F, 1.14F, 0.11F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.BODY,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 5.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.2917F, AnimationUtils.method_41829(-7.5F, 0.33F, 7.5F), Interpolations.field_37884),
                            new ModKeyframe(0.625F, AnimationUtils.method_41829(0.0F, -5.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(-7.5F, 0.33F, -7.5F), Interpolations.field_37884),
                            new ModKeyframe(1.25F, AnimationUtils.method_41829(0.0F, 5.0F, 0.0F), Interpolations.field_37884))
            )
            .build();
    public static final ModAnimation LONG_JUMPING = Builder.create(0.5F)
            .addBoneAnimation(
                    EntityModelPartNames.BODY,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(-22.5F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(-22.5F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.BODY,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(-56.14F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(-56.14F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 1.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41823(0.0F, 1.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(-56.14F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(-56.14F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 1.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41823(0.0F, 1.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(45.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(45.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(45.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )
            .build();
    public static final ModAnimation USING_TONGUE = Builder.create(0.5F)
            .addBoneAnimation(
                    EntityModelPartNames.HEAD,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.0833F, AnimationUtils.method_41829(-60.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4167F, AnimationUtils.method_41829(-60.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )/*
            .addBoneAnimation(
                    EntityModelPartNames.HEAD,
                    new ModTransformation(
                            Targets.SCALE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(1.0F, 1.0F, 1.0F), Interpolations.field_37884),
                            new ModKeyframe(0.0833F, AnimationUtils.method_41829(0.998F, 1.0F, 1.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4167F, AnimationUtils.method_41829(0.998F, 1.0F, 1.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(1.0F, 1.0F, 1.0F), Interpolations.field_37884))
            )*/
            .addBoneAnimation(
                    ModEntityModelPartNames.TONGUE,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.0833F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.4167F, AnimationUtils.method_41829(-18.0F, 0.0F, 0.0F), Interpolations.field_37884),
                            new ModKeyframe(0.5F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37884))
            )/*
            .addBoneAnimation(
                    ModEntityModelPartNames.TONGUE,
                    new ModTransformation(
                            Targets.SCALE,
                            new ModKeyframe(0.0833F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884),
                            new ModKeyframe(0.1667F, AnimationUtils.method_41822(0.5, 1.0, 5.0), Interpolations.field_37884),
                            new ModKeyframe(0.4167F, AnimationUtils.method_41822(1.0, 1.0, 1.0), Interpolations.field_37884))
            )*/
            .build();
    public static final ModAnimation SWIMMING = Builder.create(1.04167F)
            .looping()
            .addBoneAnimation(
                    EntityModelPartNames.BODY,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.3333F, AnimationUtils.method_41829(10.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.6667F, AnimationUtils.method_41829(-10.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(90.0F, 22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41829(45.0F, 22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.6667F, AnimationUtils.method_41829(-22.5F, -22.5F, -22.5F), Interpolations.field_37885),
                            new ModKeyframe(0.875F, AnimationUtils.method_41829(-45.0F, -22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(22.5F, 0.0F, 22.5F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41829(90.0F, 22.5F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, -0.64F, 2.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41823(0.0F, -0.64F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.6667F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.875F, AnimationUtils.method_41823(0.0F, -0.27F, -1.14F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41823(0.0F, -1.45F, 0.43F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41823(0.0F, -0.64F, 2.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(90.0F, -22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41829(45.0F, -22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.6667F, AnimationUtils.method_41829(-22.5F, 22.5F, 22.5F), Interpolations.field_37885),
                            new ModKeyframe(0.875F, AnimationUtils.method_41829(-45.0F, 22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(22.5F, 0.0F, -22.5F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41829(90.0F, -22.5F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, -0.64F, 2.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41823(0.0F, -0.64F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.6667F, AnimationUtils.method_41823(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.875F, AnimationUtils.method_41823(0.0F, -0.27F, -1.14F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41823(0.0F, -1.45F, 0.43F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41823(0.0F, -0.64F, 2.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.25F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41829(67.5F, -45.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41829(90.0F, 45.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(-2.5F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(0.25F, AnimationUtils.method_41823(-2.0F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41823(1.0F, -2.0F, -1.0F), Interpolations.field_37885),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41823(0.58F, 0.0F, -2.83F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41823(-2.5F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41823(-2.5F, 0.0F, 1.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.25F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41829(67.5F, 45.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41829(90.0F, -45.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41829(90.0F, 0.0F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(2.5F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(0.25F, AnimationUtils.method_41823(2.0F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(0.4583F, AnimationUtils.method_41823(-1.0F, -2.0F, -1.0F), Interpolations.field_37885),
                            new ModKeyframe(0.7917F, AnimationUtils.method_41823(-0.58F, 0.0F, -2.83F), Interpolations.field_37885),
                            new ModKeyframe(0.9583F, AnimationUtils.method_41823(2.5F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0417F, AnimationUtils.method_41823(2.5F, 0.0F, 1.0F), Interpolations.field_37885))
            )
            .build();
    public static final ModAnimation IDLING_IN_WATER = Builder.create(3.0F)
            .looping()
            .addBoneAnimation(
                    EntityModelPartNames.BODY,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(1.625F, AnimationUtils.method_41829(-10.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41829(0.0F, 0.0F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, -22.5F), Interpolations.field_37885),
                            new ModKeyframe(2.2083F, AnimationUtils.method_41829(0.0F, 0.0F, -45.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41829(0.0F, 0.0F, -22.5F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(-1.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(2.2083F, AnimationUtils.method_41823(-1.0F, -0.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41823(-1.0F, 0.0F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(0.0F, 0.0F, 22.5F), Interpolations.field_37885),
                            new ModKeyframe(2.2083F, AnimationUtils.method_41829(0.0F, 0.0F, 45.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41829(0.0F, 0.0F, 22.5F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_ARM,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(1.0F, 0.0F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(2.2083F, AnimationUtils.method_41823(1.0F, -0.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41823(1.0F, 0.0F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(22.5F, -22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0F, AnimationUtils.method_41829(22.5F, -22.5F, -45.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41829(22.5F, -22.5F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.LEFT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0F, AnimationUtils.method_41823(0.0F, -1.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41823(0.0F, 0.0F, 1.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.ROTATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41829(22.5F, 22.5F, 0.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0F, AnimationUtils.method_41829(22.5F, 22.5F, 45.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41829(22.5F, 22.5F, 0.0F), Interpolations.field_37885))
            )
            .addBoneAnimation(
                    EntityModelPartNames.RIGHT_LEG,
                    new ModTransformation(
                            Targets.TRANSLATE,
                            new ModKeyframe(0.0F, AnimationUtils.method_41823(0.0F, 0.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(1.0F, AnimationUtils.method_41823(0.0F, -1.0F, 1.0F), Interpolations.field_37885),
                            new ModKeyframe(3.0F, AnimationUtils.method_41823(0.0F, 0.0F, 1.0F), Interpolations.field_37885))
            )
            .build();

    public FrogAnimations() {
    }
}
