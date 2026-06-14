package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public class AMcreativetab {
    public static void insertAfter(BuildCreativeModeTabContentsEvent event, Item item1, Item item2) {
        event.insertAfter(
                new ItemStack(item1),
                new ItemStack(item2),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
    }

    public static void insertThese(BuildCreativeModeTabContentsEvent event, Item afterWhat, List<Item> items) {
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

    public static void insertArmorAfter(BuildCreativeModeTabContentsEvent event, Item afterWhat, AMarmor.ArmorSet armorSet) {
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

    public static void insertArmorAfter(BuildCreativeModeTabContentsEvent event, AMarmor.ArmorSet fromArmorSet, AMarmor.ArmorSet toArmorSet) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(
                    new ItemStack(fromArmorSet.bootsItem().get()),
                    new ItemStack(toArmorSet.asList().getFirst().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            for (int i = 1; i < toArmorSet.asList().size(); ++i) {
                event.insertAfter(
                        new ItemStack(toArmorSet.asList().get(i-1).get()),
                        new ItemStack(toArmorSet.asList().get(i).get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
        }
    }

    public static void insertToolSet(BuildCreativeModeTabContentsEvent event, Item afterWhatSword, Item afterWhatAxe, Item afterWhatTheRest, AMtool.ToolSet toolSet) {
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

    public static void insertToolSet(BuildCreativeModeTabContentsEvent event, AMtool.ToolSet fromToolSet, AMtool.ToolSet toToolSet) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(
                    new ItemStack(fromToolSet.swordItem().get()),
                    new ItemStack(toToolSet.swordItem().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
            event.insertAfter(
                    new ItemStack(fromToolSet.axeItem().get()),
                    new ItemStack(toToolSet.axeItem().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
        else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            List<DeferredItem<? extends Item>> tools = toToolSet.asList();

            event.insertAfter(
                    new ItemStack(fromToolSet.hoeItem().get()),
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
                    new ItemStack(toToolSet.hoeItem().get()),
                    new ItemStack(toToolSet.hammerItem().get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }
}
