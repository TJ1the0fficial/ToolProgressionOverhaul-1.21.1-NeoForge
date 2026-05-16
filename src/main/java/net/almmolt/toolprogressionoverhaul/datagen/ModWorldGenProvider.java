package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.worldgen.ModConfiguredFeatures;
import net.almmolt.toolprogressionoverhaul.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModWorldGenProvider::bootstrapConfigured)
            .add(Registries.PLACED_FEATURE, ModWorldGenProvider::bootstrapPlaced)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModWorldGenProvider::bootstrapBiomeModifiers); // ADD THIS

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ToolProgressionOverhaul.MODID));
    }

    // "WHAT" generates
    private static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        // TIN TARGETS
        List<OreConfiguration.TargetBlockState> tinTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TIN_ORE.block().get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_TIN_ORE.block().get().defaultBlockState()));

        // NICKEL TARGETS
        List<OreConfiguration.TargetBlockState> nickelTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.NICKEL_ORE.block().get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_NICKEL_ORE.block().get().defaultBlockState()));

        // SILVER TARGETS
        List<OreConfiguration.TargetBlockState> silverTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SILVER_ORE.block().get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_SILVER_ORE.block().get().defaultBlockState()));

        // Register them with their OWN targets
        context.register(ModConfiguredFeatures.TIN_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(tinTargets, 9)));
        context.register(ModConfiguredFeatures.NICKEL_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(nickelTargets, 9)));
        context.register(ModConfiguredFeatures.SILVER_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(silverTargets, 8)));
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

        context.register(ModPlacedFeatures.NICKEL_ORE_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.NICKEL_ORE_KEY),
                List.of(
                        CountPlacement.of(5), // 10 veins per chunk
                        InSquarePlacement.spread(), // Random X/Z
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(150)), // Y level
                        BiomeFilter.biome() // Only in valid biomes
                )
        ));

        context.register(ModPlacedFeatures.SILVER_ORE_PLACED_KEY, new PlacedFeature(
                configuredFeatures.getOrThrow(ModConfiguredFeatures.SILVER_ORE_KEY),
                List.of(
                        CountPlacement.of(3), // 10 veins per chunk
                        InSquarePlacement.spread(), // Random X/Z
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(70)), // Y level
                        BiomeFilter.biome() // Only in valid biomes
                )
        ));
    }

    private static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        // 1. Modifier for TIN
        context.register(ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "add_tin_ore")),
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.TIN_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );

        // 2. Modifier for NICKEL
        context.register(ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "add_nickel_ore")),
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NICKEL_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );

        // 3. Modifier for SILVER
        context.register(ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                        ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "add_silver_ore")),
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SILVER_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES
                )
        );
    }
}