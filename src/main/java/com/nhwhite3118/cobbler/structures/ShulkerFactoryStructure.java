package com.nhwhite3118.cobbler.structures;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class ShulkerFactoryStructure extends Structure<NoFeatureConfig> {

    public ShulkerFactoryStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    /*
     * The structure name to show in the /locate command.
     * 
     * Make sure this matches what the resourcelocation of your structure will be because if you don't add the MODID: part, Minecraft will put minecraft: in
     * front of the name instead and we don't want that. We want our structure to have our mod's ID rather than Minecraft so people don't get confused.
     */
    @Override
    public String getStructureName() {
        return Cobbler.MODID + ":shulker_factory";
    }

    /*
     * This is how the worldgen code will start the generation of our structure when it passes the checks.
     */
    @Override
    public Structure.IStartFactory getStartFactory() {
        return ShulkerFactoryStructure.Start::new;
    }

    /*
     * This is used so that if two structure's has the same spawn location algorithm, they will not end up in perfect sync as long as they have different seed
     * modifier.
     * 
     * So make this a big random number that is unique only to this structure.
     */
    protected int getSeedModifier() {
        return 261892189;
    }

    @Override
    public BlockPos func_236388_a_(IWorldReader worldView, StructureManager structureAccessor, BlockPos blockPos, int radius, boolean skipExistingChunks,
            long seed, StructureSeparationSettings structureConfig) {
        return locateStructureFast(worldView, structureAccessor, blockPos, radius, skipExistingChunks, seed, structureConfig, this);
    }

    public static BlockPos locateStructureFast(IWorldReader worldView, StructureManager structureAccessor, BlockPos blockPos, int radius,
            boolean skipExistingChunks, long seed, StructureSeparationSettings structureConfig, Structure<NoFeatureConfig> structure) {
        int spacing = structureConfig.func_236668_a_();
        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;
        int currentRadius = 0;

        for (SharedSeedRandom chunkRandom = new SharedSeedRandom(); currentRadius <= 100000; ++currentRadius) {
            for (int xRadius = -currentRadius; xRadius <= currentRadius; ++xRadius) {
                boolean xEdge = xRadius == -currentRadius || xRadius == currentRadius;

                for (int zRadius = -currentRadius; zRadius <= currentRadius; ++zRadius) {
                    boolean zEdge = zRadius == -currentRadius || zRadius == currentRadius;
                    if (xEdge || zEdge) {
                        int trueChunkX = chunkX + spacing * xRadius;
                        int trueChunkZ = chunkZ + spacing * zRadius;
                        ChunkPos chunkPos = structure.func_236392_a_(structureConfig, seed, chunkRandom, trueChunkX, trueChunkZ);
                        if (worldView.getNoiseBiome((chunkPos.x << 2) + 2, 60, (chunkPos.z << 2) + 2).hasStructure(structure)) {
                            IChunk chunk = worldView.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_STARTS);
                            StructureStart<?> structureStart = structureAccessor.func_235013_a_(SectionPos.from(chunk.getPos(), 0), structure, chunk);
                            if (structureStart != null && structureStart.isValid()) {
                                if (skipExistingChunks && structureStart.isRefCountBelowMax()) {
                                    structureStart.incrementRefCount();
                                    return structureStart.getPos();
                                }

                                if (!skipExistingChunks) {
                                    return structureStart.getPos();
                                }
                            }
                        }
                    } else {
                        zRadius = currentRadius - 1;
                    }
                }
            }
        }

        return null;
    }

//    /*
//     * This is where all the checks will be done to determine if the structure can spawn here.
//     * 
//     * Notice how the biome is also passed in. While you could do manual checks on the biome to see if you can spawn here, that is highly discouraged. Instead,
//     * you should do the biome check in the FMLCommonSetupEvent event (setup method in StructureTutorialMain) and add your structure to the biome with
//     * .addFeature and .addStructure methods.
//     * 
//     * Instead, this method is best used for determining if the chunk position itself is valid, if certain other structures are too close or not, or some other
//     * restrictive condition.
//     *
//     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village. (Bedrock Edition seems to not have the same
//     * check)
//     */
//    @Override
//    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom random, int x, int z, Biome biome,
//            ChunkPos chunkPos, NoFeatureConfig config) {
//        random.setLargeFeatureSeed(seed, x, z);
//
//        // ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
//
//        double spawnRate = (double) Cobbler.CobblerConfig.shulkerFactorySpawnrate.get();
//        if (spawnRate == 0 || !Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
//            return false;
//        }
////        // This should give the same rarity as before when we used separation
////        double temp = (spawnRate + spawnRate * 0.75) / 2;
////        double adjustedSpawnRate = temp * temp;
////        // Checks to see if current chunk is valid to spawn in.
////        if (random.nextDouble() < 1.0 / adjustedSpawnRate) {
////            if (biomeProvider.hasStructure(this)) {
////                return true;
////            }
////        }
//
//        return true;
//    }

    /*
     * Handles calling up the structure's pieces class and height that structure will spawn at.
     */
    public static class Start extends StructureStart {
        public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void func_230364_a_(ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome p_230364_5_,
                IFeatureConfig config) {
            // Check out vanilla's WoodlandMansionStructure for how they offset the x and z
            // so that they get the y value of the land at the mansion's entrance, no matter
            // which direction the mansion is rotated.
            //
            // However, for most purposes, getting the y value of land with the default x
            // and z is good enough.
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];

            // Turns the chunk coordinates into actual coordinates we can use. (Gets center
            // of that chunk)
            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            // Finds the y value of the terrain at location.
            int surfaceY = generator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            if (surfaceY < 30) {
                return;
            }
            BlockPos blockpos = new BlockPos(x, surfaceY, z);

            // Now adds the structure pieces to this.components with all details such as
            // where each part goes
            // so that the structure can be added to the world by worldgen.
            ShulkerFactoryPieces.start(templateManagerIn, blockpos, rotation, this.components, this.rand);

            // Sets the bounds of the structure.
            this.recalculateStructureSize();

            // I use to debug and quickly find out if the structure is spawning or not and
            // where it is.
            Cobbler.LOGGER.log(Level.DEBUG, "Shulker Factory at " + (blockpos.getX()) + " " + blockpos.getY() + " " + (blockpos.getZ()));
        }

    }

}
