package com.nhwhite3118.cobbler.structures;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.nbt.CompoundNBT;
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
	private static final ResourceLocation LOW_SPLIT_RIGHT = new ResourceLocation(Cobbler.MODID + ":shulkerfactory_low_split_right");
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
	
	private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.<ResourceLocation, BlockPos>builder()
			.put(ENTRANCE, new BlockPos(0, -4, 0))
			.put(LOW_SPLIT_LEFT, new BlockPos(0, -6, 0))  //+5x
			.put(LOW_SPLIT_RIGHT, new BlockPos(0, -6, 0)) //+5x
			.put(WATCHTOWER_SUPPORT, new BlockPos(0, -32, 0))
			.put(WATCHTOWER_LEFT, new BlockPos(0, -2, 0))
			.put(RUINED_WATCHTOWER_LEFT, new BlockPos(0, -2, 0))
			.put(SHORT_BRIDGE_UP, new BlockPos(0, 7, 0)) //+4x +6z
			.put(SPAWNER_OBSIDIAN_BASE, new BlockPos(0, 0, 0))
			.put(SPAWNER_PIT, new BlockPos(0, -10, 0))
			.put(SPAWNER_MIDDLE, new BlockPos(0, 0, 0))
			.put(SPAWNER_ROOM, new BlockPos(0, 0, 0))
			.put(SPAWNER_RAMP, new BlockPos(0, 0, 0))
			.put(SPAWNER_RAMP_SUPPORT, new BlockPos(0, 0, 0))
			.build();

	private static void assembleSpawnerTower(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {

		int x = pos.getX();
		int z = pos.getZ();
		int y = pos.getY();
		
		BlockPos blockpos = pos;
		BlockPos rotationOffSet;
		int spawnerRoomBaseHeight = pos.getY() + 4;
		pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_RAMP, blockpos, rotation));
		
		//y=32 should be right in the middle of most islands
		while(blockpos.getY() > 64) {
			blockpos = blockpos.add(0, -32, 0);
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_RAMP_SUPPORT, blockpos, rotation));
		}
		
		for(int i = -32; i<spawnerRoomBaseHeight; i+=4) {
			rotationOffSet = new BlockPos(10, i, -5).rotate(rotation);	
			blockpos = rotationOffSet.add(x, y, z);
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_MIDDLE, blockpos, rotation));
			
		}
		
		rotationOffSet = new BlockPos(10, -32, -5).rotate(rotation);	
		blockpos = rotationOffSet.add(x, y, z);
		pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_PIT, blockpos, rotation));;
		
		rotationOffSet = new BlockPos(10, spawnerRoomBaseHeight, -5).rotate(rotation);	
		blockpos = rotationOffSet.add(x, y, z);
		pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, SPAWNER_ROOM, blockpos, rotation));
	}
	
	// Randomly picks a left or right split. Returns the position to start the bridge out
	private static Tuple<BlockPos, Rotation> addSplit(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		BlockPos rotationOffSet;
		BlockPos blockpos;
		Rotation rot;
		if(random.nextInt(2) == 0) {
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_LEFT, pos, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
			rotationOffSet = new BlockPos(9, 0, 6).rotate((rotation));
			blockpos = pos.add(rotationOffSet);
			rot = rotation.add(Rotation.COUNTERCLOCKWISE_90);
		} else {
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, LOW_SPLIT_RIGHT, pos, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
			rotationOffSet = new BlockPos(7, 0, 1).rotate((rotation));
			blockpos = pos.add(rotationOffSet);
			rot = rotation.add(Rotation.CLOCKWISE_90);
		}
		return new Tuple<BlockPos, Rotation>(blockpos, rot);
	}
	
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
		BlockPos rotationOffSet = new BlockPos(0, 10, -4).rotate((rotation));
		BlockPos blockpos = pos.add(rotationOffSet);
		
		if(random.nextInt(10) < 6) {
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, RUINED_WATCHTOWER_LEFT, blockpos, rotation));

		} else {
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, WATCHTOWER_LEFT, blockpos, rotation));
		}
		
		BlockPos structurePos = blockpos;
		int supportY = blockpos.getY();
		do {
			supportY -=32;
			rotationOffSet = new BlockPos(0, supportY + 10, -4).rotate(rotation);	
			structurePos = rotationOffSet.add(pos.getX(), pos.getY(), pos.getZ());
			pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, WATCHTOWER_SUPPORT, structurePos, rotation));
		} while(supportY > 32);
		
		rotationOffSet = new BlockPos(5, 0, 0).rotate((rotation));
		blockpos = pos.add(rotationOffSet);

		return new Tuple<BlockPos, Rotation>(blockpos, rotation.add(Rotation.COUNTERCLOCKWISE_90));
	}
	
	/*
	 * Begins assembling your structure and where the pieces needs to go.
	 */
	public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random)
	{
		int x = pos.getX();
		int z = pos.getZ();
		int y = pos.getY();

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
		pieceList.add(new ShulkerFactoryPieces.Piece(templateManager, ENTRANCE, blockpos, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
		
		rotationOffSet = new BlockPos(27, 0, 0).rotate((rotation));
		blockpos = blockpos.add(rotationOffSet);		
		


		Tuple<BlockPos, Rotation> currentLoc = addSplit(templateManager, blockpos, rotation.add(Rotation.COUNTERCLOCKWISE_90), pieceList, random);
		currentLoc = steepRampsUp(templateManager, currentLoc.getA(), currentLoc.getB(), pieceList, random, 8);
		currentLoc = randomTowerLeft(templateManager, currentLoc.getA(), currentLoc.getB(), pieceList, random);
		currentLoc = steepRampsUp(templateManager, currentLoc.getA(), currentLoc.getB(), pieceList, random, 12);
		
		assembleSpawnerTower(templateManager, currentLoc.getA(), currentLoc.getB(), pieceList, random);
//
//		rotationOffSet = new BlockPos(-10, 0, 0).rotate(rotation);
//		blockpos = rotationOffSet.add(x, pos.getY(), z);
//		pieceList.add(new RunDownHousePieces.Piece(templateManager, RIGHT_SIDE, blockpos, rotation));
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
//			if ("chest".equals(function))
//			{
//				worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
//				TileEntity tileentity = worldIn.getTileEntity(pos);
//
//				//Just another check to make sure everything is going well before we try to set the chest.
//				if (tileentity instanceof ChestTileEntity)
//				{
//					//((ChestTileEntity) tileentity).setLootTable(<resource_location_to_loottable>, rand.nextLong());
//
//				}
//			}
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
