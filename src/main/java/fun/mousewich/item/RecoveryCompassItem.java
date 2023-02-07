package fun.mousewich.item;

import com.mojang.logging.LogUtils;
import fun.mousewich.ModBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;

public class RecoveryCompassItem extends Item {
	private static final Logger LOGGER = LogUtils.getLogger();
	public static final String POS_KEY = "Pos";
	public static final String DIMENSION_KEY = "Dimension";
	public static final String TRACKED_KEY = "Tracked";

	public RecoveryCompassItem(Item.Settings settings) { super(settings); }

	public static boolean hasPosition(ItemStack stack) {
		NbtCompound nbtCompound = stack.getNbt();
		return nbtCompound != null && (nbtCompound.contains(DIMENSION_KEY) || nbtCompound.contains(POS_KEY));
	}

	@Override
	public boolean hasGlint(ItemStack stack) { return hasPosition(stack) || super.hasGlint(stack); }

	public static Optional<RegistryKey<World>> getDimension(NbtCompound nbt) {
		return World.CODEC.parse(NbtOps.INSTANCE, nbt.get(DIMENSION_KEY)).result();
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world.isClient) return;
		if (hasPosition(stack)) {
			NbtCompound nbtCompound = stack.getOrCreateNbt();
			if (nbtCompound.contains(TRACKED_KEY) && !nbtCompound.getBoolean(TRACKED_KEY)) return;
			Optional<RegistryKey<World>> optional = getDimension(nbtCompound);
			if (optional.isPresent() && optional.get() == world.getRegistryKey() && nbtCompound.contains(POS_KEY) && !world.isInBuildLimit(NbtHelper.toBlockPos(nbtCompound.getCompound(POS_KEY)))) {
				nbtCompound.remove(POS_KEY);
				System.out.println("Removed position key");
			}
		}
	}

	public static void setLocation(World world, BlockPos blockPos, ItemStack itemStack) {
		writeNbt(world.getRegistryKey(), blockPos, itemStack.getOrCreateNbt());
	}

	private static void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt) {
		nbt.put(POS_KEY, NbtHelper.fromBlockPos(pos));
		World.CODEC.encodeStart(NbtOps.INSTANCE, worldKey).resultOrPartial(LOGGER::error).ifPresent(nbtElement -> nbt.put(DIMENSION_KEY, nbtElement));
		nbt.putBoolean(TRACKED_KEY, true);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return hasPosition(stack) ? "item." + ModBase.NAMESPACE + ".active_recovery_compass" : super.getTranslationKey(stack);
	}



	public static void SetDeath(PlayerEntity entity) {
		PlayerInventory inventory = entity.getInventory();
		ArrayList<ItemStack> compasses = new ArrayList<>();
		boolean anyUnpositioned = false;
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isOf(ModBase.RECOVERY_COMPASS)) {
				compasses.add(stack);
				anyUnpositioned |= !hasPosition(stack);
				System.out.println(i);
			}
		}
		if (compasses.size() > 0) {
			if (anyUnpositioned) {
				for (ItemStack stack : compasses) {
					if (!hasPosition(stack)) {
						setLocation(entity.world, entity.getBlockPos(), stack);
						break;
					}
				}
			}
			else setLocation(entity.world, entity.getBlockPos(), compasses.get(0));
			System.out.println("Any unposition? " + anyUnpositioned);
		}
		System.out.println("There are " + compasses.size() + " recovery compasses in your inventory");
	}
}