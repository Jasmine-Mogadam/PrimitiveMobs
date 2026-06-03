package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.monster.SkeletonWarrior;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renderer for the Skeleton Warrior. Reuses the vanilla {@link SkeletonModel}
 * ({@link ModelLayers#SKELETON}) plus the vanilla humanoid armor layer (so the
 * leather helmet shows) and the held-item layer that {@link HumanoidMobRenderer}
 * adds automatically — matching the 1.12 {@code RenderSkeletonWarrior}, with the
 * custom warrior texture.
 */
public final class SkeletonWarriorRenderer extends HumanoidMobRenderer<SkeletonWarrior, SkeletonModel<SkeletonWarrior>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        PrimitiveMobsMod.MOD_ID, "textures/entity/skeletonwarrior/skeletonwarrior.png");

    public SkeletonWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonModel<>(context.bakeLayer(ModelLayers.SKELETON)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(
            this,
            new SkeletonModel<>(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)),
            new SkeletonModel<>(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)),
            context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonWarrior entity) {
        return TEXTURE;
    }
}
