package net.daveyx0.primitivemobs.content.skeletonwarrior;

import net.daveyx0.primitivemobs.entity.monster.SkeletonWarrior;
import net.daveyx0.primitivemobs.registry.RegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Self-contained content module for the Skeleton Warrior (armored melee skeleton
 * variant with a tougher 25 HP, parity with the 1.12 mob).
 */
public final class SkeletonWarriorContent {
    private static final RegistryHelper REG = new RegistryHelper();

    public static final RegistryObject<EntityType<SkeletonWarrior>> SKELETON_WARRIOR =
        REG.entity("skeleton_warrior", SkeletonWarrior::new, MobCategory.MONSTER,
            b -> b.sized(0.6F, 1.99F).clientTrackingRange(8));

    public static final RegistryObject<Item> SKELETON_WARRIOR_SPAWN_EGG =
        REG.spawnEgg("skeleton_warrior_spawn_egg", SKELETON_WARRIOR, 0x9b9b9b, 0x4f4f4f);

    private SkeletonWarriorContent() {
    }

    public static void register(IEventBus modBus) {
        REG.register(modBus);
        RegistryHelper.attributes(modBus, SKELETON_WARRIOR,
            () -> AbstractSkeleton.createAttributes().add(Attributes.MAX_HEALTH, 25.0D));
        RegistryHelper.spawnPlacement(modBus, SKELETON_WARRIOR, SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.SPAWN_EGGS, SKELETON_WARRIOR_SPAWN_EGG);
    }
}
