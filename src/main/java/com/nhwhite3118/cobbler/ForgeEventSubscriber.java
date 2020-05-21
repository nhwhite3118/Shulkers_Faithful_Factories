package com.nhwhite3118.cobbler;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.functions.ExplorationMap;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/*
 * This class is for simple Forge events. More complex features such as Enderman Cocoa Farming are in their own files.
 */
@EventBusSubscriber(modid = Cobbler.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

    @SubscribeEvent
    public static void onUseBonemeal(BonemealEvent event) {
        World world = event.getWorld();

        if (world.isRemote) {
            return;
        }
        if (!Cobbler.CobblerConfig.bonemealCanSpawnSapling.get() || !event.getBlock().canSustainPlant(world,
                event.getPos(), Direction.UP, (IPlantable) Blocks.OAK_SAPLING)) {
            return;
        }

        int x = event.getPos().getX();
        int y = event.getPos().getY();
        int z = event.getPos().getZ();
        for (int i = 0; i < Cobbler.CobblerConfig.bonemealSaplingSpawnAttempts.get(); i++) {
            Random rand = new Random();
            if (rand.nextFloat() > 1.0 / (float) Cobbler.CobblerConfig.bonemealSaplingSpawnrate.get()) {
                continue;
            }
            int xOffset = rand.nextInt(7) - 3;
            int zOffset = rand.nextInt(7) - 3;
            int yOffset = rand.nextInt(3) - 1;
            // Placing a sapling over the event block will cause the default bonemeal
            // listener to do nothing.
            if (xOffset == 0 && zOffset == 0 && yOffset == 0) {
                return;
            }
            BlockPos potentialTreePos = new BlockPos(x + xOffset, y + yOffset + 1, z + zOffset);
            BlockPos potentialSoilPos = new BlockPos(x + xOffset, y + yOffset, z + zOffset);
            BlockState potentialTree = world.getBlockState(potentialTreePos);
            BlockState potentialSoil = world.getBlockState(potentialSoilPos);

            if (potentialSoil != null && potentialSoil.canSustainPlant(world, potentialTreePos, Direction.UP,
                    (IPlantable) Blocks.OAK_SAPLING)) {
                if (potentialTree != null && potentialTree.getBlock() == Blocks.AIR
                        || potentialTree.getBlock() == Blocks.GRASS) {
                    world.setBlockState(potentialTreePos, Blocks.OAK_SAPLING.getDefaultState());
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlantGrowth(SaplingGrowTreeEvent event) {
        World world = (World) event.getWorld();
        int lightLevel;
        if (world.isRemote || !Cobbler.CobblerConfig.desertSaplingsCanBecomeDeadBush.get()
                || (lightLevel = world.getLight(event.getPos())) < 10) {
            return;
        }

        Random rand = new Random();
        BlockPos saplingPos = event.getPos();
        int variance = Cobbler.CobblerConfig.desertSaplingsMaxDeathrate.get()
                - Cobbler.CobblerConfig.desertSaplingsMinDeathrate.get();
        float saplingDryPercent = (((float) variance / 5.0f) * (float) (Math.max(lightLevel - 10, 0))
                + Cobbler.CobblerConfig.desertSaplingsMinDeathrate.get()) / 100.0f;

        // func_226691_t_ appears to get the biome of a given BlockPos
        if (rand.nextFloat() <= saplingDryPercent
                && world.getBiome(saplingPos).getCategory() == Biome.Category.DESERT) {
            world.setBlockState(saplingPos, Blocks.DEAD_BUSH.getDefaultState());
            event.setResult(Result.DENY);
        }
    }

    @SubscribeEvent
    public static void lootTablesLoading(LootTableLoadEvent event) {
        if (event.getName().getPath().equals("chests/end_city_treasure")
                && Cobbler.CobblerConfig.addMapsToShulkerFactoriesToEndCities.get()) {
            LootPool.Builder customMapPoolBuilder = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry
                    .builder(Items.MAP)
                    .acceptFunction(ExplorationMap.func_215903_b().func_216065_a("cobbler:shulker_factory")
                            .func_216064_a(MapDecoration.Type.RED_X).func_216062_a((byte) 1).func_216063_a(false))
                    .acceptFunction(SetCount.builder(
                            RandomValueRange.of(-Cobbler.CobblerConfig.shulkerFactoryMapChance.get() + 2.0F, 1.0F))));
            LootPool customMapPool = customMapPoolBuilder.build();
            event.getTable().addPool(customMapPool);
        }
    }
}
