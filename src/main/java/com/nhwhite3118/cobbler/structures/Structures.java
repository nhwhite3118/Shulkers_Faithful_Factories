package com.nhwhite3118.cobbler.structures;

import java.util.Locale;

import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Structures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Cobbler.MODID);
    public static final RegistryObject<Structure<NoFeatureConfig>> SHULKER_FACTORY_REGISTRY = register("shulker_factory",
            new ShulkerFactoryStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.UNDERGROUND_STRUCTURES);
    // public static final RegistryObject<Structure<NoFeatureConfig>> TEST_STRUCTURE_REGISTRY = register(Cobbler.MODID, new
    // TestStructure(NoFeatureConfig.field_236558_a_), GenerationStage.Decoration.UNDERGROUND_STRUCTURES);

    // public static Structure<NoFeatureConfig> SHULKER_FACTORY = new ShulkerFactoryStructure(NoFeatureConfig::deserialize);
    // public static Structure<NoFeatureConfig> TEST_STRUCTURE = new TestStructure(NoFeatureConfig::deserialize);
    public static IStructurePieceType FOR_REGISTERING_SHULKER_FACTORY = com.nhwhite3118.cobbler.structures.ShulkerFactoryPieces.Piece::new;
    // public static IStructurePieceType FOR_REGISTERING_TEST_STRUCTURE = com.nhwhite3118.cobbler.structures.TestStructurePieces.Piece::new;

    private static <T extends Structure<?>> RegistryObject<T> register(String name, T structure, GenerationStage.Decoration stage) {
        Structure.field_236385_u_.put(structure, stage);
        Structure.field_236365_a_.put(name, structure);
        return STRUCTURES.register(name.toLowerCase(Locale.ROOT), () -> structure);
    }

    public static void init() {
        STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void registerStructures() {
        // It looks like changing this after generating a structure in the world throws
        // a ton of errors, so I'm leaving it as-is
        register(FOR_REGISTERING_SHULKER_FACTORY, "RDHP");
//        if (Cobbler.ENVIRONMENT == Cobbler.ENVIRONMENTS.DEBUG) {
//            register(FOR_REGISTERING_TEST_STRUCTURE, "TS");
//        }
    }

//    public static void registerFeatures(Register<Feature<?>> event) {
//        IForgeRegistry<Feature<?>> registry = event.getRegistry();
//        RegUtil.register(registry, Structures.SHULKER_FACTORY, "shulker_factory");
//        Structures.registerStructures();
//    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }

    public static void generateShulkerFactory(Biome biome, String biomeNamespace, String biomePath) {
        if (biome.getCategory() == Category.THEEND && biome != Biomes.THE_END && biome != Biomes.SMALL_END_ISLANDS) {
            biome.func_235063_a_(SHULKER_FACTORY_REGISTRY.get().func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    };

//    public static void generateTestStructures(Biome biome, String biomeNamespace, String biomePath) {
//        biome.addFeature(Decoration.SURFACE_STRUCTURES, TEST_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
//                .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//        biome.addStructure(TEST_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
//    }
}
