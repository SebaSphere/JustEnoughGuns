package dev.sebastianb.justenoughguns.item.gun;

import com.google.common.collect.Lists;
import dev.sebastianb.justenoughguns.client.renderer.TaterRenderer;
import dev.sebastianb.justenoughguns.entity.ModEntityTypes;
import dev.sebastianb.justenoughguns.registry.ModItems;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;

public class TaterEntity extends PersistentProjectileEntity {

    TaterRenderer.Texture texture;

    public TaterEntity(EntityType<? extends TaterEntity> entityType, World world) {
        super(entityType, world);
    }

    public TaterEntity(LivingEntity shooter, World world, ItemStack stack) {
        super(ModEntityTypes.Projectiles.TATER.getType(), shooter, world, stack);

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        getWorld().setBlockState(blockHitResult.getBlockPos(), Blocks.POISONOUS_POTATO_BLOCK.getDefaultState());
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return Items.POISONOUS_POTATO.getDefaultStack();
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_ANVIL_HIT;
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 0), this);

    }

    @Override
    protected double getGravity() {
        return 0.01;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getVelocity().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.getDamage(), 0.0, 2.147483647E9));


        if (this.isCritical()) {
            long l = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(l + (long)i, 2147483647L);
        }

        Entity entity2 = this.getOwner();
        DamageSource damageSource;
        if (entity2 == null) {
            damageSource = this.getDamageSources().arrow(this, this);
        } else {
            damageSource = this.getDamageSources().arrow(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }

        if (entity.damage(damageSource, (float)i)) {
            if (bl) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;

                this.onHit(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }

            }

            this.playSound(this.getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.discard();
            }
        } else {
            entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1));
            this.setYaw(this.getYaw() + 180.0F);
            this.prevYaw += 180.0F;
            if (!this.getWorld().isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.discard();
            }
        }
    }
}
