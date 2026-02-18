package net.daveyx0.primitivemobs.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class EntityAITamedHurtByTarget extends EntityAIHurtByTarget {

    public EntityAITamedHurtByTarget(EntityCreature creatureIn, boolean entityCallsForHelpIn, Class<?>... targetClassesIn) {
        super(creatureIn, entityCallsForHelpIn, targetClassesIn);
    }

    @Override
    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return false;
        }

        EntityLivingBase attacker = this.taskOwner.getRevengeTarget();
        return TameableAIHelper.shouldAttack(attacker, this.taskOwner);
    }
}
