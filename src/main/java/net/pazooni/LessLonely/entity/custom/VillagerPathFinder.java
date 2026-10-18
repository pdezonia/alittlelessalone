package net.pazooni.LessLonely.entity.custom;
// Almost all of this code was with the help of claude *sigh* so that's why it
// looks like i am writing above my skill level; refer to (1.) of
// claude 20261008-2
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class VillagerPathFinder extends PathFinder {
    private final int maxVistedNodes;

    public VillagerPathFinder(NodeEvaluator evaluator, int maxVisitedNodes) {
        super(evaluator, maxVisitedNodes);
        this.maxVistedNodes = maxVisitedNodes;
    }

    // Rec was previously called entry which i think made more sense
    private record Rec(BlockPos pos, Rec Parent, double g, double f) {} // idk wtf this does

    @Nullable
    @Override
    public Path findPath(PathNavigationRegion region, Mob mob, Set<BlockPos> targets,
                         float maxRange, int accuracy, float searchDepthMultiplier) {
        if (targets.isEmpty()) return null;
        BlockPos start = mob.blockPosition();
        BlockPos goal = targets.stream()
                .min(Comparator.comparingDouble(t -> t.distSqr(start))).get();

        PriorityQueue<Rec> open = new PriorityQueue<>(Comparator.comparingDouble(Rec::f));
        Map<BlockPos, Double> best = new HashMap<>();
        open.add(new Rec(start, null, 0, h(start, goal))); // TODO SUS
        best.put(start, 0.0);

        Rec closest = null;
        double closestH = Double.MAX_VALUE;
        int visited = 0;

        while (!open.isEmpty() && visited++ < maxVistedNodes) {
            Rec cur = open.poll();
            // TODO pos() is SUS
            if (cur.g() > best.getOrDefault(cur.pos(), Double.MAX_VALUE)) continue; // claude: "stale entry"

            double hc = h(cur.pos, goal);
            if (hc < closestH) {
                closestH = hc;
                closest = cur;
            }
            if (nearAny(cur.pos(), targets)) return build(cur, goal, true); // TODO pos() is SUS
            if (cur.pos().distSqr(start) > maxRange * maxRange) continue;

            for (Direction dir : Direction.Plane.HORIZONTAL) {
                for (int dy = 1; dy >= -3; dy--) { // claude: "step up 1, drop up to 3" // also TODO SUS
                    BlockPos next = cur.pos().relative(dir).above(dy);
                    if (!walkable(region, next)) continue;
                    // claude: "// headroom to jump"
                    if (dy == 1 && !clear(region, cur.pos().above(2))) continue;
                    double g = cur.g() + 1 + (dy != 0 ? 0.5 : 0); // claude: "<-- your custom cost goes here" // TODO sus for logic
                    if (g < best.getOrDefault(next, Double.MAX_VALUE)) {
                        best.put(next, g);
                        open.add(new Rec(next, cur, g, g + h(next, goal)));
                    }
                    break; // claude: "take the first valid Y for this direction
                }
            }
        }
        // TODO Parent() is sus, cluade used parent() which matched nothing
        return closest == null || closest.Parent() == null ? null : build(closest, goal, false);
    }

    private static double h(BlockPos a, BlockPos b) {
        return Math.sqrt(a.distSqr(b)); // TODO SUS
    }

    private static boolean nearAny(BlockPos p, Set<BlockPos> targets) {
        for (BlockPos t : targets) {
            if (Math.abs(p.getX() - t.getX()) <= 1
            && Math.abs(p.getZ() - t.getZ()) <= 1
            && Math.abs(p.getY() - t.getY()) <= 1) {
                return true;
            }
        }
        return false;
    }

    private static boolean clear(BlockGetter g, BlockPos p) {
        return g.getBlockState(p).getCollisionShape(g, p).isEmpty();
    }

    private static boolean walkable(BlockGetter g, BlockPos p) {
        BlockPos below = p.below();
        return clear(g, p) && clear(g, p.above())
                && !g.getBlockState(below).getCollisionShape(g, below).isEmpty()
                && g.getFluidState(p).isEmpty();
    }

    private static Path build(Rec end, BlockPos goal, boolean reached) {
        LinkedList<Node> nodes = new LinkedList<>();
        for (Rec r = end; r != null; r = r.Parent()) { // TODO Parent() is SUS, cluade used parent() which matched nothing
            nodes.addFirst(new Node(r.pos().getX(), r.pos().getY(), r.pos().getZ())); // TODO pos() is SUS
        }
        return new Path(nodes, goal, reached);
    }
}