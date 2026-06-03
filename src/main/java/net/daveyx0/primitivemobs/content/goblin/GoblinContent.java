package net.daveyx0.primitivemobs.content.goblin;

import net.daveyx0.primitivemobs.entity.monster.Goblin;
import net.daveyx0.primitivemobs.registry.RegistryHelper;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

/**
 * Self-contained content module for the Goblin. Owns the entity type, the goblin
 * mace weapon and the spawn egg. Ported from the 1.12 EntityGoblin which spawned
 * underground in groups of three and wielded a goblin mace.
 */
public final class GoblinContent {
    private static final RegistryHelper REG = new RegistryHelper();

    /** Goblin metal tier, ported from the 1.12 GOBLIN_METAL tool material. */
    public static final Tier GOBLIN_METAL = new ForgeTier(
        2, 400, 6.0F, 2.5F, 10, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));

    public static final RegistryObject<EntityType<Goblin>> GOBLIN =
        REG.entity("goblin", Goblin::new, MobCategory.MONSTER, b -> b.sized(0.7F, 1.95F).clientTrackingRange(8));

    public static final RegistryObject<Item> GOBLIN_MACE =
        REG.item("goblin_mace", () -> new SwordItem(GOBLIN_METAL, 3, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> GOBLIN_SPAWN_EGG =
        REG.spawnEgg("goblin_spawn_egg", GOBLIN, 0x5b7a3a, 0x2f3d1f);

    private GoblinContent() {
    }

    public static void register(IEventBus modBus) {
        REG.register(modBus);
        RegistryHelper.attributes(modBus, GOBLIN, Goblin::createAttributes);
        RegistryHelper.spawnPlacement(modBus, GOBLIN, SpawnPlacements.Type.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.COMBAT, GOBLIN_MACE);
        RegistryHelper.creativeTab(modBus, CreativeModeTabs.SPAWN_EGGS, GOBLIN_SPAWN_EGG);
    }
}
