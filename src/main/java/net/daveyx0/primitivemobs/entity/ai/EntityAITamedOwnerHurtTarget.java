package net.daveyx0.primitivemobs.entity.ai;

import net.daveyx0.multimob.common.capabilities.CapabilityTameableEntity;
import net.daveyx0.multimob.common.capabilities.ITameableEntity;
import net.daveyx0.multimob.util.EntityUtil;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAITamedOwnerHurtTarget extends EntityAITarget {
    EntityCreature tameable;
    EntityLivingBase attacker;
    private int timestamp;

    public EntityAITamedOwnerHurtTarget(EntityCreature tameableIn) {
        super(tameableIn, false);
        this.tameable = tameableIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        ITameableEntity tameableCap = EntityUtil.getCapability(this.tameable, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        if (tameableCap == null || !tameableCap.isTamed())
        {
            return false;
        }
        else
        {
            EntityLivingBase owner = tameableCap.getOwner(this.tameable);

            if (owner == null)
            {
                return false;
            }
            else
            {
                this.attacker = owner.getLastAttackedEntity(); // Target owner is attacking
                int i = owner.getLastAttackedEntityTime();

                if (i != this.timestamp && this.isSuitableTarget(this.attacker, false) && TameableAIHelper.shouldAttack(this.attacker, this.tameable))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        ITameableEntity tameableCap = EntityUtil.getCapability(this.tameable, CapabilityTameableEntity.TAMEABLE_ENTITY_CAPABILITY, null);
        if (tameableCap != null) {
             EntityLivingBase owner = tameableCap.getOwner(this.tameable);
             if (owner != null)
             {
                 this.timestamp = owner.getLastAttackedEntityTime();
             }
        }
        super.startExecuting();
    }
}
