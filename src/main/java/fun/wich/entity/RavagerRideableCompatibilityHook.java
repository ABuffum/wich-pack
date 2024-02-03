package fun.wich.entity;

import net.minecraft.util.math.Vec3d;

public interface RavagerRideableCompatibilityHook {
	boolean CheckForTravel();
	void SetCheckForTravel(boolean value);
	boolean RavagerRidableCompatibleTravel(Vec3d movementInput);
}
