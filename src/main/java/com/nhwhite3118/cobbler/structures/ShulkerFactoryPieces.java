package com.nhwhite3118.cobbler.structures;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
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
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_EIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var7");
	
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
	private static final ResourceLocation SHORT_BRIDGE_UP_DESTROYED_VAR_ONE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_short_bridge_up_destroyed_var1");

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
	
	private static final ResourceLocation RESTAURANT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_restaurant");

	private static final ResourceLocation FACTORY_LOOT = new ResourceLocation(Cobbler.MODID + ":chests/shulker_factory_treasure");
	
	private static final int TOWER_WEIGHT = 6;
	private static final int RUINED_TOWER_WEIGHT = 9;
	private static final int PLATFORM_WEIGHT = 40;
	private static final int OPTIONAL_STAIRS_WEIGHT = 5;
	private static final int RESTAURAUNT_WEIGHT = 3;
	
	private static final int BLOCKS_TO_GENERATION_BOUNDRY = 16 * 8 + 8;
	
	private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.<ResourceLocation, BlockPos>builder()
			.put(ENTRANCE, new BlockPos(0, -3, 0))
			
			.put(LOW_SPLIT_LEFT, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_ONE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_TWO, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_THREE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_FOUR, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_FIVE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_SIX, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT_VAR_EIGHT, new BlockPos(0, -3, 0))
			
			.put(LOW_SPLIT_RIGHT, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_ONE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_TWO, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_THREE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_FOUR, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_FIVE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_SIX, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_SEVEN, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_RIGHT_VAR_EIGHT, new BlockPos(0, -3, 0))

			.put(SIMPLE_SUPPORT, new BlockPos(0, 0, 0))
			.put(REINFORCED_SUPPORT, new BlockPos(0, 0, 0))
			.put(WATCHTOWER_LEFT, new BlockPos(0, -1, 0))
			.put(RUINED_WATCHTOWER_LEFT, new BlockPos(0, -1, 0))

			.put(SHORT_BRIDGE_UP, new BlockPos(0, -2, 0)) //+4x +6z
			.put(SHORT_BRIDGE_UP_DESTROYED, new BlockPos(0, -2, 0))
			.put(SHORT_BRIDGE_UP_DESTROYED_VAR_ONE, new BlockPos(0, -2, 0))

			.put(LONG_BRIDGE, new BlockPos(0, -2, 0)) //+14x
			.put(LONG_BRIDGE_DESTROYED, new BlockPos(0, -2, 0))
			.put(LONG_BRIDGE_VAR_ONE, new BlockPos(0, -2, 0))
			.put(LONG_BRIDGE_VAR_TWO, new BlockPos(0, -2, 0))
			.put(LONG_BRIDGE_VAR_THREE, new BlockPos(0, -2, 0))
			
			//y values for tower pieces are handled in the creation method
			.put(SPAWNER_OBSIDIAN_BASE, new BlockPos(0, 0, 0))
			.put(SPAWNER_PIT, new BlockPos(0, -8, 0))
			.put(SPAWNER_MIDDLE, new BlockPos(0, -8, 0))
			.put(SPAWNER_ROOM, new BlockPos(0, -8, 0))
			.put(SPAWNER_RAMP, new BlockPos(0, -27, 0))
			.put(SPAWNER_RAMP_SUPPORT, new BlockPos(0, -9, 0))

			.put(RESTAURANT, new BlockPos(0, -4, 0)) //+3z, 180 turn
			.build();

	private static void assembleSpawnerTower(GenerationInformation generationInfo) {

		int x = generationInfo.position.getX();
		int z = generationInfo.position.getZ();
		int spawnerRoomHeight = generationInfo.position.getY() + 4;
		BlockPos blockpos;
		BlockPos rotationOffSet;
		
		/*///////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * 	Ramp
		 * 
		 *///////////////////////////////////////////////////////////////////////////////////////////
		{
			rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
			blockpos = generationInfo.position.add(rotationOffSet);
			
			generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_RAMP));
			
			//y=32 should be right in the middle of most islands
			while(blockpos.getY() > 57) {
				blockpos = blockpos.add(0, -32, 0);
				generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_RAMP_SUPPORT));
			}
		}
		/*///////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * 	Tower
		 * 
		 *///////////////////////////////////////////////////////////////////////////////////////////
		{
			rotationOffSet = new BlockPos(10, spawnerRoomHeight - 32, -6).rotate(generationInfo.rotation);	
			blockpos = rotationOffSet.add(x, 0, z);
			while(blockpos.getY() <= spawnerRoomHeight - 4) {
				blockpos = blockpos.add(0, 4, 0);
				generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_MIDDLE));
				
			}
			
			rotationOffSet = new BlockPos(10, spawnerRoomHeight -42, -6).rotate(generationInfo.rotation);	
			blockpos = rotationOffSet.add(x, 0, z);
			generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_PIT));;
			
			blockpos = blockpos.add(0, -8, 0);
			while(blockpos.getY() > 2) {
				blockpos = blockpos.add(0, -2, 0);
				generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_OBSIDIAN_BASE));
				
			}
			
			rotationOffSet = new BlockPos(10, 0, -6).rotate(generationInfo.rotation);	
			blockpos = rotationOffSet.add(x, spawnerRoomHeight, z);
			generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SPAWNER_ROOM));
		}
	}
	
	private static MutableBoundingBox getSpawnerTowerBoundingBox(Tuple<BlockPos, Rotation> location) {
		BlockPos swbCorner = location.getA().add(new BlockPos(0,-256, -6).rotate(location.getB()));
		BlockPos netCorner = location.getA().add(new BlockPos(14,15, 14).rotate(location.getB()));
		return new MutableBoundingBox(
				Math.min(swbCorner.getX(), netCorner.getX()),
				Math.min(swbCorner.getZ(), netCorner.getZ()),
				Math.max(swbCorner.getX(), netCorner.getX()),
				Math.max(swbCorner.getZ(), netCorner.getZ()));
		
	}
	
	//location is the top of the supports
	private static MutableBoundingBox getSupportsBoundingBox(Tuple<BlockPos, Rotation> location) {
		BlockPos swbCorner = location.getA().add(new BlockPos(0,-256, 0).rotate(location.getB()));
		BlockPos netCorner = location.getA().add(new BlockPos(8,-4, 8).rotate(location.getB()));
		return new MutableBoundingBox(
				Math.min(swbCorner.getX(), netCorner.getX()),
				0,
				Math.min(swbCorner.getZ(), netCorner.getZ()),
				Math.max(swbCorner.getX(), netCorner.getX()),
				netCorner.getY(),
				Math.max(swbCorner.getZ(), netCorner.getZ()));
		
	}
	
	// Randomly picks a left or right split. Returns the position to start the bridge out
	private static GenerationInformation addTurnUp(GenerationInformation generationInfo) {
		if(generationInfo.random.nextInt(2) == 0) {
			return addTurnLeft(generationInfo);
		}
		return addTurnRight(generationInfo);
	}

	// Adds a 4 high, 4 long bridge
	private static GenerationInformation steepRampsUp(GenerationInformation generationInfo) {
		//2 in 5 bridges will be destroyed
		ResourceLocation structure;
		int variant = generationInfo.random.nextInt(5);
		switch(variant) {
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
		//2 in 6 bridges will be destroyed
		ResourceLocation piece;
		int variant = generationInfo.random.nextInt(6);
		switch(variant) {
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
		if(canGenerate(generationInfo, piece)) {
			//If a bridge can't fit, not many other things will
			return steepRampsUp(generationInfo);
		}
		generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, piece));
		
		BlockPos rotationOffSet = new BlockPos(14, 0, 0).rotate((generationInfo.rotation));
		GenerationInformation result = new GenerationInformation(generationInfo);
		result.position = generationInfo.position.add(rotationOffSet);

		return result;
	}
	
	// increaseBy must be divisible by four
	private static GenerationInformation steepRampsUp(GenerationInformation generationInfo, int increaseBy) {
		GenerationInformation loc = new GenerationInformation(generationInfo);
		for(int i = 0; i<increaseBy; i+=4) {
			loc = steepRampsUp(generationInfo);
		}
		return loc;
	}

	private static GenerationInformation randomTowerLeft(GenerationInformation generationInfo) {
		BlockPos rotationOffSet = new BlockPos(0, 0, -4).rotate(generationInfo.rotation);
		BlockPos blockpos = generationInfo.position.add(rotationOffSet);
		
		/*/////////////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * Tower
		 * 
		 *////////////////////////////////////////////////////////////////////////////////////////////////
		ShulkerFactoryPieces.Piece piece;
		if(generationInfo.random.nextInt(TOWER_WEIGHT + RUINED_TOWER_WEIGHT) < RUINED_TOWER_WEIGHT) {
			piece = new ShulkerFactoryPieces.Piece(generationInfo, RUINED_WATCHTOWER_LEFT);
		} else {
			piece = new ShulkerFactoryPieces.Piece(generationInfo, WATCHTOWER_LEFT);
		}
		
		if(StructurePiece.findIntersecting(generationInfo.pieceList, piece.getBoundingBox()) != null) {
			//Simple way to avoid infinite loop if it, for example, generates 4 left towers in a row
			return steepRampsUp(generationInfo);
		}
		
		generationInfo.pieceList.add(piece);
		/*/////////////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * Supports
		 * 
		 *////////////////////////////////////////////////////////////////////////////////////////////////
		
		rotationOffSet = new BlockPos(2, -3, -2).rotate(generationInfo.rotation);	
		BlockPos structurePos = generationInfo.position.add(rotationOffSet);
		
		if(!canGenerate(generationInfo, REINFORCED_SUPPORT)) {
			do {
				structurePos = structurePos.add(0, -16, 0);
				generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, REINFORCED_SUPPORT));
			} while(structurePos.getY() > 32);
		}
		
		
		rotationOffSet = new BlockPos(4, 2, -5).rotate((generationInfo.rotation));
		blockpos = generationInfo.position.add(rotationOffSet);
		GenerationInformation result = new GenerationInformation(generationInfo);
		result.position = blockpos;
		result.rotation = generationInfo.rotation.add(Rotation.COUNTERCLOCKWISE_90);

		return result;
	}

	
	private static GenerationInformation addRandomPiece(GenerationInformation generationInfo) {
		int randomValue = generationInfo.random.nextInt((RUINED_TOWER_WEIGHT + TOWER_WEIGHT + OPTIONAL_STAIRS_WEIGHT + PLATFORM_WEIGHT + RESTAURAUNT_WEIGHT) * 2);
		GenerationInformation endPosition = new GenerationInformation(generationInfo);
		int previous = 0;
		
		if(randomValue < (previous = (RUINED_TOWER_WEIGHT + TOWER_WEIGHT)*2)) {
			endPosition = randomTowerLeft(generationInfo);
		} else if(randomValue < (previous += PLATFORM_WEIGHT)) {
			endPosition = addTurnLeft(generationInfo);
		} else if(randomValue < (previous += PLATFORM_WEIGHT)) {
			endPosition = addTurnRight(generationInfo);
		} else if(randomValue < (previous += OPTIONAL_STAIRS_WEIGHT)){
			endPosition = steepRampsUp(generationInfo, 4 + generationInfo.random.nextInt(8));
		} else if(randomValue < (previous += OPTIONAL_STAIRS_WEIGHT)){
			endPosition = addBridge(generationInfo);
		} else if(randomValue < (previous += RESTAURAUNT_WEIGHT * 2)){
			endPosition = addRestaurant(generationInfo);
		} else {
			//We shouldn't get here
			Cobbler.LOGGER.info("Did you forget to update the math in Cobbler:ShulkerFactoryPieces.addRandomPiece?");
			endPosition = steepRampsUp(generationInfo, 4 + generationInfo.random.nextInt(8));
		}
		
		return endPosition;
	}
	
	private static GenerationInformation addTurnLeft(GenerationInformation generationInfo) {
		//Move from bottom left corner of entrance to bottom left corner
		BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
		BlockPos blockpos = generationInfo.position.add(rotationOffSet);
		
		if(!canGenerate(generationInfo, LOW_SPLIT_LEFT)) {
			//Simple way to avoid infinite loop if it, for example, generates 4 left towers in a row
			return steepRampsUp(generationInfo);
		}
		ResourceLocation structure;
		int variant = generationInfo.random.nextInt(8);
		switch(variant) {
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
				structure = LOW_SPLIT_LEFT_VAR_EIGHT;
				break;
			default:
				//We shouldn't get here, but the compiler will complain if this isn't included
				structure = LOW_SPLIT_LEFT;
		}
		generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, structure));
		//Move from bl corner to bl corner of stairs up and turn left
		rotationOffSet = new BlockPos(2, 1, -1).rotate(generationInfo.rotation);
		GenerationInformation result = new GenerationInformation(generationInfo);
		result.position = blockpos.add(rotationOffSet);
		result.rotation = generationInfo.rotation.add(Rotation.COUNTERCLOCKWISE_90);

		rotationOffSet = new BlockPos(0, -2, -2).rotate(generationInfo.rotation);	
		BlockPos structurePos = generationInfo.position.add(rotationOffSet);
		if(!canGenerate(generationInfo, SIMPLE_SUPPORT)) {
			do {
				structurePos = structurePos.add(0, -16, 0);
				generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SIMPLE_SUPPORT, structurePos));
			} while(structurePos.getY() > 32);
		}
		
		return result;
	}	
	
	private static GenerationInformation addTurnRight(GenerationInformation generationInfo) {
		//Move from bottom left corner of entrance to bottom left corner
		BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(generationInfo.rotation);
		BlockPos blockpos = generationInfo.position.add(rotationOffSet);
		
		if(!canGenerate(generationInfo, LOW_SPLIT_LEFT)) {
			//Simple way to avoid infinite loop if it, for example, generates 4 left towers in a row
			return steepRampsUp(generationInfo);
		}
		ResourceLocation structure;
		int variant = generationInfo.random.nextInt(9);
		switch(variant) {
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
				//We shouldn't get here, but the compiler will complain if this isn't included
				structure = LOW_SPLIT_RIGHT;
		}
		generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, structure));
		
		//Move from bl corner to bl corner of stairs up and turn right
		rotationOffSet = new BlockPos(6, 1, 9).rotate(generationInfo.rotation);
		GenerationInformation result = new GenerationInformation(generationInfo);
		result.position = blockpos.add(rotationOffSet);
		result.rotation = generationInfo.rotation.add(Rotation.CLOCKWISE_90);

		rotationOffSet = new BlockPos(0, -3, -2).rotate(generationInfo.rotation);	
		BlockPos structurePos = generationInfo.position.add(rotationOffSet);
		if(!canGenerate(generationInfo, SIMPLE_SUPPORT)) {
			do {
				structurePos = structurePos.add(0, -16, 0);
				generationInfo.pieceList.add(new ShulkerFactoryPieces.Piece(generationInfo, SIMPLE_SUPPORT));
			} while(structurePos.getY() > 32);
		}
		
		return result;
	}
	
	
	private static GenerationInformation addRestaurant(GenerationInformation generationInfo) {
		//Move from bottom left corner of entrance to bottom left corner
		BlockPos rotationOffSet = new BlockPos(0, 0, -9).rotate(generationInfo.rotation);
		BlockPos blockpos = generationInfo.position.add(rotationOffSet);
		ShulkerFactoryPieces.Piece piece = new ShulkerFactoryPieces.Piece(generationInfo, RESTAURANT);
		
		if(!canGenerate(generationInfo, RESTAURANT)) {
			//The restaurant is larger than most other structures and rarer, so just let it drop back to random
			return generationInfo;
		}
		generationInfo.pieceList.add(piece);

		rotationOffSet = new BlockPos(0, 12, 12).rotate(generationInfo.rotation);
		GenerationInformation result = new GenerationInformation(generationInfo);
		result.position = blockpos.add(rotationOffSet);
		result.rotation = generationInfo.rotation.add(Rotation.CLOCKWISE_180);
		
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
		return result;
	}
	
	/*
	 * Begins assembling your structure and where the pieces needs to go.
	 */
	public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random)
	{
		int x = pos.getX();
		int z = pos.getZ();
		int y = pos.getY();

		int north_boundry = x + BLOCKS_TO_GENERATION_BOUNDRY;
		int south_boundry = x - BLOCKS_TO_GENERATION_BOUNDRY;
		int west_boundry = z - BLOCKS_TO_GENERATION_BOUNDRY;
		int east_boundry = z + BLOCKS_TO_GENERATION_BOUNDRY;
		
		GenerationInformation generationInfo = new GenerationInformation(north_boundry, south_boundry, west_boundry, east_boundry, pos, rotation, pieceList, templateManager, random);
		//This is how we factor in rotation for multi-piece structures. 
		//
		//I would recommend using the OFFSET map above to have each piece at correct height relative of each other 
		//and keep the X and Z equal to 0. And then in rotations, have the centermost piece have a rotation 
		//of 0, 0, 0 and then have all other pieces' rotation be based off of the bottommost left corner of 
		//that piece (the corner that is smallest in X and Z). 
		//
		//Lots of trial and error may be needed to get this right for your structure.
		
		generationInfo = addEntrance(generationInfo);
		generationInfo = addTurnUp(generationInfo);

		while(generationInfo.position.getY() < 170 
				|| (y < 240 &&  !canGenerate(generationInfo, SPAWNER_ROOM))) {
			//Turn if we get too close to the edge of where we can generate
			if(generationInfo.position.getX() > north_boundry - 35) {
				switch(generationInfo.rotation) {
				case NONE:
					break;
				case CLOCKWISE_90:
					break;
				case CLOCKWISE_180:
					break;
				case COUNTERCLOCKWISE_90:
					break;
				}
			} else if(generationInfo.position.getX() < south_boundry + 35) {
				
			} else if(generationInfo.position.getZ() > east_boundry - 35) {
			
			} else if(generationInfo.position.getZ() < west_boundry + 35) {
				
			} else {
				generationInfo = addRandomPiece(generationInfo);
			}
		}
	
		assembleSpawnerTower(generationInfo);
	}
	
	private static boolean canGenerate(GenerationInformation generationInfo, ResourceLocation structure) {
		MutableBoundingBox boundingBox;
		if(structure.equals(SPAWNER_ROOM)) {
			boundingBox = getSpawnerTowerBoundingBox(new Tuple<BlockPos, Rotation>(generationInfo.position, generationInfo.rotation));
		} else if(structure.equals(REINFORCED_SUPPORT) || structure.equals(SIMPLE_SUPPORT)) {
			boundingBox = getSupportsBoundingBox(new Tuple<BlockPos, Rotation>(generationInfo.position, generationInfo.rotation));
		} else {
			boundingBox = new ShulkerFactoryPieces.Piece(generationInfo, structure).getBoundingBox();
		}
		if(StructurePiece.findIntersecting(generationInfo.pieceList, boundingBox) == null) {
			return true;
		}
		return false;
	}

	/*
	 * Here's where some voodoo happens. Most of this doesn't need to be touched but you do have to pass in the
	 * IStructurePieceType you registered into the super constructors.
	 * 
	 * The method you will most likely want to touch is the handleDataMarker method.
	 */
	public static class Piece extends TemplateStructurePiece
	{
		private ResourceLocation resourceLocation;
		private Rotation rotation;


		public Piece(GenerationInformation generationInfo, ResourceLocation resourceLocationIn)
		{
			super(Structures.FOR_REGISTERING_SHULKER_FACTORY, 0);
			this.resourceLocation = resourceLocationIn;
			BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(resourceLocation);
			this.templatePosition = generationInfo.position.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
			this.rotation = generationInfo.rotation;
			this.setupPiece(generationInfo.templateManager);
		}

		public Piece(GenerationInformation generationInfo, ResourceLocation resourceLocationIn, BlockPos positionOverride)
		{
			super(Structures.FOR_REGISTERING_SHULKER_FACTORY, 0);
			this.resourceLocation = resourceLocationIn;
			BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(resourceLocation);
			this.templatePosition = positionOverride.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
			this.rotation = generationInfo.rotation;
			this.setupPiece(generationInfo.templateManager);
		}


		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound)
		{
			super(Structures.FOR_REGISTERING_SHULKER_FACTORY, tagCompound);
			this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.setupPiece(templateManagerIn);
		}


		private void setupPiece(TemplateManager templateManager)
		{
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
			this.setup(template, this.templatePosition, placementsettings);
		}


		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readAdditional(CompoundNBT tagCompound)
		{
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putString("Rot", this.rotation.name());
		}


		/*
		 * If you added any data marker structure blocks to your structure, you can access and modify them here. In this case,
		 * our structure has a data maker with the string "chest" put into it. So we check to see if the incoming function is
		 * "chest" and if it is, we now have that exact position.
		 * 
		 * So what is done here is we replace the structure block with a chest and we can then set the loottable for it.
		 * 
		 * You can set other data markers to do other behaviors such as spawn a random mob in a certain spot, randomize what
		 * rare block spawns under the floor, or what item an Item Frame will have.
		 */
		@Override
		protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb)
		{
	         if (function.startsWith("Sentry")) {
		            ShulkerEntity shulkerentity = EntityType.SHULKER.create(worldIn.getWorld());
		            shulkerentity.setPosition((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
		            shulkerentity.setAttachmentPos(pos);
		            worldIn.addEntity(shulkerentity);
		            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 1 | 2);
	         } else if("chest".equals(function)) {
				worldIn.setBlockState(pos, Blocks.SHULKER_BOX.getDefaultState(), 2);
				TileEntity tileentity = worldIn.getTileEntity(pos);

				//Just another check to make sure everything is going well before we try to set the chest.
				if (tileentity instanceof ShulkerBoxTileEntity)
				{
					((ShulkerBoxTileEntity) tileentity).setLootTable(FACTORY_LOOT, rand.nextLong());

				}
			}
		}

		@Override
		public boolean func_225577_a_(IWorld worldIn, ChunkGenerator<?> p_225577_2_, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos)
		{
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
			BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(this.resourceLocation);
			this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(0 - blockpos.getX(), 0, 0 - blockpos.getZ())));

			return super.func_225577_a_(worldIn, p_225577_2_, randomIn, structureBoundingBoxIn, chunkPos);
		}
	}

}
