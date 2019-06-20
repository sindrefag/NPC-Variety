package com.crabman.npcvariety;

import com.crabman.npcvariety.util.NpcBody;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class VillagerEyesRenderer<T extends LivingEntity, M extends VillagerResemblingModel<T>> extends FeatureRenderer<T, M> {
   

   public VillagerEyesRenderer(FeatureRendererContext<T, M> featureRendererContext_1) {
      super(featureRendererContext_1);
   }

   public boolean hasHurtOverlay() {
      return true;
   }

   @Override
   public void render(T villager, float float_1, float float_2, float float_3, float float_4, float float_5, float float_6, float float_7) {
      if (!villager.isInvisible() && !villager.isSleeping()) {
         this.bindTexture(((NpcBody) villager).npcvariety$getEyeTexture());

         ((VillagerResemblingModel<T>)this.getModel()).render(villager, float_1, float_2, float_4, float_5, float_6, float_7);
      }
   }

}
