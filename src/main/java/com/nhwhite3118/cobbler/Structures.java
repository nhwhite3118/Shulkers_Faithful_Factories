package com.nhwhite3118.cobbler;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class Structures {

	
	public static void generateShulkerFactory(Biome biome, String biomeNamespace, String biomePath)
	{
		biome.addFeature(Decoration.SURFACE_STRUCTURES, Cobbler.SHULKER_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		
		if(biome.getCategory() == Category.THEEND  && biome != Biomes.THE_END && biome != Biomes.SMALL_END_ISLANDS)
		{
			biome.addStructure(Cobbler.SHULKER_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		}
	}
}
