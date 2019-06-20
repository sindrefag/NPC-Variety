package com.crabman.npcvariety.mixin;

import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Npc;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.village.Trader;
import net.minecraft.world.World;

@Mixin(AbstractTraderEntity.class)
public abstract class AbstractTraderMixin extends PassiveEntity implements Npc, Trader, NpcBody {
    private static final TrackedData<Integer> npcvariety$VARIANT = DataTracker.registerData(AbstractTraderEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final Identifier[] npcvariety$SKIN_TEXTURES = {
        new Identifier("npcvariety:textures/entity/villager/skin1.png"),
        new Identifier("npcvariety:textures/entity/villager/skin2.png"),
        new Identifier("npcvariety:textures/entity/villager/skin3.png"),
        new Identifier("npcvariety:textures/entity/villager/skin4.png"),
        new Identifier("npcvariety:textures/entity/villager/skin5.png"),
        new Identifier("npcvariety:textures/entity/villager/skin6.png"),
        new Identifier("npcvariety:textures/entity/villager/skin7.png"),
        new Identifier("npcvariety:textures/entity/villager/skin8.png"),
    };

    private static final Identifier[] npcvariety$EYE_TEXTURES = {
        new Identifier("npcvariety:textures/entity/villager/eyes/green.png"),
        new Identifier("npcvariety:textures/entity/villager/eyes/blue.png"),
        new Identifier("npcvariety:textures/entity/villager/eyes/light_blue.png"),
        new Identifier("npcvariety:textures/entity/villager/eyes/brown.png"),
        new Identifier("npcvariety:textures/entity/villager/eyes/amber.png"),
    };

    public AbstractTraderMixin(EntityType<? extends PassiveEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(at = @At(value = "TAIL"), method = "initDataTracker")
    protected void initDataTracker(CallbackInfo info) {
        this.dataTracker.startTracking(npcvariety$VARIANT, 0);
    }
    
    @Override
    public Identifier npcvariety$getSkinTexture(){
        return (Identifier)npcvariety$SKIN_TEXTURES[npcvariety$getSkinIndex()];
    }

    @Override
    public Identifier npcvariety$getEyeTexture(){
        return (Identifier)npcvariety$EYE_TEXTURES[npcvariety$getEyeIndex()];
    }

    @Override
    public int npcvariety$getSkinIndex() {
        return npcvariety$getVariant() & 7;
    }

    @Override
    public int npcvariety$getEyeIndex() {
        return (npcvariety$getVariant() >> 3) & 7;
    }

    @Override
    public void npcvariety$setVariant(int value)
    {
        this.dataTracker.set(npcvariety$VARIANT, value);
    }

    @Override
    public int npcvariety$getVariant() {
        return (Integer)this.dataTracker.get(npcvariety$VARIANT);
    }
    
    @Inject(method = "writeCustomDataToTag", at = @At(value = "TAIL"))
    public void writeCustomDataToTag(CompoundTag compoundTag_1, CallbackInfo info) {
        compoundTag_1.putInt("npcvariety$Variant", this.npcvariety$getVariant());
    }

    @Inject(method = "readCustomDataFromTag", at = @At(value = "TAIL"))
    public void readCustomDataFromTag(CompoundTag compoundTag_1, CallbackInfo info) {
        this.npcvariety$setVariant(compoundTag_1.getInt("npcvariety$Variant"));
    }

}