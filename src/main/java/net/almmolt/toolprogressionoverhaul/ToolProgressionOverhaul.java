package net.almmolt.toolprogressionoverhaul;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.gui.ModMenus;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.recipe.ModRecipes;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
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

    // Custom Utility functions, cause I'm lazy
    private void insertAfter(BuildCreativeModeTabContentsEvent event, Item item1, Item item2) {
        event.insertAfter(
                new ItemStack(item1),
                new ItemStack(item2),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
    }

    private void insertThese(BuildCreativeModeTabContentsEvent event, Item afterWhat, List<Item> items) {
        event.insertAfter(
                new ItemStack(afterWhat),
                new ItemStack(items.getFirst()),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
        for (int i = 1; i < items.size(); ++i) {
            event.insertAfter(
                    new ItemStack(items.get(i-1)),
                    new ItemStack(items.get(i)),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }

    private void insertArmorAfter(BuildCreativeModeTabContentsEvent event, Item afterWhat, AMarmor.ArmorSet armorSet) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(
                    new ItemStack(afterWhat),
                    new ItemStack(armorSet.asList().getFirst().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            for (int i = 1; i < armorSet.asList().size(); ++i) {
                event.insertAfter(
                        new ItemStack(armorSet.asList().get(i-1).get()),
                        new ItemStack(armorSet.asList().get(i).get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
        }
    }

    private void insertToolSet(BuildCreativeModeTabContentsEvent event, Item afterWhatSword, Item afterWhatAxe,Item afterWhatTheRest, AMtool.ToolSet toolSet) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(
                    new ItemStack(afterWhatSword),
                    new ItemStack(toolSet.swordItem().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            event.insertAfter(
                    new ItemStack(afterWhatAxe),
                    new ItemStack(toolSet.axeItem().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
        else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            List<DeferredItem<? extends Item>> tools = toolSet.asList();

            event.insertAfter(
                    new ItemStack(afterWhatTheRest),
                    new ItemStack(tools.get(1).get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            for (int i = 2; i < tools.size()-1; ++i) {
                event.insertAfter(
                        new ItemStack(tools.get(i-1).get()),
                        new ItemStack(tools.get(i).get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }

            event.insertAfter(
                    new ItemStack(toolSet.hoeItem().get()),
                    new ItemStack(toolSet.hammerItem().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }
    //

    // Add the example AMblock item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        insertToolSet(
                event,
                Items.STONE_SWORD,
                Items.STONE_AXE,
                Items.STONE_HOE,
                ModItems.BRONZE_TOOLS
        );
        insertToolSet(
                event,
                ModItems.BRONZE_TOOLS.swordItem().get(),
                ModItems.BRONZE_TOOLS.axeItem().get(),
                ModItems.BRONZE_TOOLS.hoeItem().get(),
                ModItems.SILVER_TOOLS
        );
        insertToolSet(
                event,
                ModItems.SILVER_TOOLS.swordItem().get(),
                ModItems.SILVER_TOOLS.axeItem().get(),
                ModItems.SILVER_TOOLS.hoeItem().get(),
                ModItems.INVAR_TOOLS
        );
        insertArmorAfter(
                event,
                Items.IRON_BOOTS,
                ModItems.BRONZE_ARMOR
        );
        insertArmorAfter(
                event,
                ModItems.BRONZE_ARMOR.bootsItem().get(),
                ModItems.SILVER_ARMOR
        );
        insertArmorAfter(
                event,
                ModItems.SILVER_ARMOR.bootsItem().get(),
                ModItems.INVAR_ARMOR
        );

        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            insertAfter(event,Blocks.COPPER_ORE.asItem(),ModBlocks.TIN_ORE.blockItem().get());
            insertAfter(event,Blocks.DEEPSLATE_COPPER_ORE.asItem(),ModBlocks.DEEPSLATE_TIN_ORE.blockItem().get());
            insertThese(event,Blocks.RAW_COPPER_BLOCK.asItem(),List.of(
                ModBlocks.RAW_TIN_BLOCK.blockItem().get(),
                ModBlocks.RAW_NICKEL_BLOCK.blockItem().get(),
                ModBlocks.RAW_SILVER_BLOCK.blockItem().get()
            ));
            insertAfter(event,ModBlocks.TIN_ORE.blockItem().get(),ModBlocks.NICKEL_ORE.blockItem().get());
            insertAfter(event,ModBlocks.DEEPSLATE_TIN_ORE.blockItem().get(),ModBlocks.DEEPSLATE_NICKEL_ORE.blockItem().get());
            insertAfter(event,ModBlocks.NICKEL_ORE.blockItem().get(),ModBlocks.SILVER_ORE.blockItem().get());
            insertAfter(event,ModBlocks.DEEPSLATE_NICKEL_ORE.blockItem().get(),ModBlocks.DEEPSLATE_SILVER_ORE.blockItem().get());
        }
        else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            insertThese(event, Blocks.COPPER_BLOCK.asItem(), List.of(
                    ModBlocks.TIN_BLOCK.blockItem().get(),
                    ModBlocks.BRONZE_BLOCK.blockItem().get(),
                    ModBlocks.NICKEL_BLOCK.blockItem().get(),
                    ModBlocks.SILVER_BLOCK.blockItem().get(),
                    ModBlocks.INVAR_BLOCK.blockItem().get()
            ));
        }
        else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertAfter(event,Blocks.BLAST_FURNACE.asItem(),ModBlocks.ALLOYING_SMELTER_ASITEM.get());
            insertAfter(event,ModBlocks.ALLOYING_SMELTER_ASITEM.get(),ModBlocks.CRUSHER.get().asItem());
        }
        else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            insertThese(event,Items.COPPER_INGOT,List.of(
                    ModItems.TIN_INGOT.get(),
                    ModItems.BRONZE_INGOT.get(),
                    ModItems.SILVER_INGOT.get(),
                    ModItems.NICKEL_INGOT.get(),
                    ModItems.INVAR_INGOT.get(),
                    ModItems.IRON_CRUSHING_WHEEL.get(),
                    ModItems.SILVER_CRUSHING_WHEEL.get()
            ));

            insertThese(event, Items.RAW_COPPER,List.of(
                    ModItems.RAW_TIN.get(),
                    ModItems.RAW_NICKEL.get(),
                    ModItems.RAW_SILVER.get()
            ));

            insertThese(event,Items.NETHERITE_INGOT,List.of(
                    ModItems.TIN_DUST.get(),
                    ModItems.COPPER_DUST.get(),
                    ModItems.BRONZE_DUST.get(),
                    ModItems.SILVER_DUST.get(),
                    ModItems.NICKEL_DUST.get(),
                    ModItems.INVAR_DUST.get(),
                    ModItems.IRON_DUST.get(),
                    ModItems.COAL_DUST.get()
            ));
        }
//        else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
//        }
        else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            insertAfter(event,Items.IRON_HOE,ModItems.IRON_HAMMER.get());
            insertAfter(event,Items.STONE_HOE,ModItems.STONE_HAMMER.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
    }
}
