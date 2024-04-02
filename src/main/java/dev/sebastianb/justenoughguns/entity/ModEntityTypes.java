package dev.sebastianb.justenoughguns.entity;

import dev.sebastianb.justenoughguns.JustEnoughGuns;
import dev.sebastianb.justenoughguns.item.gun.TaterEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Level;

public class ModEntityTypes {

    public enum Projectiles {
        TATER(TaterEntity::new, 0.75f, 0.75f);

        private final String name;
        private final EntityType<TaterEntity> entityType;

        Projectiles(EntityType.EntityFactory<TaterEntity> factory, float width, float height) {
            name = this.toString().toLowerCase(Locale.ROOT);
            entityType = JustEnoughGuns.REGISTRY.entityType(FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                            .dimensions(EntityDimensions.fixed(width, height))
                            .forceTrackedVelocityUpdates(true)
                            .trackedUpdateRate(20)
                            .trackRangeBlocks(256)
                    , name);
        }

        public EntityType<TaterEntity> getType() {
            return entityType;
        }
    }

    public static void register() {
        // Make sure everything is initialized
        Arrays.stream(Projectiles.values()).forEach(v -> JustEnoughGuns.LOGGER.log(Level.INFO, v.name));
    }
}