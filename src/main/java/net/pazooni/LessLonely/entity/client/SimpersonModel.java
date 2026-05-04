package net.pazooni.LessLonely.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.pazooni.LessLonely.LessLonely;
import net.pazooni.LessLonely.entity.custom.SimpersonEntity;
import org.jetbrains.annotations.NotNull;

// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
// Model originally made by MedSamer on SketchFab under Creative Commons Attribution License
// under the names Minecraft Girl and Lina 2

public class SimpersonModel<T extends SimpersonEntity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(LessLonely.MOD_ID, "simperson"), "main");

	private final ModelPart Lina2;
	private final ModelPart Body;
	private final ModelPart Body1;
	private final ModelPart Head;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;

	public SimpersonModel(ModelPart root) {
		this.Lina2 = root.getChild("Lina2");
		this.Body = this.Lina2.getChild("Body");
		this.Body1 = this.Body.getChild("Body1");
		this.Head = this.Body1.getChild("Head");
		this.RightArm = this.Body1.getChild("RightArm");
		this.LeftArm = this.Body1.getChild("LeftArm");
		this.RightLeg = this.Body.getChild("RightLeg");
		this.LeftLeg = this.Body.getChild("LeftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Lina2 = partdefinition.addOrReplaceChild("Lina2", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Body = Lina2.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 25).addBox(-4.0F, -15.0F, -1.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(-0.01F))
		.texOffs(18, 42).addBox(-3.75F, -15.0F, -0.25F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.26F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition lower_pelvis_r1 = Body.addOrReplaceChild("lower pelvis_r1", CubeListBuilder.create().texOffs(14, 23).addBox(-7.0F, -3.0F, -1.55F, 8.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.0F, -11.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition Body1 = Body.addOrReplaceChild("Body1", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(15, 21).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 1.0F));

		PartDefinition Breast_r1 = Body1.addOrReplaceChild("Breast_r1", CubeListBuilder.create().texOffs(16, 21).addBox(-3.8F, -1.5F, -2.0F, 7.6F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.15F, -6.1629F, -2.0617F, 0.829F, 0.0F, 0.0F));

		PartDefinition RightBody2_r1 = Body1.addOrReplaceChild("2RightBody_r1", CubeListBuilder.create().texOffs(24, 21).addBox(0.0F, -2.0F, -1.96F, 1.0F, 4.0F, 3.92F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.134F, -1.2321F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition RightBody1_r1 = Body1.addOrReplaceChild("1RightBody_r1", CubeListBuilder.create().texOffs(23, 21).addBox(-2.0F, 0.0F, 0.55F, 2.0F, 4.0F, 3.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -4.0F, -2.5F, 0.0F, 0.0F, 0.3054F));

		PartDefinition LeftBody2_r1 = Body1.addOrReplaceChild("2LeftBody_r1", CubeListBuilder.create().texOffs(29, 22).addBox(0.0F, -4.0F, -1.96F, 1.0F, 4.0F, 3.92F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition LeftBody1_r1 = Body1.addOrReplaceChild("1LeftBody_r1", CubeListBuilder.create().texOffs(15, 22).addBox(0.0F, 0.0F, 0.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-4.0F, -4.0F, -2.5F, 0.0F, 0.0F, -0.3054F));

		PartDefinition Head = Body1.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-3.75F, -7.75F, -3.25F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -1.0F));

		PartDefinition RightArm = Body1.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(32, 48).mirror().addBox(-1.25F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(48, 48).mirror().addBox(-1.55F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(-5.75F, -7.0F, 0.0F));

		PartDefinition LeftArm = Body1.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-2.125F, -2.0F, -1.99F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false)
		.texOffs(40, 32).mirror().addBox(-1.875F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.12F)).mirror(false), PartPose.offset(5.875F, -7.0F, 0.0F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-1.75F, 2.0F, -2.0F, 3.75F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-1.5F, 6.0F, -2.0F, 3.5F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -11.0F, 1.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, -1.0F, -1.99F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.01F))
		.texOffs(16, 50).addBox(-2.0F, 2.0F, -2.0F, 3.75F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(17, 55).addBox(-2.0F, 6.0F, -2.0F, 3.5F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -11.0F, 1.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(SimpersonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose); // Sets all parts to their standard/normal/original location
		this.applyHeadRotation(netHeadYaw, headPitch);

		this.animateWalk(SimpersonAnimations.ANIM_SIMPERSON_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.idleAnimationState, SimpersonAnimations.ANIM_SIMPERSON_IDLE, ageInTicks, 1f);
	}

	private void applyHeadRotation(float headYaw, float headPitch) {
		headYaw = Mth.clamp(headYaw, -30f, 30f);
		headPitch = Mth.clamp(headPitch, -25f, 25f);

		this.Head.yRot = headYaw * ((float) Math.PI / 180f);
		this.Head.xRot = headPitch * ((float) Math.PI / 180f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		Lina2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public @NotNull ModelPart root() {
		return Lina2;
	}
}