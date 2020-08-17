package com.nhwhite3118.cobbler.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

/*
 * Below text from TelepathicGrunt's StructureTutorialMod. Putting it here to remind me to look into Jigsaw Block
 * Sections are heavily borrowed from TelepathicGrunt's tutorial since some sections are 'voodo', and this is my first mod
 * 
 * Also, you might notice that some structures like Pillager Outposts or Woodland Mansions uses a special block called
 * Jigsaw Block to randomize which structure nbt to use to attach to other parts of the structure and still keep it
 * looking clean. This is somewhat complicated and I haven't looks into this yet. But once you're familiar with modding
 * and is pretty experienced with coding, go try and make a structure using Jigsaw blocks! (Look at
 * PillagerOutpostPieces and how it used JigsawPattern and JigsawManager. Once mastered, you will be able to generate
 * massive structures that are unique every time you find one.
 */
public class ShulkerFactoryPieces {
    private static final ResourceLocation ENTRANCE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_entrance");

    private static final ResourceLocation LOW_SPLIT_LEFT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_ONE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var1");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_TWO = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var2");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_THREE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var3");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_FOUR = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var4");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_FIVE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var5");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_SIX = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var6");
    private static final ResourceLocation LOW_SPLIT_LEFT_VAR_SEVEN = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var7");

    private static final ResourceLocation LOW_SPLIT_RIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_ONE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var1");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_TWO = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var2");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_THREE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var3");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_FOUR = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var4");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_FIVE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var5");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_SIX = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var6");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_SEVEN = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var7");
    private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_EIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var8");

    private static final ResourceLocation SIMPLE_SUPPORT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_9x18x9");
    private static final ResourceLocation REINFORCED_SUPPORT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_9x18x9_reinforced");

    private static final ResourceLocation WATCHTOWER_LEFT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_watch_tower_left");
    private static final ResourceLocation RUINED_WATCHTOWER_LEFT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_ruined_watch_tower_left");

    private static final ResourceLocation SHORT_BRIDGE_UP = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_short_bridge_up");
    private static final ResourceLocation SHORT_BRIDGE_UP_DESTROYED = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_short_bridge_up_destroyed");
    private static final ResourceLocation SHORT_BRIDGE_UP_DESTROYED_VAR_ONE = new ResourceLocation(
            Cobbler.MODID + ":shulkerfactory_short_bridge_up_destroyed_var1");

    private static final ResourceLocation LONG_BRIDGE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_long_bridge");
    private static final ResourceLocation LONG_BRIDGE_VAR_ONE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_long_bridge_var1");
    private static final ResourceLocation LONG_BRIDGE_VAR_TWO = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_long_bridge_var2");
    private static final ResourceLocation LONG_BRIDGE_VAR_THREE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_long_bridge_var3");
    private static final ResourceLocation LONG_BRIDGE_DESTROYED = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_long_bridge_destroyed");

    private static final ResourceLocation SPAWNER_OBSIDIAN_BASE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_obsidian_base");
    private static final ResourceLocation SPAWNER_PIT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_pit");
    private static final ResourceLocation SPAWNER_MIDDLE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_tower_middle_tileable");
    private static final ResourceLocation SPAWNER_ROOM = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_tower_top");
    private static final ResourceLocation SPAWNER_RAMP = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_ramp_reinforced");
    private static final ResourceLocation SPAWNER_RAMP_SUPPORT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_ramp_supports_reinforced");

    private static final ResourceLocation RESTAURANT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_incubator");

    private static final ResourceLocation INCUBATOR = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_restaurant");
    private static final ResourceLocation SUPPORT_PLATFORM_STAIRS = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_platform_stairs");
    private static final ResourceLocation SUPPORT_PLATFORM_STAIRS_VAR_ONE = new ResourceLocation(
            Cobbler.MODID + ":shulkerfactory_support_platform_stairs_damaged");
    private static final ResourceLocation SUPPORT_PLATFORM_STAIRS_VAR_TWO = new ResourceLocation(
            Cobbler.MODID + ":shulkerfactory_support_platform_stairs_very_damaged");
    private static final ResourceLocation VOID_CHANDELIER = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_platform_1_void_chandelier");
    private static final ResourceLocation SUPPORT_STAIRS_TO_STANDARD = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_stairs_to_standard");
    private static final ResourceLocation SUPPORT_ROOM_TOP = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_room_top");

    private static final ResourceLocation FACTORY_LOOT = new ResourceLocation(Cobbler.MODID + ":chests/shulker_factory_treasure");

    // Structures with uncommon rotations will generate more often than their weight
    // here would suggest
    private static final double TOWER_WEIGHT = 6;
    private static final double RUINED_TOWER_WEIGHT = 9;
    private static final double PLATFORM_WEIGHT = 40;
    private static final double OPTIONAL_STAIRS_WEIGHT = 5;
    private static final double INCUBATOR_WEIGHT = 3;
    private static final double RESTAURAUNT_WEIGHT = 3;
    private static final double SUPPORT_CITY_WEIGHT = 15;
    // 1 generates closer to start, increasing it increases the spread. As it
    // approaches infinity, piece direction will not be taken into account.
    // Increases multiplicatively.
    // Numbers less than 1 will behave unpredictable, and numbers within a few
    // orders of magnitude of double.MAX_VALUE will also behave unpredictably
    private static final double SPREAD = 1.0;

    private static final int BLOCKS_TO_GENERATION_BOUNDRY = 16 * 8 + 8;

    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.<ResourceLocation, BlockPos>builder().put(ENTRANCE, new BlockPos(0, -3, 0))

            .put(LOW_SPLIT_LEFT, new BlockPos(0, -3, 0)).put(LOW_SPLIT_LEFT_VAR_ONE, new BlockPos(0, -3, 0)).put(LOW_SPLIT_LEFT_VAR_TWO, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_LEFT_VAR_THREE, new BlockPos(0, -3, 0)).put(LOW_SPLIT_LEFT_VAR_FOUR, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_LEFT_VAR_FIVE, new BlockPos(0, -3, 0)).put(LOW_SPLIT_LEFT_VAR_SIX, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_LEFT_VAR_SEVEN, new BlockPos(0, -3, 0))

            .put(LOW_SPLIT_RIGHT, new BlockPos(0, -3, 0)).put(LOW_SPLIT_RIGHT_VAR_ONE, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_RIGHT_VAR_TWO, new BlockPos(0, -3, 0)).put(LOW_SPLIT_RIGHT_VAR_THREE, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_RIGHT_VAR_FOUR, new BlockPos(0, -3, 0)).put(LOW_SPLIT_RIGHT_VAR_FIVE, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_RIGHT_VAR_SIX, new BlockPos(0, -3, 0)).put(LOW_SPLIT_RIGHT_VAR_SEVEN, new BlockPos(0, -3, 0))
            .put(LOW_SPLIT_RIGHT_VAR_EIGHT, new BlockPos(0, -3, 0))

            .put(SIMPLE_SUPPORT, new BlockPos(0, 0, 0)).put(REINFORCED_SUPPORT, new BlockPos(0, 0, 0)).put(WATCHTOWER_LEFT, new BlockPos(0, -1, 0))
            .put(RUINED_WATCHTOWER_LEFT, new BlockPos(0, -1, 0))

            .put(SHORT_BRIDGE_UP, new BlockPos(0, -2, 0)) // +4x +6z
            .put(SHORT_BRIDGE_UP_DESTROYED, new BlockPos(0, -2, 0)).put(SHORT_BRIDGE_UP_DESTROYED_VAR_ONE, new BlockPos(0, -2, 0))

            .put(LONG_BRIDGE, new BlockPos(0, -2, 0)) // +14x
            .put(LONG_BRIDGE_DESTROYED, new BlockPos(0, -2, 0)).put(LONG_BRIDGE_VAR_ONE, new BlockPos(0, -2, 0))
            .put(LONG_BRIDGE_VAR_TWO, new BlockPos(0, -2, 0)).put(LONG_BRIDGE_VAR_THREE, new BlockPos(0, -2, 0))

            // y values for tower pieces are handled in the creation method
            .put(SPAWNER_OBSIDIAN_BASE, new BlockPos(0, 0, 0)).put(SPAWNER_PIT, new BlockPos(0, -8, 0)).put(SPAWNER_MIDDLE, new BlockPos(0, -8, 0))
            .put(SPAWNER_ROOM, new BlockPos(0, -8, 0)).put(SPAWNER_RAMP, new BlockPos(0, -27, 0)).put(SPAWNER_RAMP_SUPPORT, new BlockPos(0, -9, 0))

            .put(RESTAURANT, new BlockPos(0, -4, 0)) // +3z, 180 turn

            .put(INCUBATOR, new BlockPos(0, -3, 0)).put(SUPPORT_PLATFORM_STAIRS, new BlockPos(0, 0, 0))
            .put(SUPPORT_PLATFORM_STAIRS_VAR_ONE, new BlockPos(0, 0, 0)).put(SUPPORT_PLATFORM_STAIRS_VAR_TWO, new BlockPos(0, 0, 0))
            .put(SUPPORT_ROOM_TOP, new BlockPos(0, 0, 0)).put(SUPPORT_STAIRS_TO_STANDARD, new BlockPos(0, 0, 0)).put(VOID_CHANDELIER, new BlockPos(0, 0, 0))

            .build();

    /*
     * These will be used for finding which structures to use when we're getting near the edge of the area we can generate in. The generation method which
     * includes the variants will be called, so only the default weight is listed
     */
    private static final List<Tuple<Function<GenerationInformation, GenerationInformation>, Double>> FOREWARD_WEIGHTS = ImmutableList
            .<Tuple<Function<GenerationInformation, GenerationInformation>, Double>>builder()
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::steepRampsUp, OPTIONAL_STAIRS_WEIGHT / 2))
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::addBridge, OPTIONAL_STAIRS_WEIGHT / 2))
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::makeSupportCity, SUPPORT_CITY_WEIGHT)).build();
    private static final List<Tuple<Function<GenerationInformation, GenerationInformation>, Double>> RIGHT_WEIGHTS = ImmutableList
            .<Tuple<Function<GenerationInformation, GenerationInformation>, Double>>builder()
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::addTurnRight, PLATFORM_WEIGHT / 2))
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::addIncubator, INCUBATOR_WEIGHT / 2)).build();
    private static final List<Tuple<Function<GenerationInformation, GenerationInformation>, Double>> REVERSE_WEIGHTS = ImmutableList
            .<Tuple<Function<GenerationInformation, GenerationInformation>, Double>>builder()
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::addRestaurant, RESTAURAUNT_WEIGHT)).build();
    private static final List<Tuple<Function<GenerationInformation, GenerationInformation>, Double>> LEFT_WEIGHTS = ImmutableList
            .<Tuple<Function<GenerationInformation, GenerationInformation>, Double>>builder()
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::addTurnLeft, PLATFORM_WEIGHT / 2))
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::addIncubator, INCUBATOR_WEIGHT / 2))
            .add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(ShulkerFactoryPieces::randomTowerLeft,
                    (TOWER_WEIGHT + RUINED_TOWER_WEIGHT) / 2))
            .build();
    private static final double SUM_OF_FOREWARD_WEIGHTS = FOREWARD_WEIGHTS.stream().mapToDouble(a -> a.getB()).sum();
    private static final double SUM_OF_RIGHT_WEIGHTS = RIGHT_WEIGHTS.stream().mapToDouble(a -> a.getB()).sum();
    private static final double SUM_OF_REVERSE_WEIGHTS = REVERSE_WEIGHTS.stream().mapToDouble(a -> a.getB()).sum();
    private static final double SUM_OF_LEFT_WEIGHTS = LEFT_WEIGHTS.stream().mapToDouble(a -> a.getB()).sum();

    private static void assembleSpawnerTower(GenerationInformation generationInfo) {

        int x = generationInfo.position.getX();
        int z = generationInfo.position.getZ();
        int spawnerRoomHeight = generationInfo.position.getY() + 4;
        BlockPos blockpos;
        BlockPos rotationOffSet;

        /*
         * ///////////////////////////////////////////////////////////////////////////// //////////////
         * 
         * Ramp
         * 
         *///////////////////////////////////////////////////////////////////////////////////////////
        {
            rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
            blockpos = generationInfo.position.add(rotationOffSet);

            generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_RAMP, blockpos));

            // y=32 should be right in the middle of most islands
            while (blockpos.getY() > 57) {
                blockpos = blockpos.add(0, -32, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_RAMP_SUPPORT, blockpos));
            }
        }
        /*
         * ///////////////////////////////////////////////////////////////////////////// //////////////
         * 
         * Tower
         * 
         *///////////////////////////////////////////////////////////////////////////////////////////
        {
            rotationOffSet = new BlockPos(10, spawnerRoomHeight - 32, -6).rotate(generationInfo.rotation);
            blockpos = rotationOffSet.add(x, 0, z);
            while (blockpos.getY() <= spawnerRoomHeight - 4) {
                blockpos = blockpos.add(0, 4, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_MIDDLE, blockpos));

            }

            rotationOffSet = new BlockPos(10, spawnerRoomHeight - 42, -6).rotate(generationInfo.rotation);
            blockpos = rotationOffSet.add(x, 0, z);
            generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_PIT, blockpos));
            ;

            blockpos = blockpos.add(0, -8, 0);
            while (blockpos.getY() > 2) {
                blockpos = blockpos.add(0, -2, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_OBSIDIAN_BASE, blockpos));

            }

            rotationOffSet = new BlockPos(10, 0, -6).rotate(generationInfo.rotation);
            blockpos = rotationOffSet.add(x, spawnerRoomHeight, z);
            generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_ROOM, blockpos));
        }
    }

    private static MutableBoundingBox getSpawnerTowerBoundingBox(Tuple<BlockPos, Rotation> location) {
        BlockPos swbCorner = location.getA().add(new BlockPos(0, -256, -6).rotate(location.getB()));
        BlockPos netCorner = location.getA().add(new BlockPos(14, 15, 14).rotate(location.getB()));
        return new MutableBoundingBox(Math.min(swbCorner.getX(), netCorner.getX()), Math.min(swbCorner.getZ(), netCorner.getZ()),
                Math.max(swbCorner.getX(), netCorner.getX()), Math.max(swbCorner.getZ(), netCorner.getZ()));

    }

    // location is the top of the supports
    private static MutableBoundingBox getSupportsBoundingBox(Tuple<BlockPos, Rotation> location) {
        BlockPos swbCorner = location.getA().add(new BlockPos(0, -256, 0).rotate(location.getB()));
        BlockPos netCorner = location.getA().add(new BlockPos(8, -4, 8).rotate(location.getB()));
        return new MutableBoundingBox(Math.min(swbCorner.getX(), netCorner.getX()), 0, Math.min(swbCorner.getZ(), netCorner.getZ()),
                Math.max(swbCorner.getX(), netCorner.getX()), netCorner.getY(), Math.max(swbCorner.getZ(), netCorner.getZ()));

    }

    // Randomly picks a left or right split. Returns the position to start the
    // bridge out
    private static GenerationInformation addTurnUp(GenerationInformation generationInfo) {
        if (generationInfo.random.nextInt(2) == 0) {
            return addTurnLeft(generationInfo);
        }
        return addTurnRight(generationInfo);
    }

    // Adds a 4 high, 4 long bridge
    private static GenerationInformation steepRampsUp(GenerationInformation generationInfo) {
        // 2 in 5 bridges will be destroyed
        ResourceLocation structure;
        int variant = generationInfo.random.nextInt(5);
        switch (variant) {
        case 0:
            structure = SHORT_BRIDGE_UP_DESTROYED;
            break;
        case 1:
            structure = SHORT_BRIDGE_UP_DESTROYED_VAR_ONE;
            break;
        default:
            structure = SHORT_BRIDGE_UP;
            break;
        }
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, structure));

        BlockPos rotationOffSet = new BlockPos(4, 4, 0).rotate((generationInfo.rotation));
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = generationInfo.position.add(rotationOffSet);
        return result;
    }

    // Adds a 4 high, 4 long bridge
    private static GenerationInformation addBridge(GenerationInformation generationInfo) {
        // 2 in 6 bridges will be destroyed
        ResourceLocation piece;
        int variant = generationInfo.random.nextInt(6);
        switch (variant) {
        case 0:
            piece = LONG_BRIDGE;
            break;
        case 1:
            piece = LONG_BRIDGE_VAR_ONE;
            break;
        case 2:
            piece = LONG_BRIDGE_VAR_TWO;
        case 3:
            piece = LONG_BRIDGE_VAR_THREE;
        default:
            piece = LONG_BRIDGE_DESTROYED;
            break;
        }
        if (canGenerate(generationInfo, piece)) {
            // If a bridge can't fit, not many other things will
            return steepRampsUp(generationInfo);
        }
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, piece));

        BlockPos rotationOffSet = new BlockPos(14, 0, 0).rotate((generationInfo.rotation));
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = generationInfo.position.add(rotationOffSet);

        return result;
    }

    // increaseBy must be divisible by four
    private static GenerationInformation multipleSteepRampsUp(GenerationInformation generationInfo) {
        GenerationInformation loc = new GenerationInformation(generationInfo);
        for (int i = 0; i < generationInfo.random.nextInt(3) + 1; i++) {
            loc = steepRampsUp(generationInfo);
        }
        return loc;
    }

    private static GenerationInformation randomTowerLeft(GenerationInformation generationInfo) {
        BlockPos rotationOffSet = new BlockPos(0, 0, -4).rotate(generationInfo.rotation);
        BlockPos blockpos = generationInfo.position.add(rotationOffSet);

        /*
         * ///////////////////////////////////////////////////////////////////////////// ////////////////////
         * 
         * Tower
         * 
         *////////////////////////////////////////////////////////////////////////////////////////////////
        ShulkerFactoryPieces.Piece piece;
        if (generationInfo.random.nextInt((int) (TOWER_WEIGHT + RUINED_TOWER_WEIGHT)) < RUINED_TOWER_WEIGHT) {
            piece = new ShulkerFactoryPieces.Piece(generationInfo, RUINED_WATCHTOWER_LEFT, blockpos);
        } else {
            piece = new ShulkerFactoryPieces.Piece(generationInfo, WATCHTOWER_LEFT, blockpos);
        }

        if (StructurePiece.findIntersecting(generationInfo.pieceList, piece.getBoundingBox()) != null) {
            // Simple way to avoid infinite loop if it, for example, generates 4 left towers
            // in a row
            return steepRampsUp(generationInfo);
        }

        generationInfo.pieceList.add(piece);
        /*
         * ///////////////////////////////////////////////////////////////////////////// ////////////////////
         * 
         * Supports
         * 
         *////////////////////////////////////////////////////////////////////////////////////////////////

        rotationOffSet = new BlockPos(2, -3, -2).rotate(generationInfo.rotation);
        BlockPos structurePos = generationInfo.position.add(rotationOffSet);

        GenerationInformation supportInfo = new GenerationInformation(generationInfo);
        supportInfo.position = structurePos;
        if (canGenerate(generationInfo, REINFORCED_SUPPORT)) {
            do {
                structurePos = structurePos.add(0, -16, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, REINFORCED_SUPPORT, structurePos));
            } while (structurePos.getY() > 32);
        }

        rotationOffSet = new BlockPos(4, 2, -5).rotate((generationInfo.rotation));
        blockpos = generationInfo.position.add(rotationOffSet);
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = blockpos;
        result.rotation = generationInfo.rotation.add(Rotation.COUNTERCLOCKWISE_90);

        return result;
    }

    private static GenerationInformation addRandomPiece(GenerationInformation generationInfo) {
        int randomValue = generationInfo.random.nextInt((int) ((RUINED_TOWER_WEIGHT + TOWER_WEIGHT + OPTIONAL_STAIRS_WEIGHT + PLATFORM_WEIGHT
                + RESTAURAUNT_WEIGHT + INCUBATOR_WEIGHT + SUPPORT_CITY_WEIGHT) * 2));
        GenerationInformation endPosition = new GenerationInformation(generationInfo);
        int previous = 0;

        if (randomValue < (previous = (int) (RUINED_TOWER_WEIGHT + TOWER_WEIGHT) * 2)) {
            endPosition = randomTowerLeft(generationInfo);
        } else if (randomValue < (previous += PLATFORM_WEIGHT)) {
            endPosition = addTurnLeft(generationInfo);
        } else if (randomValue < (previous += PLATFORM_WEIGHT)) {
            endPosition = addTurnRight(generationInfo);
        } else if (randomValue < (previous += OPTIONAL_STAIRS_WEIGHT)) {
            endPosition = multipleSteepRampsUp(generationInfo);
        } else if (randomValue < (previous += OPTIONAL_STAIRS_WEIGHT)) {
            endPosition = addBridge(generationInfo);
        } else if (randomValue < (previous += RESTAURAUNT_WEIGHT * 2)) {
            endPosition = addRestaurant(generationInfo);
        } else if (randomValue < (previous += INCUBATOR_WEIGHT * 2)) {
            endPosition = addRestaurant(generationInfo);
        } else if (randomValue < (previous += SUPPORT_CITY_WEIGHT * 2)) {
            endPosition = makeSupportCity(generationInfo);
        } else {
            // We might get here now due to truncating double weights, but it shouldn't
            // happen often
            // endPosition = multipleSteepRampsUp(generationInfo);
        }

        return endPosition;
    }

    private static GenerationInformation addTurnLeft(GenerationInformation generationInfo) {
        // Move from bottom left corner of entrance to bottom left corner
        BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
        BlockPos blockpos = generationInfo.position.add(rotationOffSet);

        if (!canGenerate(generationInfo, LOW_SPLIT_LEFT)) {
            // Simple way to avoid infinite loop if it, for example, generates 4 left towers
            // in a row
            GenerationInformation result = new GenerationInformation(generationInfo);
            result.lastGenerationSucceded = false;
            result.lastStructureAttempted = LOW_SPLIT_LEFT;
            return result;
        }
        ResourceLocation structure;
        int variant = generationInfo.random.nextInt(8);
        switch (variant) {
        case 0:
            structure = LOW_SPLIT_LEFT;
            break;
        case 1:
            structure = LOW_SPLIT_LEFT_VAR_ONE;
            break;
        case 2:
            structure = LOW_SPLIT_LEFT_VAR_TWO;
            break;
        case 3:
            structure = LOW_SPLIT_LEFT_VAR_THREE;
            break;
        case 4:
            structure = LOW_SPLIT_LEFT_VAR_FOUR;
            break;
        case 5:
            structure = LOW_SPLIT_LEFT_VAR_FIVE;
            break;
        case 6:
            structure = LOW_SPLIT_LEFT_VAR_SIX;
            break;
        case 7:
            structure = LOW_SPLIT_LEFT_VAR_SEVEN;
            break;
        default:
            // We shouldn't get here, but the compiler will complain if this isn't included
            structure = LOW_SPLIT_LEFT;
        }
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, structure, blockpos));
        // Move from bl corner to bl corner of stairs up and turn left
        rotationOffSet = new BlockPos(2, 1, -1).rotate(generationInfo.rotation);
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = blockpos.add(rotationOffSet);
        result.rotation = generationInfo.rotation.add(Rotation.COUNTERCLOCKWISE_90);
        result.lastGenerationSucceded = true;
        result.lastStructureAttempted = LOW_SPLIT_LEFT;

        rotationOffSet = new BlockPos(0, -2, -2).rotate(generationInfo.rotation);
        BlockPos structurePos = generationInfo.position.add(rotationOffSet);

        GenerationInformation supportInfo = new GenerationInformation(generationInfo);
        supportInfo.position = structurePos;
        if (canGenerate(supportInfo, SIMPLE_SUPPORT)) {
            do {
                structurePos = structurePos.add(0, -16, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SIMPLE_SUPPORT, structurePos));
            } while (structurePos.getY() > 32);
        }

        return result;
    }

    private static GenerationInformation addIncubator(GenerationInformation generationInfo) {
        // Create Incubator
        BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
        BlockPos blockpos = generationInfo.position.add(rotationOffSet);

        if (!canGenerate(generationInfo, INCUBATOR)) {
            // Simple way to avoid infinite loop if it, for example, generates 4 left towers
            // in a row
            GenerationInformation result = new GenerationInformation(generationInfo);
            result.lastGenerationSucceded = false;
            result.lastStructureAttempted = INCUBATOR;
            return result;
        }
        ResourceLocation structure = INCUBATOR;
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, structure, blockpos));

        // Create supports
        rotationOffSet = new BlockPos(0, -2, -2).rotate(generationInfo.rotation);
        BlockPos structurePos = generationInfo.position.add(rotationOffSet);

        GenerationInformation supportInfo = new GenerationInformation(generationInfo);
        supportInfo.position = structurePos;
        supportInfo.position = structurePos.add(generationInfo.position.add(rotationOffSet));
        if (canGenerate(supportInfo, SIMPLE_SUPPORT)) {
            do {
                structurePos = structurePos.add(0, -16, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SIMPLE_SUPPORT, structurePos));
            } while (structurePos.getY() > 32);
        }

        // Branch and pick main path foreward if applicable
        GenerationInformation result = new GenerationInformation(generationInfo);
        int forewardPath = generationInfo.random.nextInt(23);
        int numberOfPaths = generationInfo.random.nextInt(2);
        switch (forewardPath) {
        case 0:
            if (result.isMainPath || numberOfPaths > 0) {
                result = splitTurnLeft(result);
            }
            if (numberOfPaths == 2) {
                GenerationInformation sidePath = new GenerationInformation(generationInfo);
                sidePath.isMainPath = false;
                sidePath = splitTurnRight(sidePath);
                makeSidePath(sidePath, 0);
            }
            break;
        case 1:
            if (result.isMainPath || numberOfPaths > 0) {
                result = splitTurnRight(result);
            }
            if (numberOfPaths == 2) {
                GenerationInformation sidePath = new GenerationInformation(generationInfo);
                sidePath.isMainPath = false;
                sidePath = splitTurnLeft(sidePath);
                makeSidePath(sidePath, 0);
            }

            break;
        case 2:
            // foreward
            break;
        default:
            break;
        }
        result.lastGenerationSucceded = true;
        result.lastStructureAttempted = INCUBATOR;

        return result;
    }

    private static GenerationInformation splitTurnLeft(GenerationInformation generationInfo) {
        // Move from bl corner to bl corner of stairs up and turn left
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = generationInfo.position.add(new BlockPos(0, 0, -2).rotate(generationInfo.rotation));
        BlockPos rotationOffSet = new BlockPos(2, 1, -1).rotate(generationInfo.rotation);
        result.position = result.position.add(rotationOffSet);
        result.rotation = generationInfo.rotation.add(Rotation.COUNTERCLOCKWISE_90);

        rotationOffSet = new BlockPos(0, -2, -2).rotate(generationInfo.rotation);

        return result;
    }

    private static GenerationInformation splitTurnRight(GenerationInformation generationInfo) {
        // Move from bl corner to bl corner of stairs up and turn left
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = generationInfo.position.add(new BlockPos(0, 0, -2).rotate(generationInfo.rotation));
        BlockPos rotationOffSet = new BlockPos(6, 1, 9).rotate(generationInfo.rotation);
        result.position = result.position.add(rotationOffSet);
        result.rotation = generationInfo.rotation.add(Rotation.COUNTERCLOCKWISE_90);

        rotationOffSet = new BlockPos(0, -3, -2).rotate(generationInfo.rotation);

        return result;
    }

    private static GenerationInformation makeSupportCity(GenerationInformation generationInfo) {
        //
        // Generate going down
        //
        BlockPos rotationOffSet = new BlockPos(0, -1, -2).rotate(generationInfo.rotation);
        BlockPos structurePos = generationInfo.position.add(rotationOffSet);
        GenerationInformation supportInfo = new GenerationInformation(generationInfo);
        supportInfo.position = generationInfo.position.add(rotationOffSet);

        if (!canGenerate(generationInfo, SIMPLE_SUPPORT)) {
            generationInfo.lastGenerationSucceded = false;
            generationInfo.lastStructureAttempted = SUPPORT_PLATFORM_STAIRS;
            return generationInfo;
        }
        do {
            int pieceSelector = generationInfo.random.nextInt(3);
            ResourceLocation selectedPiece;
            switch (pieceSelector) {
            case 0:
                selectedPiece = SUPPORT_PLATFORM_STAIRS;
                break;
            case 1:
                selectedPiece = SUPPORT_PLATFORM_STAIRS_VAR_ONE;
                break;
            case 2:
                selectedPiece = SUPPORT_PLATFORM_STAIRS_VAR_TWO;
                break;
            default:
                selectedPiece = SUPPORT_PLATFORM_STAIRS;
                break;
            }
            generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, selectedPiece, structurePos));
            structurePos = structurePos.add(0, -18, 0);
        } while (structurePos.getY() > 45);
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, VOID_CHANDELIER, structurePos));
        structurePos = structurePos.add(0, -18, 0);

        //
        // Generate going up
        //

        GenerationInformation result = new GenerationInformation(generationInfo);
        structurePos = supportInfo.position; // Bottom left of supports
        // reserve a spot for the top room
        int numPossiblePiecesUp = (200 - structurePos.getY()) / 16;
        int numberGoingUp = numPossiblePiecesUp <= 0 ? 0 : generationInfo.random.nextInt(numPossiblePiecesUp);
        numberGoingUp = Math.max(1, numberGoingUp);
        numberGoingUp = generationInfo.random.nextInt(numberGoingUp);
        numberGoingUp = Math.max(1, numberGoingUp);
        boolean hasBranched = false;
        for (int i = 0; i < numberGoingUp; i++) {

            // Increases likelihood of branching off each time until it's guaranteed, if this is the main path
            int shouldBranchHere = 0;
            if (generationInfo.isMainPath) {
                shouldBranchHere = numberGoingUp - i - 1 > 0 ? supportInfo.random.nextInt(numberGoingUp - i - 1) : 0;
            } else {
                shouldBranchHere = (numberGoingUp - i) * 2 > 0 ? supportInfo.random.nextInt((numberGoingUp - i) * 2) : 1;
            }

            if (shouldBranchHere == 0 && !hasBranched) {
                BlockPos branchPos = generationInfo.position.add(9, 7, 0).rotate(generationInfo.rotation);
                result.position = branchPos;
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SUPPORT_STAIRS_TO_STANDARD, branchPos));
                result.position = result.position.add(new BlockPos(7, 1, 0).rotate(generationInfo.rotation));
            }

            // Add the stairs
            structurePos = structurePos.add(0, 16, 0);
            int pieceSelector = generationInfo.random.nextInt(3);
            ResourceLocation selectedPiece;
            switch (pieceSelector) {
            case 0:
                selectedPiece = SUPPORT_PLATFORM_STAIRS;
                break;
            case 1:
                selectedPiece = SUPPORT_PLATFORM_STAIRS_VAR_ONE;
                break;
            case 2:
                selectedPiece = SUPPORT_PLATFORM_STAIRS_VAR_TWO;
                break;
            default:
                selectedPiece = SUPPORT_PLATFORM_STAIRS;
                break;
            }
            generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, selectedPiece, structurePos));

        }
        structurePos = structurePos.add(-8, 16, -5).rotate(generationInfo.rotation);
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SUPPORT_ROOM_TOP, structurePos));

        if (!hasBranched) {
            return null;
        }

        return result;
    }

    private static void makeSidePath(GenerationInformation generationInfo, int sidePiecesAdded) {
        int branchLength = 10;
        boolean terminate = branchLength <= sidePiecesAdded ? true : generationInfo.random.nextInt(branchLength - sidePiecesAdded) == 0;

        generationInfo = addRandomPiece(generationInfo);
        if (!terminate && generationInfo != null) {
            makeSidePath(generationInfo, sidePiecesAdded++);
        }
    }

    private static GenerationInformation addTurnRight(GenerationInformation generationInfo) {
        // Move from bottom left corner of entrance to bottom left corner
        BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
        BlockPos blockpos = generationInfo.position.add(rotationOffSet);

        if (!canGenerate(generationInfo, LOW_SPLIT_LEFT)) {
            // Simple way to avoid infinite loop if it, for example, generates 4 left towers
            // in a row
            GenerationInformation result = new GenerationInformation(generationInfo);
            result.lastGenerationSucceded = false;
            result.lastStructureAttempted = LOW_SPLIT_RIGHT;
            return result;
        }
        ResourceLocation structure;
        int variant = generationInfo.random.nextInt(9);
        switch (variant) {
        case 0:
            structure = LOW_SPLIT_RIGHT;
            break;
        case 1:
            structure = LOW_SPLIT_RIGHT_VAR_ONE;
            break;
        case 2:
            structure = LOW_SPLIT_RIGHT_VAR_TWO;
            break;
        case 3:
            structure = LOW_SPLIT_RIGHT_VAR_THREE;
            break;
        case 4:
            structure = LOW_SPLIT_RIGHT_VAR_FOUR;
            break;
        case 5:
            structure = LOW_SPLIT_RIGHT_VAR_FIVE;
            break;
        case 6:
            structure = LOW_SPLIT_RIGHT_VAR_SIX;
            break;
        case 7:
            structure = LOW_SPLIT_RIGHT_VAR_SEVEN;
            break;
        case 8:
            structure = LOW_SPLIT_RIGHT_VAR_EIGHT;
            break;
        default:
            // We shouldn't get here, but the compiler will complain if this isn't included
            structure = LOW_SPLIT_RIGHT;
        }
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, structure, blockpos));

        // Move from bl corner to bl corner of stairs up and turn right
        rotationOffSet = new BlockPos(6, 1, 9).rotate(generationInfo.rotation);
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = blockpos.add(rotationOffSet);
        result.rotation = generationInfo.rotation.add(Rotation.CLOCKWISE_90);
        result.lastGenerationSucceded = true;
        result.lastStructureAttempted = LOW_SPLIT_RIGHT;

        rotationOffSet = new BlockPos(0, -3, -2).rotate(generationInfo.rotation);
        BlockPos structurePos = generationInfo.position.add(rotationOffSet);

        GenerationInformation supportInfo = new GenerationInformation(generationInfo);
        supportInfo.position = structurePos;
        if (canGenerate(supportInfo, SIMPLE_SUPPORT)) {
            do {
                structurePos = structurePos.add(0, -16, 0);
                generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SIMPLE_SUPPORT, structurePos));
            } while (structurePos.getY() > 32);
        }

        return result;
    }

    private static GenerationInformation addRestaurant(GenerationInformation generationInfo) {
        // Move from bottom left corner of entrance to bottom left corner
        BlockPos rotationOffSet = new BlockPos(0, 0, -9).rotate(generationInfo.rotation);
        BlockPos blockpos = generationInfo.position.add(rotationOffSet);
        ShulkerFactoryPieces.Piece piece = new ShulkerFactoryPieces.Piece(generationInfo, RESTAURANT, blockpos);

        if (!canGenerate(generationInfo, RESTAURANT)) {
            GenerationInformation result = new GenerationInformation(generationInfo);
            result.lastGenerationSucceded = false;
            result.lastStructureAttempted = RESTAURANT;
            return result;
        }
        generationInfo.pieceList.add(piece);

        rotationOffSet = new BlockPos(0, 12, 12).rotate(generationInfo.rotation);
        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = blockpos.add(rotationOffSet);
        result.rotation = generationInfo.rotation.add(Rotation.CLOCKWISE_180);
        result.lastGenerationSucceded = true;
        result.lastStructureAttempted = RESTAURANT;

        return result;
    }

    private static GenerationInformation addEntrance(GenerationInformation generationInfo) {
        int x = generationInfo.position.getX();
        int z = generationInfo.position.getZ();
        int y = generationInfo.position.getY();
        generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, ENTRANCE));

        BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(generationInfo.rotation);
        BlockPos blockpos = rotationOffSet.add(x, y, z);
        rotationOffSet = new BlockPos(27, 5, 3).rotate((generationInfo.rotation));
        blockpos = blockpos.add(rotationOffSet);
        generationInfo.position = blockpos;

        GenerationInformation result = new GenerationInformation(generationInfo);
        result.position = blockpos;
        result.lastGenerationSucceded = true;
        result.lastStructureAttempted = ENTRANCE;
        return result;
    }

    /*
     * Begins assembling your structure and where the pieces needs to go.
     */
    public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
        int x = pos.getX();
        int z = pos.getZ();
        int y = pos.getY();

        int north_boundry = x + BLOCKS_TO_GENERATION_BOUNDRY;
        int south_boundry = x - BLOCKS_TO_GENERATION_BOUNDRY;
        int west_boundry = z - BLOCKS_TO_GENERATION_BOUNDRY;
        int east_boundry = z + BLOCKS_TO_GENERATION_BOUNDRY;

        GenerationInformation generationInfo = new GenerationInformation(north_boundry, south_boundry, west_boundry, east_boundry, pos, rotation, pieceList,
                templateManager, random, true);

        generationInfo = addEntrance(generationInfo);
        generationInfo = addTurnUp(generationInfo);

        int generationFailCount = 0;
        while ((generationInfo.position.getY() < 170 || (y < 240 && !canGenerate(generationInfo, SPAWNER_ROOM))) && !(
        // If we get too close to the boundary don't risk getting cut off; just generate
        // the spawner structure
        generationInfo.position.getX() > north_boundry - 35 || generationInfo.position.getX() < south_boundry + 35
                || generationInfo.position.getZ() > east_boundry - 35 || generationInfo.position.getZ() < west_boundry + 35)) {
            // We want to try to pull away from the edges of where we can generate if we go
            // too far in one direction
            // 1 pulls strongly to positive, -1 to negative, and 0 has no pull
            double pullX = -((double) (generationInfo.position.getX() - south_boundry) / (double) (north_boundry - south_boundry));
            double pullZ = -((double) (generationInfo.position.getZ() - west_boundry) / (double) (east_boundry - west_boundry));
            double pullRight = 0;
            double pullForewards = 0;
            switch (generationInfo.rotation) {
            case NONE:
                pullForewards = pullX;
                pullRight = pullZ;
                break;
            case CLOCKWISE_90:
                pullForewards = pullZ;
                pullRight = -pullX;
                break;
            case CLOCKWISE_180:
                pullForewards = -pullX;
                pullRight = -pullZ;
                break;
            case COUNTERCLOCKWISE_90:
                pullForewards = -pullZ;
                pullRight = pullX;
                break;
            }
            generationInfo = generateRandomWithDirectionalWeights(generationInfo, pullForewards, pullRight);
            if (!generationInfo.lastGenerationSucceded) {
                generationFailCount++;
                if (generationFailCount > 2) {
                    generationInfo = steepRampsUp(generationInfo);
                    generationFailCount = 0;
                }
            }
        }

        if (generationInfo.isMainPath) {
            assembleSpawnerTower(generationInfo);
        }
    }

    private static GenerationInformation generateRandomWithDirectionalWeights(GenerationInformation generationInfo, double pullForewards, double pullRight) {
        // Multipliers are between 0 and 2
        double leftMultiplier = -pullRight + SPREAD;
        double rightMultiplier = pullRight + SPREAD;
        double forewardMultiplier = pullForewards + SPREAD;
        double reverseMultiplier = -pullForewards + SPREAD;
        double rollBound = leftMultiplier * (double) SUM_OF_LEFT_WEIGHTS + rightMultiplier * (double) SUM_OF_RIGHT_WEIGHTS
                + forewardMultiplier * (double) SUM_OF_FOREWARD_WEIGHTS + reverseMultiplier * (double) SUM_OF_REVERSE_WEIGHTS;
        double rand = generationInfo.random.nextDouble() * rollBound;
        double previous = 0;
        ArrayList<Tuple<Function<GenerationInformation, GenerationInformation>, Double>> weightedGenerationOptionCutoffs = new ArrayList<Tuple<Function<GenerationInformation, GenerationInformation>, Double>>();
        for (Tuple<Function<GenerationInformation, GenerationInformation>, Double> weightedPiece : LEFT_WEIGHTS) {
            weightedGenerationOptionCutoffs.add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(weightedPiece.getA(),
                    previous += weightedPiece.getB() * leftMultiplier));
        }
        for (Tuple<Function<GenerationInformation, GenerationInformation>, Double> weightedPiece : RIGHT_WEIGHTS) {
            weightedGenerationOptionCutoffs.add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(weightedPiece.getA(),
                    previous += weightedPiece.getB() * rightMultiplier));
        }
        for (Tuple<Function<GenerationInformation, GenerationInformation>, Double> weightedPiece : FOREWARD_WEIGHTS) {
            weightedGenerationOptionCutoffs.add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(weightedPiece.getA(),
                    previous += weightedPiece.getB() * forewardMultiplier));
        }
        for (Tuple<Function<GenerationInformation, GenerationInformation>, Double> weightedPiece : REVERSE_WEIGHTS) {
            weightedGenerationOptionCutoffs.add(new Tuple<Function<GenerationInformation, GenerationInformation>, Double>(weightedPiece.getA(),
                    previous += weightedPiece.getB() * reverseMultiplier));
        }
        GenerationInformation result = null;
        for (Tuple<Function<GenerationInformation, GenerationInformation>, Double> pieceCutoff : weightedGenerationOptionCutoffs) {
            if (pieceCutoff.getB() >= rand) {
                result = pieceCutoff.getA().apply(generationInfo);
                break;
            }
        }
        if (result == null) {
            // I messed up
            Cobbler.LOGGER.info("generateRandomWithDirectionalWeights([generationInfo], " + pullForewards + ", " + pullRight
                    + " encountered an error, generating a bridge");
            result = addBridge(generationInfo);
        }
        return result;

    }

    private static boolean canGenerate(GenerationInformation generationInfo, ResourceLocation structure) {
        MutableBoundingBox boundingBox;
        if (structure.equals(SPAWNER_ROOM)) {
            boundingBox = getSpawnerTowerBoundingBox(new Tuple<BlockPos, Rotation>(generationInfo.position, generationInfo.rotation));
        } else if (structure.equals(REINFORCED_SUPPORT) || structure.equals(SIMPLE_SUPPORT)) {
            boundingBox = getSupportsBoundingBox(new Tuple<BlockPos, Rotation>(generationInfo.position, generationInfo.rotation));
        } else {
            boundingBox = new ShulkerFactoryPieces.Piece(generationInfo, structure).getBoundingBox();
        }
        if (StructurePiece.findIntersecting(generationInfo.pieceList, boundingBox) == null) {
            return true;
        }
        return false;
    }

    /*
     * Here's where some voodoo happens. Most of this doesn't need to be touched but you do have to pass in the IStructurePieceType you registered into the
     * super constructors.
     * 
     * The method you will most likely want to touch is the handleDataMarker method.
     */
    public static class Piece extends TemplateStructurePiece {
        private ResourceLocation resourceLocation;
        private Rotation rotation;

        public Piece(GenerationInformation generationInfo, ResourceLocation resourceLocationIn) {
            super(Structures.FOR_REGISTERING_SHULKER_FACTORY_PIECES, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(resourceLocation);
            this.templatePosition = generationInfo.position.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = generationInfo.rotation;
            this.setupPiece(generationInfo.templateManager);
        }

        public Piece(GenerationInformation generationInfo, ResourceLocation resourceLocationIn, BlockPos positionOverride) {
            super(Structures.FOR_REGISTERING_SHULKER_FACTORY_PIECES, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(resourceLocation);
            this.templatePosition = positionOverride.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = generationInfo.rotation;
            this.setupPiece(generationInfo.templateManager);
        }

        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(Structures.FOR_REGISTERING_SHULKER_FACTORY_PIECES, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        /*
         * If you added any data marker structure blocks to your structure, you can access and modify them here. In this case, our structure has a data maker
         * with the string "chest" put into it. So we check to see if the incoming function is "chest" and if it is, we now have that exact position.
         * 
         * So what is done here is we replace the structure block with a chest and we can then set the loottable for it.
         * 
         * You can set other data markers to do other behaviors such as spawn a random mob in a certain spot, randomize what rare block spawns under the floor,
         * or what item an Item Frame will have.
         */
        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if (function.startsWith("Sentry")) {
                ShulkerEntity shulkerentity = EntityType.SHULKER.create(worldIn.getWorld());
                shulkerentity.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
                shulkerentity.setAttachmentPos(pos);
                worldIn.addEntity(shulkerentity);
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 1 | 2);
            } else if ("chest".equals(function)) {
                worldIn.setBlockState(pos, Blocks.SHULKER_BOX.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                // Just another check to make sure everything is going well before we try to set
                // the chest.
                if (tileentity instanceof ShulkerBoxTileEntity) {
                    ((ShulkerBoxTileEntity) tileentity).setLootTable(FACTORY_LOOT, rand.nextLong());

                }
            }
        }

//        @Override
//        public boolean func_230383_a_(ISeedReader seedReader, StructureManager structureManager, ChunkGenerator p_225577_2_, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
//            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
//            BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(this.resourceLocation);
//            this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(0 - blockpos.getX(), 0, 0 - blockpos.getZ())));
//            return super.func_214810_a(p_214810_1_, p_214810_2_) func_230383_a_(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_)
//            return super.func_225577_a_(worldIn, p_225577_2_, randomIn, structureBoundingBoxIn, chunkPos);
//        }
    }

}
