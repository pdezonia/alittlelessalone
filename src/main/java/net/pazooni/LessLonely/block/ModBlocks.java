package net.pazooni.LessLonely.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.block.custom.SixSidedBlock;
import net.pazooni.LessLonely.block.custom.TownBlock;
import net.pazooni.LessLonely.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(LessLonely.MOD_ID);

    // The ill fated town block
    public static final DeferredBlock<Block> TOWN_BLOCK = registerBlock("town_block",
            () -> new TownBlock(BlockBehaviour.Properties.of().strength(2f).noLootTable()));

    // Begin pathfinding blocks
//    public static final  DeferredBlock<Block> EXPRESS_PATH_BLOCK = registerBlock(
//            "express_path_block", () -> new Block(BlockBehaviour.Properties.of()
//                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
//    public static final DeferredBlock<RotatedPillarBlock> EXPRESS_PATH_BLOCK = registerBlock(
//            "express_path_block", () -> new RotatedPillarBlock(
//                    BlockBehaviour.Properties.of().strength(4f)
//                            .sound(SoundType.STONE)
//                            .requiresCorrectToolForDrops()));
    public static final DeferredBlock<SixSidedBlock> EXPRESS_PATH_BLOCK = BLOCKS.register(
            "express_path_block",
            () -> new SixSidedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    // End of Mod Block declaration
    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
