package com.nhwhite3118.cobbler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.TaigaHillsBiome;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Cobbler.MODID, bus = EventBusSubscriber.Bus.FORGE)
public final class ForgeEventSubscriber {

	@SubscribeEvent
    public static void onUseBonemeal(BonemealEvent event) {
		World world = event.getWorld();

		int x = event.getPos().getX();
		int y = event.getPos().getY();
		int z = event.getPos().getZ();
		BlockPos potentialTree = new BlockPos(x+1,y+1,z);
		BlockPos potentialSoil = new BlockPos(x+1,y,z);
		
		Random rand = new Random();
		if(rand.nextFloat() > 0.05) {
			
		}
    	if(event.getBlock() != null && event.getBlock().canSustainPlant(world, potentialSoil, Direction.UP, (IPlantable) Blocks.OAK_SAPLING) ) {
    		if(world.isAirBlock(potentialTree) || world.getBlockState(potentialTree).getBlock() == Blocks.GRASS) {
    			world.setBlockState(potentialTree, Blocks.OAK_SAPLING.getDefaultState());
    		}
    	}
    }
}
