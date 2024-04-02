package dev.sebastianb.justenoughguns.mixin.client;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowEntityRenderer.class)
public abstract class ArrowEntityRendererMixin extends ProjectileEntityRenderer<ArrowEntity> {

    public ArrowEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(ArrowEntity persistentProjectileEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {

        if (persistentProjectileEntity.getItemStack().getItem() == Items.POISONOUS_POTATO) {


        } else {
            super.render(persistentProjectileEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
        }
    }

}
