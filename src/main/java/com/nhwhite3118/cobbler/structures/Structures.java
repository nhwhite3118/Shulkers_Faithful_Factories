package com.nhwhite3118.cobbler.structures;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Structures {
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Cobbler.MODID);
    public static Structure<NoFeatureConfig> SHULKER_FACTORY = new ShulkerFactoryStructure(NoFeatureConfig.field_236558_a_);
    public static IStructurePieceType FOR_REGISTERING_SHULKER_FACTORY_PIECES = com.nhwhite3118.cobbler.structures.ShulkerFactoryPieces.Piece::new;

    public static void registerPieces() {
        // It looks like changing this after generating a structure in the world throws
        // a ton of errors, so I'm leaving it as-is

        register(FOR_REGISTERING_SHULKER_FACTORY_PIECES, "RDHP");
    }

    public static void registerStructures(Register<Feature<?>> event) {
        int shulkerFactorySpawnRate = Cobbler.CobblerConfig.shulkerFactorySpawnrate.get();
        if (shulkerFactorySpawnRate == 0 || !Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
            return;
        }
        registerStructure(new ResourceLocation(Cobbler.MODID, "shulker_factory"), SHULKER_FACTORY, GenerationStage.Decoration.SURFACE_STRUCTURES,
                new StructureSeparationSettings(shulkerFactorySpawnRate, (int) (shulkerFactorySpawnRate * 0.75f), 261892189));
        Structures.registerPieces();
    }

    private static <F extends Structure<?>> F addToStructureInfoMaps(String name, F structure, GenerationStage.Decoration generationStage) {
        Structure.field_236365_a_.put(name.toLowerCase(Locale.ROOT), structure);
        Structure.field_236385_u_.put(structure, generationStage);
        return Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structure);
    }

    public static <F extends Structure<NoFeatureConfig>> void registerStructure(ResourceLocation resourceLocation, F structure,
            GenerationStage.Decoration stage, StructureSeparationSettings StructureSeparationSettings) {
        structure.setRegistryName(resourceLocation);
        addToStructureInfoMaps(resourceLocation.toString(), structure, stage);
        FlatGenerationSettings.STRUCTURES.put(structure, structure.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));

        Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(DimensionStructuresSettings.field_236191_b_);
        tempMap.put(structure, StructureSeparationSettings);
        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.copyOf(tempMap);

        DimensionSettings.Preset.field_236122_b_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236123_c_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236124_d_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236125_e_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236126_f_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        DimensionSettings.Preset.field_236127_g_.func_236137_b_().func_236108_a_().func_236195_a_().put(structure, StructureSeparationSettings);
        // DimensionSettings.Preset.field_236121_a_.forEach(
        // (presetResourceLocation, preset) -> preset.getChunkGeneratorType().getConfig().getStructures().put(structure, StructureSeparationSettings));
    }

    /*
     * Registers the structures pieces themselves. If you don't do this part, Forge will complain to you in the Console.
     */
    private static IStructurePieceType register(IStructurePieceType structurePiece, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), structurePiece);
    }

    public static void generateShulkerFactory(Biome biome, String biomeNamespace, String biomePath) {
        if (biome.getCategory() == Category.THEEND && biome != Biomes.THE_END && biome != Biomes.SMALL_END_ISLANDS) {
            int spawnRate = Cobbler.CobblerConfig.shulkerFactorySpawnrate.get();

            biome.func_235063_a_(SHULKER_FACTORY.func_236391_a_(IFeatureConfig.NO_FEATURE_CONFIG));

        }
    };
}
