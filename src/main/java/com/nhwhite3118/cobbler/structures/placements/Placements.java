package com.nhwhite3118.cobbler.structures.placements;

import com.nhwhite3118.cobbler.Cobbler;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;

public class Placements {
    public static final Placement<CountRangeConfig> SHULKER_FACTORY_PLACEMENT = new ShulkerFactoryPlacement(CountRangeConfig.field_236485_a_);

    public static void registerPlacements(final RegistryEvent.Register<Placement<?>> event) {
        Registry.register(Registry.DECORATOR, Cobbler.MODID + ":shulker_factory_placement", SHULKER_FACTORY_PLACEMENT);
    }
}
