package com.crabman.npcvariety.mixin;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;

import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

@Mixin(ZombieEntity.class)
public abstract class ZombieMixin extends HostileEntity {
    protected ZombieMixin(EntityType<? extends HostileEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(method = "onKilledOther", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void onKilledOther(LivingEntity livingEntity_1, CallbackInfo info, VillagerEntity villagerEntity_1, ZombieVillagerEntity zombieVillagerEntity_1) {
        ((NpcBody)zombieVillagerEntity_1).npcvariety$setVariant(((NpcBody) villagerEntity_1).npcvariety$getVariant());
    }
}