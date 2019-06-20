package com.crabman.npcvariety.mixin.render;

import com.crabman.npcvariety.IllagerFeaturesRenderer;
import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EvokerIllagerEntityRenderer;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

@Mixin(EvokerIllagerEntityRenderer.class)
public abstract class EvokerRendererMixin <T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T>{

    public EvokerRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1);
    }

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void EvokerIllagerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1, CallbackInfo info) {
        this.addFeature(new IllagerFeaturesRenderer(this));
    }   


    @Inject(at = @At(value = "RETURN"), method = "method_3961", cancellable = true)
    protected void method_3961(T evokerEntity_1, CallbackInfoReturnable<Identifier> infoReturnable){
        infoReturnable.setReturnValue(((NpcBody) evokerEntity_1).npcvariety$getSkinTexture());
    }

}