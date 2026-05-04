package net.pazooni.LessLonely.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.entity.ModEntities;
import net.pazooni.LessLonely.entity.client.SimpersonModel;
import net.pazooni.LessLonely.entity.custom.SimpersonEntity;

@EventBusSubscriber(modid = LessLonely.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SimpersonModel.LAYER_LOCATION, SimpersonModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SIMPERSON.get(), SimpersonEntity.createAttributes().build());
    }
}
