package fun.mousewich.client.render.entity.animation;

import net.minecraft.util.math.Vec3f;

public class ModKeyframe {
	public float timestamp;
	public Vec3f target;
	public ModTransformation.Interpolation interpolation;

	public ModKeyframe(float timestamp, Vec3f target, ModTransformation.Interpolation interpolation) {
		this.timestamp = timestamp;
		this.target = target;
		this.interpolation = interpolation;
	}
}
