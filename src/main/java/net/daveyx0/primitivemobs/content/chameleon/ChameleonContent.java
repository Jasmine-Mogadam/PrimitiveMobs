package net.daveyx0.primitivemobs.content.chameleon;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.passive.Chameleon;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
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
 * Self-contained content module for the Chameleon (passive forest/jungle
 * animal). Follows the Dodo module template: owns its own registries and wires
 * up attribute, creative-tab and spawn-placement listeners.
 */
public final class ChameleonContent {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PrimitiveMobsMod.MOD_ID);

    public static final RegistryObject<EntityType<Chameleon>> CHAMELEON = ENTITY_TYPES.register("chameleon", () ->
        EntityType.Builder.of(Chameleon::new, MobCategory.CREATURE)
            .sized(0.7F, 0.5F)
            .clientTrackingRange(8)
            .build("primitivemobs:chameleon"));

    public static final RegistryObject<Item> CHAMELEON_SPAWN_EGG = ITEMS.register("chameleon_spawn_egg",
        () -> new ForgeSpawnEggItem(CHAMELEON, 0x048C00, 0x025600, new Item.Properties()));

    private ChameleonContent() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        modBus.addListener(ChameleonContent::onRegisterAttributes);
        modBus.addListener(ChameleonContent::onBuildCreativeTabs);
        modBus.addListener(ChameleonContent::onCommonSetup);
    }

    private static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        event.put(CHAMELEON.get(), Chameleon.createAttributes().build());
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> SpawnPlacements.register(
            CHAMELEON.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Animal::checkAnimalSpawnRules));
    }

    private static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            event.accept(CHAMELEON_SPAWN_EGG);
        }
    }
}
