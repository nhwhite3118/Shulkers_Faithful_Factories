package com.nhwhite3118.cobbler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Cobbler.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class EndermanCocoaFarming {

    /*
     * If cocoa has the enderman_holdable tag then it will cause issues with this because the enderman will make two attempts to pick it up, one the vanilla way
     * and one our way
     */
    @SubscribeEvent
    public static void letEndermenBreakAndPlaceCocoa(EntityMobGriefingEvent event) {
        if (!(event.getEntity() instanceof EndermanEntity) || !Cobbler.CobblerConfig.endermenCanPickupAndPlaceCocoa.get()) {
            return;
        }
        EndermanEntity enderman = (EndermanEntity) event.getEntity();
        BlockState heldItem = enderman.getHeldBlockState();
        if (heldItem == null) {
            if (enderman.getRNG().nextInt(20) == 0) {
                endermanPickUpCocoa(event, enderman);
                return;
            }
            // The only goal Endermen have with priority 10 is placeBlock. There are no other unique identifiers for the goal
        } else if (heldItem.getBlock() == Blocks.COCOA) {
            if (enderman.getRNG().nextInt(20) == 0) {
                endermanPlaceBlock(event, enderman);
            }
            // Vanilla place block will fail anyways; stopping it now is better for
            // performance.
            event.setResult(Result.DENY);
        }
    }

    private static void endermanPickUpCocoa(EntityMobGriefingEvent event, EndermanEntity enderman) {
        // Pickup cocoa. Below code is a modified version of the tick event in EndermanEntity.TakeBlockGoal
        Random random = enderman.getRNG();
        World world = enderman.world;
        int i = MathHelper.floor(enderman.getPosX() - 2.0D + random.nextDouble() * 4.0D);
        int j = MathHelper.floor(enderman.getPosY() + random.nextDouble() * 3.0D);
        int k = MathHelper.floor(enderman.getPosZ() - 2.0D + random.nextDouble() * 4.0D);
        BlockPos blockpos = new BlockPos(i, j, k);
        BlockState blockstate = world.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (block != Blocks.COCOA) {
            // Decided to pick up something that wasn't cocoa. Just fall back to the vanilla code
            return;
        }
        int cocoaAge = blockstate.get(BlockStateProperties.AGE_0_2);
        if (cocoaAge != 2) {
            // We don't want endermen repeatedly breaking young cocoa because it drops the cocoa on the ground and gets it as a block, so quickly picking up a
            // young cocoa and replacing it would effectively duplicate it. Getting an extra one from a fully grown cocoa isn't a big deal since they don't have
            // fortune and will get less than a player could anyways
            return;
        }
        Vec3d vec3d = new Vec3d((double) MathHelper.floor(enderman.getPosX()) + 0.5D, (double) j + 0.5D, (double) MathHelper.floor(enderman.getPosZ()) + 0.5D);
        Vec3d vec3d1 = new Vec3d((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D);
        BlockRayTraceResult blockraytraceresult = world
                .rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, enderman));
        if (!blockraytraceresult.getPos().equals(blockpos)) {
            // Can't see the block
            return;
        }
        enderman.setHeldBlockState(Blocks.COCOA.getDefaultState());
        // Destroy block causes it to drop cocoa on the floor as if it was broken by a player w/o fortune. This does cause 1 extra to be
        // created because of the line above
        world.destroyBlock(blockpos, true, enderman);
        // Don't try to pick it up again
        event.setResult(Result.DENY);
    }

    private static void endermanPlaceBlock(EntityMobGriefingEvent event, EndermanEntity enderman) {

        // Place cocoa. Below code is a modified version of the tick event in EndermanEntity.PlaceBlockGoal
        Random random = enderman.getRNG();
        IWorld iworld = enderman.world;
        int i = MathHelper.floor(enderman.getPosX() - 1.0D + random.nextDouble() * 2.0D);
        int j = MathHelper.floor(enderman.getPosY() + random.nextDouble() * 2.0D);
        int k = MathHelper.floor(enderman.getPosZ() - 1.0D + random.nextDouble() * 2.0D);
        BlockPos blockpos = new BlockPos(i, j, k);
        BlockState blockstate = iworld.getBlockState(blockpos);
        Tuple<BlockPos, Direction> adjacentLog = jungleLogAdjacent(iworld, blockpos, enderman.getHorizontalFacing());
        if (adjacentLog == null) {
            return;
        }
        BlockPos jungleLogPos = adjacentLog.getA();
        BlockState jungleLogState = iworld.getBlockState(jungleLogPos);
        BlockState heldBlockState = enderman.getHeldBlockState();
        if (heldBlockState != null && canPlaceBlock(iworld, blockpos, heldBlockState, blockstate, jungleLogState, jungleLogPos)
                && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(enderman,
                        new net.minecraftforge.common.util.BlockSnapshot(iworld, blockpos, jungleLogState), adjacentLog.getB())) {
            iworld.setBlockState(blockpos, heldBlockState, 3);
            enderman.setHeldBlockState((BlockState) null);
        }
        // We have already done the effect, so we don't want the vanilla place block code to run
        event.setResult(Result.DENY);
    }

    /*
     * Helper function for determining if a block can be placed in a given spot Code adapted from EndermanEntity, with vars renamed to human readable ones
     */
    private static boolean canPlaceBlock(IWorldReader iWorld, BlockPos locationToPlace, BlockState heldBlock, BlockState placementLocationBlockState,
            BlockState blockToPlaceOnBlockState, BlockPos blockToPlaceOnPlacement) {
        return placementLocationBlockState.isAir(iWorld, locationToPlace) && !blockToPlaceOnBlockState.isAir(iWorld, blockToPlaceOnPlacement)
                && blockToPlaceOnBlockState.isCollisionShapeOpaque(iWorld, blockToPlaceOnPlacement) && heldBlock.isValidPosition(iWorld, locationToPlace);
    }

    private static Tuple<BlockPos, Direction> jungleLogAdjacent(IWorldReader iWorld, BlockPos locationToPlace, Direction direction) {
        BlockPos west = locationToPlace.add(0, 0, -1);
        BlockPos east = locationToPlace.add(0, 0, 1);
        BlockPos north = locationToPlace.add(1, 0, 0);
        BlockPos south = locationToPlace.add(-1, 0, 0);
        if (iWorld.getBlockState(west).getBlock().isIn(BlockTags.JUNGLE_LOGS)) {
            return new Tuple<BlockPos, Direction>(west, Direction.WEST);
        } else if (iWorld.getBlockState(east).getBlock().isIn(BlockTags.JUNGLE_LOGS)) {
            return new Tuple<BlockPos, Direction>(east, Direction.EAST);
        } else if (iWorld.getBlockState(north).getBlock().isIn(BlockTags.JUNGLE_LOGS)) {
            return new Tuple<BlockPos, Direction>(north, Direction.NORTH);
        } else if (iWorld.getBlockState(south).getBlock().isIn(BlockTags.JUNGLE_LOGS)) {
            return new Tuple<BlockPos, Direction>(south, Direction.SOUTH);
        }
        return null;
    }
}
