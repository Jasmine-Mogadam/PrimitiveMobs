package net.daveyx0.primitivemobs.registry;

import net.daveyx0.primitivemobs.PrimitiveMobsMod;
import net.daveyx0.primitivemobs.entity.passive.Dodo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class PrimitiveMobsEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PrimitiveMobsMod.MOD_ID);

    public static final RegistryObject<EntityType<Dodo>> DODO = ENTITY_TYPES.register("dodo", () ->
        EntityType.Builder.of(Dodo::new, MobCategory.CREATURE)
            .sized(0.75F, 0.75F)
            .clientTrackingRange(8)
            .build("primitivemobs:dodo")
    );

    private PrimitiveMobsEntityTypes() {
    }

    public static void register(IEventBus modBus) {
        ENTITY_TYPES.register(modBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DODO.get(), Chicken.createAttributes().build());
    }
}
