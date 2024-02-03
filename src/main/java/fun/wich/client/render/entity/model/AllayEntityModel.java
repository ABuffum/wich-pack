package fun.wich.client.render.entity.model;

import fun.wich.entity.passive.allay.AllayEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class AllayEntityModel extends SinglePartEntityModel<AllayEntity> implements ModelWithArms {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightWing;
	private final ModelPart leftWing;

	public AllayEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		this.root = root.getChild(ModEntityModelPartNames.ROOT);
		this.head = this.root.getChild(EntityModelPartNames.HEAD);
		this.body = this.root.getChild(EntityModelPartNames.BODY);
		this.rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
		this.rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
		this.leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
	}
	private static final ModelTransform rootTransform = ModelTransform.pivot(0.0f, 23.5f, 0.0f);
	private static final ModelTransform headTransform = ModelTransform.pivot(0.0f, -3.99f, 0.0f);
	private static final ModelTransform bodyTransform = ModelTransform.pivot(0.0f, -4.0f, 0.0f);
	private static final ModelTransform rightArmTransform = ModelTransform.pivot(-1.75f, 0.5f, 0.0f);
	private static final ModelTransform leftArmTransform = ModelTransform.pivot(1.75f, 0.5f, 0.0f);
	private static final ModelTransform rightWingTransform = ModelTransform.pivot(-0.5f, 0.0f, 0.6f);
	private static final ModelTransform leftWingTransform = ModelTransform.pivot(0.5f, 0.0f, 0.6f);
	@Override
	public ModelPart getPart() { return this.root; }
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild(ModEntityModelPartNames.ROOT, ModelPartBuilder.create(), rootTransform);
		modelPartData2.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, -5.0f, -2.5f, 5.0f, 5.0f, 5.0f, new Dilation(0.0f)), headTransform);
		ModelPartData modelPartData3 = modelPartData2.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 10).cuboid(-1.5f, 0.0f, -1.0f, 3.0f, 4.0f, 2.0f, new Dilation(0.0f)).uv(0, 16).cuboid(-1.5f, 0.0f, -1.0f, 3.0f, 5.0f, 2.0f, new Dilation(-0.2f)), bodyTransform);
		modelPartData3.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(23, 0).cuboid(-0.75f, -0.5f, -1.0f, 1.0f, 4.0f, 2.0f, new Dilation(-0.01f)), rightArmTransform);
		modelPartData3.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(23, 6).cuboid(-0.25f, -0.5f, -1.0f, 1.0f, 4.0f, 2.0f, new Dilation(-0.01f)), leftArmTransform);
		modelPartData3.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(16, 14).cuboid(0.0f, 1.0f, 0.0f, 0.0f, 5.0f, 8.0f, new Dilation(0.0f)), rightWingTransform);
		modelPartData3.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(16, 14).cuboid(0.0f, 1.0f, 0.0f, 0.0f, 5.0f, 8.0f, new Dilation(0.0f)), leftWingTransform);
		return TexturedModelData.of(modelData, 32, 32);
	}

	public ModelTransform getDefaultTransform(ModelPart part) {
		if (part == this.root) return rootTransform;
		else if (part == this.head) return headTransform;
		else if (part == this.body) return bodyTransform;
		else if (part == this.rightArm) return rightArmTransform;
		else if (part == this.leftArm) return leftArmTransform;
		else if (part == this.rightWing) return rightWingTransform;
		else if (part == this.leftWing) return leftWingTransform;
		return ModelTransform.NONE;
	}
	public void resetTransform(ModelPart part) { part.setTransform(getDefaultTransform(part)); }
	@Override
	public void setAngles(AllayEntity allayEntity, float f, float g, float h, float i, float j) {
		float t;
		float s;
		float r;
		this.getPart().traverse().forEach(this::resetTransform);
		float k = h * 20.0f * ((float)Math.PI / 180) + f;
		float l = MathHelper.cos(k) * (float)Math.PI * 0.15f + g;
		float m = h - (float)allayEntity.age;
		float n = h * 9.0f * ((float)Math.PI / 180);
		float o = Math.min(g / 0.3f, 1.0f);
		float p = 1.0f - o;
		float q = allayEntity.method_43397(m);
		if (allayEntity.isDancing()) {
			r = h * 8.0f * ((float)Math.PI / 180) + g;
			s = MathHelper.cos(r) * 16.0f * ((float)Math.PI / 180);
			t = allayEntity.method_44368(m);
			float u = MathHelper.cos(r) * 14.0f * ((float)Math.PI / 180);
			float v = MathHelper.cos(r) * 30.0f * ((float)Math.PI / 180);
			this.root.yaw = allayEntity.method_44360() ? (float)Math.PI * 4 * t : this.root.yaw;
			this.root.roll = s * (1.0f - t);
			this.head.yaw = v * (1.0f - t);
			this.head.roll = u * (1.0f - t);
		}
		else {
			this.head.pitch = j * ((float)Math.PI / 180);
			this.head.yaw = i * ((float)Math.PI / 180);
		}
		this.rightWing.pitch = 0.43633232f * (1.0f - o);
		this.rightWing.yaw = -0.7853982f + l;
		this.leftWing.pitch = 0.43633232f * (1.0f - o);
		this.leftWing.yaw = 0.7853982f - l;
		this.body.pitch = o * 0.7853982f;
		r = q * MathHelper.lerp(o, -1.0471976f, -1.134464f);
		this.root.pivotY += (float)Math.cos(n) * 0.25f * p;
		this.rightArm.pitch = r;
		this.leftArm.pitch = r;
		s = p * (1.0f - q);
		t = 0.43633232f - MathHelper.cos(n + 4.712389f) * (float)Math.PI * 0.075f * s;
		this.leftArm.roll = -t;
		this.rightArm.roll = t;
		this.rightArm.yaw = 0.27925268f * q;
		this.leftArm.yaw = -0.27925268f * q;
	}

	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.root.rotate(matrices);
		this.body.rotate(matrices);
		matrices.translate(0.0f, 0.0625f, 0.1875f);
		matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(this.rightArm.pitch));
		matrices.scale(0.7f, 0.7f, 0.7f);
		matrices.translate(0.0625f, 0.0f, 0.0f);
	}
}