package net.daveyx0.primitivemobs.registry;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
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
 * Per-module registration helper that removes the boilerplate from each
 * {@code content/<mob>/<Mob>Content} class while preserving the self-contained
 * module pattern: every module owns its own {@code RegistryHelper} instance (and
 * therefore its own {@link DeferredRegister}s), so mobs can be added in parallel
 * without sharing a registry file.
 *
 * <p>Instance methods declare registry objects; the {@code static} helpers wire up
 * the mod-bus event listeners (attributes, spawn placement, creative tabs).
 *
 * <pre>{@code
 * private static final RegistryHelper REG = new RegistryHelper();
 * public static final RegistryObject<EntityType<Foo>> FOO =
 *     REG.entity("foo", Foo::new, MobCategory.CREATURE, b -> b.sized(0.7F, 0.7F).clientTrackingRange(8));
 * public static final RegistryObject<Item> FOO_SPAWN_EGG = REG.spawnEgg("foo_spawn_egg", FOO, 0xAABBCC, 0x112233);
 *
 * public static void register(IEventBus bus) {
 *     REG.register(bus);
 *     RegistryHelper.attributes(bus, FOO, Foo::createAttributes);
 *     RegistryHelper.spawnPlacement(bus, FOO, SpawnPlacements.Type.ON_GROUND,
 *         Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Foo::checkSpawnRules);
 *     RegistryHelper.creativeTab(bus, CreativeModeTabs.SPAWN_EGGS, FOO_SPAWN_EGG);
 * }
 * }</pre>
 */
public final class RegistryHelper {
    private final DeferredRegister<Item> items =
        DeferredRegister.create(ForgeRegistries.ITEMS, PrimitiveMobsMod.MOD_ID);
    private final DeferredRegister<EntityType<?>> entityTypes =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PrimitiveMobsMod.MOD_ID);

    /** Registers this module's item and entity-type registers to the mod bus. */
    public void register(IEventBus modBus) {
        items.register(modBus);
        entityTypes.register(modBus);
    }

    // --- declarations -------------------------------------------------------

    public RegistryObject<Item> item(String name, Supplier<? extends Item> factory) {
        return items.register(name, factory);
    }

    public <T extends Mob> RegistryObject<Item> spawnEgg(String name, Supplier<EntityType<T>> type, int background, int highlight) {
        return items.register(name, () -> new ForgeSpawnEggItem(type, background, highlight, new Item.Properties()));
    }

    public <T extends Entity> RegistryObject<EntityType<T>> entity(
            String name,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            UnaryOperator<EntityType.Builder<T>> builder) {
        return entityTypes.register(name, () ->
            builder.apply(EntityType.Builder.of(factory, category)).build(PrimitiveMobsMod.MOD_ID + ":" + name));
    }

    // --- mod-bus wiring -----------------------------------------------------

    /** Registers entity attributes on {@link EntityAttributeCreationEvent}. */
    public static void attributes(IEventBus modBus,
            Supplier<? extends EntityType<? extends LivingEntity>> type,
            Supplier<net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder> attributes) {
        modBus.addListener((EntityAttributeCreationEvent event) -> event.put(type.get(), attributes.get().build()));
    }

    /** Registers a spawn placement during common setup (the correct, deferred time). */
    public static <T extends Mob> void spawnPlacement(IEventBus modBus,
            Supplier<EntityType<T>> type,
            SpawnPlacements.Type placement,
            Heightmap.Types heightmap,
            SpawnPlacements.SpawnPredicate<T> predicate) {
        modBus.addListener((FMLCommonSetupEvent event) ->
            event.enqueueWork(() -> SpawnPlacements.register(type.get(), placement, heightmap, predicate)));
    }

    /** Adds the given entries to a creative tab on {@link BuildCreativeModeTabContentsEvent}. */
    @SafeVarargs
    public static void creativeTab(IEventBus modBus, ResourceKey<CreativeModeTab> tab, RegistryObject<? extends ItemLike>... entries) {
        modBus.addListener((BuildCreativeModeTabContentsEvent event) -> {
            if (event.getTabKey().equals(tab)) {
                for (RegistryObject<? extends ItemLike> entry : entries) {
                    event.accept(entry.get());
                }
            }
        });
    }

}
