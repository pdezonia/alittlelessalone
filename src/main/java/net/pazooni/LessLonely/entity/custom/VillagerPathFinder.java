package net.pazooni.LessLonely.entity.custom;
// Almost all of this code was with the help of claude *sigh* so that's why it
// looks like i am writing above my skill level; refer to (1.) of
// claude 20261008-2
// ultimately claude 20261018-1 (a continuation of 20261017) was instrumental
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VillagerPathFinder extends PathFinder {
    private final NodeEvaluator evaluator;
    private final int maxVisted;
    private final Node[] neighbors = new Node[32];
    private final BinaryHeap openSet = new BinaryHeap();

    public VillagerPathFinder(NodeEvaluator evaluator, int maxVisitedNodes) {
        super(evaluator, maxVisitedNodes);
        this.evaluator = evaluator;
        this.maxVisted = maxVisitedNodes;
    }

    @Nullable
    @Override
    public Path findPath(PathNavigationRegion region, Mob mob, Set<BlockPos> targetPositions,
                         float maxRange, int accuracy, float searchDepthMultiplier) {
        openSet.clear();
        evaluator.prepare(region, mob);
        Node start = evaluator.getStart();
        if (start == null) {
            return null;
        }

        Map<Target, BlockPos> targets = targetPositions.stream().collect(Collectors.toMap(
                pos -> evaluator.getTarget(pos.getX(), pos.getY(), pos.getZ()),
                Function.identity()));
        return super.findPath(region, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
    }

    @Nullable
    private Path search(Node start, Map<Target, BlockPos> targetPositions,
                        float maxRange, int accuracy, float depthMultiplier) {
        Set<Target> targets = targetPositions.keySet();
        start.g = 0.0F;
        start.h = bestH(start, targets);
        start.f = start.h;
        openSet.insert(start);

        Set<Target> reached = new HashSet<>();
        int budget = (int) (maxVisted * depthMultiplier);
        int visited = 0;

        while (!openSet.isEmpty()) {
            if (++visited >= budget) break;

            Node current = openSet.pop();
            current.closed = true;

            for (Target target : targets) {
                if (current.distanceManhattan(target) <= accuracy) {
                    target.setReached();
                    reached.add(target);
                }
            }
            if (!reached.isEmpty()) break;

            if (current.distanceTo(start) >= maxRange) continue;

            int count = evaluator.getNeighbors(neighbors, current);
            for (int i = 0; i < count; i++) {
                Node next = neighbors[i];
                // claude: "// CHANGE: edge cost"
                float step = stepCost(current, next);
                next.walkedDistance = current.walkedDistance + step;
                float newG = current.g + step + next.costMalus;

                if (next.walkedDistance < maxRange && (!next.inOpenSet() || newG < next.g)) {
                    next.cameFrom = current;
                    next.g = newG;
                    // claude: "// CHANGE: heuristic weight"
                    next.h = bestH(next, targets) * 1.5F;
                    if (next.inOpenSet()) {
                        openSet.changeCost(next, next.g + next.h);
                    } else {
                        next.f = next.g + next.h;
                        openSet.insert(next);
                    }
                }
            }
        }

        Optional<Path> best = !reached.isEmpty()
                ? reached.stream()
                    .map(t -> reconstruct(t.getBestNode(), targetPositions.get(t), true))
                    .min(Comparator.comparingInt(Path::getNodeCount))
                : targets.stream()
                    .map(t -> reconstruct(t.getBestNode(), targetPositions.get(t), false))
                    .min(Comparator.comparingDouble(Path::getDistToTarget)
                            .thenComparingInt(Path::getNodeCount));
        return best.orElse(null);
    }

    private float stepCost(Node from, Node to) {
        return from.distanceTo(to);
    }

    private float bestH(Node node, Set<Target> targets) {
        float best = Float.MAX_VALUE;
        for (Target target : targets) {
            float h = node.distanceTo(target);
            target.updateBest(h, node);
            best = Math.min(best, h);
        }
        return best;
    }

    private Path reconstruct(Node end, BlockPos targetPos, boolean reached) {
        List<Node> nodes = new ArrayList<>();
        for (Node n = end; n != null; n = n.cameFrom) {
            nodes.add(0, n);
        }
        return new Path(nodes, targetPos, reached);
    }

    /*
    // Rec was previously called entry which i think made more sense
    private record Rec(BlockPos pos, Rec Parent, double g, double f) {} // idk wtf this does

    @Nullable
    @Override
    public Path findPath(@NotNull PathNavigationRegion region, @NotNull Mob mob,
                         Set<BlockPos> targets, float maxRange, int accuracy,
                         float searchDepthMultiplier) {
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

        while (!open.isEmpty() && visited++ < maxVisitedNodes) {
            Rec cur = open.poll();
            // TODO pos() is SUS
            // claude: "stale entry"
            if (cur.g() > best.getOrDefault(cur.pos(), Double.MAX_VALUE)) continue;

            double hc = h(cur.pos, goal);
            if (hc < closestH) {
                closestH = hc;
                closest = cur;
            }
            // TODO pos() is SUS
            if (nearAny(cur.pos(), targets)) return build(cur, goal, true);
            if (cur.pos().distSqr(start) > maxRange * maxRange) continue;

            for (Direction dir : Direction.Plane.HORIZONTAL) {
                // also TODO SUS
                for (int dy = 1; dy >= -3; dy--) { // claude: "step up 1, drop up to 3"
                    BlockPos next = cur.pos().relative(dir).above(dy);
                    if (!walkable(region, next)) continue;
                    // claude: "// headroom to jump"
                    if (dy == 1 && !clear(region, cur.pos().above(2))) continue;
                    // claude: "<-- your custom cost goes here" // TODO sus for logic
                    double g = cur.g() + 1 + (dy != 0 ? 0.5 : 0);
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
        for (Rec r = end; r != null; r = r.Parent()) {
            // TODO pos() is SUS
            nodes.addFirst(new Node(r.pos().getX(), r.pos().getY(), r.pos().getZ()));
        }
        return new Path(nodes, goal, reached);
    }
     */
}