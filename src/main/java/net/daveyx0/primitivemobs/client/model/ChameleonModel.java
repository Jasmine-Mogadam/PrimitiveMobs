package net.daveyx0.primitivemobs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.passive.Chameleon;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Geometry ported 1:1 from the 1.12 {@code ModelChameleon}. In 1.12 each cube
 * carried its own rotation point (often y=20/21) and was rendered without a
 * parent hierarchy; here every cube is attached directly to the root with the
 * same offsets/rotations. Baby scaling is handled natively by
 * {@link AgeableListModel} instead of the 1.12 manual GL scale.
 */
public final class ChameleonModel extends AgeableListModel<Chameleon> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
        PrimitiveMobsMod.id("chameleon"),
        "main"
    );

    private static final float HEAD_X_ROT = 0.2974289F;
    private static final float HEAD2_X_ROT = -0.7063936F;
    private static final float EYE_X_ROT = 0.1396263F;
    private static final float TONGUE_X_ROT = 0.2974216F;

    private final ModelPart head;
    private final ModelPart head2;
    private final ModelPart eyeLeft;
    private final ModelPart eyeRight;
    private final ModelPart tongue1;
    private final ModelPart tongue2;

    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart tail;
    private final ModelPart legFrontLeft1;
    private final ModelPart legFrontRight1;
    private final ModelPart legFrontLeft2;
    private final ModelPart legFrontRight2;
    private final ModelPart legBackLeft1;
    private final ModelPart legBackRight1;
    private final ModelPart legBackLeft2;
    private final ModelPart legBackRight2;

    private float tintR = 1.0F;
    private float tintG = 1.0F;
    private float tintB = 1.0F;

    public ChameleonModel(ModelPart root) {
        super();
        this.body1 = root.getChild("body1");
        this.body2 = root.getChild("body2");
        this.tail = root.getChild("tail");
        this.head = root.getChild("head");
        this.head2 = root.getChild("head2");
        this.eyeLeft = root.getChild("eye_left");
        this.eyeRight = root.getChild("eye_right");
        this.tongue1 = root.getChild("tongue1");
        this.tongue2 = root.getChild("tongue2");
        this.legFrontLeft1 = root.getChild("leg_front_left1");
        this.legFrontRight1 = root.getChild("leg_front_right1");
        this.legFrontLeft2 = root.getChild("leg_front_left2");
        this.legFrontRight2 = root.getChild("leg_front_right2");
        this.legBackLeft1 = root.getChild("leg_back_left1");
        this.legBackRight1 = root.getChild("leg_back_right1");
        this.legBackLeft2 = root.getChild("leg_back_left2");
        this.legBackRight2 = root.getChild("leg_back_right2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("body1",
            CubeListBuilder.create().texOffs(0, 0).addBox(-2F, -1F, 0F, 4, 4, 6),
            PartPose.offsetAndRotation(0F, 20F, -4F, 0.1487144F, 0F, 0F));
        root.addOrReplaceChild("body2",
            CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -2F, 5.5F, 3, 3, 2),
            PartPose.offsetAndRotation(0F, 20F, -4F, -0.0743572F, 0F, 0F));
        root.addOrReplaceChild("tail",
            CubeListBuilder.create().texOffs(0, 17).addBox(-1F, -1F, -0.2F, 2, 4, 4),
            PartPose.offsetAndRotation(0F, 20F, 3F, -0.4461433F, 0F, -0.5948578F));

        root.addOrReplaceChild("head",
            CubeListBuilder.create().texOffs(25, 0).addBox(-1.5F, -1.5F, -3.5F, 3, 3, 4),
            PartPose.offsetAndRotation(0F, 21F, -4F, HEAD_X_ROT, 0F, 0F));
        root.addOrReplaceChild("head2",
            CubeListBuilder.create().texOffs(25, 8).addBox(-1F, -2F, -3F, 2, 4, 2),
            PartPose.offsetAndRotation(0F, 21F, -4F, HEAD2_X_ROT, 0F, 0F));
        root.addOrReplaceChild("eye_left",
            CubeListBuilder.create().texOffs(34, 8).addBox(1.3F, -0.5F, -2F, 1, 1, 1),
            PartPose.offsetAndRotation(0F, 21F, -4F, 0.1115358F, 0.2230717F, -0.0743572F));
        root.addOrReplaceChild("eye_right",
            CubeListBuilder.create().texOffs(34, 11).addBox(-2.3F, -0.5F, -2F, 1, 1, 1),
            PartPose.offsetAndRotation(0F, 21F, -4F, 0.1115358F, -0.2230705F, 0.074351F));

        root.addOrReplaceChild("tongue1",
            CubeListBuilder.create().texOffs(25, 15).addBox(-0.5F, 0.5F, -7.4F, 1, 0, 4),
            PartPose.offsetAndRotation(0F, 21F, -4F, TONGUE_X_ROT, 0F, 0F));
        root.addOrReplaceChild("tongue2",
            CubeListBuilder.create().texOffs(25, 15).addBox(-0.5F, 0F, -8.4F, 1, 1, 1),
            PartPose.offsetAndRotation(0F, 21F, -4F, TONGUE_X_ROT, 0F, 0F));

        root.addOrReplaceChild("leg_front_left1",
            CubeListBuilder.create().texOffs(45, 0).addBox(-0.5F, 0F, -0.5F, 1, 2, 1),
            PartPose.offsetAndRotation(2F, 21F, -3.5F, 1.115358F, 0.2974367F, 0.1115513F));
        root.addOrReplaceChild("leg_front_right1",
            CubeListBuilder.create().texOffs(50, 0).addBox(-0.5F, 0F, -0.5F, 1, 2, 1),
            PartPose.offsetAndRotation(-2F, 21F, -3.5F, 1.115358F, -0.297439F, -0.111544F));
        root.addOrReplaceChild("leg_front_left2",
            CubeListBuilder.create().texOffs(45, 4).addBox(-0.5F, -0.5F, 2F, 1, 3, 1),
            PartPose.offsetAndRotation(2F, 21F, -3.5F, -0.4635966F, 0.297439F, 0.111544F));
        root.addOrReplaceChild("leg_front_right2",
            CubeListBuilder.create().texOffs(50, 4).addBox(-0.5F, -0.5F, 2F, 1, 3, 1),
            PartPose.offsetAndRotation(-2F, 21F, -3.5F, -0.4635966F, -0.297439F, -0.111544F));

        root.addOrReplaceChild("leg_back_left1",
            CubeListBuilder.create().texOffs(45, 9).addBox(-0.7F, 0F, -0.5F, 1, 2, 1),
            PartPose.offsetAndRotation(1F, 21F, 3F, -1.152532F, -0.8179311F, -0.1487144F));
        root.addOrReplaceChild("leg_back_right1",
            CubeListBuilder.create().texOffs(50, 9).addBox(-0.3F, 0F, -0.5F, 1, 2, 1),
            PartPose.offsetAndRotation(-1F, 21F, 3F, -1.152528F, 0.8179334F, 0.1487195F));
        root.addOrReplaceChild("leg_back_left2",
            CubeListBuilder.create().texOffs(45, 13).addBox(-0.7F, -0.5F, -3F, 1, 3, 1),
            PartPose.offsetAndRotation(1F, 21F, 3F, 0.426418F, -0.8179311F, -0.1487195F));
        root.addOrReplaceChild("leg_back_right2",
            CubeListBuilder.create().texOffs(50, 13).addBox(-0.3F, -0.5F, -3F, 1, 3, 1),
            PartPose.offsetAndRotation(-1F, 21F, 3F, 0.426418F, 0.8179311F, 0.1487195F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    /** Set by the renderer each frame so the chameleon's skin is tinted to its variant colour. */
    public void setTint(float r, float g, float b) {
        this.tintR = r;
        this.tintG = g;
        this.tintB = b;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay,
            red * this.tintR, green * this.tintG, blue * this.tintB, alpha);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return List.of(this.head, this.head2, this.eyeLeft, this.eyeRight, this.tongue1, this.tongue2);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return List.of(this.body1, this.body2, this.tail,
            this.legFrontLeft1, this.legFrontRight1, this.legFrontLeft2, this.legFrontRight2,
            this.legBackLeft1, this.legBackRight1, this.legBackLeft2, this.legBackRight2);
    }

    @Override
    public void setupAnim(Chameleon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float pitch = headPitch * ((float) Math.PI / 180.0F);
        float yaw = netHeadYaw * ((float) Math.PI / 180.0F);

        this.head.xRot = pitch + HEAD_X_ROT;
        this.head.yRot = yaw;
        this.head2.xRot = pitch + HEAD2_X_ROT;
        this.head2.yRot = yaw;
        this.eyeLeft.xRot = pitch + EYE_X_ROT;
        this.eyeLeft.yRot = yaw;
        this.eyeRight.xRot = pitch + EYE_X_ROT;
        this.eyeRight.yRot = yaw;
        this.tongue1.xRot = pitch + TONGUE_X_ROT;
        this.tongue1.yRot = yaw;
        this.tongue2.xRot = pitch + TONGUE_X_ROT;
        this.tongue2.yRot = yaw;

        float swing = Mth.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.6F * limbSwingAmount;
        this.legFrontLeft1.xRot = swing + 1.115358F;
        this.legFrontLeft2.xRot = swing - 0.4635966F;
        this.legFrontRight1.xRot = -swing + 1.115358F;
        this.legFrontRight2.xRot = -swing - 0.4635966F;
        this.legBackLeft1.xRot = -swing - 1.152532F;
        this.legBackLeft2.xRot = -swing + 0.426418F;
        this.legBackRight1.xRot = swing - 1.152532F;
        this.legBackRight2.xRot = swing + 0.426418F;
    }
}
