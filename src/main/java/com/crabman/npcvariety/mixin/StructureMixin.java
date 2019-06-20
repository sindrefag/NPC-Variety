package com.crabman.npcvariety.mixin;

import java.util.Optional;

import com.crabman.npcvariety.util.NpcSelection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.Structure;
import net.minecraft.world.IWorld;

@Mixin(Structure.class)
public abstract class StructureMixin {

    @Inject(method ="Lnet/minecraft/structure/Structure;method_17916(Lnet/minecraft/world/IWorld;Lnet/minecraft/nbt/CompoundTag;)Ljava/util/Optional;", at=@At(value="RETURN", ordinal=0), cancellable = true)
    private static void spawnEntity(IWorld iWorld_1, CompoundTag compoundTag_1, CallbackInfoReturnable infoReturnable){
        System.out.println("Structure spawning entity mixin starting");
        Optional<Entity> opt = (Optional<Entity>) infoReturnable.getReturnValue();
        opt.ifPresent((entity)->{
            System.out.println("Generating an entity: "+entity);
            if(entity.getType() == EntityType.VILLAGER || entity.getType() == EntityType.ZOMBIE_VILLAGER)
            {
                ((NpcSelection) entity).npcvariety$chooseVariant();
            }
        });
        infoReturnable.setReturnValue(opt);
    }
}