package net.daveyx0.primitivemobs.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.client.model.ChameleonModel;
import net.daveyx0.primitivemobs.entity.passive.Chameleon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Renders the chameleon with a single greyscale texture tinted to the entity's
 * synced variant colour (the 1.20.1 stand-in for the 1.12 dynamic skin-colour
 * blending). The tint is applied through {@link ChameleonModel#setTint}.
 */
public final class ChameleonRenderer extends MobRenderer<Chameleon, ChameleonModel> {
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(PrimitiveMobsMod.MOD_ID, "textures/entity/chameleon/chameleon.png");

    public ChameleonRenderer(EntityRendererProvider.Context context) {
        super(context, new ChameleonModel(context.bakeLayer(ChameleonModel.LAYER_LOCATION)), 0.4F);
    }

    @Override
    public void render(Chameleon entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        int[] colors = Chameleon.VARIANT_COLORS;
        int color = colors[Math.floorMod(entity.getVariant(), colors.length)];
        this.getModel().setTint(
            ((color >> 16) & 0xFF) / 255.0F,
            ((color >> 8) & 0xFF) / 255.0F,
            (color & 0xFF) / 255.0F);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Chameleon entity) {
        return TEXTURE;
    }
}
