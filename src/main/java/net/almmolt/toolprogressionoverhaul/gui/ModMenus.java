package net.almmolt.toolprogressionoverhaul.gui;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterMenu;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(
            BuiltInRegistries.MENU,
            ToolProgressionOverhaul.MODID
    );

    public static final Supplier<MenuType<AlloyingSmelterMenu>> ALLOYING_SMELTER_MENU = MENUS.register(
            "alloying_smelter_menu",
            () -> new MenuType(
                    AlloyingSmelterMenu::new,
                    FeatureFlags.DEFAULT_FLAGS
            )
    );

    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.ALLOYING_SMELTER_MENU.get(), AlloyingSmelterScreen::new);
    }
}