package net.pazooni.LessLonely.entity.custom;
// Almost all of this code was with the help of claude *sigh* so that's why it
// looks like i am writing above my skill level; refer to (3.) of
// claude 20261008-2
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BetterVillager extends Villager {
    public BetterVillager(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Villager.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20d)
                .add(Attributes.MOVEMENT_SPEED, 0.6d)
                .add(Attributes.FOLLOW_RANGE, 25d); // this
    }

    // BEHAVIORS
    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain<?> brain = super.makeBrain(dynamic);
        addCustomBehaviors((Brain<Villager>) brain);
        return brain;
    }

    @Override
    public void refreshBrain(ServerLevel level) {
        super.refreshBrain(level);
        addCustomBehaviors(this.getBrain());
    }

    private static void addCustomBehaviors(Brain<Villager> brain) {
        // claude said that the 1 is the priority and that for comparison,
        // wander has a priority of 2 so our new behavior would win
        // note also that it said that using IDLE means this will run during
        // the villager's free time. we can also use WORK to tie it to the
        // villager's schedule
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(1, new WalkToCreateBlock(
                        ResourceLocation.fromNamespaceAndPath("create", "depot"),
                        24, 0.5f))
        ));
    }
    /*
    claude has this to say about BEHAVIOR code chunk:
    makeBrain is called from the super constructor, so don’t touch your own
    instance fields inside it. That’s why the helper is static.
    The behavior only sets the WALK_TARGET memory. The villager’s built-in MoveToTargetSink in the core activity does the pathfinding.

    Notes
    Create dependency: looking the block up by registry ID means you don’t need
    Create on your compile classpath. Add it to neoforge.mods.toml as a
    dependency (required or optional) with ordering = "AFTER". If you’d rather
    reference AllBlocks.DEPOT directly, add Create’s maven repo and dependency
    in build.gradle.
    Activity choice: Activity.IDLE runs during free time. Use Activity.WORK to
    tie it to the villager’s schedule, or Activity.CORE to run it always (this
    can fight with sleeping).
    Priority: lower numbers run first. Vanilla’s idle wander is at priority 2,
    so priority 1 lets yours win.
    Arrival: to do something on arrival (like “use” the depot),
    check villager.blockPosition().closerThan(target, 2) in tick() and do your
    logic there. To remember the target, register a custom MemoryModuleType and
    use it as an entry requirement.
    Block entity or tag instead: to target any Create block, or a group of them,
    check a block tag in the filter instead of an exact ID.

    If you want a standalone villager that doesn’t inherit the profession and
    trading brain, extend AbstractVillager instead and build the Brain and
    Sensors yourself.
     */

    // PATHING
    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AStarNavigation(this, level);
    }
}