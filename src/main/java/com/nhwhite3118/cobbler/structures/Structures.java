package com.nhwhite3118.cobbler.structures;

import java.util.Locale;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;

public class Structures {
    public static Structure<NoFeatureConfig> SHULKER_FACTORY = new ShulkerFactoryStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType SHULKER_FACTORY_PIECE_TYPE = com.nhwhite3118.cobbler.structures.ShulkerFactoryPieces.Piece::new;

    /*
     * Adds structures to all the registries and maps which they need to be in for them to work properly.
     */
    public static void registerStructures(Register<Structure<?>> event) {
        int shulkerFactorySpawnRate = Cobbler.CobblerConfig.shulkerFactorySpawnrate.get();
        if (shulkerFactorySpawnRate == 0 || !Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
            return;
        }

        Cobbler.register(event.getRegistry(), SHULKER_FACTORY, "shulker_factory");
        /*
         * IMPORTANT: Once you have set the name for your structure below and distributed your mod, it should NEVER be changed or else it can cause worlds to
         * become corrupted if they generated any chunks with your mod with the old structure name. See MC-194811 in Mojang's bug tracker for details.
         *
         * Forge has an issue report here: https://github.com/MinecraftForge/MinecraftForge/issues/7363 Keep watch on that to know when it is safe to remove or
         * change structure's registry names
         */

        registerStructure(new ResourceLocation(Cobbler.MODID, "shulker_factory"), SHULKER_FACTORY, GenerationStage.Decoration.SURFACE_STRUCTURES,
                new StructureSeparationSettings(shulkerFactorySpawnRate, (int) (shulkerFactorySpawnRate * 0.75f), 261892189));
        Structures.registerPieces();
    }

    /*
     * Adds the provided structure to the registry, and adds the separation settings. This is how the rarity of the structure is set.
     */
    public static <F extends Structure<NoFeatureConfig>> void registerStructure(ResourceLocation resourceLocation, F structure,
            GenerationStage.Decoration stage, StructureSeparationSettings structureSeparationSettings) {

        /*
         * We need to add our structures into the map in Structure alongside vanilla structures or else it will cause errors. Called by registerStructure.
         *
         * (If you are using deferred registries, do not put this line inside the deferred method. Instead, call it anywhere else before you start the
         * configuredstructure registering)
         */
        Structure.field_236365_a_.put(structure.getRegistryName().toString(), structure);

        /*
         * Adds the structure's spacing into several places so that the structure's spacing remains correct in any dimension or worldtype instead of not
         * spawning.
         *
         * However, it seems it doesn't always work for code made dimensions as they read from this list beforehand. Use the WorldEvent.Load event in
         * StructureTutorialMain to add the structure spacing from this list into that dimension.
         */
        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings).build();
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    public static void registerPieces() {
        // It looks like changing this after generating a structure in the world throws
        // a ton of errors, so I'm leaving it as-is
        register(SHULKER_FACTORY_PIECE_TYPE, "RDHP");
    }

    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }
}
