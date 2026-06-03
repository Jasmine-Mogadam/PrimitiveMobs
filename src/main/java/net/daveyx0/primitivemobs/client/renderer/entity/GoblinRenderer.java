package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.model.GoblinModel;
import net.daveyx0.primitivemobs.entity.monster.Goblin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * Humanoid renderer for the Goblin, ported from the 1.12 RenderGoblin
 * (RenderBiped). Adds an item-in-hand layer so the wielded goblin mace renders.
 */
public final class GoblinRenderer extends HumanoidMobRenderer<Goblin, GoblinModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        PrimitiveMobsMod.MOD_ID, "textures/entity/goblin/goblin.png");

    public GoblinRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinModel(context.bakeLayer(GoblinModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(Goblin entity) {
        return TEXTURE;
    }
}
