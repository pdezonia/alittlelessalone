package net.pazooni.LessLonely.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LessLonely.MOD_ID);

    public static final Supplier<CreativeModeTab> SIMPEOPLE_ITEMS_TAB = CREATIVE_MODE_TAB.register("simpeople_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SIMPERSON_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.lesslonely.simpeople_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SIMPERSON_SPAWN_EGG);
                        output.accept(ModBlocks.TOWN_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
