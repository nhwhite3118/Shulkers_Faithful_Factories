package com.nhwhite3118.cobbler;

import java.util.Random;

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
		if(rand.nextFloat() > 0.05) {
			return;
		}
		World world = event.getWorld();

		if(world.isRemote) {
			return;
		}
		
		int x = event.getPos().getX();
		int y = event.getPos().getY();
		int z = event.getPos().getZ();
		int xOffset = rand.nextInt(6) - 3;
		int zOffset = rand.nextInt(6) - 3;
		//Placing a sapling over the event block will cause the default bonemeal listener to do nothing.
		if(xOffset == 0 && zOffset == 0) {
			return;
		}
		BlockPos potentialTree = new BlockPos(x + xOffset,y+1,z + zOffset);
		BlockPos potentialSoil = new BlockPos(x + xOffset,y,z + zOffset);
		
    	if(event.getBlock() != null && event.getBlock().canSustainPlant(world, potentialSoil, Direction.UP, (IPlantable) Blocks.OAK_SAPLING) ) {
    		if(world.isAirBlock(potentialTree) || world.getBlockState(potentialTree).getBlock() == Blocks.GRASS) {
    			world.setBlockState(potentialTree, Blocks.OAK_SAPLING.getDefaultState());
    		}
    	}
    }
}
