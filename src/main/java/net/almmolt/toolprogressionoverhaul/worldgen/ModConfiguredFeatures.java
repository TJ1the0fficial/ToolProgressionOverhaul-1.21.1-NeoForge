package net.almmolt.toolprogressionoverhaul.worldgen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

// World Gen code was all done by AI, cause I'm lazy to do all that just to generate a couple of blocks

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE_KEY = registerKey("tin_ore");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name));
    }
}
