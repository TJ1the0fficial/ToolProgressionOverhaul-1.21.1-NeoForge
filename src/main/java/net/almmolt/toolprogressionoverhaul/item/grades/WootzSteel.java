package net.almmolt.toolprogressionoverhaul.item.grades;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.item.custom.HammerItem;
import net.almmolt.toolprogressionoverhaul.item.custom.SilverArmorItem;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool;
import net.minecraft.core.Holder;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredItem;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registerArmorMaterial;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.*;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.setAttackSpeed;

public class WootzSteel {
    public static Tier WOOTZ_STEEL_TIER;
    public static Holder<ArmorMaterial> WOOTZ_STEEL_MATERIAL;

    public static DeferredItem<SwordItem> WOOTZ_STEEL_SWORD;

    public static  DeferredItem<ShovelItem> WOOTZ_STEEL_SHOVEL;

    public static  DeferredItem<PickaxeItem> WOOTZ_STEEL_PICKAXE;

    public static  DeferredItem<AxeItem> WOOTZ_STEEL_AXE;

    public static  DeferredItem<HoeItem> WOOTZ_STEEL_HOE;

    public static  DeferredItem<HammerItem> WOOTZ_STEEL_HAMMER;

    public static AMtool.ToolSet WOOTZ_STEEL_TOOLS;

    public static  DeferredItem<ArmorItem> WOOTZ_STEEL_HELMET;

    public static  DeferredItem<ArmorItem> WOOTZ_STEEL_CHESTPLATE;

    public static  DeferredItem<ArmorItem> WOOTZ_STEEL_LEGGINGS;

    public static  DeferredItem<ArmorItem> WOOTZ_STEEL_BOOTS;

    public static  AMarmor.ArmorSet WOOTZ_STEEL_ARMOR;

    public static void register() {
        // tier
        WOOTZ_STEEL_TIER = new SimpleTier(
                ModTags.INCORRECT_FOR_WOOTZ_STEEL_TOOL,
                800,
                6.0f,
                2.0f,
                16,
                () -> Ingredient.of(ModItems.WOOTZ_STEEL_INGOT)
        );

        // armor material
        WOOTZ_STEEL_MATERIAL = registerArmorMaterial(
                "wootz_steel",ModItems.WOOTZ_STEEL_INGOT,20,
                3,
                7,
                6,
                2,
                5,
                1,
                0.02f
        );

        // tools
        WOOTZ_STEEL_SWORD = ModItems.ITEMS.register(
                "wootz_steel_sword",
                () -> new SwordItem(
                        WOOTZ_STEEL_TIER,
                        new Item.Properties().attributes(
                                SwordItem.createAttributes(
                                        WOOTZ_STEEL_TIER,
                                        setSwordAttackDamage(6.0f),
                                        setAttackSpeed(1.6f)
                                )
                        )
                ) {

                }
        );

        WOOTZ_STEEL_SHOVEL = ModItems.ITEMS.register(
                "wootz_steel_shovel",
                () -> new ShovelItem(
                        WOOTZ_STEEL_TIER,
                        new Item.Properties().attributes(
                                ShovelItem.createAttributes(
                                        WOOTZ_STEEL_TIER,
                                        setAttackDamage(5.0f),
                                        setAttackSpeed(1.0f)
                                )
                        )
                ) {
                    
                }
        );

        WOOTZ_STEEL_PICKAXE = ModItems.ITEMS.register(
                "wootz_steel_pickaxe",
                () -> new PickaxeItem(
                        WOOTZ_STEEL_TIER,
                        new Item.Properties().attributes(
                                PickaxeItem.createAttributes(
                                        WOOTZ_STEEL_TIER,
                                        setAttackDamage(4.5f),
                                        setAttackSpeed(1.2f)
                                )
                        )
                ) {
                    
                }
        );

        WOOTZ_STEEL_AXE = ModItems.ITEMS.register(
                "wootz_steel_axe",
                () -> new AxeItem(
                        WOOTZ_STEEL_TIER,
                        new Item.Properties().attributes(
                                AxeItem.createAttributes(
                                        WOOTZ_STEEL_TIER,
                                        setSwordAttackDamage(9.0f),
                                        setAttackSpeed(0.9f)
                                )
                        )
                ) {
                    
                }
        );

        WOOTZ_STEEL_HOE = ModItems.ITEMS.register(
                "wootz_steel_hoe",
                () -> new HoeItem(
                        WOOTZ_STEEL_TIER,
                        new Item.Properties().attributes(
                                HoeItem.createAttributes(
                                        WOOTZ_STEEL_TIER,
                                        setSwordAttackDamage(1.0f),
                                        setAttackSpeed(2.5f)
                                )
                        )
                ) {

                }
        );

        WOOTZ_STEEL_HAMMER = ModItems.ITEMS.register(
                "wootz_steel_hammer",
                () -> new HammerItem(
                        WOOTZ_STEEL_TIER,
                        new Item.Properties().attributes(
                                HammerItem.createAttributes(
                                        WOOTZ_STEEL_TIER,
                                        setAttackDamage(5.0f),
                                        setAttackSpeed(0.6f)
                                )
                        )
                ) {
                    
                }
        );

        WOOTZ_STEEL_TOOLS = new ToolSet(
                WOOTZ_STEEL_SWORD,"wootz_steel_sword","Wootz Steel Sword",
                WOOTZ_STEEL_SHOVEL,"wootz_steel_shovel", "Wootz Steel Shovel",
                WOOTZ_STEEL_PICKAXE, "wootz_steel_pickaxe", "Wootz Steel Pickaxe",
                WOOTZ_STEEL_AXE, "wootz_steel_axe", "Wootz Steel Axe",
                WOOTZ_STEEL_HOE, "wootz_steel_hoe", "Wootz Steel Hoe",
                WOOTZ_STEEL_HAMMER, "wootz_steel_hammer", "Wootz Steel Hammer"
        );

        // armor
        WOOTZ_STEEL_HELMET = ModItems.ITEMS.register(
                "wootz_steel_helmet",
                () -> new SilverArmorItem(
                        WOOTZ_STEEL_MATERIAL,
                        ArmorItem.Type.HELMET,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
                )
        );

        WOOTZ_STEEL_CHESTPLATE = ModItems.ITEMS.register(
                "wootz_steel_chestplate",
                () -> new SilverArmorItem(
                        WOOTZ_STEEL_MATERIAL,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
                )
        );

        WOOTZ_STEEL_LEGGINGS = ModItems.ITEMS.register(
                "wootz_steel_leggings",
                () -> new SilverArmorItem(
                        WOOTZ_STEEL_MATERIAL,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
                )
        );

        WOOTZ_STEEL_BOOTS = ModItems.ITEMS.register(
                "wootz_steel_boots",
                () -> new SilverArmorItem(
                        WOOTZ_STEEL_MATERIAL,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
                )
        );

        WOOTZ_STEEL_ARMOR = new AMarmor.ArmorSet(
                WOOTZ_STEEL_HELMET,"wootz_steel_helmet","Wootz Steel Helmet",
                WOOTZ_STEEL_CHESTPLATE,"wootz_steel_chestplate","Wootz Steel Chestplate",
                WOOTZ_STEEL_LEGGINGS,"wootz_steel_leggings","Wootz Steel Leggings",
                WOOTZ_STEEL_BOOTS,"wootz_steel_boots","Wootz Steel Boots"
        );
    }
}
