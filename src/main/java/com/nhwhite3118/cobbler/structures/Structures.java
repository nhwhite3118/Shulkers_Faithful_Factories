package com.nhwhite3118.cobbler.structures;

import java.util.Locale;

import com.nhwhite3118.cobbler.Cobbler;
import com.nhwhite3118.cobbler.utils.RegUtil;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.IForgeRegistry;

public class Structures {
    public static Structure<NoFeatureConfig> SHULKER_FACTORY = new ShulkerFactoryStructure(
            NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> TEST_STRUCTURE = new TestStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType FOR_REGISTERING_SHULKER_FACTORY = com.nhwhite3118.cobbler.structures.ShulkerFactoryPieces.Piece::new;
    public static IStructurePieceType FOR_REGISTERING_TEST_STRUCTURE = com.nhwhite3118.cobbler.structures.TestStructurePieces.Piece::new;

    public static void registerFeatures(Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        RegUtil.register(registry, Structures.SHULKER_FACTORY, "shulker_factory");

        if (Cobbler.ENVIRONMENT == Cobbler.ENVIRONMENTS.DEBUG) {
            RegUtil.register(registry, Structures.TEST_STRUCTURE, "test_structure");
        }
        Structures.registerStructures();
    }

    public static void registerStructures() {
        // It looks like changing this after generating a structure in the world throws
        // a ton of errors, so I'm leaving it as-is
        register(FOR_REGISTERING_SHULKER_FACTORY, "RDHP");
        if (Cobbler.ENVIRONMENT == Cobbler.ENVIRONMENTS.DEBUG) {
            register(FOR_REGISTERING_TEST_STRUCTURE, "TS");
        }
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge
     * will complain to you in the Console.
     */
    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }

    public static void generateShulkerFactory(Biome biome, String biomeNamespace, String biomePath) {
        biome.addFeature(Decoration.SURFACE_STRUCTURES,
                SHULKER_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));

        if (biome.getCategory() == Category.THEEND && biome != Biomes.THE_END && biome != Biomes.SMALL_END_ISLANDS) {
            biome.addStructure(SHULKER_FACTORY.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public static void generateTestStructures(Biome biome, String biomeNamespace, String biomePath) {
        biome.addFeature(Decoration.SURFACE_STRUCTURES,
                TEST_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
        biome.addStructure(TEST_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }
}
