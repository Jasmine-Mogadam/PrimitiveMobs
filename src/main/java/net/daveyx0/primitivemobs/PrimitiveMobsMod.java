package net.daveyx0.primitivemobs;

import com.mojang.logging.LogUtils;
import net.daveyx0.primitivemobs.registry.PrimitiveMobsEntityTypes;
import net.daveyx0.primitivemobs.registry.PrimitiveMobsItems;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PrimitiveMobsMod.MOD_ID)
public final class PrimitiveMobsMod {
    public static final String MOD_ID = "primitivemobs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrimitiveMobsMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        PrimitiveMobsItems.register(modBus);
        PrimitiveMobsEntityTypes.register(modBus);
        modBus.addListener(this::onBuildCreativeTabContents);
        modBus.addListener(PrimitiveMobsEntityTypes::registerAttributes);

        SpawnPlacements.register(
            PrimitiveMobsEntityTypes.DODO.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            net.daveyx0.primitivemobs.entity.passive.Dodo::checkDodoSpawnRules
        );
    }

    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(PrimitiveMobsItems.WONDER_SAP);
            event.accept(PrimitiveMobsItems.DODO_EGG);
            event.accept(PrimitiveMobsItems.MIMIC_ORB);
        }

        if (event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
            event.accept(PrimitiveMobsItems.RAW_DODO);
            event.accept(PrimitiveMobsItems.COOKED_DODO);
        }

        if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            event.accept(PrimitiveMobsItems.DODO_SPAWN_EGG);
        }
    }
}
