package com.crabman.npcvariety.mixin.render;

import com.crabman.npcvariety.RavagerFeaturesRenderer;
import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.RavagerEntityRenderer;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.Identifier;

@Mixin(RavagerEntityRenderer.class)
public abstract class RavagerRendererMixin extends MobEntityRenderer<RavagerEntity, RavagerEntityModel>  {
    
    public RavagerRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1, RavagerEntityModel entityModel_1,
            float float_1) {
        super(entityRenderDispatcher_1, entityModel_1, float_1);
    }

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void RavagerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1, CallbackInfo info) {
        this.addFeature(new RavagerFeaturesRenderer(this));
    }


    @Inject(at = @At(value = "RETURN"), method = "method_3984", cancellable = true)
    protected void method_3984(RavagerEntity ravagerEntity_1, CallbackInfoReturnable<Identifier> infoReturnable){
        infoReturnable.setReturnValue(((NpcBody) ravagerEntity_1).npcvariety$getSkinTexture());
    }

}