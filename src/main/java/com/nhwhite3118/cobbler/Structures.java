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
		if(biome.getCategory() == Category.THEEND  && biome != Biomes.THE_END && biome != Biomes.SMALL_END_ISLANDS)
		{
			//func_225566_b_ appears to create a configured feature from a feature and config
			biome.addFeature(Decoration.SURFACE_STRUCTURES, Cobbler.SHULKER_FACTORY.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		}
	}
}
