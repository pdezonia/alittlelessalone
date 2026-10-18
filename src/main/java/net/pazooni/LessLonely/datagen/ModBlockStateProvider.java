package net.pazooni.LessLonely.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
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

        // Begin path blocks
        String name = "express_path_block";
        ModelFile model = models().cube(name,
                tex(name + "_top"),
                tex(name + "_top"),
                tex(name + "_top"),
                tex(name + "_top"),
                tex(name + "_side"),
                tex(name + "_side"))
                .texture("particle", tex(name + "_top"));

        horizontalBlock(ModBlocks.EXPRESS_PATH_BLOCK.get(), model);
        simpleBlockItem(ModBlocks.EXPRESS_PATH_BLOCK.get(), model);
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
}
