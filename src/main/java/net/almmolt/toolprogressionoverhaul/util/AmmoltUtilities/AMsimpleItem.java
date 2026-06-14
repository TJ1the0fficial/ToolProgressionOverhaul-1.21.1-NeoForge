package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class AMsimpleItem {
    public static final HashMap<Supplier<Item>, String> registeredItems = new HashMap<>();
    public static final HashMap<crushingWheel, String> registeredWheels = new HashMap<>();
    public static final HashMap<Supplier<Item>, String> registeredDusts = new HashMap<>();

    public static DeferredItem<Item> registerItem(String id, String displayName) {
        DeferredItem<Item> ITEM = ModItems.ITEMS.register(
                id, () -> new Item(new Item.Properties())
        );
        registeredItems.put(ITEM, displayName);
        return ITEM;
    }

    public record crushingWheel(Supplier<Item> item, String id, String displayName, int tier) {
        public static int getTierByItem(Item item) {
            for (crushingWheel wheel : registeredWheels.keySet()) {
                if (AMrecipe.itemId(wheel.item().get()).equals(AMrecipe.itemId(item))) {
                    return wheel.tier();
                }
            }
            return 1;
        }
    }

    public static DeferredItem<Item> registerWheel(String id, String displayName, int tier) {
        DeferredItem<Item> ITEM = ModItems.ITEMS.register(
                id, () -> new Item(new Item.Properties())
        );
        registeredItems.put(ITEM, displayName);
        registeredWheels.put(new crushingWheel(ITEM,id,displayName,tier), displayName);
        return ITEM;
    }

    // register dusts, there are vanilla and modded materials used for dusts, here come the problems
    public static DeferredItem<Item> registerDust(String id, String displayName) {
        DeferredItem<Item> ITEM = ModItems.ITEMS.register(
                id, () -> new Item(new Item.Properties())
        );
        registeredItems.put(ITEM, displayName);
        registeredDusts.put(ITEM, id);
        return ITEM;
    }
}
