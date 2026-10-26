package net.pazooni.LessLonely.entity.custom;
// refer to claude 20261026-1
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class WalkToCreateBlock extends Behavior<Villager> {
    private final ResourceLocation blockId;
    private final int radius;
    private final float speed;
    private long nextSearch;

    public WalkToCreateBlock(ResourceLocation blockId, int radius, float speed) {
        // cluade: "no memory requirements, max run time 600 ticks"
        super(Map.of(), 600);
        this.blockId = blockId;
        this.radius = radius;
        this.speed = speed;
    }

    private Optional<BlockPos> findTarget(ServerLevel level, Villager villager) {
        // claude: "returns air if Create isn't loaded"
        // I think this means claude knows/asssumes that block will be AIR
        // if Create (the mod) isn't loaded
        System.out.println("IN WALKTTOCREATEBLOCK");
        Block block = BuiltInRegistries.BLOCK.get(blockId);
        System.out.println("target block is: " + block);
        if (block == Blocks.AIR) return Optional.empty();

        BlockPos origin = villager.blockPosition();
        // I think what is happening here is claude is just assuming that we
        // won't need to look higher or lower than 4 meters from the villager
        Optional<BlockPos> target_blockpos =  BlockPos.betweenClosedStream(
                origin.offset(-radius, -4, -radius),
                origin.offset(radius, 4, radius))
                .map(BlockPos::immutable) // famous time when claude got the order of function calls wrong
                .filter(p -> level.isLoaded(p) && level.getBlockState(p).is(block))
                .min(Comparator.comparingDouble(p -> p.distSqr(origin)));
//                .filter(p -> level.isLoaded(p) && level.getBlockState(p).is(block))
//                .min(Comparator.comparingDouble(p -> p.distSqr(origin)))
//                .map(BlockPos::immutable);

        System.out.println("target block position is: " + target_blockpos);

        return target_blockpos;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Villager villager) {
        if (level.getGameTime() < nextSearch) return false;
        nextSearch = level.getGameTime() + 100; // claude: "throttle the scan"
        return findTarget(level, villager).isPresent();
    }

    @Override
    protected void start(ServerLevel level, Villager villager, long gameTime) {
        findTarget(level, villager).ifPresent(pos ->
                BehaviorUtils.setWalkAndLookTargetMemories(villager, pos, speed, 2));
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Villager villager, long gameTime) {
        return villager.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET);
    }
    /*
    Note that claude has this to say about this java class:
    The behavior only sets the WALK_TARGET memory. The villager’s built-in
    MoveToTargetSink in the core activity does the pathfinding.
     */
}
