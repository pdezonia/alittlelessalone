package net.pazooni.LessLonely.entity.client;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.entity.custom.BetterVillager;

public class BetterVillagerRenderer extends MobRenderer<BetterVillager,
        VillagerModel<BetterVillager>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(LessLonely.MOD_ID,
                    "textures/entity/better_villager/better_villager_plains_unemp_masc_v2.png");

    public BetterVillagerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new VillagerModel<>(ctx.bakeLayer(ModelLayers.VILLAGER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BetterVillager betterVillager) {
        return TEXTURE;
    }
}
