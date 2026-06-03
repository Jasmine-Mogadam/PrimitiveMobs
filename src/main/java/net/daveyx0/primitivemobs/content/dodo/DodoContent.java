package net.daveyx0.primitivemobs.content.dodo;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.passive.Dodo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.food.FoodProperties;
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
 * Self-contained content module for the Dodo. This is the template every mob
 * follows: each module owns its own DeferredRegisters and wires up its own
 * creative-tab, attribute and spawn-placement listeners, so mobs can be added
 * in parallel without touching shared registry files.
 *
 * To add a mob, copy this class, then add one line to
 * {@link net.daveyx0.primitivemobs.PrimitiveMobsMod}: {@code YourContent.register(modBus);}
 */
public final class DodoContent {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PrimitiveMobsMod.MOD_ID);

    public static final RegistryObject<EntityType<Dodo>> DODO = ENTITY_TYPES.register("dodo", () ->
        EntityType.Builder.of(Dodo::new, MobCategory.CREATURE)
            .sized(0.75F, 0.75F)
            .clientTrackingRange(8)
            .build("primitivemobs:dodo"));

    public static final RegistryObject<Item> RAW_DODO = ITEMS.register("dodo",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(1.2F).meat().build())));
    public static final RegistryObject<Item> COOKED_DODO = ITEMS.register("cooked_dodo",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(2.4F).meat().build())));
    public static final RegistryObject<Item> DODO_EGG = ITEMS.register("dodo_egg",
        () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DODO_SPAWN_EGG = ITEMS.register("dodo_spawn_egg",
        () -> new ForgeSpawnEggItem(DODO, 0xc4a56b, 0xeee7d1, new Item.Properties()));

    private DodoContent() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        modBus.addListener(DodoContent::onRegisterAttributes);
        modBus.addListener(DodoContent::onBuildCreativeTabs);
        modBus.addListener(DodoContent::onCommonSetup);
    }

    private static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        event.put(DODO.get(), Chicken.createAttributes().build());
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> SpawnPlacements.register(
            DODO.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Dodo::checkDodoSpawnRules));
    }

    private static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(DODO_EGG);
        } else if (event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            event.accept(RAW_DODO);
            event.accept(COOKED_DODO);
        } else if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            event.accept(DODO_SPAWN_EGG);
        }
    }
}
