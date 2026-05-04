package net.pazooni.LessLonely;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.pazooni.LessLonely.entity.ModEntities;
import net.pazooni.LessLonely.entity.client.SimpersonRenderer;

@Mod(value = LessLonely.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = LessLonely.MOD_ID, value = Dist.CLIENT)
public class LessLonelyClient {
    public LessLonelyClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.SIMPERSON.get(), SimpersonRenderer::new);
    }
}
