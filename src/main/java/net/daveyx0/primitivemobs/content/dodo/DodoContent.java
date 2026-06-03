package net.daveyx0.primitivemobs.content.dodo;

import net.daveyx0.primitivemobs.entity.passive.Dodo;
import net.daveyx0.primitivemobs.registry.RegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Self-contained content module for the Dodo — the reference example for the
 * per-mob module pattern. Copy this class for new mobs; see {@link RegistryHelper}.
 */
public final class DodoContent {
    private static final RegistryHelper REG = new RegistryHelper();

    public static final RegistryObject<EntityType<Dodo>> DODO =
        REG.entity("dodo", Dodo::new, MobCategory.CREATURE, b -> b.sized(0.75F, 0.75F).clientTrackingRange(8));

    public static final RegistryObject<Item> RAW_DODO = REG.item("dodo",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(1.2F).meat().build())));
    public static final RegistryObject<Item> COOKED_DODO = REG.item("cooked_dodo",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(2.4F).meat().build())));
    public static final RegistryObject<Item> DODO_EGG = REG.item("dodo_egg",
        () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DODO_SPAWN_EGG = REG.spawnEgg("dodo_spawn_egg", DODO, 0xc4a56b, 0xeee7d1);

    private DodoContent() {
    }

    public static void register(IEventBus modBus) {
        REG.register(modBus);
        RegistryHelper.attributes(modBus, DODO, Chicken::createAttributes);
        RegistryHelper.spawnPlacement(modBus, DODO, SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dodo::checkDodoSpawnRules);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.INGREDIENTS, DODO_EGG);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.FOOD_AND_DRINKS, RAW_DODO, COOKED_DODO);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.SPAWN_EGGS, DODO_SPAWN_EGG);
    }
}
