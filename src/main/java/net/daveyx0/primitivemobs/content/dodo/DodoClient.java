package net.daveyx0.primitivemobs.content.dodo;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.model.DodoModel;
import net.daveyx0.primitivemobs.client.renderer.entity.DodoRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-side wiring for the Dodo. Auto-discovered via the annotation, so no
 * central client registrar is needed: each mob ships its own *Client class.
 */
@Mod.EventBusSubscriber(modid = PrimitiveMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class DodoClient {
    private DodoClient() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DodoContent.DODO.get(), DodoRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DodoModel.LAYER_LOCATION, DodoModel::createBodyLayer);
    }
}
