package net.pazooni.LessLonely.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.block.ModBlocks;
import net.pazooni.LessLonely.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LessLonely.MOD_ID);

    public static final DeferredItem<Item> SIMPERSON_SPAWN_EGG = ITEMS.register("simperson_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SIMPERSON, 0x00ff00, 0xffffff,
                    new Item.Properties()));

    public static final DeferredItem<Item> BETTER_VILLAGER_SPAWN_EGG = ITEMS.register("better_villager_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.BETTER_VILLAGER, 0x222222, 0xff8800,
                    new Item.Properties()));

    // Begin express path related items
    public static final DeferredItem<BlockItem> EXPRESS_PATH_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlocks.EXPRESS_PATH_BLOCK);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
