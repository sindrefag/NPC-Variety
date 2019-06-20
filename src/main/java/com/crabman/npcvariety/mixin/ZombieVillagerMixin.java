package com.crabman.npcvariety.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.NpcSelection;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

@Mixin(ZombieVillagerEntity.class)
public abstract class ZombieVillagerMixin extends ZombieEntity implements VillagerDataContainer, NpcBody, NpcSelection {
    private static final TrackedData<Integer> npcvariety$VARIANT = DataTracker.registerData(ZombieVillagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final int npcvariety$skinColorMax = 8;
    private static final int npcvariety$eyeColorMax = 5;

    private static final Identifier[] npcvariety$SKIN_TEXTURES = {
        new Identifier("npcvariety:textures/entity/zombie_villager/skin1.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin2.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin3.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin4.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin5.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin6.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin7.png"),
        new Identifier("npcvariety:textures/entity/zombie_villager/skin8.png"),
    };

    private static final Identifier[] npcvariety$EYE_TEXTURES = {
        new Identifier("npcvariety:textures/entity/zombie_villager/skin1.png"),
    };

    public ZombieVillagerMixin(EntityType<? extends ZombieEntity> entityType_1, World world_1) {
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

    @Override //DO NOT CALL THIS!!!
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

    @Inject(method = "initialize", at = @At(value = "RETURN"))
    public void initialize(IWorld iWorld_1, LocalDifficulty localDifficulty_1, SpawnType spawnType_1,
            EntityData entityData_1, CompoundTag compoundTag_1, CallbackInfoReturnable<EntityData> infoReturnable) {
        if (spawnType_1 != SpawnType.CONVERSION)
            this.npcvariety$chooseVariant();
    }

    @Inject(method = "finishConversion", at = @At(value = "INVOKE", target="Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    protected void finishConversion(ServerWorld serverWorld_1, CallbackInfo info, VillagerEntity villagerEntity_1) {
        ((NpcBody)villagerEntity_1).npcvariety$setVariant(((NpcBody) this).npcvariety$getVariant());
     }

    @Override
    public void npcvariety$chooseVariant() {
        int eyeColor;
        int skinColor;

        int typeColoring1;
        int typeColoring2;
        int typeVariation;
        int profVariation;

        int min = 0;
        int max = npcvariety$skinColorMax - 1;
        double travelerChance = this.random.nextDouble();

        if(travelerChance < 0.95D)
        {
            String type = String.valueOf(this.getVillagerData().getType());
            if(travelerChance < 0.9D)
            {
                switch(type){
                    case "snow": min = 0; max = 2; break;
                    case "taiga": min = 1; max = 3; break;
                    case "plains": min = 2; max = 4; break;
                    case "swamp": min = 2; max = 4; break;
                    case "savanna": min = 5; max = 7; break;
                    case "jungle": min = 5; max = 7; break;
                    case "desert": min = 4; max = 7; break;
                }
            }
            else{
                switch(type){
                    case "snow": min = 0; max = 3; break;
                    case "taiga": min = 0; max = 4; break;
                    case "plains": min = 1; max = 5; break;
                    case "swamp": min = 1; max = 5; break;
                    case "savanna": min = 4; max = 7; break;
                    case "jungle": min = 4; max = 7; break;
                    case "desert": min = 3; max = 7; break;
                }
            }
        }

        skinColor = min + this.random.nextInt((max - min) + 1);
        eyeColor = this.random.nextInt(npcvariety$eyeColorMax);
        typeColoring1 = this.random.nextInt(16);
        typeColoring2 = this.random.nextInt(16);

        typeVariation = this.random.nextInt(8);
        profVariation = this.random.nextInt(8);
        int personalVariation = this.random.nextInt(8);

        ((NpcBody) this).npcvariety$setVariant(skinColor | eyeColor << 3 |
         typeColoring1 << 6 | typeColoring2 << 10 | typeVariation << 14 | profVariation << 18 | personalVariation << 24);
    }
}