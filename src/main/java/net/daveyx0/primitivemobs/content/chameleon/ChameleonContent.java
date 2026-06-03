package net.daveyx0.primitivemobs.content.chameleon;

import net.daveyx0.primitivemobs.entity.passive.Chameleon;
import net.daveyx0.primitivemobs.registry.RegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Self-contained content module for the Chameleon (passive forest/jungle animal).
 */
public final class ChameleonContent {
    private static final RegistryHelper REG = new RegistryHelper();

    public static final RegistryObject<EntityType<Chameleon>> CHAMELEON =
        REG.entity("chameleon", Chameleon::new, MobCategory.CREATURE, b -> b.sized(0.7F, 0.5F).clientTrackingRange(8));

    public static final RegistryObject<Item> CHAMELEON_SPAWN_EGG =
        REG.spawnEgg("chameleon_spawn_egg", CHAMELEON, 0x048C00, 0x025600);

    private ChameleonContent() {
    }

    public static void register(IEventBus modBus) {
        REG.register(modBus);
        RegistryHelper.attributes(modBus, CHAMELEON, Chameleon::createAttributes);
        RegistryHelper.spawnPlacement(modBus, CHAMELEON, SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.SPAWN_EGGS, CHAMELEON_SPAWN_EGG);
    }
}
