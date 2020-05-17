package com.nhwhite3118.cobbler;

import com.nhwhite3118.cobbler.utils.ConfigHelper;
import com.nhwhite3118.cobbler.utils.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CobblerConfig
{
	public static class CobblerConfigValues
	{
		public ConfigValueListener<Boolean> spawnShulkerFactories;
		public ConfigValueListener<Integer> shulkerFactorySpawnrate;

		CobblerConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("Feature Options");

				builder.push("EndStructures");
				
				spawnShulkerFactories = subscriber.subscribe(builder
							.comment("\r\n Whether or not to spawn Shulker Factories - End City inspired structures with a shulker spawner")
						.translation("repurposedstructures.config.feature.endStructures.addshulkerfactories")
						.define("shulkerFactorys", true));
					
				shulkerFactorySpawnrate = subscriber.subscribe(builder
							.comment( "\r\n How often Shulker Factories will attempt to spawn per chunk. " 
									+ "\r\n The chance of a factory generating at a chunk is 1/spawnrate."
									+ "\r\n 10 to practically always have one in render distance, 1000 for extremely rare factories" 
									+ "\r\n 20 is slightly more common than end cities" 
									+ "\r\n Default of 200 should average one every ~7,000 blocks (varies widely)")
							.translation("nhwhite3118.config.structure.endStructures.shulkerfactories")
							.defineInRange("shulkerFactorySpawnrate", 200, 10, 1000));
				builder.pop();
			builder.pop();
		}
	}
}