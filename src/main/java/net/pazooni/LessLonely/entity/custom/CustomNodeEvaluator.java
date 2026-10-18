package net.pazooni.LessLonely.entity.custom;
// Almost all of this code was with the help of claude *sigh* so that's why it
// looks like i am writing above my skill level;
// refer to (3.) of claude 20261008-1
// refer also to claude 20261017-1
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.NotNull;

public class CustomNodeEvaluator extends WalkNodeEvaluator {
    private static final float VANILLA_MALUS = 5.0F;

    @Override
    public int getNeighbors(Node[] output, Node node) {
        int count = super.getNeighbors(output, node);
        for (int i = 0; i < count; i++) {
            applyVanillaPenalty(output[i]);
        }
        return count;
    }

    private void applyVanillaPenalty(Node node) {
        BlockPos pos = node.asBlockPos();
        // Claude calls out wanting to penalize blocks based on what they stand
        // on in 20261017-1
        if (isVanilla(pos) || isVanilla(pos.below())) {
            // claude: "max() keeps this idempotent, since nodes are
            // cached per search"
            node.costMalus = Math.max(node.costMalus, VANILLA_MALUS);
        }
    }

    private boolean isVanilla(BlockPos pos) {
        BlockState state = currentContext.getBlockState(pos);
        if (state.isAir()) {
            return false;
        }
        if (BuiltInRegistries.BLOCK.getKey(state.getBlock())
                .getNamespace().equals("LessLonely")) {
            System.out.println("=============== namespace match!!!! ===============");
            return false;
        }
        System.out.println("=============== isVanilla called!!!! ===============");
        return true;
//        return BuiltInRegistries.BLOCK.getKey(state.getBlock())
//                .getNamespace().equals("minecraft");
    }

    @Override
    public @NotNull PathType getPathType(PathfindingContext context, int x, int y, int z) {
        PathType base = super.getPathType(context, x, y, z);

        BlockState state = context.level().getBlockState(new BlockPos(x, y , z));

        // claude: "Example: treat soul sand as impassable and avoid magma
        if (state.is(Blocks.SOUL_SAND)) return PathType.BLOCKED;
        if (state.is(Blocks.MAGMA_BLOCK)) return PathType.DAMAGE_OTHER;

        return base;
    }

}
