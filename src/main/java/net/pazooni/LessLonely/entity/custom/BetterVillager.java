package net.pazooni.LessLonely.entity.custom;
// Almost all of this code was with the help of claude *sigh* so that's why it
// looks like i am writing above my skill level; refer to (3.) of
// claude 20261008-2
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
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
//    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA;
//    public static final Map<Item, Integer> FOOD_POINTS;
//    private static final Set<Item> WANTED_ITEMS;
//    private static final ImmutableList MEMORY_TYPES;
//    private static final ImmutableList<SensorType<? extends Sensor<? super Villager>>> SENSOR_TYPES;
//    private int numberOfRestocksToday;
//    public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, Holder<PoiType>>> POI_MEMORIES;

    public BetterVillager(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Villager.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.25d)
                .add(Attributes.FOLLOW_RANGE, 25d); // this
    }

    private void registerBrainGoals(Brain<Villager> villagerBrain) {
        VillagerProfession villagerprofession = this.getVillagerData().getProfession();
        if (this.isBaby()) {
            villagerBrain.setSchedule(Schedule.VILLAGER_BABY);
            villagerBrain.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
        } else {
            villagerBrain.setSchedule(Schedule.VILLAGER_DEFAULT);
            villagerBrain.addActivityWithConditions(Activity.WORK, VillagerGoalPackages.getWorkPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));
        }

        villagerBrain.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage(villagerprofession, 0.5F));
        villagerBrain.addActivityWithConditions(Activity.MEET, VillagerGoalPackages.getMeetPackage(villagerprofession, 0.5F), ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
        villagerBrain.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage(villagerprofession, 0.5F));
        villagerBrain.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage(villagerprofession, 0.5F));
        villagerBrain.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage(villagerprofession, 0.5F));
        villagerBrain.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage(villagerprofession, 0.5F));
        villagerBrain.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage(villagerprofession, 0.5F));
        villagerBrain.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage(villagerprofession, 0.5F));
        villagerBrain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        villagerBrain.setDefaultActivity(Activity.IDLE);
        villagerBrain.setActiveActivityIfPossible(Activity.IDLE);
        villagerBrain.updateActivityFromSchedule(this.level().getDayTime(), this.level().getGameTime());
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new AStarNavigation(this, level);
    }

//    private void startTrading(Player player) {
//        this.updateSpecialPrices(player);
//        this.setTradingPlayer(player);
//        this.openTradingScreen(player, this.getDisplayName(), this.getVillagerData().getLevel());
//    }
//
//    private void resendOffersToTradingPlayer() {
//        MerchantOffers merchantoffers = this.getOffers();
//        Player player = this.getTradingPlayer();
//        if (player != null && !merchantoffers.isEmpty()) {
//            player.sendMerchantOffers(player.containerMenu.containerId, merchantoffers, this.getVillagerData().getLevel(), this.getVillagerXp(), this.showProgressBar(), this.canRestock());
//        }
//
//    }
//
//    private void catchUpDemand() {
//        int i = 2 - this.numberOfRestocksToday;
//        if (i > 0) {
//            Iterator var2 = this.getOffers().iterator();
//
//            while(var2.hasNext()) {
//                MerchantOffer merchantoffer = (MerchantOffer)var2.next();
//                merchantoffer.resetUses();
//            }
//        }
//
//        for(int j = 0; j < i; ++j) {
//            this.updateDemand();
//        }
//
//        this.resendOffersToTradingPlayer();
//    }
//
//    private void updateDemand() {
//        Iterator var1 = this.getOffers().iterator();
//
//        while(var1.hasNext()) {
//            MerchantOffer merchantoffer = (MerchantOffer)var1.next();
//            merchantoffer.updateDemand();
//        }
//
//    }
//
//    private void updateSpecialPrices(Player player) {
//        int i = this.getPlayerReputation(player);
//        if (i != 0) {
//            Iterator var3 = this.getOffers().iterator();
//
//            while(var3.hasNext()) {
//                MerchantOffer merchantoffer = (MerchantOffer)var3.next();
//                merchantoffer.addToSpecialPriceDiff(-Mth.floor((float)i * merchantoffer.getPriceMultiplier()));
//            }
//        }
//
//        if (player.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
//            MobEffectInstance mobeffectinstance = player.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
//            int k = mobeffectinstance.getAmplifier();
//            Iterator var5 = this.getOffers().iterator();
//
//            while(var5.hasNext()) {
//                MerchantOffer merchantoffer1 = (MerchantOffer)var5.next();
//                double d0 = 0.3 + 0.0625 * (double)k;
//                int j = (int)Math.floor(d0 * (double)merchantoffer1.getBaseCostA().getCount());
//                merchantoffer1.addToSpecialPriceDiff(-Math.max(j, 1));
//            }
//        }
//
//    }
//
//    private void resetNumberOfRestocks() {
//        this.catchUpDemand();
//        this.numberOfRestocksToday = 0;
//    }
//
//    private boolean golemSpawnConditionsMet(long gameTime) {
//        Optional<Long> optional = this.brain.getMemory(MemoryModuleType.LAST_SLEPT);
//        return optional.isPresent() ? gameTime - (Long)optional.get() < 24000L : false;
//    }

//    static {
//        DATA_VILLAGER_DATA = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.VILLAGER_DATA);
//        FOOD_POINTS = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
//        WANTED_ITEMS = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, new Item[]{Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD});
//        MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, new MemoryModuleType[]{MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_DETECTED_RECENTLY});
//        SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES, SensorType.VILLAGER_BABIES, SensorType.SECONDARY_POIS, SensorType.GOLEM_DETECTED);
//        POI_MEMORIES = ImmutableMap.of(MemoryModuleType.HOME, (p_219625_, p_219626_) -> {
//            return p_219626_.is(PoiTypes.HOME);
//        }, MemoryModuleType.JOB_SITE, (p_219622_, p_219623_) -> {
//            return p_219622_.getVillagerData().getProfession().heldJobSite().test(p_219623_);
//        }, MemoryModuleType.POTENTIAL_JOB_SITE, (p_219619_, p_219620_) -> {
//            return VillagerProfession.ALL_ACQUIRABLE_JOBS.test(p_219620_);
//        }, MemoryModuleType.MEETING_POINT, (p_219616_, p_219617_) -> {
//            return p_219617_.is(PoiTypes.MEETING);
//        });
//    }
}