package com.nhwhite3118.cobbler;

import java.util.EnumSet;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreateWebGoal extends Goal {
    private final MobEntity idleEntity;
    private double lookX;
    private double lookZ;
    private int idleTime;

    public CreateWebGoal(MobEntity entitylivingIn) {
        this.idleEntity = entitylivingIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this method as well. Spiders will only spin webs
     * in dim light since spiders generally prefer spinning webs at dusk
     */
    public boolean shouldExecute() {
        World world = idleEntity.getEntityWorld();
        if (!Cobbler.CobblerConfig.spidersCanSpinWebs.get() || world == null || world.isRemote()) {
            return false;
        }
        int lightLevel = world.getLight(this.idleEntity.getPosition());
        return this.idleEntity.getRNG().nextFloat() * 100000 < Cobbler.CobblerConfig.webSpinningFrequency.get()
                && lightLevel >= Cobbler.CobblerConfig.webSpinningMinLightLevel.get() && lightLevel <= Cobbler.CobblerConfig.webSpinningMaxLightLevel.get();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (this.idleTime < 0) {
            spawnWeb();
        }
        return this.idleTime >= 0;
    }

    /**
     * Checks that the world isn't remote (block placement must be server side only), and places a cobweb if there is a suitable adjacent surface
     */
    public void spawnWeb() {
        World world = idleEntity.getEntityWorld();
        if (world == null || world.isRemote()) {
            return;
        }

        BlockPos currentPosition = this.idleEntity.getPosition();
        BlockState currentPositionState = world.getBlockState(currentPosition);
        if (currentPositionState.getBlock() != Blocks.AIR || (!Cobbler.CobblerConfig.websEverywhere.get() && !isValidCobwebLocation(world, currentPosition))) {
            return;
        }
        world.setBlockState(currentPosition, Blocks.COBWEB.getDefaultState());
    }

    /*
     * Responsible for determining which types of blocks can have cobwebs spun on them. Not limiting it to trees and other webs makes them spin webs everywhere
     * which would annoy most players
     */

    private boolean isValidCobwebLocation(World world, BlockPos blockPos) {
        BlockState adjacent[] = { world.getBlockState(blockPos.east()), world.getBlockState(blockPos.west()), world.getBlockState(blockPos.south()),
                world.getBlockState(blockPos.north()), world.getBlockState(blockPos.up()), world.getBlockState(blockPos.down()) };

        for (BlockState blockState : adjacent) {
            if (blockState.getBlock().isIn(BlockTags.LEAVES) || blockState.getBlock().isIn(BlockTags.LOGS) || blockState.getBlock() == Blocks.COBWEB
                    || blockState.getBlock() == Blocks.SPAWNER) {
                return true;
            }
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        double d0 = (Math.PI * 2D) * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        --this.idleTime;
        this.idleEntity.getLookController().setLookPosition(this.idleEntity.getPosX() + this.lookX, this.idleEntity.getPosYEye(),
                this.idleEntity.getPosZ() + this.lookZ);
    }
}
