package net.daveyx0.primitivemobs.registry;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class PrimitiveMobsItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);

    public static final RegistryObject<Item> WONDER_SAP = ITEMS.register("wonder_sap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_DODO = ITEMS.register("dodo", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(1.2F).meat().build())));
    public static final RegistryObject<Item> COOKED_DODO = ITEMS.register("cooked_dodo", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(2.4F).meat().build())));
    public static final RegistryObject<Item> DODO_EGG = ITEMS.register("dodo_egg", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> MIMIC_ORB = ITEMS.register("mimic_orb", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DODO_SPAWN_EGG = ITEMS.register("dodo_spawn_egg", () -> new ForgeSpawnEggItem(PrimitiveMobsEntityTypes.DODO, 0xc4a56b, 0xeee7d1, new Item.Properties()));

    private PrimitiveMobsItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
