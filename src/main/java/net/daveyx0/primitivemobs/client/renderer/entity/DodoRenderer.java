package net.daveyx0.primitivemobs.client.renderer.entity;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.model.DodoModel;
import net.daveyx0.primitivemobs.entity.passive.Dodo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class DodoRenderer extends MobRenderer<Dodo, DodoModel> {
    private static final ResourceLocation TEXTURE = PrimitiveMobsMod.id("textures/entity/rareanimals/dodo.png");

    public DodoRenderer(EntityRendererProvider.Context context) {
        super(context, new DodoModel(context.bakeLayer(DodoModel.LAYER_LOCATION)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(Dodo entity) {
        return TEXTURE;
    }
}
