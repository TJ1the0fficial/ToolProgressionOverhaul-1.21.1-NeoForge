package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.item.custom.HammerItem;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class AMtool {
    public static float setAttackSpeed(float attackSpeed) {
        return -(4.0f - attackSpeed);
    }

    public static float setAttackDamage(float attackDamage) {
        return -(2.5f - attackDamage);
    }

    public static float setSwordAttackDamage(float attackDamage) {
        return -(2.5f - attackDamage)-1.0f;
    }

    public static final List<ToolSet> registeredToolSets = new ArrayList<>();

    public record ToolSet(
            DeferredItem<SwordItem> swordItem, String swordId, String swordDisplayName,
            DeferredItem<ShovelItem> shovelItem, String shovelId, String shovelDisplayName,
            DeferredItem<PickaxeItem> pickaxeItem, String pickaxeId, String pickaxeDisplayName,
            DeferredItem<AxeItem> axeItem, String axeId, String axeDisplayName,
            DeferredItem<HoeItem> hoeItem, String hoeId, String hoeDisplayName,
            DeferredItem<HammerItem> hammerItem, String hammerId, String hammerDisplayName
    ) {
        public List<DeferredItem<? extends Item>> asList() {
            return List.of(swordItem, shovelItem, pickaxeItem, axeItem, hoeItem, hammerItem);
        }

        public List<String> DisplayNamesAsList() {
            return List.of(swordDisplayName, shovelDisplayName, pickaxeDisplayName, axeDisplayName, hoeDisplayName, hammerDisplayName);
        }
    }

    public static ToolSet registerTools(
            String tierId, String tierDisplayName, Tier tier,
            float swordAttackDamage, float swordAttackSpeed,
            float shovelAttackDamage, float shovelAttackSpeed,
            float pickaxeAttackDamage, float pickaxeAttackSpeed,
            float axeAttackDamage, float axeAttackSpeed,
            float hoeAttackDamage, float hoeAttackSpeed,
            float hammerAttackDamage, float hammerAttackSpeed
    ) {
        DeferredItem<ShovelItem> SHOVEL = ModItems.ITEMS.register(
                tierId+"_shovel",
                () -> new ShovelItem(
                        tier,
                        new Item.Properties().attributes(
                                ShovelItem.createAttributes(
                                        tier,
                                        setSwordAttackDamage(shovelAttackDamage),
                                        setAttackSpeed(shovelAttackSpeed)
                                )
                        )
                )
        );

        DeferredItem<PickaxeItem> PICKAXE = ModItems.ITEMS.register(
                tierId+"_pickaxe",
                () -> new PickaxeItem(
                        tier,
                        new Item.Properties().attributes(
                                PickaxeItem.createAttributes(
                                        tier,
                                        setAttackDamage(pickaxeAttackDamage),
                                        setAttackSpeed(pickaxeAttackSpeed)
                                )
                        )
                )
        );

        DeferredItem<AxeItem> AXE = ModItems.ITEMS.register(
                tierId+"_axe",
                () -> new AxeItem(
                        tier,
                        new Item.Properties().attributes(
                                AxeItem.createAttributes(
                                        tier,
                                        setAttackDamage(axeAttackDamage),
                                        setAttackSpeed(axeAttackSpeed)
                                )
                        )
                )
        );

        DeferredItem<HoeItem> HOE = ModItems.ITEMS.register(
                tierId+"_hoe",
                () -> new HoeItem(
                        tier,
                        new Item.Properties().attributes(
                                HoeItem.createAttributes(
                                        tier,
                                        setAttackDamage(hoeAttackDamage),
                                        setAttackSpeed(hoeAttackSpeed)
                                )
                        )
                )
        );

        DeferredItem<SwordItem> SWORD = ModItems.ITEMS.register(
                tierId+"_sword",
                () -> new SwordItem(
                        tier,
                        new Item.Properties().attributes(
                                SwordItem.createAttributes(
                                        tier,
                                        setAttackDamage(swordAttackDamage),
                                        setAttackSpeed(swordAttackSpeed)
                                )
                        )
                )
        );

        DeferredItem<HammerItem> HAMMER = ModItems.ITEMS.register(
                tierId+"_hammer",
                () -> new HammerItem(
                        tier,
                        new Item.Properties().attributes(
                                HammerItem.createAttributes(
                                        tier,
                                        setAttackDamage(hammerAttackDamage),
                                        setAttackSpeed(hammerAttackSpeed)
                                )
                        )
                )
        );

        ToolSet toolSet = new ToolSet(
                SWORD,tierId+"_sword",tierDisplayName+" Sword",
                SHOVEL,tierId+"_shovel", tierDisplayName+" Shovel",
                PICKAXE, tierId+"_pickaxe", tierDisplayName+" Pickaxe",
                AXE, tierId+"_axe", tierDisplayName+" Axe",
                HOE, tierId+"_hoe", tierDisplayName+" Hoe",
                HAMMER, tierId+"_hammer", tierDisplayName+" Hammer"
        );

        registeredToolSets.add(toolSet);

        return toolSet;
    }
}
