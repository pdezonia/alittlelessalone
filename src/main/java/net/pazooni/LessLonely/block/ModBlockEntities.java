package net.pazooni.LessLonely.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.block.custom.TownBlockEntity;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> MOD_BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, LessLonely.MOD_ID);

    public static final Supplier<BlockEntityType<TownBlockEntity>> TOWN_BLOCK_ENTITY = MOD_BLOCK_ENTITY_TYPES.register(
            "town_block_entity", () -> BlockEntityType.Builder.of(TownBlockEntity::new, ModBlocks.TOWN_BLOCK.get()
            ).build(null)
    );

    public static void register(IEventBus eventBus) {
        MOD_BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
