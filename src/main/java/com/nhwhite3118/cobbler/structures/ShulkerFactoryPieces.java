package com.nhwhite3118.cobbler.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.Level;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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
import net.minecraft.world.storage.loot.LootTables;
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
	private static final ResourceLocation LOW_SPLIT_LEFT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_improved");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_ONE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var1");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_TWO = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var2");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_THREE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var3");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_FOUR = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var4");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_FIVE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var5");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_SIX = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var6");
	private static final ResourceLocation LOW_SPLIT_LEFT_VAR_EIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_left_var7");
	private static final ResourceLocation LOW_SPLIT_RIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_improved");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_ONE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var1");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_TWO = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var2");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_THREE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var3");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_FOUR = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var4");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_FIVE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var5");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_SIX = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var6");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_SEVEN = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var7");
	private static final ResourceLocation LOW_SPLIT_RIGHT_VAR_EIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right_var8");
	private static final ResourceLocation WATCHTOWER_SUPPORT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_support_9x18x9");
	private static final ResourceLocation WATCHTOWER_LEFT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_watch_tower_left");
	private static final ResourceLocation RUINED_WATCHTOWER_LEFT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_ruined_watch_tower_left");
	private static final ResourceLocation SHORT_BRIDGE_UP = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_short_bridge_up");
	private static final ResourceLocation SPAWNER_OBSIDIAN_BASE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_obsidian_base");
	private static final ResourceLocation SPAWNER_PIT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_pit");
	private static final ResourceLocation SPAWNER_MIDDLE = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_tower_middle_tileable");
	private static final ResourceLocation SPAWNER_ROOM = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_tower_top");
	private static final ResourceLocation SPAWNER_RAMP = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_ramp");
	private static final ResourceLocation SPAWNER_RAMP_SUPPORT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_spawner_ramp_supports");
	private static final ResourceLocation FACTORY_LOOT = new ResourceLocation(Cobbler.MODID + ":chests/shulker_factory_treasure");
	
	private static int TOWER_WEIGHT = 5;
	private static int RUINED_TOWER_WEIGHT = 10;
	private static int PLATFORM_WEIGHT = 40;
	private static int OPTIONAL_STAIRS_WEIGHT = 5;
	
	private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.<ResourceLocation, BlockPos>builder()
			.put(ENTRANCE, new BlockPos(0, -3, 0))
			.put(LOW_SPLIT_LEFT, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_ONE, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_TWO, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_THREE, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_FOUR, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_FIVE, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_SIX, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_LEFT_VAR_EIGHT, new BlockPos(0, -3, 0))  //+5x
			.put(LOW_SPLIT_RIGHT, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_ONE, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_TWO, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_THREE, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_FOUR, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_FIVE, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_SIX, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_SEVEN, new BlockPos(0, -3, 0)) //+5x
			.put(LOW_SPLIT_RIGHT_VAR_EIGHT, new BlockPos(0, -3, 0)) //+5x
			.put(WATCHTOWER_SUPPORT, new BlockPos(0, 0, 0))
			.put(WATCHTOWER_LEFT, new BlockPos(0, -1, 0))
			.put(RUINED_WATCHTOWER_LEFT, new BlockPos(0, -1, 0))
			.put(SHORT_BRIDGE_UP, new BlockPos(0, -2, 0)) //+4x +6z
			//y values for tower pieces are handled in the creation method
			.put(SPAWNER_OBSIDIAN_BASE, new BlockPos(0, 0, 0))
			.put(SPAWNER_PIT, new BlockPos(0, -8, 0))
			.put(SPAWNER_MIDDLE, new BlockPos(0, -8, 0))
			.put(SPAWNER_ROOM, new BlockPos(0, -8, 0))
			.put(SPAWNER_RAMP, new BlockPos(0, -28, 0))
			.put(SPAWNER_RAMP_SUPPORT, new BlockPos(0, -9, 0))
			.build();

	private static void assembleSpawnerTower(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {

		int x = pos.getX();
		int z = pos.getZ();
		int spawnerRoomHeight = pos.getY() + 4;
		BlockPos blockpos;
		BlockPos rotationOffSet;
		
		/*///////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * 	Ramp
		 * 
		 *///////////////////////////////////////////////////////////////////////////////////////////
		{
			rotationOffSet = new BlockPos(0, 0, -2).rotate(rotation);
			blockpos = pos.add(rotationOffSet);
			
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_RAMP, blockpos, rotation));
			
			//y=32 should be right in the middle of most islands
			while(blockpos.getY() > 57) {
				blockpos = blockpos.add(0, -32, 0);
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_RAMP_SUPPORT, blockpos, rotation));
			}
		}
		/*///////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * 	Tower
		 * 
		 *///////////////////////////////////////////////////////////////////////////////////////////
		{
			rotationOffSet = new BlockPos(10, spawnerRoomHeight - 32, -6).rotate(rotation);	
			blockpos = rotationOffSet.add(x, 0, z);
			while(blockpos.getY() <= spawnerRoomHeight - 4) {
				blockpos = blockpos.add(0, 4, 0);
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_MIDDLE, blockpos, rotation));
				
			}
			
			rotationOffSet = new BlockPos(10, spawnerRoomHeight -42, -6).rotate(rotation);	
			blockpos = rotationOffSet.add(x, 0, z);
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_PIT, blockpos, rotation));;
			
			blockpos = blockpos.add(0, -8, 0);
			while(blockpos.getY() > 2) {
				blockpos = blockpos.add(0, -2, 0);
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_OBSIDIAN_BASE, blockpos, rotation));
				
			}
			
			rotationOffSet = new BlockPos(10, 0, -6).rotate(rotation);	
			blockpos = rotationOffSet.add(x, spawnerRoomHeight, z);
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_ROOM, blockpos, rotation));
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
		BlockPos netCorner = location.getA().add(new BlockPos(9,-1, 9).rotate(location.getB()));
		return new MutableBoundingBox(
				Math.min(swbCorner.getX(), netCorner.getX()),
				Math.min(swbCorner.getZ(), netCorner.getZ()),
				Math.max(swbCorner.getX(), netCorner.getX()),
				Math.max(swbCorner.getZ(), netCorner.getZ()));
		
	}
	
	// Randomly picks a left or right split. Returns the position to start the bridge out
	private static Tuple<BlockPos, Rotation> addTurnUp(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		if(random.nextInt(2) == 0) {
			return addTurnLeft(templateManager, pos, rotation, pieceList, random);
		}
		return addTurnRight(templateManager, pos, rotation, pieceList, random);
	}

	// Adds a 4 high, 4 long bridge
	private static Tuple<BlockPos, Rotation> steepRampsUp(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SHORT_BRIDGE_UP, pos, rotation));
		BlockPos rotationOffSet = new BlockPos(4, 4, 0).rotate((rotation));
		BlockPos blockpos = pos.add(rotationOffSet);

		return new Tuple<BlockPos, Rotation>(blockpos, rotation);
	}
	
	// increaseBy must be divisible by four
	private static Tuple<BlockPos, Rotation> steepRampsUp(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random, int increaseBy) {
		Tuple<BlockPos, Rotation> loc = new Tuple<BlockPos, Rotation>(pos, rotation);
		for(int i = 0; i<increaseBy; i+=4) {
			loc = steepRampsUp(templateManager, loc.getA(), loc.getB(), pieceList, random);
		}
		return loc;
	}

	private static Tuple<BlockPos, Rotation> randomTowerLeft(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		BlockPos rotationOffSet = new BlockPos(0, 0, -4).rotate((rotation));
		BlockPos blockpos = pos.add(rotationOffSet);
		
		/*/////////////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * Tower
		 * 
		 *////////////////////////////////////////////////////////////////////////////////////////////////
		ShulkerFactoryPieces.Piece piece;
		if(random.nextInt(TOWER_WEIGHT + RUINED_TOWER_WEIGHT) < RUINED_TOWER_WEIGHT) {
			piece = new ShulkerFactoryPieces.Piece(templateManager, RUINED_WATCHTOWER_LEFT, blockpos, rotation);
		} else {
			piece = new ShulkerFactoryPieces.Piece(templateManager, WATCHTOWER_LEFT, blockpos, rotation);
		}
		
		if(StructurePiece.findIntersecting(pieceList, piece.getBoundingBox()) != null) {
			//Simple way to avoid infinite loop if it, for example, generates 4 left towers in a row
			return steepRampsUp(templateManager, pos, rotation, pieceList, random);
		}
		
		pieceList.add(piece);
		/*/////////////////////////////////////////////////////////////////////////////////////////////////
		 * 
		 * Supports
		 * 
		 *////////////////////////////////////////////////////////////////////////////////////////////////
		
		rotationOffSet = new BlockPos(2, -3, -2).rotate(rotation);	
		BlockPos structurePos = pos.add(rotationOffSet);
		
		if(StructurePiece.findIntersecting(pieceList, getSupportsBoundingBox(new Tuple<BlockPos, Rotation>(structurePos, rotation))) == null) {
			do {
				structurePos = structurePos.add(0, -16, 0);
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, WATCHTOWER_SUPPORT, structurePos, rotation));
			} while(structurePos.getY() > 32);
		}
		
		
		rotationOffSet = new BlockPos(4, 2, -5).rotate((rotation));
		blockpos = pos.add(rotationOffSet);

		return new Tuple<BlockPos, Rotation>(blockpos, rotation.add(Rotation.COUNTERCLOCKWISE_90));
	}

	
	private static Tuple<BlockPos, Rotation> addRandomPiece(TemplateManager templateManager, Tuple<BlockPos, Rotation> location, List<StructurePiece> pieceList, Random random) {
		int randomValue = random.nextInt(100);
		Tuple<BlockPos, Rotation> endPosition;
		
		if(randomValue > 50) {
			endPosition = addNonClockWisePiece(templateManager, location, pieceList, random);
		} else {
			endPosition = addNonCounterClockWisePiece(templateManager, location, pieceList, random);
		}
		return endPosition;
	}
	
	
	private static Tuple<BlockPos, Rotation> addNonClockWisePiece(TemplateManager templateManager, Tuple<BlockPos, Rotation> location, List<StructurePiece> pieceList, Random random) {
		int randomValue = random.nextInt((RUINED_TOWER_WEIGHT + TOWER_WEIGHT)*2 + OPTIONAL_STAIRS_WEIGHT + PLATFORM_WEIGHT);
		Tuple<BlockPos, Rotation> endPosition = location;
		
		if(randomValue < (RUINED_TOWER_WEIGHT + TOWER_WEIGHT)*2) {
			endPosition = randomTowerLeft(templateManager, location.getA(), location.getB(), pieceList, random);
		} else if(randomValue < (RUINED_TOWER_WEIGHT + TOWER_WEIGHT)*2 + PLATFORM_WEIGHT) {
			endPosition = addTurnLeft(templateManager, location.getA(), location.getB(), pieceList, random);
		} else if(randomValue < (RUINED_TOWER_WEIGHT + TOWER_WEIGHT)*2 + PLATFORM_WEIGHT + OPTIONAL_STAIRS_WEIGHT){
			endPosition = steepRampsUp(templateManager, location.getA(), location.getB(), pieceList, random, 4 + random.nextInt(8));
		}
		return endPosition;
	}	
	
	
	private static Tuple<BlockPos, Rotation> addNonCounterClockWisePiece(TemplateManager templateManager, Tuple<BlockPos, Rotation> location, List<StructurePiece> pieceList, Random random) {
		int randomValue = random.nextInt(OPTIONAL_STAIRS_WEIGHT + PLATFORM_WEIGHT);
		Tuple<BlockPos, Rotation> endPosition = location;
		
		if(randomValue < PLATFORM_WEIGHT) {
			endPosition = addTurnRight(templateManager, location.getA(), location.getB(), pieceList, random);
		} else if(randomValue < PLATFORM_WEIGHT + OPTIONAL_STAIRS_WEIGHT){
			endPosition = steepRampsUp(templateManager, location.getA(), location.getB(), pieceList, random, 4 + random.nextInt(8));
		}
		return endPosition;
	}	
	
	private static Tuple<BlockPos, Rotation> addTurnLeft(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		//Move from bl corner of entrance to bl corner
		BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(rotation);
		BlockPos blockpos = pos.add(rotationOffSet);
		
		if(StructurePiece.findIntersecting(pieceList, new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT, blockpos, rotation).getBoundingBox()) != null) {
			//Simple way to avoid infinite loop if it, for example, generates 4 left towers in a row
			return steepRampsUp(templateManager, pos, rotation, pieceList, random);
		}
		
		Rotation rot;
		int variant = random.nextInt(8);
		switch(variant) {
			case 0:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT, blockpos, rotation));
				break;
			case 1:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_ONE, blockpos, rotation));
				break;
			case 2:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_TWO, blockpos, rotation));
				break;
			case 3:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_THREE, blockpos, rotation));
				break;
			case 4:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_FOUR, blockpos, rotation));
				break;
			case 5:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_FIVE, blockpos, rotation));
				break;
			case 6:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_SIX, blockpos, rotation));
				break;
			case 7:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT_VAR_EIGHT, blockpos, rotation));
				break;
		}
		//Move from bl corner to bl corner of stairs up and turn left
		rotationOffSet = new BlockPos(2, 1, -1).rotate((rotation));
		BlockPos finalPos = blockpos.add(rotationOffSet);
		rot = rotation.add(Rotation.COUNTERCLOCKWISE_90);

		rotationOffSet = new BlockPos(0, -2, -2).rotate(rotation);	
		BlockPos structurePos = pos.add(rotationOffSet);
		do {
			structurePos = structurePos.add(0, -16, 0);
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, WATCHTOWER_SUPPORT, structurePos, rotation));
		} while(structurePos.getY() > 32);
		
		return new Tuple<BlockPos, Rotation>(finalPos, rot);
	}	
	
	
	private static Tuple<BlockPos, Rotation> addTurnRight(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		//Move from bl corner of entrance to bl corner
		BlockPos rotationOffSet = new BlockPos(0, 0, -2).rotate(rotation);
		BlockPos blockpos = pos.add(rotationOffSet);
		
		if(StructurePiece.findIntersecting(pieceList, new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT, blockpos, rotation).getBoundingBox()) != null) {
			//Simple way to avoid infinite loop if it, for example, generates 4 left towers in a row
			return steepRampsUp(templateManager, pos, rotation, pieceList, random);
		}
		int variant = random.nextInt(9);
		switch(variant) {
			case 0:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT, blockpos, rotation));
				break;
			case 1:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_ONE, blockpos, rotation));
				break;
			case 2:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_TWO, blockpos, rotation));
				break;
			case 3:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_THREE, blockpos, rotation));
				break;
			case 4:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_FOUR, blockpos, rotation));
				break;
			case 5:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_FIVE, blockpos, rotation));
				break;
			case 6:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_SIX, blockpos, rotation));
				break;
			case 7:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_SEVEN, blockpos, rotation));
				break;
			case 8:
				pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT_VAR_EIGHT, blockpos, rotation));
				break;
		}
		
		//Move from bl corner to bl corner of stairs up and turn right
		rotationOffSet = new BlockPos(6, 1, 9).rotate((rotation));
		BlockPos finalPos = blockpos.add(rotationOffSet);
		Rotation rot = rotation.add(Rotation.CLOCKWISE_90);

		rotationOffSet = new BlockPos(0, -3, -2).rotate(rotation);	
		BlockPos structurePos = pos.add(rotationOffSet);
		do {
			structurePos = structurePos.add(0, -16, 0);
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, WATCHTOWER_SUPPORT, structurePos, rotation));
		} while(structurePos.getY() > 32);
		
		return new Tuple<BlockPos, Rotation>(finalPos, rot);
	}
	
	/*
	 * Begins assembling your structure and where the pieces needs to go.
	 */
	public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random)
	{
		int x = pos.getX();
		int z = pos.getZ();
		int y = pos.getY();
		ArrayList<Tuple<BlockPos, BlockPos>> existingStructures = new ArrayList<Tuple<BlockPos, BlockPos>>();

		//This is how we factor in rotation for multi-piece structures. 
		//
		//I would recommend using the OFFSET map above to have each piece at correct height relative of each other 
		//and keep the X and Z equal to 0. And then in rotations, have the centermost piece have a rotation 
		//of 0, 0, 0 and then have all other pieces' rotation be based off of the bottommost left corner of 
		//that piece (the corner that is smallest in X and Z). 
		//
		//Lots of trial and error may be needed to get this right for your structure.
		BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockpos = rotationOffSet.add(x, y, z);
		pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, ENTRANCE, blockpos, rotation));
		existingStructures.add(new Tuple<BlockPos, BlockPos>(blockpos, blockpos.add(27,0, 15)));
		
		rotationOffSet = new BlockPos(27, 9, 3).rotate((rotation));
		blockpos = blockpos.add(rotationOffSet);
		
		Tuple<BlockPos, Rotation> currentLoc = addTurnUp(templateManager, blockpos, rotation, pieceList, random);

		while(currentLoc.getA().getY() < 170 || (y < 240 &&  StructurePiece.findIntersecting(pieceList, getSpawnerTowerBoundingBox(currentLoc)) != null)) {
			currentLoc = addRandomPiece(templateManager, currentLoc, pieceList, random);
		}
	
		assembleSpawnerTower(templateManager, currentLoc.getA(), currentLoc.getB(), pieceList, random);
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


		public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn)
		{
			super(Structures.RDHP, 0);
			this.resourceLocation = resourceLocationIn;
			BlockPos blockpos = ShulkerFactoryPieces.OFFSET.get(resourceLocation);
			this.templatePosition = pos.add(blockpos.getX(), blockpos.getY(), blockpos.getZ());
			this.rotation = rotationIn;
			this.setupPiece(templateManagerIn);
		}


		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound)
		{
			super(Structures.RDHP, tagCompound);
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
