package com.nhwhite3118.cobbler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhwhite3118.cobbler.CobblerConfig.CobblerConfigValues;
import com.nhwhite3118.cobbler.structures.Structures;
import com.nhwhite3118.cobbler.utils.ConfigHelper;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

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
        Structures.STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        CobblerConfig = ConfigHelper.register(ModConfig.Type.COMMON, CobblerConfig.CobblerConfigValues::new);
    }

    public void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            Structures.registerStructures();
            Cobbler.addFeaturesAndStructuresToBiomes();
        });
    }

    private static void addFeaturesAndStructuresToBiomes() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            String biomeNamespace = biome.getRegistryName().getNamespace();
            String biomePath = biome.getRegistryName().getPath();

            if (Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
                Structures.generateShulkerFactory(biome, biomeNamespace, biomePath);
            }

//            if (Cobbler.ENVIRONMENT == Cobbler.ENVIRONMENTS.DEBUG) {
//                Structures.generateTestStructures(biome, biomeNamespace, biomePath);
//            }
        }
    }
}
