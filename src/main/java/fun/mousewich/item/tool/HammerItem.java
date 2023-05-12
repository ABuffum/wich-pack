package fun.mousewich.item.tool;

import com.nhoryzon.mc.farmersdelight.registry.EnchantmentsRegistry;
import fun.mousewich.ModBase;
import fun.mousewich.ModFactory;
import fun.mousewich.gen.data.loot.DropTable;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.material.ModToolMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HammerItem extends MiningToolItem {
	public static final Set<Enchantment> DISALLOWED_ENCHANTMENTS = new HashSet<>(Set.of(Enchantments.FORTUNE, Enchantments.SILK_TOUCH));
	public static Map<Block, DropTable> HAMMER_DROPS = new HashMap<>();

	public HammerItem(ModToolMaterials material) { this(material, material.getHammerDamage(), material.getHammerSpeed()); }
	public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed) { this(material, attackDamage, attackSpeed, ModFactory.ItemSettings()); }
	public HammerItem(ModToolMaterials material, Settings settings) { this(material, material.getHammerDamage(), material.getHammerSpeed(), settings); }
	public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(attackDamage, attackSpeed, material, ModBlockTags.HAMMER_MINEABLE, settings.maxDamageIfAbsent(material.getDurability() * 4));
	}

	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		Block block = state.getBlock();
		return !state.isIn(ModBlockTags.HAMMER_MINEABLE) && !HAMMER_DROPS.containsKey(block) ? super.getMiningSpeedMultiplier(stack, state) : this.miningSpeed;
	}

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		return true;
	}
}
