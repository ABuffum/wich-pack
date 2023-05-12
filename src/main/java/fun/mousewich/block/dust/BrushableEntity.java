package fun.mousewich.block.dust;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public interface BrushableEntity {
	boolean brush(long worldTime, PlayerEntity player, Direction hitDirection);
	int getDustedLevel();
	Direction getHitDirection();
	ItemStack getItem();
}
