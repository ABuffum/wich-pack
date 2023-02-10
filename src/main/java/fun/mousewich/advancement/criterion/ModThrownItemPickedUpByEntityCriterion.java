package fun.mousewich.advancement.criterion;

import com.google.gson.JsonObject;
import fun.mousewich.advancement.ModCriteria;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModThrownItemPickedUpByEntityCriterion extends AbstractCriterion<ModThrownItemPickedUpByEntityCriterion.Conditions> {
	private final Identifier id;

	public ModThrownItemPickedUpByEntityCriterion(Identifier id) {
		this.id = id;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	protected ModThrownItemPickedUpByEntityCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
		EntityPredicate.Extended extended2 = EntityPredicate.Extended.getInJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
		return new ModThrownItemPickedUpByEntityCriterion.Conditions(this.id, extended, itemPredicate, extended2);
	}

	public void trigger(ServerPlayerEntity player, ItemStack stack, @Nullable Entity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		this.trigger(player, conditions -> conditions.test(player, stack, lootContext));
	}

	public static class Conditions
			extends AbstractCriterionConditions {
		private final ItemPredicate item;
		private final EntityPredicate.Extended entity;

		public Conditions(Identifier id, EntityPredicate.Extended player, ItemPredicate item, EntityPredicate.Extended entity) {
			super(id, player);
			this.item = item;
			this.entity = entity;
		}

		public static ModThrownItemPickedUpByEntityCriterion.Conditions createThrownItemPickedUpByEntity(EntityPredicate.Extended player, ItemPredicate item, EntityPredicate.Extended entity) {
			return new ModThrownItemPickedUpByEntityCriterion.Conditions(Criteria.THROWN_ITEM_PICKED_UP_BY_ENTITY.getId(), player, item, entity);
		}

		public static ModThrownItemPickedUpByEntityCriterion.Conditions createThrownItemPickedUpByPlayer(EntityPredicate.Extended player, ItemPredicate item, EntityPredicate.Extended entity) {
			return new ModThrownItemPickedUpByEntityCriterion.Conditions(ModCriteria.THROWN_ITEM_PICKED_UP_BY_PLAYER.getId(), player, item, entity);
		}

		public boolean test(ServerPlayerEntity player, ItemStack stack, LootContext entityContext) {
			if (!this.item.test(stack)) {
				return false;
			}
			return this.entity.test(entityContext);
		}

		@Override
		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("item", this.item.toJson());
			jsonObject.add("entity", this.entity.toJson(predicateSerializer));
			return jsonObject;
		}
	}
}
