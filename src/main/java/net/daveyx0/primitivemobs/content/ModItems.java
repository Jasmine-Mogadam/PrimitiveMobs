package net.daveyx0.primitivemobs.content;

import net.daveyx0.primitivemobs.registry.RegistryHelper;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Misc, non-mob items. Mob-specific items live in that mob's content module
 * (e.g. {@link net.daveyx0.primitivemobs.content.dodo.DodoContent}).
 */
public final class ModItems {
    private static final RegistryHelper REG = new RegistryHelper();

    public static final RegistryObject<Item> WONDER_SAP = REG.item("wonder_sap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MIMIC_ORB = REG.item("mimic_orb", () -> new Item(new Item.Properties().stacksTo(1)));

    private ModItems() {
    }

    public static void register(IEventBus modBus) {
        REG.register(modBus);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.INGREDIENTS, WONDER_SAP, MIMIC_ORB);
    }
}
