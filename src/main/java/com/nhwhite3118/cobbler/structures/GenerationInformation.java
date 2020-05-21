package com.nhwhite3118.cobbler.structures;

import java.util.List;
import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

/*
 * To be used by ShulkerFactoryPieces. Stores a bunch of information so that I don't have to pass a million variables
 * through each method start calls, or use static variables and leak memory.
 */
public class GenerationInformation {
    public int north_boundry;
    public int south_boundry;
    public int west_boundry;
    public int east_boundry;
    public BlockPos position;
    public Rotation rotation;
    public List<StructurePiece> pieceList;
    public TemplateManager templateManager;
    public Random random;
    public boolean lastGenerationSucceded = true;
    public ResourceLocation lastStructureAttempted;

    GenerationInformation(int north, int south, int west, int east, BlockPos pos, Rotation rot,
            List<StructurePiece> pieces, TemplateManager template, Random rand) {
        north_boundry = north;
        south_boundry = south;
        west_boundry = west;
        east_boundry = east;
        position = pos;
        rotation = rot;
        pieceList = pieces;
        templateManager = template;
        random = rand;
    }

    /*
     * Copy Constructor
     */
    GenerationInformation(GenerationInformation in) {
        north_boundry = in.north_boundry;
        south_boundry = in.south_boundry;
        west_boundry = in.west_boundry;
        east_boundry = in.east_boundry;
        position = in.position;
        rotation = in.rotation;
        // We don't want a deep copy, we just need to keep a reference to it
        pieceList = in.pieceList;
        templateManager = in.templateManager;
        random = in.random;
    }

}
