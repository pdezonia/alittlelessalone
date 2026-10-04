package net.pazooni.LessLonely.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.datafix.fixes.BedItemColorFix;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;


public class ClaimBedGoal extends Goal {
    private final PathfinderMob mob;
    private double speedModifier;
    private int searchRadius = 16; // How close [meters] mob has to be to bed to be able to use this function
    private int vertSearchRadius = 8;
    public DyeColor occupiedColor = DyeColor.LIME; // Chosen for being pretty rare
    public BlockPos targetPos;
    public BlockPos claimedBedPos;

    public ClaimBedGoal(PathfinderMob mob, double speedModifier, DyeColor occupiedColor){
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.occupiedColor = occupiedColor;
    }

    private Boolean bedStillFree() {
        BlockState targetBedState = mob.level().getBlockState(targetPos);
        if (targetBedState.isBed(mob.level(), targetPos, mob)) {
            DyeColor targetBedColor = ((BedBlock) targetBedState.getBlock()).getColor();
            return (targetBedColor != occupiedColor);
        }
        return false;
    }

    private BlockPos findBed() {
        BlockPos mobPos = this.mob.blockPosition();
        BlockPos bedPos;
        for (int y = mobPos.getY() - vertSearchRadius/2; y < mobPos.getY() + vertSearchRadius/2; y++) {
            for (int x = mobPos.getX() - searchRadius/2; x < mobPos.getX() + searchRadius/2; x++) {
                for (int z = mobPos.getZ() - searchRadius/2; z < mobPos.getZ() + searchRadius/2; z++) {
                    bedPos = new BlockPos(x, y, z);
                    BlockState blockState = mob.level().getBlockState(bedPos);
                    if (blockState.isBed(mob.level(), bedPos, mob)) {
                        /* Test if we can get bed color now
                         * THIS TOOK A LONG TIME SO I'M WRITING IT DOWN
                         * If we have a POSITION, we can check if there is a bed at it with BLOCKSTATE
                         * however, in order to treat what we know to be a bed at that position,
                         * USING BEDBLOCK functions, we must first convert that BLOCKSTATE into a BLOCK with
                         * .GETBLOCK() and then CAST that BLOCK into a BEDBLOCK with (BEDBLOCK)!!
                         */
                        DyeColor bedColor = ((BedBlock) blockState.getBlock()).getColor();
                        if (this.occupiedColor != bedColor) {
                            targetPos = bedPos;
                            return bedPos;
                        }
                        /*
                        //This actually works pretty well during run time for finding out what properties we have access to
                        blockState.getProperties().forEach(prop -> {
                            System.out.println(prop.getName());
                        });
                         */
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean canUse() {
        // canUse() only gets called once when the goal it belongs to is chosen,
        // if canUse returns false, then the goal is abandoned and nothing else in it is run until it is called again
        targetPos = findBed();
        if (targetPos != null) {
            // Send the mob on its way!
            this.mob.getNavigation().createPath(targetPos, 2);
//            mob.setPose(Pose.SLEEPING);
            return true;
        }
        mob.setPose(Pose.STANDING);
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return bedStillFree();
    }

    @Override
    public void tick() {
        super.tick();
        /*
        if (this.mob.getNavigation().isDone()) {
            // Replace bed with bed of occupied color
            this.claimedBedPos = targetPos;
            System.out.println("=============== i found a bed!!!! ===============");
            // ok so the problem now is that we can detect when a bed is nearby and path to it (although the test
            // wasn't terribly convincing) but now we have no way of communicating to other simpeople that the bed
            // is claimed. changing the color of the bed is prohibitively difficult btw
//            BlockState targetBedState = mob.level().getBlockState(this.claimedBedPos);
//            BedBlock newBed = ((BedBlock) targetBedState.getBlock());
//            newBed.destroy(mob.level(), this.claimedBedPos, targetBedState);
//            mob.level().setBlock(this.claimedBedPos, targetBedState.setValue(BedBlock.DyeColor, DyeColor.LIME), Block.UPDATE_IMMEDIATE);
        }
         */
    }
}
