package com.nhwhite3118.cobbler;

import com.nhwhite3118.cobbler.structures.Structures;

import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Cobbler.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

    @SubscribeEvent
    public static void onRegistery(final RegistryEvent.Register<Feature<?>> event) {
        Structures.registerStructures(event);
    }
}
