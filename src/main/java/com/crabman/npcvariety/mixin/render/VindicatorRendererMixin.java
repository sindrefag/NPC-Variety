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
import net.minecraft.client.render.entity.VindicatorEntityRenderer;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.util.Identifier;

@Mixin(VindicatorEntityRenderer.class)
public abstract class VindicatorRendererMixin extends IllagerEntityRenderer<VindicatorEntity> {
    
    public VindicatorRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1) {
        super(entityRenderDispatcher_1);
    }

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void VindicatorEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1, CallbackInfo info) {
        this.addFeature(new IllagerFeaturesRenderer(this));
    }


    @Inject(at = @At(value = "RETURN"), method = "method_4147", cancellable = true)
    protected void method_4147(VindicatorEntity vindicatorEntity_1, CallbackInfoReturnable<Identifier> infoReturnable){
        infoReturnable.setReturnValue(((NpcBody) vindicatorEntity_1).npcvariety$getSkinTexture());
    }

}