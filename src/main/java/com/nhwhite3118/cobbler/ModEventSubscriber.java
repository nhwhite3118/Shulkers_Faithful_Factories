package com.nhwhite3118.cobbler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Set;

import com.nhwhite3118.cobbler.structures.Structures;
import com.nhwhite3118.cobbler.structures.placements.Placements;

import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@EventBusSubscriber(modid = Cobbler.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

    @SubscribeEvent
    public static void onRegistery(final RegistryEvent.Register<Feature<?>> event) {
        Structures.registerStructures(event);
    }

    @SubscribeEvent
    public static void onRegisterPlacements(final RegistryEvent.Register<Placement<?>> event) {
        loadConfigs();
        Placements.registerPlacements(event);
    }

    /**
     * Loads RS's configs as Forge won't load configs before registry events.
     */
    private static void loadConfigs() {
        try {
            Field fld = ConfigTracker.class.getDeclaredField("configSets");
            fld.setAccessible(true);
            Class[] partypes = new Class[2];
            partypes[0] = ModConfig.class;
            partypes[1] = Path.class;
            Method method = ConfigTracker.class.getDeclaredMethod("openConfig", partypes);
            method.setAccessible(true);

            for (ModConfig modConfig : ((EnumMap<ModConfig.Type, Set<ModConfig>>) fld.get(ConfigTracker.INSTANCE)).get(ModConfig.Type.COMMON)) {
                if (modConfig.getModId().equals(Cobbler.MODID)) {
                    Object[] arglist = new Object[2];
                    arglist[0] = modConfig;
                    arglist[1] = FMLPaths.CONFIGDIR.get();
                    method.invoke(ConfigTracker.INSTANCE, arglist);
                }
            }

            fld.setAccessible(false);
            method.setAccessible(false);
        } catch (Throwable e) {
            System.err.println(e);
        }
    }
}
