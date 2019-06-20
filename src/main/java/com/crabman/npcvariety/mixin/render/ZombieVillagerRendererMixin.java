package com.crabman.npcvariety.mixin.render;

import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieVillagerEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieVillagerEntityModel;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.util.Identifier;

@Mixin(ZombieVillagerEntityRenderer.class)
public abstract class ZombieVillagerRendererMixin
        extends BipedEntityRenderer<ZombieVillagerEntity, ZombieVillagerEntityModel<ZombieVillagerEntity>> {

    public ZombieVillagerRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1,
            ZombieVillagerEntityModel<ZombieVillagerEntity> bipedEntityModel_1, float float_1) {
        super(entityRenderDispatcher_1, bipedEntityModel_1, float_1);
    }

    @Inject(at = @At(value = "RETURN"), method = "method_4175", cancellable = true)
    protected void method_4175(ZombieVillagerEntity zombieVillager,
            CallbackInfoReturnable<Identifier> infoReturnable) {
        infoReturnable.setReturnValue(((NpcBody) zombieVillager).npcvariety$getSkinTexture());
    }
}