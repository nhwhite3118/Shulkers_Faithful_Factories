package com.nhwhite3118.cobbler.structures;

import org.apache.logging.log4j.Level;

import com.mojang.serialization.Codec;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ShulkerFactoryStructure extends Structure<NoFeatureConfig> {

    public ShulkerFactoryStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    /*
     * Determines the valid chunks that this structure can spawn in.
     * 
     * This is using vanilla's default algorithm to determine chunks that this structure can spawn in.
     * 
     */
//    @Override
//    public final ChunkPos func_236392_a_(StructureSeparationSettings p_236392_1_, long p_236392_2_, SharedSeedRandom p_236392_4_, int p_236392_5_,
//            int p_236392_6_) {
//        int i = p_236392_1_.func_236668_a_();
//        int j = p_236392_1_.func_236671_b_();
//        int k = Math.floorDiv(p_236392_5_, i);
//        int l = Math.floorDiv(p_236392_6_, i);
//        p_236392_4_.setLargeFeatureSeedWithSalt(p_236392_2_, k, l, p_236392_1_.func_236673_c_());
//        int i1;
//        int j1;
//        if (this.func_230365_b_()) {
//           i1 = p_236392_4_.nextInt(i - j);
//           j1 = p_236392_4_.nextInt(i - j);
//        } else {
//           i1 = (p_236392_4_.nextInt(i - j) + p_236392_4_.nextInt(i - j)) / 2;
//           j1 = (p_236392_4_.nextInt(i - j) + p_236392_4_.nextInt(i - j)) / 2;
//        }
//
//        return new ChunkPos(k * i + i1, l * i + j1);
//    }

//    protected ChunkPos func_236392_a_(ChunkGenerator chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
//        // ~10 end cities generate in a 200x200 chunk square. Factories should be ~10x
//        // rarer
//
//        int maxDistance = Cobbler.CobblerConfig.shulkerFactorySpawnrate.get();
//        int minDistance = (int) (maxDistance * 0.75f);
//
//        int xTemp = x + maxDistance * spacingOffsetsX;
//        int ztemp = z + maxDistance * spacingOffsetsZ;
//        int xTemp2 = xTemp < 0 ? xTemp - maxDistance + 1 : xTemp;
//        int zTemp2 = ztemp < 0 ? ztemp - maxDistance + 1 : ztemp;
//        int validChunkX = xTemp2 / maxDistance;
//        int validChunkZ = zTemp2 / maxDistance;
//
//        ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), validChunkX, validChunkZ, this.getSeedModifier());
//        validChunkX = validChunkX * maxDistance;
//        validChunkZ = validChunkZ * maxDistance;
//        validChunkX = validChunkX + random.nextInt(maxDistance - minDistance);
//        validChunkZ = validChunkZ + random.nextInt(maxDistance - minDistance);
//
//        return new ChunkPos(validChunkX, validChunkZ);
//    }

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

    /*
     * This is where all the checks will be done to determine if the structure can spawn here.
     * 
     * Notice how the biome is also passed in. While you could do manual checks on the biome to see if you can spawn here, that is highly discouraged. Instead,
     * you should do the biome check in the FMLCommonSetupEvent event (setup method in StructureTutorialMain) and add your structure to the biome with
     * .addFeature and .addStructure methods.
     * 
     * Instead, this method is best used for determining if the chunk position itself is valid, if certain other structures are too close or not, or some other
     * restrictive condition.
     *
     * For example, Pillager Outposts added a check to make sure it cannot spawn within 10 chunk of a Village. (Bedrock Edition seems to not have the same
     * check)
     */
    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeProvider, long seed, SharedSeedRandom random, int x, int z, Biome biome,
            ChunkPos chunkPos, NoFeatureConfig config) {
        random.setLargeFeatureSeed(seed, x, z);

        // ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);

        int spawnRate = Cobbler.CobblerConfig.shulkerFactorySpawnrate.get();
        if (spawnRate == 0 || !Cobbler.CobblerConfig.spawnShulkerFactories.get()) {
            return false;
        }
        // Checks to see if current chunk is valid to spawn in.
        if (random.nextDouble() < 1.0 / (double) spawnRate) {
            if (biomeProvider.hasStructure(this)) {
                return true;
            }
        }

        return false;
    }

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
