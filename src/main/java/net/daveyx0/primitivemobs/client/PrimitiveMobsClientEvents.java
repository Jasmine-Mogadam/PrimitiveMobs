package net.daveyx0.primitivemobs.client;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.model.DodoModel;
import net.daveyx0.primitivemobs.client.renderer.entity.DodoRenderer;
import net.daveyx0.primitivemobs.registry.PrimitiveMobsEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PrimitiveMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class PrimitiveMobsClientEvents {
    private PrimitiveMobsClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PrimitiveMobsEntityTypes.DODO.get(), DodoRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DodoModel.LAYER_LOCATION, DodoModel::createBodyLayer);
    }
}
