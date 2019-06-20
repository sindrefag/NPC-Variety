package com.crabman.npcvariety.mixin.render;

import com.crabman.npcvariety.IllagerFeaturesRenderer;
import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.PillagerEntityRenderer;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.util.Identifier;

@Mixin(PillagerEntityRenderer.class)
public abstract class PillagerRendererMixin extends IllagerEntityRenderer<PillagerEntity> {
    
    public PillagerRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1);
    }

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void PillagerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1, CallbackInfo info) {
        this.addFeature(new IllagerFeaturesRenderer(this));
    }


    @Inject(at = @At(value = "RETURN"), method = "method_4092", cancellable = true)
    protected void method_4092(PillagerEntity PillagerEntity_1, CallbackInfoReturnable<Identifier> infoReturnable){
        infoReturnable.setReturnValue(((NpcBody) PillagerEntity_1).npcvariety$getSkinTexture());
    }

}