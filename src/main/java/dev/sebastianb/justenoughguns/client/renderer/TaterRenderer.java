package dev.sebastianb.justenoughguns.client.renderer;

import dev.sebastianb.justenoughguns.JustEnoughGuns;
import dev.sebastianb.justenoughguns.client.JustEnoughGunsClient;
import dev.sebastianb.justenoughguns.client.model.TaterModel;
import dev.sebastianb.justenoughguns.item.gun.TaterEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TaterRenderer<T extends PersistentProjectileEntity> extends EntityRenderer<TaterEntity> {
    private Model model;

    private Texture taterTexture = Texture.POISON;

    public TaterRenderer(EntityRendererFactory.Context context) {
        super(context);

        this.model = new TaterModel(context.getPart(JustEnoughGunsClient.TATER_LAYER));
    }

    @Override
    public void render(TaterEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(getTexture(entity)));
        Quaternionf rotation = RotationAxis.NEGATIVE_Y.rotationDegrees(yaw);

        matrices.push();
        matrices.translate(0, 1.3, 0);

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(rotation);

        model.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(TaterEntity entity) {

        return taterTexture.getTexture();
    }


    public enum Texture {

        POISON("poison_tater"),
        REGULAR("tater");

        private final Identifier texture;

        Texture(String name) {
            this.texture = new Identifier(JustEnoughGuns.MOD_ID, "textures/entity/" + name + ".png");
        }

        public Identifier getTexture() {
            return this.texture;
        }

    }

}
