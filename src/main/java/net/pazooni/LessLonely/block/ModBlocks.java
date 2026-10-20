package net.pazooni.LessLonely.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
    public static final DeferredBlock<SixSidedBlock> EXPRESS_PATH_BLOCK = BLOCKS.register(
            "express_path_block",
            () -> new SixSidedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));
    public static final DeferredBlock<SixSidedBlock> EXPRESS_INTERSECTION_BLOCK = BLOCKS.register(
            "express_intersection_block",
            () -> new SixSidedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));
    public static final DeferredBlock<SixSidedBlock> EXPRESS_END_BLOCK = BLOCKS.register(
            "express_end_block",
            () -> new SixSidedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));
    // Dummy express block for the stair and slab blocks to steal
    public static final DeferredBlock<SixSidedBlock> EXPRESS_DUMMY_BLOCK = BLOCKS.register(
            "express_dummy_block",
            () -> new SixSidedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

//    public static final DeferredBlock<Block> EXPRESS_DUMMY_BLOCK = BLOCKS.registerSimpleBlock(
//            "express_dummy_block", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<StairBlock> EXPRESS_STAIR_BLOCK = registerBlock(
            "express_stair_block",
            () -> new StairBlock(ModBlocks.EXPRESS_DUMMY_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(EXPRESS_DUMMY_BLOCK.get())));

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
