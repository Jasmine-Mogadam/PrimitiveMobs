package net.daveyx0.primitivemobs.entity.passive;

import net.daveyx0.primitivemobs.content.dodo.DodoContent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class Dodo extends Chicken {
    private static final String SHED_TIME_TAG = "ShedTime";
    private static final Ingredient FOOD_ITEMS = Ingredient.of(
        Items.COCOA_BEANS,
        Items.BEETROOT_SEEDS,
        Items.BROWN_MUSHROOM,
        Items.RED_MUSHROOM,
        Items.PUMPKIN_SEEDS,
        Items.MELON_SEEDS,
        Items.WHEAT_SEEDS,
        Items.APPLE,
        Items.BEETROOT,
        Items.CARROT,
        Items.MELON_SLICE,
        Items.WHEAT,
        Items.SUGAR_CANE,
        Items.POTATO,
        Items.PUMPKIN,
        Items.MELON
    );

    private int timeUntilNextShed;

    public Dodo(EntityType<? extends Chicken> entityType, Level level) {
        super(entityType, level);
        this.timeUntilNextShed = this.random.nextInt(6000) + 6000;
        this.eggTime = this.random.nextInt(10000) + 10000;
    }

    @Override
    public void aiStep() {
        int dodoEggTime = this.eggTime;
        this.eggTime = Integer.MAX_VALUE;
        super.aiStep();
        this.eggTime = dodoEggTime;

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.timeUntilNextShed <= 0) {
            this.playSound(SoundEvents.GRASS_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.setGrassToMycelium();
            this.timeUntilNextShed = this.random.nextInt(6000) + 6000;
        }

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(DodoContent.DODO_EGG.get());
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(10000) + 10000;
        }
    }

    private void setGrassToMycelium() {
        BlockPos blockPos = this.blockPosition().below();
        if (this.level().getBlockState(blockPos).is(Blocks.GRASS_BLOCK)) {
            this.level().setBlockAndUpdate(blockPos, Blocks.MYCELIUM.defaultBlockState());
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    public @Nullable Dodo getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return DodoContent.DODO.get().create(level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(SHED_TIME_TAG, this.timeUntilNextShed);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(SHED_TIME_TAG, 3)) {
            this.timeUntilNextShed = tag.getInt(SHED_TIME_TAG);
        }
    }

    public static boolean checkDodoSpawnRules(EntityType<Dodo> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(Blocks.MYCELIUM)
            && isBrightEnoughToSpawn(level, pos)
            && Mob.checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }
}
