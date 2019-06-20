package com.crabman.npcvariety;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.RavagerClothing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.entity.mob.RavagerEntity;

@Environment(EnvType.CLIENT)
public class RavagerFeaturesRenderer<T extends RavagerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
   

   public RavagerFeaturesRenderer(FeatureRendererContext<T, M> featureRendererContext_1) {
      super(featureRendererContext_1);
   }

   public boolean hasHurtOverlay() {
      return true;
   }

   @Override
   public void render(T ravager, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
      if (!ravager.isInvisible()) {
         if(!ravager.isSleeping())  {
            this.bindTexture(((NpcBody) ravager).npcvariety$getEyeTexture());
            ((RavagerEntityModel)this.getModel()).render(ravager, float_1, float_2, float_4, float_5, float_6, float_7);
         }

         this.bindTexture(((RavagerClothing) ravager).npcvariety$getOverclothes());

         ((RavagerEntityModel)this.getModel()).render(ravager, float_1, float_2, float_4, float_5, float_6, float_7);
      }
   }

}