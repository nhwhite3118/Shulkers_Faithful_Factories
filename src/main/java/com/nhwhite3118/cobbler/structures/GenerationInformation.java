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
    public boolean isMainPath = true;

    GenerationInformation(int north, int south, int west, int east, BlockPos pos, Rotation rot, List<StructurePiece> pieces, TemplateManager template,
            Random rand, boolean isMainPath) {
        north_boundry = north;
        south_boundry = south;
        west_boundry = west;
        east_boundry = east;
        position = pos;
        rotation = rot;
        pieceList = pieces;
        templateManager = template;
        random = rand;
        this.isMainPath = isMainPath;
    }

    /*
     * Copy Constructor
     */
    GenerationInformation(GenerationInformation in) {
        Copy(in);
    }

    private void Copy(GenerationInformation in) {
        north_boundry = in.north_boundry;
        south_boundry = in.south_boundry;
        west_boundry = in.west_boundry;
        east_boundry = in.east_boundry;
        position = in.position;
        rotation = in.rotation;
        isMainPath = in.isMainPath;
        // We don't want a deep copy, we just need to keep a reference to it
        pieceList = in.pieceList;
        templateManager = in.templateManager;
        random = in.random;
    }

    /*
     * Copy Constructors with options for commonly changed things. If only Java allowed default values.
     */
    GenerationInformation(GenerationInformation in, BlockPos newPos) {
        Copy(in);
        position = newPos;
    }

    GenerationInformation(GenerationInformation in, Rotation newRot) {
        Copy(in);
        rotation = newRot;
    }

    GenerationInformation(GenerationInformation in, BlockPos newPos, Rotation newRot) {
        Copy(in);
        position = newPos;
        rotation = newRot;
    }

    GenerationInformation(GenerationInformation in, boolean mainPath) {
        Copy(in);
        isMainPath = mainPath;
    }

    GenerationInformation(GenerationInformation in, BlockPos newPos, Rotation newRot, boolean mainPath) {
        Copy(in);
        position = newPos;
        rotation = newRot;
        isMainPath = mainPath;
    }

    GenerationInformation(GenerationInformation in, BlockPos newPos, boolean mainPath) {
        Copy(in);
        position = newPos;
        isMainPath = mainPath;
    }

}
