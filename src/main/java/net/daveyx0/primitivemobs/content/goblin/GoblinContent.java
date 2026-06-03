package net.daveyx0.primitivemobs.content.goblin;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.monster.Goblin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Self-contained content module for the Goblin. Owns the entity type, the
 * goblin mace weapon item and the spawn egg, and wires up attribute, creative
 * tab and spawn-placement listeners. Ported from the 1.12 EntityGoblin which
 * spawned underground in groups of three and wielded a goblin mace.
 */
public final class GoblinContent {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PrimitiveMobsMod.MOD_ID);

    /** Goblin metal tier, ported from the 1.12 GOBLIN_METAL tool material. */
    public static final Tier GOBLIN_METAL = new ForgeTier(
        2,            // tier level (mineable level)
        400,          // uses (durability)
        6.0F,         // speed
        2.5F,         // attack damage bonus
        10,           // enchantment value
        net.minecraft.tags.BlockTags.NEEDS_IRON_TOOL, // blocks this can mine
        () -> Ingredient.of(Items.IRON_INGOT));

    public static final RegistryObject<EntityType<Goblin>> GOBLIN = ENTITY_TYPES.register("goblin", () ->
        EntityType.Builder.of(Goblin::new, MobCategory.MONSTER)
            .sized(0.7F, 1.95F)
            .clientTrackingRange(8)
            .build("primitivemobs:goblin"));

    public static final RegistryObject<Item> GOBLIN_MACE = ITEMS.register("goblin_mace",
        () -> new SwordItem(GOBLIN_METAL, 3, -3.0F, new Item.Properties()));

    public static final RegistryObject<Item> GOBLIN_SPAWN_EGG = ITEMS.register("goblin_spawn_egg",
        () -> new ForgeSpawnEggItem(GOBLIN, 0x5b7a3a, 0x2f3d1f, new Item.Properties()));

    private GoblinContent() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        modBus.addListener(GoblinContent::onRegisterAttributes);
        modBus.addListener(GoblinContent::onBuildCreativeTabs);
        modBus.addListener(GoblinContent::onCommonSetup);
    }

    private static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        event.put(GOBLIN.get(), Goblin.createAttributes().build());
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> SpawnPlacements.register(
            GOBLIN.get(),
            SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Monster::checkMonsterSpawnRules));
    }

    private static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
            event.accept(GOBLIN_MACE);
        } else if (event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS)) {
            event.accept(GOBLIN_SPAWN_EGG);
        }
    }
}
