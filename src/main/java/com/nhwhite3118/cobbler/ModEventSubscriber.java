package com.nhwhite3118.cobbler;

import com.nhwhite3118.cobbler.utils.ConfigHelper;
import com.nhwhite3118.cobbler.utils.ConfigHelper.ConfigValueListener;

import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Cobbler.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event)
	{
		Cobbler.registerFeatures(event);
	}

	public static class Config {
		public ConfigValueListener<Boolean> shulkerFactorys;
		public ConfigValueListener<Integer> shulkerFactorySpawnrate;
		
		Config(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
		{
			builder.push("Feature Options");
				builder.push("Dungeons");
				shulkerFactorySpawnrate = subscriber.subscribe(builder
							.comment( "\r\n How often dungeons will attempt to spawn per chunk.\r\n " 
									+ "1 for extremely rare Dungeons and 1000 for max Dungeon spawnrate.")
							.translation("cobbler.config.structure.shulkerFactory.shulkerFactoryspawnrate")
							.defineInRange("shulkerFactoryspawnrate", 8, 1, 1000));
				
					shulkerFactorys = subscriber.subscribe(builder
							.comment("\r\n Add End themed dungeon with shulker spawner to End biomes outside the Enderdragon island.")
						.translation("cobbler.config.feature.dungeons.shulkerFactorys")
						.define("shulkerFactorys", true));
					
				builder.pop();
			builder.pop();
		}
	}
}
