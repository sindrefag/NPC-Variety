package com.crabman.npcvariety.mixin.render;

import com.crabman.npcvariety.VillagerEyesRenderer;
import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.Identifier;

@Mixin(VillagerEntityRenderer.class)
public abstract class VillagerRendererMixin
        extends MobEntityRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {

    public VillagerRendererMixin(EntityRenderDispatcher entityRenderDispatcher_1,
            VillagerResemblingModel<VillagerEntity> entityModel_1, float float_1) {
        super(entityRenderDispatcher_1, entityModel_1, float_1);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/VillagerEntityRenderer;addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 0), method = "<init>")
    protected final boolean myFeature(VillagerEntityRenderer receiverThing, FeatureRenderer parameterForTarget){
        this.addFeature(parameterForTarget);
        return this.addFeature(new VillagerEyesRenderer(this));
    }

    @Inject(at = @At(value = "RETURN"), method = "method_4151", cancellable = true)
    protected void method_4151(VillagerEntity villager,
            CallbackInfoReturnable<Identifier> infoReturnable) {
        infoReturnable.setReturnValue(((NpcBody) villager).npcvariety$getSkinTexture());
    }

}