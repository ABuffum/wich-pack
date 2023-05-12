package fun.mousewich.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CollectionUtil {
	public static <T> List<T> copyShuffled(Stream<T> stream, Random random) {
		ObjectArrayList<T> objectArrayList = new ObjectArrayList<>(stream.collect(Collectors.toList()));
		shuffle(objectArrayList, random);
		return objectArrayList;
	}
	public static <T> List<T> copyShuffled(T[] array, Random random) {
		ObjectArrayList<T> objectArrayList = new ObjectArrayList<T>(array);
		shuffle(objectArrayList, random);
		return objectArrayList;
	}
	public static <T> List<T> copyShuffled(ObjectArrayList<T> list, Random random) {
		ObjectArrayList<T> objectArrayList = new ObjectArrayList<T>(list);
		shuffle(objectArrayList, random);
		return objectArrayList;
	}
	public static <T> void shuffle(ObjectArrayList<T> list, Random random) {
		int i;
		for (int j = i = list.size(); j > 1; --j) {
			int k = random.nextInt(j);
			list.set(j - 1, list.set(k, list.get(j - 1)));
		}
	}
	@Nullable
	public static <T, R> R map(@Nullable T value, Function<T, R> mapper) {
		if (value == null) {
			return null;
		}
		return mapper.apply(value);
	}
}
