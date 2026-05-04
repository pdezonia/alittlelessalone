package net.pazooni.LessLonely.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.entity.SimpersonVariant;
import net.pazooni.LessLonely.entity.custom.SimpersonEntity;

import java.util.Map;

public class SimpersonRenderer extends MobRenderer<SimpersonEntity, SimpersonModel<SimpersonEntity>> {
    private static final Map<SimpersonVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SimpersonVariant.class), map -> {
                map.put(SimpersonVariant.SLIM_A,
                        ResourceLocation.fromNamespaceAndPath(LessLonely.MOD_ID, "textures/entity/simperson/simperson_slim_a.png"));
            });

    public SimpersonRenderer(EntityRendererProvider.Context context) {
        super(context, new SimpersonModel<>(context.bakeLayer(SimpersonModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SimpersonEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(SimpersonEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }else{
            poseStack.scale(1f, 1f, 1f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
