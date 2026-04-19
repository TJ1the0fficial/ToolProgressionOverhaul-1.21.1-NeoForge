package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, existingFileHelper, List.of(new ModAdvancementGenerator()));
    }

    private static final class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            // 1. Create the Root (or child of a vanilla/external root)
            AdvancementHolder root = createTask(
                    saver, existingFileHelper,
                    "road_to_growth",
                    ModItems.RAW_TIN.get(),
                    null // Passing null here to trigger the root placeholder logic
            );

            // 2. Pass the 'root' holder as the parent
            AdvancementHolder tinIngot = createTask(
                    saver, existingFileHelper,
                    "tin_ingot",
                    ModItems.TIN_INGOT.get(),
                    root
            );

            // 3. Pass the 'tinIngot' holder as the parent
            AdvancementHolder bronzeIngot = createGoal(
                    saver, existingFileHelper,
                    "bronze_ingot",
                    ModItems.BRONZE_INGOT.get(),
                    tinIngot
            );
        }
    }

    public static AdvancementHolder createTask(
            Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, // neccesary
            String name,
            Item item,
            AdvancementHolder parent
    ) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        // Handle Parent Logic
        if (parent != null) {
            builder.parent(parent);
        } else {
            // Note: If 'story/root' is NOT a real file yet, use .addCriterion and display
            // settings on this advancement to make IT the root.
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.withDefaultNamespace("textures/block/stone.png"), // Root needs a background
                    AdvancementType.TASK,
                    true, true, false
            );
        }

        // Display settings (only if not already set by root logic above)
        if (parent != null) {
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    null,
                    AdvancementType.TASK,
                    true, true, false
            );
        }

        builder.rewards(AdvancementRewards.Builder.experience(100));

        // Use a proper trigger - using Items.DIRT is fine for testing, but
        // usually you'd want 'has_item' for the item passed in the parameters
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item));

        // BUILD AND SAVE
        // builder.save returns the AdvancementHolder automatically!
        // No need for final arrays or double calling.
        return builder.save(saver, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name), existingFileHelper);
    }

    public static AdvancementHolder createGoal(
            Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, // neccesary
            String name,
            Item item,
            AdvancementHolder parent
    ) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        // Handle Parent Logic
        if (parent != null) {
            builder.parent(parent);
        } else {
            // Note: If 'story/root' is NOT a real file yet, use .addCriterion and display
            // settings on this advancement to make IT the root.
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.withDefaultNamespace("textures/block/stone.png"), // Root needs a background
                    AdvancementType.GOAL,
                    true, true, false
            );
        }

        // Display settings (only if not already set by root logic above)
        if (parent != null) {
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    null,
                    AdvancementType.GOAL,
                    true, true, false
            );
        }

        builder.rewards(AdvancementRewards.Builder.experience(100));

        // Use a proper trigger - using Items.DIRT is fine for testing, but
        // usually you'd want 'has_item' for the item passed in the parameters
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item));

        // BUILD AND SAVE
        // builder.save returns the AdvancementHolder automatically!
        // No need for final arrays or double calling.
        return builder.save(saver, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name), existingFileHelper);
    }

    public static AdvancementHolder createChallenge(
            Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper, // neccesary
            String name,
            Item item,
            AdvancementHolder parent
    ) {
        Advancement.Builder builder = Advancement.Builder.advancement();

        // Handle Parent Logic
        if (parent != null) {
            builder.parent(parent);
        } else {
            // Note: If 'story/root' is NOT a real file yet, use .addCriterion and display
            // settings on this advancement to make IT the root.
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    ResourceLocation.withDefaultNamespace("textures/block/stone.png"), // Root needs a background
                    AdvancementType.CHALLENGE,
                    true, true, false
            );
        }

        // Display settings (only if not already set by root logic above)
        if (parent != null) {
            builder.display(
                    new ItemStack(item),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".title"),
                    Component.translatable("advancements." + ToolProgressionOverhaul.MODID + "." + name + ".description"),
                    null,
                    AdvancementType.CHALLENGE,
                    true, true, false
            );
        }

        builder.rewards(AdvancementRewards.Builder.experience(100));

        // Use a proper trigger - using Items.DIRT is fine for testing, but
        // usually you'd want 'has_item' for the item passed in the parameters
        builder.addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(item));

        // BUILD AND SAVE
        // builder.save returns the AdvancementHolder automatically!
        // No need for final arrays or double calling.
        return builder.save(saver, ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, name), existingFileHelper);
    }
}
