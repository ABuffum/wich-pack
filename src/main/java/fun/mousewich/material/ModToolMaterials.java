package fun.mousewich.material;

import fun.mousewich.ModBase;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
    AMETHYST(MiningLevels.STONE, 1561, 4.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.AMETHYST_SHARD);
    }),
    COPPER(MiningLevels.IRON, 200, 9.0F, 1.0F, 18, () -> {
        return Ingredient.ofItems(Items.COPPER_INGOT);
    }),
    ECHO(MiningLevels.NETHERITE, 2031, 9.0F, 4.0F, 15, () -> {
        return Ingredient.ofItems(ModBase.ECHO_SHARD);
    }),
    EMERALD(MiningLevels.STONE, 850, 4.0F, 2.5F, 10, () -> {
        return Ingredient.ofItems(Items.EMERALD);
    }),
    FLINT(1, 131, 4.0F, 1.0F, 5, () -> {
        return Ingredient.ofItems(Items.FLINT);
    }),
    OBSIDIAN(MiningLevels.DIAMOND, 1561, 8.0F, 3.0F, 10, () -> {
        return Ingredient.ofItems(Items.OBSIDIAN);
    }),
    QUARTZ(1, 450, 4.0F, 1.5F, 12, () -> {
        return Ingredient.ofItems(Items.QUARTZ);
    });

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    private ModToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }
}