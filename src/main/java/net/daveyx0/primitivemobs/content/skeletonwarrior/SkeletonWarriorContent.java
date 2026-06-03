package net.daveyx0.primitivemobs.content.skeletonwarrior;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.monster.SkeletonWarrior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Self-contained content module for the Skeleton Warrior (armored melee skeleton
 * variant). Follows the per-mob module pattern: owns its own registries and wires
 * up its attribute, creative-tab and spawn-placement listeners.
 */
public final class SkeletonWarriorContent {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PrimitiveMobsMod.MOD_ID);

    public static final RegistryObject<EntityType<SkeletonWarrior>> SKELETON_WARRIOR =
        ENTITY_TYPES.register("skeleton_warrior", () ->
            EntityType.Builder.of(SkeletonWarrior::new, MobCategory.MONSTER)
                .sized(0.6F, 1.99F)
                .clientTrackingRange(8)
                .build("primitivemobs:skeleton_warrior"));

    public static final RegistryObject<Item> SKELETON_WARRIOR_SPAWN_EGG =
        ITEMS.register("skeleton_warrior_spawn_egg",
            () -> new ForgeSpawnEggItem(SKELETON_WARRIOR, 0x9b9b9b, 0x4f4f4f, new Item.Properties()));

    private SkeletonWarriorContent() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        modBus.addListener(SkeletonWarriorContent::onRegisterAttributes);
        modBus.addListener(SkeletonWarriorContent::onBuildCreativeTabs);
        modBus.addListener(SkeletonWarriorContent::onCommonSetup);
    }

    private static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        // AbstractSkeleton attributes, but a tougher 25 HP (parity with 1.12).
        event.put(SKELETON_WARRIOR.get(),
            AbstractSkeleton.createAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 25.0D)
                .build());
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> SpawnPlacements.register(
            SKELETON_WARRIOR.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Monster::checkMonsterSpawnRules));
    }

    private static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            event.accept(SKELETON_WARRIOR_SPAWN_EGG);
        }
    }
}
