package com.crabman.npcvariety.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.NpcSelection;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin extends AbstractTraderEntity
        implements InteractionObserver, VillagerDataContainer, NpcSelection {

    private static final int npcvariety$skinColorMax = 8;
    private static final int npcvariety$eyeColorMax = 5; 

    public VillagerMixin(EntityType<? extends AbstractTraderEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(method = "initialize", at = @At(value = "RETURN"))
    public void initialize(IWorld iWorld_1, LocalDifficulty localDifficulty_1, SpawnType spawnType_1,
            EntityData entityData_1, CompoundTag compoundTag_1, CallbackInfoReturnable<EntityData> infoReturnable) {
        if (spawnType_1 != SpawnType.BREEDING && spawnType_1 != SpawnType.CONVERSION)
            this.npcvariety$chooseVariant();
    }

    @Inject(method = "method_7225", at = @At(value = "RETURN"))
    public void method_7225(PassiveEntity passiveEntity_1, CallbackInfoReturnable<VillagerEntity> infoReturnable) {
        final double eyeInheritance = this.random.nextDouble();
        final double skinInheritance = this.random.nextDouble();

        final int eyeColor;
        final int skinColor;

        int typeColoring1;
        int typeColoring2;
        int typeVariation;
        int profVariation;

        final int thisSkin = ((NpcBody) this).npcvariety$getSkinIndex();
        final int partnerSkin = ((NpcBody) passiveEntity_1).npcvariety$getSkinIndex();

        VillagerEntity villagerBaby = infoReturnable.getReturnValue();

        // Choosing eye color
        if (eyeInheritance > 0.99D)
            eyeColor = this.random.nextInt(npcvariety$eyeColorMax);
        else
            eyeColor = ((NpcBody) this).npcvariety$getEyeIndex();

        // Choosing skin color
        if (skinInheritance < 0.70D)
            skinColor = Math.min(thisSkin, partnerSkin) + this.random.nextInt(Math.abs(partnerSkin - thisSkin) + 1);
        else
            skinColor = thisSkin;

            typeColoring1 = this.random.nextInt(16);
            typeColoring2 = this.random.nextInt(16);
    
            typeVariation = this.random.nextInt(8);
            profVariation = this.random.nextInt(8);
            int personalVariation = this.random.nextInt(8);
    
            ((NpcBody) villagerBaby).npcvariety$setVariant(skinColor | eyeColor << 3 |
             typeColoring1 << 6 | typeColoring2 << 10 | typeVariation << 14 | profVariation << 18 | personalVariation << 24);
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