package net.daveyx0.primitivemobs.client.model;

import java.util.List;
import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.passive.Dodo;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public final class DodoModel extends AgeableListModel<Dodo> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
        PrimitiveMobsMod.id("dodo"),
        "main"
    );

    private static final float NECK_X_ROT = -0.2443461F;
    private static final float BEAK_X_ROT = 0.2792527F;

    private final ModelPart neck;
    private final ModelPart beak;
    private final ModelPart body;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightFoot;
    private final ModelPart leftFoot;
    private final ModelPart tail;

    public DodoModel(ModelPart root) {
        super(false, 5.0F, 2.0F, 2.0F, 2.0F, 24.0F);
        this.neck = root.getChild("neck");
        this.beak = root.getChild("beak");
        this.body = root.getChild("body");
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightFoot = root.getChild("right_foot");
        this.leftFoot = root.getChild("left_foot");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 12.0F),
            PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, -0.1919862F, 0.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "neck",
            CubeListBuilder.create().texOffs(29, 0).addBox(-2.0F, -10.0F, -3.0F, 4.0F, 10.0F, 4.0F),
            PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F, NECK_X_ROT, 0.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "beak",
            CubeListBuilder.create().texOffs(14, 0).addBox(-1.0F, -10.0F, -3.0F, 2.0F, 3.0F, 5.0F),
            PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F, BEAK_X_ROT, 0.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "right_wing",
            CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 5.0F),
            PartPose.offsetAndRotation(4.0F, 12.0F, -3.0F, -0.1919862F, 0.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "left_wing",
            CubeListBuilder.create().texOffs(28, 14).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 5.0F),
            PartPose.offsetAndRotation(-4.0F, 12.0F, -3.0F, -0.1919862F, 0.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F),
            PartPose.offset(2.0F, 20.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F),
            PartPose.offset(-2.0F, 20.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "right_foot",
            CubeListBuilder.create().texOffs(1, 8).addBox(-1.5F, 3.0F, -2.0F, 3.0F, 1.0F, 3.0F),
            PartPose.offset(2.0F, 20.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "left_foot",
            CubeListBuilder.create().texOffs(1, 8).addBox(-1.5F, 3.0F, -2.0F, 3.0F, 1.0F, 3.0F),
            PartPose.offset(-2.0F, 20.0F, 0.0F)
        );
        root.addOrReplaceChild(
            "tail",
            CubeListBuilder.create().texOffs(4, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F),
            PartPose.offset(0.0F, 13.0F, 5.0F)
        );

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return List.of(this.neck, this.beak);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return List.of(this.body, this.rightWing, this.leftWing, this.rightLeg, this.leftLeg, this.rightFoot, this.leftFoot, this.tail);
    }

    @Override
    public void setupAnim(Dodo entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.neck.xRot = headPitch * ((float) Math.PI / 180.0F) + NECK_X_ROT;
        this.neck.yRot = netHeadYaw * ((float) Math.PI / 180.0F);
        this.beak.xRot = headPitch * ((float) Math.PI / 180.0F) + BEAK_X_ROT;
        this.beak.yRot = this.neck.yRot;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightFoot.xRot = this.rightLeg.xRot;
        this.leftFoot.xRot = this.leftLeg.xRot;
        this.rightWing.zRot = ageInTicks;
        this.leftWing.zRot = -ageInTicks;
    }
}
