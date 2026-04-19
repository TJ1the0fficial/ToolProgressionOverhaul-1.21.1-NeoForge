package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.worldgen.ModConfiguredFeatures;
import net.almmolt.toolprogressionoverhaul.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModWorldGenProvider::bootstrapConfigured)
            .add(Registries.PLACED_FEATURE, ModWorldGenProvider::bootstrapPlaced);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ToolProgressionOverhaul.MODID));
    }

    // "WHAT" generates
    private static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // Define what blocks can be replaced (Stone, Deepslate)
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TIN_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_TIN_ORE.get().defaultBlockState())
        );

        // Vein size of 9
        context.register(ModConfiguredFeatures.TIN_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targets, 9)));
    }

    // "WHERE" it generates
    private static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(ModPlacedFeatures.TIN_ORE_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.TIN_ORE_KEY),
                List.of(
                        CountPlacement.of(10), // 10 veins per chunk
                        InSquarePlacement.spread(), // Random X/Z
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)), // Y level
                        BiomeFilter.biome() // Only in valid biomes
                )
        ));
    }
}