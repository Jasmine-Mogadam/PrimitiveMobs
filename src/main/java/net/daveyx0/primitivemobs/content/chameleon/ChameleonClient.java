package net.daveyx0.primitivemobs.content.chameleon;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.model.ChameleonModel;
import net.daveyx0.primitivemobs.client.renderer.entity.ChameleonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-side wiring for the Chameleon. Auto-discovered via the annotation.
 */
@Mod.EventBusSubscriber(modid = PrimitiveMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ChameleonClient {
    private ChameleonClient() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ChameleonContent.CHAMELEON.get(), ChameleonRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ChameleonModel.LAYER_LOCATION, ChameleonModel::createBodyLayer);
    }
}
