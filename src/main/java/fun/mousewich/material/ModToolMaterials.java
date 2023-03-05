package fun.mousewich.material;

import fun.mousewich.ModBase;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial, FactoryToolMaterial {
	AMETHYST(MiningLevels.STONE, 1561, 4.0F, 3.0F, 10, () -> Ingredient.ofItems(Items.AMETHYST_SHARD),
			5, -3, -3, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F),
	COPPER(MiningLevels.IRON, 200, 9.0F, 1.0F, 18, () -> Ingredient.ofItems(Items.COPPER_INGOT),
			6, -3, -1, -2, 1, -2.8F, 1.5F, -3, 3, -2.4F),
	ECHO(MiningLevels.NETHERITE, 2031, 9.0F, 4.0F, 15, () -> Ingredient.ofItems(ModBase.ECHO_SHARD),
			5, -3, -4, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F),
	EMERALD(MiningLevels.STONE, 850, 4.0F, 2.5F, 10, () -> Ingredient.ofItems(Items.EMERALD),
			5, -3, -2, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F),
	OBSIDIAN(MiningLevels.DIAMOND, 1561, 8.0F, 3.0F, 10, () -> Ingredient.ofItems(Items.OBSIDIAN),
			5, -3, -3, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F),
	QUARTZ(1, 450, 4.0F, 1.5F, 12, () -> Ingredient.ofItems(Items.QUARTZ),
			5, -3, -1, 0, 1, -2.8F, 1.5F, -3, 3, -2.4F);

	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Lazy<Ingredient> repairIngredient;
	private final float axeDmg;
	private final float axeSpd;
	private final int hoeDmg;
	private final float hoeSpd;
	private final int picDmg;
	private final float picSpd;
	private final float shvDmg;
	private final float shvSpd;
	private final int swdDmg;
	private final float swdSpd;

	ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient,
					 float axeDmg, float axeSpd,
					 int hoeDmg, float hoeSpd,
					 int picDmg, float picSpd,
					 float shvDmg, float shvSpd,
					 int swdDmg, float swdSpd) {
		this.miningLevel = miningLevel;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = new Lazy<>(repairIngredient);
		this.axeDmg = axeDmg;
		this.axeSpd = axeSpd;
		this.hoeDmg = hoeDmg;
		this.hoeSpd = hoeSpd;
		this.picDmg = picDmg;
		this.picSpd = picSpd;
		this.shvDmg = shvDmg;
		this.shvSpd = shvSpd;
		this.swdDmg = swdDmg;
		this.swdSpd = swdSpd;
	}

	public int getDurability() { return this.itemDurability; }

	public float getMiningSpeedMultiplier() { return this.miningSpeed; }

	public float getAttackDamage() { return this.attackDamage; }

	public int getMiningLevel() { return this.miningLevel; }

	public int getEnchantability() { return this.enchantability; }

	public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }

	public float getAxeDamage() { return this.axeDmg; }
	public float getAxeSpeed() { return this.axeSpd; }
	public int getHoeDamage() { return this.hoeDmg; }
	public float getHoeSpeed() { return this.hoeSpd; }
	public int getPickaxeDamage() { return this.picDmg; }
	public float getPickaxeSpeed() { return this.picSpd; }
	public float getShovelDamage() { return this.shvDmg; }
	public float getShovelSpeed() { return this.shvSpd; }
	public int getSwordDamage() { return this.swdDmg; }
	public float getSwordSpeed() { return this.swdSpd; }
}