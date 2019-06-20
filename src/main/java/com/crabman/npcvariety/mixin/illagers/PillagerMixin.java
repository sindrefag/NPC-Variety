package com.crabman.npcvariety.mixin.illagers;

import java.awt.Color;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.IllagerClothing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

@Mixin(PillagerEntity.class)
public abstract class PillagerMixin extends IllagerEntity implements CrossbowUser, RangedAttackMob, IllagerClothing {

    private static final  int npcvariety$skinColorMax = 8;
    private static final int npcvariety$eyeColorMax = 5;

    private static final int npcvariety$headFeatureTypes = 3;
    private static final int npcvariety$trousersColors = 16;
    private static final int npcvariety$shirtColors = 15;
    private static final int npcvariety$overclothesTypes = 4;
    private static final int npcvariety$shoeTypes = 1;

    protected PillagerMixin(EntityType<? extends IllagerEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    private static final Identifier[] npcvariety$HEAD_FEATURE = {
        new Identifier("npcvariety:textures/entity/illager/head_features/empty.png"),
        new Identifier("npcvariety:textures/entity/illager/head_features/eyepatch_l.png"),
        new Identifier("npcvariety:textures/entity/illager/head_features/eyepatch_r.png"),
    };

    private static final Identifier npcvariety$TROUSERS_TEXTURE = new Identifier("npcvariety:textures/entity/illager/trousers/pillager.png");
    private static final Identifier npcvariety$COLORABLE_TEXTURE = new Identifier("npcvariety:textures/entity/illager/shirt/pillager.png");
    private static final Identifier npcvariety$SHOES_TEXTURE = new Identifier("npcvariety:textures/entity/illager/shoes/pillager.png");

    @Override
    public Identifier npcvariety$getHeadFeatureTexture(){
        int index = Math.min((((NpcBody) this).npcvariety$getVariant() >> 6) & 15, 2);
        return (Identifier)npcvariety$HEAD_FEATURE[index];
    }

    @Override
    public Identifier npcvariety$getTrouserTexture(){
        return npcvariety$TROUSERS_TEXTURE;
    }
    @Override
    public Identifier npcvariety$getShirtTexture(){return npcvariety$COLORABLE_TEXTURE;}
    @Override
    public Identifier npcvariety$getOverclothesTexture(){return npcvariety$COLORABLE_TEXTURE;}
    @Override
    public Identifier npcvariety$getShoesTexture(){return npcvariety$SHOES_TEXTURE;}

    @Override
    public float[] npcvariety$getTrouserColor() {
        int index = (((NpcBody) this).npcvariety$getVariant() >> 10) & 15;
        return DyeColor.byId(index).getColorComponents();
    }
    @Override
    public float[] npcvariety$getShirtColor() {
        int index = (((NpcBody) this).npcvariety$getVariant() >> 14) & 15;
        float[] colorAsFloats = DyeColor.byId(index).getColorComponents();
        return npcvariety$convertColors(colorAsFloats);
    }

    @Override
    public float[] npcvariety$convertColors(float[] colorInput) {

        int r  = (int)(colorInput[0]*255.0);
        int g  = (int)(colorInput[1]*255.0);
        int b = (int)(colorInput[2]*255.0);

        float[] colorHSB = Color.RGBtoHSB(r, g, b, null);
        colorHSB[1] /= 2;

        int RGB = Color.HSBtoRGB(colorHSB[0], colorHSB[1], colorHSB[2]);
        final float[] outputColor = {
            (RGB >> 16 & 0xFF) / 255.0F,
            (RGB >> 8 & 0xFF) / 255.0F,
            (RGB & 0xFF) / 255.0F,
        };

        return outputColor;
    }

    @Inject(method = "initialize", at = @At(value = "RETURN"))
    public void initialize(IWorld iWorld_1, LocalDifficulty localDifficulty_1, SpawnType spawnType_1, 
            EntityData entityData_1, CompoundTag compoundTag_1, CallbackInfoReturnable<EntityData> infoReturnable) {
        //entityData_1 = super.initialize(iWorld_1, localDifficulty_1, spawnType_1, entityData_1, compoundTag_1);

        this.npcvariety$chooseVariant(iWorld_1);
    }

    //@Override
    private void npcvariety$chooseVariant(IWorld iWorld)
    {
        int skinColor;
        int eyeColor;

        int headFeature = 0;
        int trousersColor;
        int shirtColor;
        int overclothes;
        int shoes;
    
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

        //max and min repurposed for head feature
        min = 0;
        max = 0;//headFeatureTypes;

        final double eyepatchChance = this.random.nextDouble();
        if (eyepatchChance <= 0.15D)
        {
            min = 1;
            max = 2;
        }
        else if(this.random.nextDouble() < 0.2d)
        {
            min = 0;
            max = 0;
        }
        
        headFeature = min + this.random.nextInt((max - min) + 1);

        trousersColor = this.random.nextInt(npcvariety$trousersColors);
        shirtColor = this.random.nextInt(npcvariety$shirtColors);
        overclothes = this.random.nextInt(npcvariety$overclothesTypes);
        shoes = this.random.nextInt(npcvariety$shoeTypes);

        ((NpcBody) this).npcvariety$setVariant(skinColor | eyeColor << 3 |
         headFeature << 6 | trousersColor << 10 | shirtColor << 14 | overclothes << 18 | shoes << 20);
    }
}