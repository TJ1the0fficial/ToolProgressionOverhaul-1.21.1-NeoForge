package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.tag.ModBlockTagsProvider;
import net.almmolt.toolprogressionoverhaul.tag.ModItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ToolProgressionOverhaul.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new PixelatorProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));
        generator.addProvider(event.includeServer(), new ModBlockModelProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemModelProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput,lookupProvider));
        generator.addProvider(event.includeServer(), new en_us_ModLanguageProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));

        // AI Code Ah, NeoForge site didn't mention how to get contentsGetter()
        // 1. Create the BlockTagsProvider and store it in a variable
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        // 2. Add the BlockTagsProvider
        generator.addProvider(event.includeServer(), blockTagsProvider);

        // 3. Pass the blockTagsProvider.contentsGetter() to the ItemTagsProvider
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter()));
    }
}
