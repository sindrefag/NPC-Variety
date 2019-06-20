package com.crabman.npcvariety.mixin.illagers;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.RavagerClothing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

@Mixin(RavagerEntity.class)
public abstract class RavagerMixin extends RaiderEntity implements NpcBody, RavagerClothing {
    private static final TrackedData<Integer> npcvariety$VARIANT = DataTracker.registerData(RavagerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final int npcvariety$skinColorMax = 8;
    private static final int npcvariety$eyeColorMax = 5;

    private static final Identifier[] npcvariety$SKIN_TEXTURES = {
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager1.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager2.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager3.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager4.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager5.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager6.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager7.png"),
        new Identifier("npcvariety:textures/entity/illager/ravager/ravager8.png"),
    };

    private static final Identifier[] npcvariety$EYE_TEXTURES = {
        new Identifier("npcvariety:textures/entity/illager/eyes/ravager_green.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/ravager_blue.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/ravager_light_blue.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/ravager_brown.png"),
        new Identifier("npcvariety:textures/entity/illager/eyes/ravager_amber.png"),
    };

    private static final Identifier[] npcvariety$CLOTHING_TEXTURES = {
        new Identifier("npcvariety:textures/entity/illager/ravager/chains.png"),
    };
 
    protected RavagerMixin(EntityType<? extends RaiderEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(npcvariety$VARIANT, 0);
    }
    
    @Override
    public Identifier npcvariety$getSkinTexture(){
        return (Identifier)npcvariety$SKIN_TEXTURES[3]; //TODO make other ravager textures good
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
    public Identifier npcvariety$getOverclothes(){
        int index = (((NpcBody) this).npcvariety$getVariant() >> 6) & 15;
        return (Identifier)npcvariety$CLOTHING_TEXTURES[index];
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
    
    @Override
    public EntityData initialize(IWorld iWorld_1, LocalDifficulty localDifficulty_1, SpawnType spawnType_1, EntityData entityData_1, CompoundTag compoundTag_1) {
        entityData_1 = super.initialize(iWorld_1, localDifficulty_1, spawnType_1, entityData_1, compoundTag_1);

        this.npcvariety$chooseVariant(iWorld_1);

        return entityData_1;
    }

    //@Override
    private void npcvariety$chooseVariant(IWorld iWorld)
    {
        int eyeColor;
        int skinColor;
        int clothesType;
        int personalVariation;

        int min = 0;
        int max = npcvariety$skinColorMax - 1;
        double travelerChance = this.random.nextDouble();

        if(travelerChance < 0.8D)
        {
            final BlockPos pos = new BlockPos(this);
            final Biome biome = world.getBiome(pos);
            if(travelerChance < 0.6D)
            {
                switch(biome.getCategory()){
                    case ICY: min = 0; max = 2; break;
                    case TAIGA:
                    case EXTREME_HILLS: min = 1; max = 3; break;
                    case PLAINS:
                    case RIVER:
                    case FOREST: min = 2; max = 4; break;
                    case SWAMP: min = 2; max = 4; break;
                    case SAVANNA: min = 5; max = 7; break;
                    case JUNGLE: min = 5; max = 7; break;
                    case DESERT:
                    case MESA: min = 4; max = 7; break;
                    default: break;
                }
            }
            else{
                switch(biome.getCategory()){
                    case ICY: min = 0; max = 3; break;
                    case TAIGA:
                    case EXTREME_HILLS: min = 0; max = 4; break;
                    case PLAINS:
                    case RIVER:
                    case FOREST: min = 1; max = 5; break;
                    case SWAMP: min = 1; max = 5; break;
                    case SAVANNA: min = 4; max = 7; break;
                    case JUNGLE: min = 4; max = 7; break;
                    case DESERT:
                    case MESA: min = 3; max = 7; break;
                    default: break;
                }
            }
        }

        skinColor = min + this.random.nextInt((max - min) + 1);
        eyeColor = this.random.nextInt(npcvariety$eyeColorMax);
        clothesType = 0;//this.random.nextInt(2);
        personalVariation = 0;//this.random.nextInt(15);

        ((NpcBody) this).npcvariety$setVariant(skinColor | eyeColor << 3 | clothesType << 6 | personalVariation << 14);
    }
}