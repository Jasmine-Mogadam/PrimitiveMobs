package net.daveyx0.primitivemobs.client.model;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.monster.Goblin;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

/**
 * Goblin model ported from the 1.12 {@code ModelGoblin} (which extended
 * ModelBiped). Geometry rebuilt as a 1.20.1 humanoid model so the held mace and
 * standard biped attack/walk animation work natively. Extra parts (nose, ears,
 * helmet) are children of the head; the body armour and back bag are children
 * of the body. Texture is 128x64.
 */
public class GoblinModel extends HumanoidModel<Goblin> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
        PrimitiveMobsMod.id("goblin"),
        "main"
    );

    private final ModelPart bag;

    public GoblinModel(ModelPart root) {
        super(root);
        this.bag = this.body.getChild("bag");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        CubeDeformation none = CubeDeformation.NONE;

        PartDefinition head = root.addOrReplaceChild(
            "head",
            CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -5.1F, 8.0F, 10.0F, 8.0F, none),
            PartPose.offset(0.0F, 7.0F, -3.0F));
        head.addOrReplaceChild(
            "nose",
            CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -2.0F, -7.0F, 2.0F, 4.0F, 2.0F, none),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3490659F, 0.0F, 0.0F));
        head.addOrReplaceChild(
            "ear1",
            CubeListBuilder.create().texOffs(33, 0).addBox(0.0F, -9.0F, 0.0F, 1.0F, 3.0F, 5.0F, none),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4537856F, 0.0F, 0.5759587F));
        head.addOrReplaceChild(
            "ear2",
            CubeListBuilder.create().texOffs(46, 0).addBox(-1.0F, -9.0F, 0.0F, 1.0F, 3.0F, 5.0F, none),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4537856F, 0.0F, -0.5759587F));
        head.addOrReplaceChild(
            "helmet",
            CubeListBuilder.create().texOffs(0, 36).addBox(-4.5F, -9.5F, -5.5F, 9.0F, 11.0F, 9.0F, none),
            PartPose.ZERO);

        // The original hid the hat layer; reuse the head pose with empty geometry.
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -3.0F));

        PartDefinition body = root.addOrReplaceChild(
            "body",
            CubeListBuilder.create().texOffs(17, 20).addBox(-5.0F, 0.0F, -4.0F, 10.0F, 10.0F, 6.0F, none),
            PartPose.offset(0.0F, 6.0F, 0.0F));
        body.addOrReplaceChild(
            "armor",
            CubeListBuilder.create().texOffs(37, 36).addBox(-5.5F, -0.5F, -4.5F, 11.0F, 11.0F, 7.0F, none),
            PartPose.ZERO);
        body.addOrReplaceChild(
            "bag",
            CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 16.0F, none),
            PartPose.offsetAndRotation(0.0F, 2.0F, 2.0F, 0.1396263F, 0.0F, 0.0F));

        root.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create().texOffs(48, 22).addBox(-4.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, none),
            PartPose.offset(-5.0F, 7.0F, -1.0F));
        root.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create().texOffs(48, 22).addBox(0.0F, -1.0F, -2.0F, 4.0F, 10.0F, 4.0F, none),
            PartPose.offset(5.0F, 7.0F, -1.0F));

        root.addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create().texOffs(0, 22).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, none),
            PartPose.offset(-2.0F, 16.0F, -1.0F));
        root.addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create().texOffs(0, 22).addBox(-2.1F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, none),
            PartPose.offset(2.0F, 16.0F, -1.0F));

        return LayerDefinition.create(mesh, 128, 64);
    }

    @Override
    public void setupAnim(Goblin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        // Swing the bag slightly while walking, matching the 1.12 behaviour.
        this.bag.xRot = Mth.cos(limbSwing * 0.6662F) * 0.1F * limbSwingAmount;
    }
}
