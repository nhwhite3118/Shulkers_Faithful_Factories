package com.nhwhite3118.cobbler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhwhite3118.cobbler.CobblerConfig.CobblerConfigValues;
import com.nhwhite3118.cobbler.structures.Structures;
import com.nhwhite3118.cobbler.utils.ConfigHelper;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("cobbler")
public class Cobbler {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "cobbler";
    public static CobblerConfigValues CobblerConfig = null;
    public static final ENVIRONMENTS ENVIRONMENT = ENVIRONMENTS.PRODUCTION;

    public enum ENVIRONMENTS {
        DEBUG, PRODUCTION
    };

    public Cobbler() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        CobblerConfig = ConfigHelper.register(ModConfig.Type.COMMON, CobblerConfig.CobblerConfigValues::new);
    }

    @SuppressWarnings("deprecation")
    public void setup(final FMLCommonSetupEvent event) {
        Cobbler.addFeaturesAndStructuresToBiomes();
    }

    private static void addFeaturesAndStructuresToBiomes() {
        for (Biome biome : WorldGenRegistries.field_243657_i) {
            // This is super-super bad. getKey runs in n time on number of biomes, and so does this loop, making it n^2. This only runs once though, so I'll
            // leave it for now
            ResourceLocation biomeRegisteryKey = WorldGenRegistries.field_243657_i.getKey(biome);

            if (Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
                Structures.addShulkerFactoryToBiomes(biome, biomeRegisteryKey);
            }
        }
    }
}
