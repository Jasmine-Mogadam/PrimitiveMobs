package net.daveyx0.primitivemobs;

import com.mojang.logging.LogUtils;
import net.daveyx0.primitivemobs.content.ModItems;
import net.daveyx0.primitivemobs.content.chameleon.ChameleonContent;
import net.daveyx0.primitivemobs.content.dodo.DodoContent;
import net.daveyx0.primitivemobs.content.goblin.GoblinContent;
import net.daveyx0.primitivemobs.content.skeletonwarrior.SkeletonWarriorContent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PrimitiveMobsMod.MOD_ID)
public final class PrimitiveMobsMod {
    public static final String MOD_ID = "primitivemobs";
    public static final Logger LOGGER = LogUtils.getLogger();

    /** Convenience for a {@link ResourceLocation} in this mod's namespace. */
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public PrimitiveMobsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Misc items, then one line per mob content module. Mob modules are
        // self-contained (own registries, attributes, creative tab, spawn,
        // client) so this list is the only shared touch-point when adding mobs.
        ModItems.register(modBus);
        DodoContent.register(modBus);
        ChameleonContent.register(modBus);
        SkeletonWarriorContent.register(modBus);
        GoblinContent.register(modBus);
    }
}
