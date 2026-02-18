package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.util.EntityUtil;
import net.daveyx0.primitivemobs.entity.monster.EntityPrimitiveTameableMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class TameableAIHelper {

    public static boolean shouldAttack(EntityLivingBase attacker, EntityLivingBase self) {
        if (attacker == null || self == null) {
            return false; 
        }

        EntityLivingBase selfOwner = getOwnerOf(self);
        
        if (selfOwner != null) {
            // If attacker is the owner, don't attack
            if (selfOwner.getUniqueID().equals(attacker.getUniqueID())) {
                return false;
            }

            EntityLivingBase attackerOwner = getOwnerOf(attacker);
            if (attackerOwner != null) {
                // If both have the same owner, don't attack
                if (selfOwner.getUniqueID().equals(attackerOwner.getUniqueID())) {
                    return false;
                }
            }
        }
        
        return true;
    }

    private static EntityLivingBase getOwnerOf(EntityLivingBase entity) {
        if (entity == null) return null;
        
        // Check Capability
        ITameableEntity cap = EntityUtil.getCapability(entity, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        if (cap != null && cap.isTamed()) {
            EntityLivingBase owner = cap.getOwner(entity);
            if (owner != null) return owner;
        }
        
        // Check EntityPrimitiveTameableMob
        if (entity instanceof EntityPrimitiveTameableMob) {
            EntityPrimitiveTameableMob mob = (EntityPrimitiveTameableMob) entity;
            if (mob.isTamed()) {
                return mob.getOwner();
            }
        }
        
        // Check EntityTameable (Vanilla)
        if (entity instanceof EntityTameable) {
            EntityTameable tameable = (EntityTameable) entity;
            if (tameable.isTamed()) {
                return tameable.getOwner();
            }
        }
        
        return null;
    }
}
