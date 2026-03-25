package net.daveyx0.primitivemobs.util;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.util.EntityUtil;
import net.minecraft.entity.Entity;

public class TameableUtil {
    
    public static boolean canTameableDespawn(Entity entity) {
        ITameableEntity tameable = EntityUtil.getCapability(entity, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        if(tameable != null && tameable.isTamed()) {
            return false;
        }
        return true;
    }

    public static boolean isTameablePreventingPlayerRest(Entity entity) {
        ITameableEntity tameable = EntityUtil.getCapability(entity, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        if(tameable != null && tameable.isTamed()) {
            return false;
        }
        return true;
    }
}
