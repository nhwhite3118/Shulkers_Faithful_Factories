package com.nhwhite3118.cobbler.structures.placements;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;

public class ShulkerFactoryPlacement extends Placement<CountRangeConfig> {
    public ShulkerFactoryPlacement(Codec<CountRangeConfig> config) {
        super(config);
    }

    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator generator, Random random, CountRangeConfig config, BlockPos pos) {
        int maxCount = config.count;
        int range = Math.max(config.maximum - config.bottomOffset, 1);
        return IntStream.range(0, maxCount).mapToObj((index) -> {
            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = random.nextInt(range) + config.bottomOffset;
            return new BlockPos(x, y, z);
        });
    }
}
