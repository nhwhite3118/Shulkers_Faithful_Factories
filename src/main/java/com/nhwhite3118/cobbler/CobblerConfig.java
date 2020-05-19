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
		public ConfigValueListener<Boolean> bonemealCanSpawnSapling;
		public ConfigValueListener<Integer> bonemealSaplingSpawnrate;
		public ConfigValueListener<Integer> bonemealSaplingSpawnAttempts;
		public ConfigValueListener<Boolean> desertSaplingsCanBecomeDeadBush;
		public ConfigValueListener<Integer> desertSaplingsMaxDeathrate;
		public ConfigValueListener<Integer> desertSaplingsMinDeathrate;
		
		public ConfigValueListener<Boolean> spawnShulkerFactories;
		public ConfigValueListener<Integer> shulkerFactorySpawnrate;
		public ConfigValueListener<Boolean> addMapsToShulkerFactoriesToEndCities;
		public ConfigValueListener<Integer> shulkerFactoryMapWeight;

		CobblerConfigValues(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("Feature Options");

				builder.push("EndStructures");
				
					spawnShulkerFactories = subscriber.subscribe(builder
							.comment("\r\n Whether or not to spawn Shulker Factories - End City inspired structures with a shulker spawner"
									+ "\r\n Default value is true")
							.translation("repurposedstructures.config.feature.endStructures.addshulkerfactories")
							.define("shulkerFactorys", true));
						
					shulkerFactorySpawnrate = subscriber.subscribe(builder
								.comment( "\r\n How often Shulker Factories will attempt to spawn per chunk. " 
										+ "\r\n The chance of a factory generating at a chunk is 1/spawnrate."
										+ "\r\n 10 to practically always have one in render distance, 1000 for extremely rare factories" 
										+ "\r\n 20 is slightly more common than end cities" 
										+ "\r\n Default value of 200 should average one every ~7,000 blocks (varies widely)")
								.translation("nhwhite3118.config.structure.endStructures.shulkerfactories")
								.defineInRange("shulkerFactorySpawnrate", 200, 10, 1000));
				
					addMapsToShulkerFactoriesToEndCities = subscriber.subscribe(builder
							.comment("\r\n Whether or not to add a map, similar to the ones which cartographers sell, to the loot table for"
									+ "\r\n End City Chests (and by extention very rarely within shulker factory chests)"
									+ "\r\n Default value is true")
							.translation("repurposedstructures.config.feature.endStructures.addMapsToShulkerFactoriesToEndCities")
							.define("addMapsToShulkerFactoriesToEndCities", true));
						
					shulkerFactoryMapWeight = subscriber.subscribe(builder
								.comment( "\r\n The weight of shulker factory maps on the End City loot table" 
										+ "\r\n By default, the sum of the weights of other items in the loot table is ~80, and the number of rolls per chest is 2-6"
										+ "\r\n If you have other mods installed which add items to the loot table, the probability of rolling a factory map will go down" 
										+ "\r\n Default value is 5. With no other mods installed, this translates to ~20% chance of getting a map from an End City chest")
								.translation("nhwhite3118.config.structure.endStructures.shulkerFactoryMapWeight")
								.defineInRange("shulkerFactoryMapWeight", 5, 1, 1000));
				builder.pop();

				builder.push("Farmability");
				
					bonemealCanSpawnSapling = subscriber.subscribe(builder
							.comment("\r\n Whether or not bonemeal can spawn oak saplings"
									+ "\r\n Default value is true")
							.translation("repurposedstructures.config.feature.farmability.bonemealCanSpawnSapling")
							.define("bonemealCanSpawnSapling", true));
					
					bonemealSaplingSpawnrate = subscriber.subscribe(builder
							.comment( "\r\n Chance of attempting to spawn an oak sapling on a random block in a 7x3x7 area centered on the bonemealed block when bonemealing grass."
									+ "\r\n The probability of attempting to spawn an oak sapling each time gass is bonemealed is 1/spawnrate."
									+ "\r\n The probability of a sapling appearing is 1 - (((1 - ((plantableBlocksInRadius/147) * 1/bonemealSaplingSpawnRate))) ^ bonemealSaplingSpawnAttempts)"
									+ "\r\n Default value is 6")
							.translation("nhwhite3118.config.feature.farmability.bonemealSaplingSpawnrate")
							.defineInRange("bonemealSaplingSpawnrate", 6, 1, 1000));
					
					bonemealSaplingSpawnAttempts = subscriber.subscribe(builder
							.comment( "\r\n How many times to attempt to spawn a sapling when grass is bonemealed. (also max # of saplings which can spawn per bonemeal)"
									+ "\r\n Increasing this value too high may cause performance issues as it is not optimized for a large number of spawn attempts."
									+ "\r\n The probability of a sapling appearing is 1 - (((1 - ((plantableBlocksInRadius/147) * 1/bonemealSaplingSpawnRate))) ^ bonemealSaplingSpawnAttempts)"
									+ "\r\n Default value is 2")
							.translation("nhwhite3118.config.feature.farmability.bonemealSaplingSpawnAttempts")
							.defineInRange("bonemealSaplingSpawnAttempts", 2, 1, 1000));
					
					desertSaplingsCanBecomeDeadBush = subscriber.subscribe(builder
								.comment("\r\n Whether or not saplings can convert to dead bushes in high light levels in the desert"
										+ "\r\n Default value is true")
							.translation("repurposedstructures.config.feature.farmability.desertSaplingsCanBecomeDeadBush")
							.define("desertSaplingsCanBecomeDeadBush", true));
					
					desertSaplingsMaxDeathrate = subscriber.subscribe(builder
							.comment( "\r\n How many times out of 100 a sapling will become a dead bush instead of a tree at light level 15 in the desert"
									+ "\r\n Default value is 75")
							.translation("nhwhite3118.config.feature.farmability.desertSaplingsMaxDeathrate")
							.defineInRange("desertSaplingsMaxDeathrate", 75, 1, 100));
					
					desertSaplingsMinDeathrate = subscriber.subscribe(builder
							.comment( "\r\n How many times out of 100 a sapling will become a dead bush instead of a tree at light level 10 in the desert"
									+ "\r\n Default value is 25")
							.translation("nhwhite3118.config.feature.farmability.desertSaplingsMinDeathrate")
							.defineInRange("desertSaplingsMinDeathrate", 25, 1, 100));
				
				builder.pop();
				
			builder.pop();
		}
	}
}