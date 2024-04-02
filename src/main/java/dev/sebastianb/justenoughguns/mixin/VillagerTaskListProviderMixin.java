package dev.sebastianb.justenoughguns.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerTaskListProvider.class)
public abstract class VillagerTaskListProviderMixin {


    @Shadow
    private static Pair<Integer, Task<LivingEntity>> createBusyFollowTask() {
        return null;
    }

    // stop panic, will fire off gun stuff in VillagerEntityMixin
    @Inject(method = "createPanicTasks", at = @At("HEAD"), cancellable = true)
    private static void JEG_panic(VillagerProfession profession, float speed, CallbackInfoReturnable<ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>>> cir) {

        float f = speed * 2F;
        cir.setReturnValue(
                ImmutableList.of(Pair.of(0, StopPanickingTask.create()),
                Pair.of(1, FindWalkTargetTask.create(f, 0, 0)),
                        Pair.of(1, LookAtMobTask.create(EntityType.PLAYER, 8.0F))));
    }

}
