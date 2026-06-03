package net.daveyx0.primitivemobs.entity.passive;

import net.daveyx0.primitivemobs.content.chameleon.ChameleonContent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * Passive chameleon. In 1.12 the chameleon dynamically blended its skin colour
 * to match the block beneath it (a client-only render tint driven by
 * MultiMob's variant/colour capability). 1.20.1 reimplements the colour aspect
 * as a synced integer "variant" chosen at spawn time; the renderer reads the
 * variant and applies a matching tint. The behaviour (passive animal that
 * panics/flees, can be tempted/bred with fermented spider eye, follows parents,
 * wanders and watches players) is reproduced with native 1.20.1 AI goals.
 */
public class Chameleon extends Animal {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID =
        SynchedEntityData.defineId(Chameleon.class, EntityDataSerializers.INT);

    private static final String VARIANT_TAG = "Variant";

    /** Tint colours (RGB) the chameleon can wear, mirroring the leafy/earthy 1.12 palette. */
    public static final int[] VARIANT_COLORS = new int[] {
        0x3F762A, // forest green (1.12 default-ish 63,118,42)
        0x6CA62C, // bright leaf green
        0x4E8C2A, // grass green
        0x8C7A3A, // sandy brown
        0x6B5836, // bark brown
        0x2C6E4E  // teal/jungle
    };

    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.FERMENTED_SPIDER_EYE);

    public Chameleon(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
            .add(Attributes.MAX_HEALTH, 8.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, 0);
    }

    public int getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return FOOD_ITEMS.test(stack);
    }

    @Override
    @Nullable
    public Chameleon getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        Chameleon child = ChameleonContent.CHAMELEON.get().create(level);
        if (child != null && otherParent instanceof Chameleon mate) {
            child.setVariant(this.random.nextBoolean() ? this.getVariant() : mate.getVariant());
        }
        return child;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
                                        @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        this.setVariant(this.random.nextInt(VARIANT_COLORS.length));
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(VARIANT_TAG, this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains(VARIANT_TAG, 3)) {
            this.setVariant(tag.getInt(VARIANT_TAG));
        }
    }
}
