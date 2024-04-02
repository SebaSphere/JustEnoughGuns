package dev.sebastianb.justenoughguns.client;

import dev.sebastianb.justenoughguns.JustEnoughGuns;
import dev.sebastianb.justenoughguns.client.model.TaterModel;
import dev.sebastianb.justenoughguns.client.renderer.TaterRenderer;
import dev.sebastianb.justenoughguns.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class JustEnoughGunsClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */

    public static final EntityModelLayer TATER_LAYER = new EntityModelLayer(new Identifier(JustEnoughGuns.MOD_ID, "tater_render_layer"), "tater_render_layer");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.Projectiles.TATER.getType(), TaterRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(TATER_LAYER, TaterModel::getTexturedModelData);
    }
}
