package com.crabman.npcvariety.mixin.render;

import com.crabman.npcvariety.TraderClothesRenderer;
import com.crabman.npcvariety.VillagerEyesRenderer;
import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WanderingTraderEntityRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.util.Identifier;

@Mixin(WanderingTraderEntityRenderer.class)
public abstract class WanderingTraderRendererMixin
        extends MobEntityRenderer<WanderingTraderEntity, VillagerResemblingModel<WanderingTraderEntity>> {
    
    @Inject(at = @At(value = "RETURN"), method = "<init>")
    public void WanderingTraderEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1, CallbackInfo info) {
        this.addFeature(new VillagerEyesRenderer(this));
        this.addFeature(new TraderClothesRenderer(this));
    }

    public WanderingTraderRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1,
            VillagerResemblingModel<WanderingTraderEntity> entityModel_1, float float_1) {
        super(entityRenderDispatcher_1, entityModel_1, float_1);
    }

    @Inject(at = @At(value = "RETURN"), method = "method_18045", cancellable = true)
    protected void method_18045(WanderingTraderEntity wanderingTraderEntity_1,
            CallbackInfoReturnable<Identifier> infoReturnable) {
        infoReturnable.setReturnValue(((NpcBody) wanderingTraderEntity_1).npcvariety$getSkinTexture());
    }

}