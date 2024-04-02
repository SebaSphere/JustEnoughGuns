package dev.sebastianb.justenoughguns.mixin;

import dev.sebastianb.justenoughguns.item.gun.PotatoGun;
import dev.sebastianb.justenoughguns.item.gun.TaterEntity;
import dev.sebastianb.justenoughguns.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {


    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }


    private int tickCounter = 0;

    @Override
    public void tick() {
        super.tick();

        tickCounter++;

        if (tickCounter % 3 == 0 && tickCounter <= 40 && isAlive()) {
            shootTater(attackingPlayer);
        }

        if (tickCounter >= 200) {
            if (getMainHandStack().isOf(ModItems.Guns.TATERGUN.asItem())) {
                setStackInHand(Hand.MAIN_HAND, Items.AIR.getDefaultStack());
            }
        }
    }

    public void shootTater(LivingEntity attacker) {
        if (attacker != null && attacker.isAlive()) {
            World world = getEntityWorld();

            // Calculate yaw angle
            double dX = attacker.getX() - this.getX();
            double dZ = attacker.getZ() - this.getZ();
            float yaw = (float) (Math.atan2(dZ, dX) * (180 / Math.PI) - 90);
            if (yaw < 0) {
                yaw += 360;
            }

            // Calculate pitch angle
            double dY = attacker.getY() + 0.9 - this.getY();
            double distance = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
            float pitch = (float) (-Math.atan2(dY, distance) * (180 / Math.PI));

            TaterEntity taterEntity = new TaterEntity(this, getWorld(), Items.POISONOUS_POTATO.getDefaultStack().copyWithCount(1)); // create entity
            taterEntity.setPos(getX(), getY() + 1, getZ()); // set position
            taterEntity.setVelocity(this, pitch, yaw, 0.0F, 0.8F, 1.0F);
            world.spawnEntity(taterEntity); // spawn entity
        }

    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity) {
            // Increment tickCounter to immediately trigger a shot when damaged
            tickCounter = 0;
            tickCounter++;
            shootTater((LivingEntity) source.getAttacker());

            setStackInHand(Hand.MAIN_HAND, ModItems.Guns.TATERGUN.asStack());

        }

        return super.damage(source, amount);
    }

    @Override
    public void onDamaged(DamageSource damageSource) {

        super.onDamaged(damageSource);
    }
}
