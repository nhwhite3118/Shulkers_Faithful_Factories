package com.nhwhite3118.cobbler;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nhwhite3118.cobbler.CobblerConfig.CobblerConfigValues;
import com.nhwhite3118.cobbler.structures.ConfiguredStructures;
import com.nhwhite3118.cobbler.structures.Structures;
import com.nhwhite3118.cobbler.utils.ConfigHelper;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

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
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addGenericListener(Structure.class, this::onRegisterStructures);

        // For events that happen after initialization. This is probably going to be use a lot.
        MinecraftForge.EVENT_BUS.register(this);
        CobblerConfig = ConfigHelper.register(ModConfig.Type.COMMON, CobblerConfig.CobblerConfigValues::new);
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);

        // The comments for BiomeLoadingEvent and StructureSpawnListGatherEvent says to do HIGH for additions.
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    /**
     * This method will be called by Forge when it is time for the mod to register features.
     */
    public void onRegisterStructures(final RegistryEvent.Register<Structure<?>> event) {
        // Registers the structures.
        // If you don't do this, bad things might happen... very bad things... Spooky...
        LOGGER.log(Level.INFO, "onRegisterStructures start");
        Structures.registerStructures(event);
        ConfiguredStructures.registerConfiguredStructures();
        LOGGER.log(Level.INFO, "onRegisterStructures end");

        // LOGGER.log(Level.INFO, "structures registered.");
    }

    /**
     * This is the event you will use to add anything to any biome. This includes spawns, changing the biome's looks, messing with its surfacebuilders, adding
     * carvers, spawning new features... etc
     *
     * Here, we will use this to add our structure to all biomes.
     */
    public void biomeModification(final BiomeLoadingEvent event) {
        // Add our structure to biomes including other modded biomes

        if (Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
            if (event.getCategory() == Category.THEEND && event.getName() != Biomes.THE_END.getLocation()
                    && event.getName() != Biomes.SMALL_END_ISLANDS.getLocation()) {

                event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_SHULKER_FACTORY);
            }
        }
    }

    /**
     * Will go into the world's chunkgenerator and manually add our structure spacing. If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure. (Don't forget to attempt to remove your structure too from the map if you are blacklisting that
     * dimension! It might have your structure in it already.)
     *
     * Basically use this to mak absolutely sure the chunkgeneration can or cannot spawn your structure.
     */
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        LOGGER.log(Level.INFO, "addDimensionalSpacing start");
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            // Prevent spawning our structure in Vanilla's superflat world as
            // people seem to want their superflat worlds free of modded structures.
            // Also that vanilla superflat is really tricky and buggy to work with in my experience.
            if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
                return;
            }

            // Only The End and modded biomes
            if (serverWorld.getDimensionKey().equals(World.OVERWORLD) || serverWorld.getDimensionKey().equals(World.THE_NETHER)) {
                return;
            }
            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.put(Structures.SHULKER_FACTORY, DimensionStructuresSettings.field_236191_b_.get(Structures.SHULKER_FACTORY));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
        LOGGER.log(Level.INFO, "addDimensionalSpacing end");
    }

    /*
     * Helper method to quickly register features, blocks, items, structures, biomes, anything that can be registered.
     */
    public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T entry, String registryKey) {
        LOGGER.log(Level.INFO, "register start");
        entry.setRegistryName(new ResourceLocation(Cobbler.MODID, registryKey));
        registry.register(entry);
        LOGGER.log(Level.INFO, "register end");
        return entry;
    }
}
