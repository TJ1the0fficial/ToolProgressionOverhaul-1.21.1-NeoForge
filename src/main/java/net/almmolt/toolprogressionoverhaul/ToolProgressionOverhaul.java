package net.almmolt.toolprogressionoverhaul;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.compat.jade.ITPOJadePlugin;
import net.almmolt.toolprogressionoverhaul.gui.ModMenus;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.item.grades.*;
import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMcreativetab;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ToolProgressionOverhaul.MODID)
public class ToolProgressionOverhaul {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "toolprogressionoverhaul";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ToolProgressionOverhaul(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ToolProgressionOverhaul) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModRecipes.register(modEventBus);

        // Register all screens
        modEventBus.addListener(ModMenus::registerScreens);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example AMblock item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // ITEM --------------------------
        // put bronze after stone
        AMcreativetab.insertToolSet(
                event,
                Items.STONE_SWORD,
                Items.STONE_AXE,
                Items.STONE_HOE,
                Bronze.BRONZE_TOOLS
        );
        // put silver after bronze
        AMcreativetab.insertToolSet(
                event,
                Bronze.BRONZE_TOOLS,
                Silver.SILVER_TOOLS
        );
        // put invar after silver
        AMcreativetab.insertToolSet(
                event,
                Silver.SILVER_TOOLS,
                Invar.INVAR_TOOLS
        );
        // put invar after silver
        AMcreativetab.insertToolSet(
                event,
                Items.GOLDEN_SWORD,
                Items.GOLDEN_AXE,
                Items.GOLDEN_HOE,
                GalvanizedIron.GALVANIZED_IRON_TOOLS
        );
        // put invar after silver
        AMcreativetab.insertToolSet(
                event,
                GalvanizedIron.GALVANIZED_IRON_TOOLS,
                WootzSteel.WOOTZ_STEEL_TOOLS
        );
        // put invar after silver
        AMcreativetab.insertToolSet(
                event,
                WootzSteel.WOOTZ_STEEL_TOOLS,
                Steel.STEEL_TOOLS
        );

        // ARMOR ------------------------
        // put bronze armor after leather
        AMcreativetab.insertArmorAfter(
                event,
                Items.LEATHER_BOOTS,
                Bronze.BRONZE_ARMOR
        );
        // put silver armor after bronze
        AMcreativetab.insertArmorAfter(
                event,
                Bronze.BRONZE_ARMOR,
                Silver.SILVER_ARMOR
        );
        // put invar armor after silver
        AMcreativetab.insertArmorAfter(
                event,
                Silver.SILVER_ARMOR,
                Invar.INVAR_ARMOR
        );
        // put galvanized iron armor after invar
        AMcreativetab.insertArmorAfter(
                event,
                Items.IRON_BOOTS,
                GalvanizedIron.GALVANIZED_IRON_ARMOR
        );
        // put wootz steel armor after galvanized iron
        AMcreativetab.insertArmorAfter(
                event,
                GalvanizedIron.GALVANIZED_IRON_ARMOR,
                WootzSteel.WOOTZ_STEEL_ARMOR
        );
        // put steel armor after wootz steel
        AMcreativetab.insertArmorAfter(
                event,
                WootzSteel.WOOTZ_STEEL_ARMOR,
                Steel.STEEL_ARMOR
        );

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            AMcreativetab.insertAfter(event,Blocks.COPPER_ORE.asItem(),ModBlocks.TIN_ORE.blockItem().get());
            AMcreativetab.insertAfter(event,Blocks.DEEPSLATE_COPPER_ORE.asItem(),ModBlocks.DEEPSLATE_TIN_ORE.blockItem().get());
            AMcreativetab.insertThese(event,Blocks.RAW_COPPER_BLOCK.asItem(),List.of(
                ModBlocks.RAW_TIN_BLOCK.blockItem().get(),
                ModBlocks.RAW_NICKEL_BLOCK.blockItem().get(),
                ModBlocks.RAW_SILVER_BLOCK.blockItem().get(),
                ModBlocks.RAW_ZINC_BLOCK.blockItem().get()
            ));
            AMcreativetab.insertAfter(event,ModBlocks.TIN_ORE.blockItem().get(),ModBlocks.NICKEL_ORE.blockItem().get());
            AMcreativetab.insertAfter(event,ModBlocks.DEEPSLATE_TIN_ORE.blockItem().get(),ModBlocks.DEEPSLATE_NICKEL_ORE.blockItem().get());
            AMcreativetab.insertAfter(event,ModBlocks.NICKEL_ORE.blockItem().get(),ModBlocks.SILVER_ORE.blockItem().get());
            AMcreativetab.insertAfter(event,ModBlocks.DEEPSLATE_NICKEL_ORE.blockItem().get(),ModBlocks.DEEPSLATE_SILVER_ORE.blockItem().get());
            AMcreativetab.insertAfter(event,ModBlocks.SILVER_ORE.blockItem().get(),ModBlocks.ZINC_ORE.blockItem().get());
            AMcreativetab.insertAfter(event,ModBlocks.DEEPSLATE_SILVER_ORE.blockItem().get(),ModBlocks.DEEPSLATE_ZINC_ORE.blockItem().get());
        }
        else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            AMcreativetab.insertThese(event, Blocks.COPPER_BLOCK.asItem(), List.of(
                    ModBlocks.TIN_BLOCK.blockItem().get(),
                    ModBlocks.BRONZE_BLOCK.blockItem().get(),
                    ModBlocks.NICKEL_BLOCK.blockItem().get(),
                    ModBlocks.SILVER_BLOCK.blockItem().get(),
                    ModBlocks.INVAR_BLOCK.blockItem().get(),
                    ModBlocks.ZINC_BLOCK.blockItem().get(),
                    ModBlocks.GALVANIZED_IRON_BLOCK.blockItem().get(),
                    ModBlocks.WOOTZ_STEEL_BLOCK.blockItem().get()
            ));
        }
        else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            AMcreativetab.insertAfter(event,Blocks.BLAST_FURNACE.asItem(),ModBlocks.ALLOYING_SMELTER_ASITEM.get());
            AMcreativetab.insertAfter(event,ModBlocks.ALLOYING_SMELTER_ASITEM.get(),ModBlocks.CRUSHER.get().asItem());
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            AMcreativetab.insertThese(event,Items.COPPER_INGOT,List.of(
                    ModItems.TIN_INGOT.get(),
                    ModItems.BRONZE_INGOT.get(),
                    ModItems.SILVER_INGOT.get(),
                    ModItems.NICKEL_INGOT.get(),
                    ModItems.INVAR_INGOT.get(),
                    ModItems.ZINC_INGOT.get(),
                    ModItems.GALVANIZED_IRON_INGOT.get(),
                    ModItems.WOOTZ_STEEL_INGOT.get(),
                    ModItems.STEEL_INGOT.get(),
                    ModItems.IRON_CRUSHING_WHEEL.get(),
                    ModItems.WOOTZ_STEEL_CRUSHING_WHEEL.get()
            ));

            AMcreativetab.insertThese(event, Items.RAW_COPPER,List.of(
                    ModItems.RAW_TIN.get(),
                    ModItems.RAW_NICKEL.get(),
                    ModItems.RAW_SILVER.get(),
                    ModItems.RAW_ZINC.get()
            ));

            AMcreativetab.insertThese(event,Items.NETHERITE_INGOT,List.of(
                    ModItems.TIN_DUST.get(),
                    ModItems.COPPER_DUST.get(),
                    ModItems.BRONZE_DUST.get(),
                    ModItems.SILVER_DUST.get(),
                    ModItems.NICKEL_DUST.get(),
                    ModItems.INVAR_DUST.get(),
                    ModItems.IRON_DUST.get(),
                    ModItems.GOLD_DUST.get(),
                    ModItems.COAL_DUST.get(),
                    ModItems.SAND_FLUX_DUST.get(),
                    ModItems.BONE_FLUX_DUST.get(),
                    ModItems.CALCITE_FLUX_DUST.get(),
                    ModItems.GALVANIZED_IRON_DUST.get(),
                    ModItems.WOOTZ_STEEL_DUST.get(),
                    ModItems.STEEL_DUST.get()
            ));

            AMcreativetab.insertAfter(
                    event,
                    Items.CHARCOAL,
                    ModItems.COKE.get()
            );
        }
//        else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
//        }
        else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            AMcreativetab.insertAfter(event,Items.IRON_HOE,ModItems.IRON_HAMMER.get());
            AMcreativetab.insertAfter(event,Items.STONE_HOE,ModItems.STONE_HAMMER.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }
}
