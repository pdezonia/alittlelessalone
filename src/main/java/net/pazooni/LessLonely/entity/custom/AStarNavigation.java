package net.pazooni.LessLonely.entity.custom;
// Almost all of this code was with the help of claude *sigh* so that's why it
// looks like i am writing above my skill level;
// refer to (2.) of claude 20261008-2
// refer to (2.) of claude 20261008-1

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.NotNull;


public class AStarNavigation extends GroundPathNavigation {
    public AStarNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new CustomNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanOpenDoors(true);
        return new VillagerPathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}
