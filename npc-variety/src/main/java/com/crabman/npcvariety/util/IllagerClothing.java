package com.crabman.npcvariety.util;

import net.minecraft.util.Identifier;

public interface IllagerClothing {

    public abstract Identifier npcvariety$getHeadFeatureTexture();
    public abstract Identifier npcvariety$getTrouserTexture();
    public abstract Identifier npcvariety$getShirtTexture();
    public abstract Identifier npcvariety$getOverclothesTexture();
    public abstract Identifier npcvariety$getShoesTexture();

    public abstract float[] npcvariety$getTrouserColor();
    public abstract float[] npcvariety$getShirtColor();

    public abstract float[] npcvariety$convertColors(float[] colorInput);
 }