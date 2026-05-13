package net.pazooni.LessLonely.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ClaimBedGoal extends Goal {
    private final PathfinderMob mob;
    private double speedModifier;
    private int searchRadius = 16; // How close [meters] mob has to be to bed to be able to use this function
    private int vertSearchRadius = 8;

    public ClaimBedGoal(PathfinderMob mob, double speedModifier){
        this.mob = mob;
        this.speedModifier = speedModifier;
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
                        return bedPos;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean canUse() {
        if (findBed() != null) {
            return true;
        }
        mob.setPose(Pose.STANDING);
        return false;
    }

    @Override
    public void tick() {
        if (canUse()){
            mob.setPose(Pose.SLEEPING);
        } else {
            mob.setPose(Pose.STANDING);
        }
    }
}
