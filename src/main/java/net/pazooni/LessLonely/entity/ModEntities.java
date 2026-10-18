package net.pazooni.LessLonely.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.entity.custom.BetterVillager;
import net.pazooni.LessLonely.entity.custom.SimpersonEntity;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, LessLonely.MOD_ID);

    public static final Supplier<EntityType<SimpersonEntity>> SIMPERSON =
            ENTITY_TYPES.register("simperson",
                    () -> EntityType.Builder.of(SimpersonEntity::new, MobCategory.CREATURE)
                    .sized(1.0F, 2.0F).build("simperson"));

    public static final Supplier<EntityType<BetterVillager>> BETTER_VILLAGER =
            ENTITY_TYPES.register("better_villager", () ->
                    EntityType.Builder.of(BetterVillager::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.95f)
                            .clientTrackingRange(10) // TODO SUS for using .clienttrackingrange
                            // TODO SUS for not using Supplier (used to use DeferredHolder)
                            .build("better_villager"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
