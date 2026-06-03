package net.daveyx0.primitivemobs.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * Skeleton Warrior — an armored melee variant of the vanilla skeleton.
 *
 * <p>Behaviour spec ported from the 1.12 {@code EntitySkeletonWarrior}: a tougher
 * skeleton (25 HP) that fights in melee with an iron sword while wearing a leather
 * helmet, instead of using a bow. The 1.12 version dynamically switched between a
 * bow and a sword depending on range (custom MultiMob {@code EntityAISwitchBetweenRangedAndMelee}
 * and a {@code EntityAISwitchWeapons} goal); that weapon-swapping is simplified here
 * to a straightforward melee skeleton, since {@link AbstractSkeleton#reassessWeaponGoal()}
 * already drives a {@link net.minecraft.world.entity.ai.goal.MeleeAttackGoal} whenever
 * the mob is not holding a bow. The 1.12 tipped-arrow weakness effect is therefore
 * not applicable in the melee-only configuration and is omitted.
 */
public class SkeletonWarrior extends AbstractSkeleton {

    public SkeletonWarrior(EntityType<? extends SkeletonWarrior> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, @Nullable SpawnGroupData spawnData,
                                        @Nullable net.minecraft.nbt.CompoundTag tag) {
        // AbstractSkeleton#finalizeSpawn already invokes our overridden
        // populateDefaultEquipmentSlots (sword + helmet), then enchants the gear
        // and calls reassessWeaponGoal() — which selects the melee goal since we
        // are not holding a bow.
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, tag);
        this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier());
        return data;
    }

    @Override
    protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
        // Melee loadout: iron sword + leather helmet (parity with 1.12 SkeletonWarrior).
        this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource damageSource) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }
}
