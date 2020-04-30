package com.nhwhite3118.cobbler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Cobbler.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

	@SubscribeEvent
    public static void onUseBonemeal(BonemealEvent event) {
		Random rand = new Random();
		if(rand.nextFloat() > 0.15) {
			return;
		}
		World world = event.getWorld();

		if(world.isRemote) {
			return;
		}
		
		int x = event.getPos().getX();
		int y = event.getPos().getY();
		int z = event.getPos().getZ();
		int xOffset = rand.nextInt(7) - 3;
		int zOffset = rand.nextInt(7) - 3;
		int yOffset = rand.nextInt(3) - 1;
		//Placing a sapling over the event block will cause the default bonemeal listener to do nothing.
		if(xOffset == 0 && zOffset == 0 && yOffset == 0) {
			return;
		}
		BlockPos potentialTreePos = new BlockPos(x + xOffset,y + yOffset + 1,z + zOffset);
		BlockPos potentialSoilPos = new BlockPos(x + xOffset,y+ yOffset,z + zOffset);
		BlockState potentialTree = world.getBlockState(potentialTreePos);
		BlockState potentialSoil = world.getBlockState(potentialSoilPos);
		
    	if(potentialSoil != null && potentialSoil.canSustainPlant(world, potentialTreePos, Direction.UP, (IPlantable) Blocks.OAK_SAPLING) ) {
    		if(potentialTree != null && potentialTree.getBlock() == Blocks.AIR || potentialTree.getBlock() == Blocks.GRASS) {
    			world.setBlockState(potentialTreePos, Blocks.OAK_SAPLING.getDefaultState());
    		}
    	}
    }
}
