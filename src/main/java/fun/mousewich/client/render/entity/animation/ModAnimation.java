package fun.mousewich.client.render.entity.animation;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Map;

@Environment(value = EnvType.CLIENT)
public final class ModAnimation {
	public final float lengthInSeconds;
	public final boolean looping;
	public final Map<String, List<ModTransformation>> boneAnimations;

	public ModAnimation(float lengthInSeconds, boolean looping, Map<String, List<ModTransformation>> boneAnimations) {
		this.lengthInSeconds = lengthInSeconds;
		this.looping = looping;
		this.boneAnimations = boneAnimations;
	}

	@Environment(value = EnvType.CLIENT)
	public static class Builder {
		private final float lengthInSeconds;
		private final Map<String, List<ModTransformation>> transformations = Maps.newHashMap();
		private boolean looping;
		public static Builder create(float lengthInSeconds) { return new Builder(lengthInSeconds); }

		private Builder(float lengthInSeconds) { this.lengthInSeconds = lengthInSeconds; }
		public Builder looping() {
			this.looping = true;
			return this;
		}
		public Builder addBoneAnimation(String name2, ModTransformation transformation) {
			this.transformations.computeIfAbsent(name2, name -> Lists.newArrayList()).add(transformation);
			return this;
		}
		public ModAnimation build() {
			return new ModAnimation(this.lengthInSeconds, this.looping, this.transformations);
		}
	}
}
