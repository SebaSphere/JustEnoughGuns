package dev.sebastianb.justenoughguns.mixin.client;

import dev.sebastianb.justenoughguns.registry.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.Cancellable;

@Mixin(VillagerHeldItemFeatureRenderer.class)
public class VillagerHeldItemFeatureRendererMixin {


    @Shadow @Final private HeldItemRenderer heldItemRenderer;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.MAINHAND);

        if (itemStack.isOf(ModItems.Guns.TATERGUN.asItem())) {
            matrixStack.translate(0,0.3,-0.5);
            // matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
            this.heldItemRenderer.renderItem(livingEntity, itemStack, ModelTransformationMode.FIRST_PERSON_RIGHT_HAND, false, matrixStack, vertexConsumerProvider, i);
            ci.cancel();
        }


    }


}
