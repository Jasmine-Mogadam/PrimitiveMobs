package net.daveyx0.primitivemobs.content;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Misc, non-mob items. Mob-specific items live in that mob's content module
 * (e.g. {@link net.daveyx0.primitivemobs.content.dodo.DodoContent}).
 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);

    public static final RegistryObject<Item> WONDER_SAP =
        ITEMS.register("wonder_sap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MIMIC_ORB =
        ITEMS.register("mimic_orb", () -> new Item(new Item.Properties().stacksTo(1)));

    private ModItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        modBus.addListener(ModItems::onBuildCreativeTabs);
    }

    private static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(WONDER_SAP);
            event.accept(MIMIC_ORB);
        }
    }
}
