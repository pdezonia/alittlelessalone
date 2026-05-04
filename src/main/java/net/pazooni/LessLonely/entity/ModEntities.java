package net.pazooni.LessLonely.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.entity.custom.SimpersonEntity;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, LessLonely.MOD_ID);

    public static final Supplier<EntityType<SimpersonEntity>> SIMPERSON =
            ENTITY_TYPES.register("simperson", () -> EntityType.Builder.of(SimpersonEntity::new, MobCategory.CREATURE)
                    .sized(1.0F, 2.0F).build("simperson"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
