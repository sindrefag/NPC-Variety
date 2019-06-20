package com.crabman.npcvariety.util;

import net.minecraft.util.Identifier;

public interface NpcBody {

    public abstract Identifier npcvariety$getSkinTexture();
    public abstract Identifier npcvariety$getEyeTexture();

    public abstract int npcvariety$getSkinIndex();
    public abstract int npcvariety$getEyeIndex();

    public abstract void npcvariety$setVariant(int value);
    public abstract int npcvariety$getVariant();
 }