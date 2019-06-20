package com.crabman.npcvariety.mixin;

import com.crabman.npcvariety.util.NpcBody;
import com.crabman.npcvariety.util.TraderClothing;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.passive.AbstractTraderEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.IWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderMixin extends AbstractTraderEntity implements TraderClothing {

    private static final int npcvariety$skinColorMax = 8;
    private static final int npcvariety$eyeColorMax = 5;

    private static final Identifier[] npcvariety$CLOTHES_TYPE = {
        new Identifier("npcvariety:textures/entity/trader/blue_robe.png"),
    };

    public WanderingTraderMixin(EntityType<? extends AbstractTraderEntity> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Override
    public Identifier npcvariety$getClothesTexture(){
        int index = Math.min((((NpcBody) this).npcvariety$getVariant() >> 2 * 8) & 255, 0);
        return (Identifier)npcvariety$CLOTHES_TYPE[index];
    }
    
    @Override
    public EntityData initialize(IWorld iWorld_1, LocalDifficulty localDifficulty_1, SpawnType spawnType_1, EntityData entityData_1, CompoundTag compoundTag_1) {
        entityData_1 = super.initialize(iWorld_1, localDifficulty_1, spawnType_1, entityData_1, compoundTag_1);

        this.npcvariety$chooseVariant();

        return entityData_1;
    }

    //@Override
    private void npcvariety$chooseVariant()
    {
        int eyeColor;
        int skinColor;

        int clothesType;
        int color1;
        int color2;
        int headFeature;
        int clothesVariation;

        skinColor = this.random.nextInt(npcvariety$skinColorMax);
        eyeColor = this.random.nextInt(npcvariety$eyeColorMax);

        clothesType = this.random.nextInt(2);
        color1 = this.random.nextInt(16);
        color2 = this.random.nextInt(16);

        headFeature = this.random.nextInt(16);
        clothesVariation = this.random.nextInt(16);

        ((NpcBody) this).npcvariety$setVariant(skinColor | eyeColor << 3 | 
            clothesType << 6 | color1 << 10 | color2 << 14 | headFeature << 18 | clothesVariation << 21);
    }
}