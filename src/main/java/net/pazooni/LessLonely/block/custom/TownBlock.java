package net.pazooni.LessLonely.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.pazooni.LessLonely.block.ModBlockEntities;
import net.pazooni.LessLonely.entity.custom.SimpersonEntity;
import org.jetbrains.annotations.NotNull;

public class TownBlock extends Block implements EntityBlock {
    private int townRad = 128; // defines a square with side length 256

    public TownBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /*************************************************************************
     **** THIS is where the BlockEntity and Block are wed to each other!! ****
     *************************************************************************/
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new TownBlockEntity(blockPos,blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        int simpersonCount = ModBlockEntities.TOWN_BLOCK_ENTITY.get().getBlockEntity(level, pos).countAnimals(pos, SimpersonEntity.class, townRad);
        player.sendSystemMessage(Component.literal( + simpersonCount + " simpeople in this town."));
        return InteractionResult.SUCCESS;
    }

    public boolean withinTown(BlockPos pos) {
        // when given a block position, will respond with whether said blockpos is within
        // this town block's borders in x and z
        boolean isInTown = false;
        int queryX = pos.getX();
        int gueryZ = pos.getZ();
//        if (queryX > )
        // HOW DO I ACCESS THIS BLOCK'S POSITION?
        // THAT MIGHT NOT EVEN BE POSSIBLE SINCE BLOCKS DON'T "HAVE" A POSITION ACCORDING TO NEOFORGED DOCS
        // SO THAT KIND OF SOLVES THE PROBLEM OF HOW TO HANDLE MULTIPLE TOWNS
        // I GUESS WHAT WE NEED TO DO THEN IS MAINTAIN A LIST OF BLOCKPOS'S AS TOWN BLOCKS ARE PLACED AND
        // DESTROYED AND THEN RETURN A BLOCKPOS INDEX FOR A GIVEN QUERIED LOCATION (SUCH AS OF A BED)
        // RATHER THAN A TRUE OR FALSE
        // WE MIGHT BE BETTER OFF MAKING A CUSTOM BED RECIPE FOR A CUSTOM BED THAT ONLY SIMPEOPLE CAN USE
        // AND LETTING BEDS STORE THE VILLAGER ID ASSOCIATED WITH THEM
        // FORTUNATELY IT SEEMS LIKE ENTITIES ARE MUCH MORE CAPABLE OF STORING INFO THAN BLOCKS
        return isInTown;
    }
}
