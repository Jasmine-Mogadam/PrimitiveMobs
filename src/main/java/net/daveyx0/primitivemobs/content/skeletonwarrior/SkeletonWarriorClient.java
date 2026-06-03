package net.daveyx0.primitivemobs.content.skeletonwarrior;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.renderer.entity.SkeletonWarriorRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Client-side wiring for the Skeleton Warrior. Auto-discovered via the annotation.
 * The renderer reuses the vanilla skeleton model/armor layers, so no custom layer
 * definition needs registering here.
 */
@Mod.EventBusSubscriber(modid = PrimitiveMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SkeletonWarriorClient {
    private SkeletonWarriorClient() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SkeletonWarriorContent.SKELETON_WARRIOR.get(), SkeletonWarriorRenderer::new);
    }
}
