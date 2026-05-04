package net.pazooni.LessLonely.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LessLonely.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.SIMPERSON_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
