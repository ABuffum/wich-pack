package fun.mousewich.gen.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;
import java.util.Random;

public record PredicatedStateProvider(BlockStateProvider fallback, List<Rule> rules) {
	public static final Codec<PredicatedStateProvider> CODEC = RecordCodecBuilder.create(instance -> instance.group(
					BlockStateProvider.TYPE_CODEC.fieldOf("fallback").forGetter(PredicatedStateProvider::fallback),
					Rule.CODEC.listOf().fieldOf("rules").forGetter(PredicatedStateProvider::rules))
			.apply(instance, PredicatedStateProvider::new));

	public static PredicatedStateProvider of(BlockStateProvider stateProvider) {
		return new PredicatedStateProvider(stateProvider, List.of());
	}

	public static PredicatedStateProvider of(Block block) {
		return PredicatedStateProvider.of(BlockStateProvider.of(block));
	}

	public BlockState getBlockState(StructureWorldAccess world, Random random, BlockPos pos) {
		for (Rule rule : this.rules) {
			if (!rule.ifTrue().test(world, pos)) continue;
			return rule.then().getBlockState(random, pos);
		}
		return this.fallback.getBlockState(random, pos);
	}
	public record Rule(BlockPredicate ifTrue, BlockStateProvider then) {
		public static final Codec<Rule> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						BlockPredicate.BASE_CODEC.fieldOf("if_true").forGetter(Rule::ifTrue),
						BlockStateProvider.TYPE_CODEC.fieldOf("then").forGetter(Rule::then))
				.apply(instance, Rule::new));
	}
}