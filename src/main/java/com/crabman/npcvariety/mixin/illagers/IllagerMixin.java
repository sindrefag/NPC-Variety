package com.crabman.npcvariety.mixin.illagers;

import com.crabman.npcvariety.util.NpcBody;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(IllagerEntity.class)
public abstract class IllagerMixin extends RaiderEntity implements NpcBody {
    private static final TrackedData<Integer> npcvariety$VARIANT = DataTracker.registerData(IllagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final Identifier[] npcvariety$SKIN_TEXTURES = {
        new Identifier("npcvariety:textures/entity/illager/skin1.png"),
        new Identifier("npcvariety:textures/entity/illager/skin2.png"),
        new Identifier("npcvariety:textures/entity/illager/skin3.png"),
        new Identifier("npcvariety:textures/entity/illager/skin4.png"),
        new Identifier("npcvariety:textures/entity/illager/skin5.png"),
        new Identifier("npcvariety:textures/entity/illager/skin6.png"),
        new Identifier("npcvariety:textures/entity/illager/skin7.png"),
        new Identifier("npcvariety:textures/entity/illager/skin8.png"),
    };

    private static final Identifier[] npcvariety$EYE_TEXTURES = {
        new Identifier("npcvariety:textures/entity/illager/eyes/green.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/blue.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/light_blue.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/brown.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/amber.png"),
    };

    public IllagerMixin(EntityType<? extends RaiderEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Override //@Inject(at = @At(value = "TAIL"), method = "initDataTracker")
    protected void initDataTracker() {
        super.initDataTracker();
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

    @Override
    public void writeCustomDataToTag(CompoundTag compoundTag_1) {
        super.writeCustomDataToTag(compoundTag_1);
        compoundTag_1.putInt("npcvariety$Variant", this.npcvariety$getVariant());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag compoundTag_1) {
        super.readCustomDataFromTag(compoundTag_1);
        this.npcvariety$setVariant(compoundTag_1.getInt("npcvariety$Variant"));
    }

}