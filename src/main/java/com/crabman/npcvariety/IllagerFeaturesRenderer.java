package com.crabman.npcvariety;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.IllagerClothing;
import com.mojang.blaze3d.platform.GlStateManager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EvilVillagerEntityModel;
import net.minecraft.entity.mob.IllagerEntity;

@Environment(EnvType.CLIENT)
public class IllagerFeaturesRenderer<T extends IllagerEntity, M extends EvilVillagerEntityModel<T>> extends FeatureRenderer<T, M> {

    public IllagerFeaturesRenderer(FeatureRendererContext<T, M> featureRendererContext_1) {
        super(featureRendererContext_1);
    }

    public boolean hasHurtOverlay() {
      return true;
   }

   @Override
   public void render(T illagerEntity, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
      if (!illagerEntity.isInvisible()) {
         if(!illagerEntity.isSleeping())   {
            this.bindTexture(((NpcBody) illagerEntity).npcvariety$getEyeTexture());
            ((EvilVillagerEntityModel<T>)this.getModel()).render(illagerEntity, float_1, float_2, float_4, float_5, float_6, float_7);
         }
         IllagerClothing illagerEntityCast = ((IllagerClothing) illagerEntity);
         float[] color;
         
         this.bindTexture(illagerEntityCast.npcvariety$getHeadFeatureTexture());
         ((EvilVillagerEntityModel<T>)this.getModel()).render(illagerEntity, float_1, float_2, float_4, float_5, float_6, float_7);

         // Color for trousers
         color = illagerEntityCast.npcvariety$getTrouserColor();
         this.bindTexture(illagerEntityCast.npcvariety$getTrouserTexture());
         GlStateManager.color3f(color[0], color[1], color[2]);
         ((EvilVillagerEntityModel<T>)this.getModel()).render(illagerEntity, float_1, float_2, float_4, float_5, float_6, float_7);

         // Switch color to shirt
         color = illagerEntityCast.npcvariety$getShirtColor();
         this.bindTexture(illagerEntityCast.npcvariety$getShirtTexture());
         GlStateManager.color3f(color[0], color[1], color[2]);
         ((EvilVillagerEntityModel<T>)this.getModel()).render(illagerEntity, float_1, float_2, float_4, float_5, float_6, float_7);

         this.bindTexture(illagerEntityCast.npcvariety$getOverclothesTexture());
         GlStateManager.color3f(1.0f, 1.0f, 1.0f);
         ((EvilVillagerEntityModel<T>)this.getModel()).render(illagerEntity, float_1, float_2, float_4, float_5, float_6, float_7);

         this.bindTexture(illagerEntityCast.npcvariety$getShoesTexture());
         ((EvilVillagerEntityModel<T>)this.getModel()).render(illagerEntity, float_1, float_2, float_4, float_5, float_6, float_7);
      }
   }

}
