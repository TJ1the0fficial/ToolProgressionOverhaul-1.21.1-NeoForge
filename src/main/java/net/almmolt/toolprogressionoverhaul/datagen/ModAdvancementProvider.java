package net.almmolt.toolprogressionoverhaul.datagen;

import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.advancements.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMadvancement.createTask;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMadvancement.createGoal;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMadvancement.createChallenge;

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
            AdvancementHolder bronzeIngot = createTask(
                    saver, existingFileHelper,
                    "bronze_ingot",
                    ModItems.BRONZE_INGOT.get(),
                    tinIngot
            );

            AdvancementHolder bronzeHammer = createTask(
                    saver, existingFileHelper,
                    "bronze_hammer",
                    ModItems.BRONZE_TOOLS.hammerItem().get(),
                    bronzeIngot
            );

            AdvancementHolder alloyingSmelter = createTask(
                    saver, existingFileHelper,
                    "alloying_smelter",
                    ModBlocks.ALLOYING_SMELTER_ASITEM.get(),
                    bronzeHammer
            );

            AdvancementHolder ironIngot = createTask(
                    saver, existingFileHelper,
                    "iron_ingot",
                    Items.IRON_INGOT,
                    bronzeIngot
            );

            AdvancementHolder rawNickel = createTask(
                    saver, existingFileHelper,
                    "raw_nickel",
                    ModItems.RAW_NICKEL.get(),
                    ironIngot
            );

            AdvancementHolder invarIngot = createTask(
                    saver, existingFileHelper,
                    "invar_ingot",
                    ModItems.INVAR_INGOT.get(),
                    rawNickel
            );

            AdvancementHolder silverIngot = createTask(
                    saver, existingFileHelper,
                    "silver_ingot",
                    ModItems.SILVER_INGOT.get(),
                    ironIngot
            );

            AdvancementHolder crusher = createTask(
                    saver, existingFileHelper,
                    "crusher",
                    ModBlocks.CRUSHER.get().asItem(),
                    invarIngot
            );

            AdvancementHolder crusherWheels = createTask(
                    saver, existingFileHelper,
                    "crusher_wheels",
                    ModItems.IRON_CRUSHING_WHEEL.get(),
                    invarIngot
            );
        }
    }
}
