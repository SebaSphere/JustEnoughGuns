package dev.sebastianb.justenoughguns.item.gun;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class PotatoGun extends BowItem {
    public PotatoGun(Settings settings) {
        super(settings);
    }

    public static final Predicate<ItemStack> GUN_AMMO = (stack) -> stack.isOf(Items.POISONOUS_POTATO);

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return GUN_AMMO;
    }

    @Override
    public Predicate<ItemStack> getHeldProjectiles() {
        return this.getProjectiles();
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks % 4 != 0) {
            return;
        }

        ItemStack itemStack = user.getProjectileType(stack);
        if (!itemStack.isEmpty()) {

            List<ItemStack> list = load(stack, itemStack, user);
            if (!world.isClient() && !list.isEmpty()) {
                this.shootAll(world, user, user.getActiveHand(), stack, list, 3.0F, 1.0F, true, (LivingEntity)null);
            }

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) * 0.5F);

        }
    }

    @Override
    protected ProjectileEntity createArrowEntity(World world, LivingEntity shooter, ItemStack weaponStack, ItemStack projectileStack, boolean critical) {

        return new TaterEntity(shooter, world, projectileStack);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }

}
