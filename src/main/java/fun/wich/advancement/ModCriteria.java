package fun.wich.advancement;

import fun.wich.advancement.criterion.ItemCriterion;
import fun.wich.advancement.criterion.ModThrownItemPickedUpByEntityCriterion;
import fun.wich.advancement.criterion.ModTickCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.OnKilledCriterion;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;

public class ModCriteria {
	public static final OnKilledCriterion KILL_MOB_NEAR_SCULK_CATALYST = Criteria.register(new OnKilledCriterion(new Identifier("kill_mob_near_sculk_catalyst")));
	public static OnKilledCriterion.Conditions createKillMobNearSculkCatalyst() {
		return new OnKilledCriterion.Conditions(KILL_MOB_NEAR_SCULK_CATALYST.getId(), EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY, DamageSourcePredicate.EMPTY);
	}
	public static final ItemCriterion ALLAY_DROP_ITEM_ON_BLOCK = Criteria.register(new ItemCriterion(new Identifier("allay_drop_item_on_block")));
	public static final ModTickCriterion AVOID_VIBRATION = Criteria.register(new ModTickCriterion(new Identifier("avoid_vibration")));

	public static final ModThrownItemPickedUpByEntityCriterion THROWN_ITEM_PICKED_UP_BY_PLAYER = Criteria.register(new ModThrownItemPickedUpByEntityCriterion(new Identifier("thrown_item_picked_up_by_player")));

	public static void Register() { }
}
