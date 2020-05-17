package com.nhwhite3118.cobbler;

import com.nhwhite3118.cobbler.structures.Structures;
//import com.nhwhite3118.cobbler.utils.ConfigHelper;
//import com.nhwhite3118.cobbler.utils.ConfigHelper.ConfigValueListener;

import net.minecraft.world.gen.feature.Feature;
//import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Cobbler.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterFeatures(final RegistryEvent.Register<Feature<?>> event)
	{
		Structures.registerFeatures(event);
	}
}
