package net.pazooni.LessLonely.datagen;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LessLonely.MOD_ID, exFileHelper);
    }

    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.TOWN_BLOCK);

        /* *********************** Begin path blocks *********************** */
        // Express Path block
        String express_path_name = "express_path_block";
        ModelFile express_path_block_model = models().cube(express_path_name,
                tex(express_path_name + "_top"),
                tex(express_path_name + "_top"),
                tex(express_path_name + "_top"),
                tex(express_path_name + "_top"),
                tex(express_path_name + "_side"),
                tex(express_path_name + "_side"))
                .texture("particle", tex(express_path_name + "_top"));
        horizontalBlock(ModBlocks.EXPRESS_PATH_BLOCK.get(), express_path_block_model);
        simpleBlockItem(ModBlocks.EXPRESS_PATH_BLOCK.get(), express_path_block_model);
        // Express intersection block
        String express_intersection_name = "express_intersection_block";
        ModelFile express_intersection_block_model = models().cube(express_intersection_name,
                tex(express_intersection_name + "_top"),
                tex(express_intersection_name + "_top"),
                tex(express_intersection_name + "_side"),
                tex(express_intersection_name + "_side"),
                tex(express_intersection_name + "_side"),
                tex(express_intersection_name + "_side"))
                .texture("particle", tex(express_intersection_name + "_top"));
        horizontalBlock(ModBlocks.EXPRESS_INTERSECTION_BLOCK.get(),
                express_intersection_block_model);
        simpleBlockItem(ModBlocks.EXPRESS_INTERSECTION_BLOCK.get(),
                express_intersection_block_model);
        // Express end (terminal) block
        String express_end_name = "express_end_block";
        ModelFile express_end_block_model = models().cube(express_end_name,
                tex(express_end_name + "_top"),
                tex(express_end_name + "_top"),
                tex(express_end_name + "_side"),
                tex(express_end_name + "_side"),
                tex(express_end_name + "_side"),
                tex(express_end_name + "_side"))
                .texture("particle", tex(express_end_name + "_top"));
        horizontalBlock(ModBlocks.EXPRESS_END_BLOCK.get(),
                express_end_block_model);
        simpleBlockItem(ModBlocks.EXPRESS_END_BLOCK.get(),
                express_end_block_model);
        // Dummy express pattern block
        String express_dummy_name = "express_dummy_block";
        ModelFile express_dummy_block_model = models().cube(express_dummy_name,
                tex(express_dummy_name + "_top"),
                tex(express_dummy_name + "_bottom"),
                tex(express_dummy_name + "_side"),
                tex(express_dummy_name + "_side"),
                tex(express_dummy_name + "_side"),
                tex( express_dummy_name + "_side"))
                .texture("particle", tex(express_dummy_name + "_top"));
        horizontalBlock(ModBlocks.EXPRESS_DUMMY_BLOCK.get(),
                express_dummy_block_model);
        simpleBlockItem(ModBlocks.EXPRESS_DUMMY_BLOCK.get(),
                express_dummy_block_model);
//        blockWithItem(ModBlocks.EXPRESS_DUMMY_BLOCK);
//        simpleBlockWithItem(ModBlocks.EXPRESS_DUMMY_BLOCK.get(),
//                cubeAll(ModBlocks.EXPRESS_DUMMY_BLOCK.get()));
        // Express stair block
        customStairs(ModBlocks.EXPRESS_STAIR_BLOCK.get(),
                modLoc("block/express_dummy_block_side"),
                modLoc("block/express_dummy_block_bottom"),
                modLoc("block/express_dummy_block_top"));
//        stairsBlock(ModBlocks.EXPRESS_STAIR_BLOCK.get(),
//                modLoc("block/express_path_block_top"),
//                modLoc("block/express_path_block_side"),
//                modLoc("block/express_path_block_top"));
//        stairsBlock(ModBlocks.EXPRESS_STAIR_BLOCK.get(),
//                blockTexture(ModBlocks.EXPRESS_DUMMY_BLOCK.get()));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(
                "lesslonely:block/" + deferredBlock.getId().getPath()));
    }

    private ResourceLocation tex(String path) {
        return ResourceLocation.fromNamespaceAndPath(LessLonely.MOD_ID,
                "block/" + path);
    }

    // Variant builder for stairs THIS ONE is all claude
    private void customStairs(StairBlock block,
                              ResourceLocation side_texture,
                              ResourceLocation bottom_texture,
                              ResourceLocation top_texture) {
        ModelFile stairs = models().stairs(name(block), side_texture, bottom_texture, top_texture);
        ModelFile inner  = models().stairsInner(name(block) + "_inner",
                side_texture, bottom_texture, top_texture);
        ModelFile outer  = models().stairsOuter(name(block) + "_outer",
                side_texture, bottom_texture, top_texture);

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(StairBlock.FACING);
            Half half = state.getValue(StairBlock.HALF);
            StairsShape shape = state.getValue(StairBlock.SHAPE);

            int yRot = (int) facing.getClockWise().toYRot();
            if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                yRot += 270;
            }
            if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                yRot += 90;
//                yRot += 270;
            }
            yRot %= 360;

            ModelFile model = switch (shape) {
                case STRAIGHT -> stairs;
                case INNER_LEFT, INNER_RIGHT -> inner;
                default -> outer;
            };

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationX(half == Half.BOTTOM ? 0 : 180)
                    .rotationY(yRot)
                    // me: we WANT the textures to stay aligned to the stairs
                    .uvLock(false)
                    .build();
        }, StairBlock.WATERLOGGED);
    }
    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
