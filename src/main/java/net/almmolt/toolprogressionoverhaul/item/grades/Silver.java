package net.almmolt.toolprogressionoverhaul.item.grades;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.item.custom.HammerItem;
import net.almmolt.toolprogressionoverhaul.item.custom.SilverArmorItem;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.minecraft.core.Holder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredItem;

import static net.almmolt.toolprogressionoverhaul.item.custom.SilverArmorItem.SILVER_FACTOR;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registerArmorMaterial;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.*;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.setAttackSpeed;

public class Silver {
    public static  Tier SILVER_TIER;
    public static  Holder<ArmorMaterial> SILVER_MATERIAL;

    public static  DeferredItem<SwordItem> SILVER_SWORD;

    public static  DeferredItem<ShovelItem> SILVER_SHOVEL;

    public static  DeferredItem<PickaxeItem> SILVER_PICKAXE;

    public static  DeferredItem<AxeItem> SILVER_AXE;

    public static  DeferredItem<HoeItem> SILVER_HOE;

    public static  DeferredItem<HammerItem> SILVER_HAMMER;

    public static  ToolSet SILVER_TOOLS;

    public static  DeferredItem<ArmorItem> SILVER_HELMET;

    public static  DeferredItem<ArmorItem> SILVER_CHESTPLATE;

    public static  DeferredItem<ArmorItem> SILVER_LEGGINGS;

    public static  DeferredItem<ArmorItem> SILVER_BOOTS;

    public static  AMarmor.ArmorSet SILVER_ARMOR;

    public static void register() {
        // tier
        SILVER_TIER = new SimpleTier(
                ModTags.INCORRECT_FOR_SILVER_TOOL,
                200,
                7.0f,
                2.5f,
                24,
                () -> Ingredient.of(ModItems.SILVER_INGOT)
        );

        // armor material
        SILVER_MATERIAL = registerArmorMaterial(
                "silver",ModItems.SILVER_INGOT,25,
                2,
                5,
                4,
                2,
                5
        );

        // tools
        SILVER_SWORD = ModItems.ITEMS.register(
                "silver_sword",
                () -> new SwordItem(
                        SILVER_TIER,
                        new Item.Properties().attributes(
                                SwordItem.createAttributes(
                                        SILVER_TIER,
                                        setSwordAttackDamage(6.0f),
                                        setAttackSpeed(1.6f)
                                )
                        )
                ) {
                    @Override
                    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                        if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                            if (target.isAlive()) target.hurt(attacker.damageSources().magic(),SILVER_FACTOR);
                        }
                        return super.hurtEnemy(stack, target, attacker);
                    }
                }
        );

        SILVER_SHOVEL = ModItems.ITEMS.register(
                "silver_shovel",
                () -> new ShovelItem(
                        SILVER_TIER,
                        new Item.Properties().attributes(
                                ShovelItem.createAttributes(
                                        SILVER_TIER,
                                        setAttackDamage(5.0f),
                                        setAttackSpeed(1.0f)
                                )
                        )
                ) {
                    @Override
                    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                        if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                            if (target.isAlive()) target.hurt(attacker.damageSources().magic(),SILVER_FACTOR);
                        }

                        return super.hurtEnemy(stack, target, attacker);
                    }
                }
        );

        SILVER_PICKAXE = ModItems.ITEMS.register(
                "silver_pickaxe",
                () -> new PickaxeItem(
                        SILVER_TIER,
                        new Item.Properties().attributes(
                                PickaxeItem.createAttributes(
                                        SILVER_TIER,
                                        setAttackDamage(4.5f),
                                        setAttackSpeed(1.2f)
                                )
                        )
                ) {
                    @Override
                    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                        if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                            if (target.isAlive()) target.hurt(attacker.damageSources().magic(),SILVER_FACTOR);
                        }

                        return super.hurtEnemy(stack, target, attacker);
                    }
                }
        );

        SILVER_AXE = ModItems.ITEMS.register(
                "silver_axe",
                () -> new AxeItem(
                        SILVER_TIER,
                        new Item.Properties().attributes(
                                AxeItem.createAttributes(
                                        SILVER_TIER,
                                        setSwordAttackDamage(9.0f),
                                        setAttackSpeed(0.9f)
                                )
                        )
                ) {
                    @Override
                    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                        if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                            if (target.isAlive()) target.hurt(attacker.damageSources().magic(),SILVER_FACTOR-10.0f);
                        }

                        return super.hurtEnemy(stack, target, attacker);
                    }
                }
        );

        SILVER_HOE = ModItems.ITEMS.register(
                "silver_hoe",
                () -> new HoeItem(
                        SILVER_TIER,
                        new Item.Properties().attributes(
                                HoeItem.createAttributes(
                                        SILVER_TIER,
                                        setSwordAttackDamage(1.0f),
                                        setAttackSpeed(2.5f)
                                )
                        )
                ) {

                }
        );

        SILVER_HAMMER = ModItems.ITEMS.register(
                "silver_hammer",
                () -> new HammerItem(
                        SILVER_TIER,
                        new Item.Properties().attributes(
                                HammerItem.createAttributes(
                                        SILVER_TIER,
                                        setAttackDamage(5.0f),
                                        setAttackSpeed(0.6f)
                                )
                        )
                ) {
                    @Override
                    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                        if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                            if (target.isAlive()) target.hurt(attacker.damageSources().magic(),SILVER_FACTOR);
                        }

                        return super.hurtEnemy(stack, target, attacker);
                    }
                }
        );

        SILVER_TOOLS = new ToolSet(
                SILVER_SWORD,"silver_sword","Silver Sword",
                SILVER_SHOVEL,"silver_shovel", "Silver Shovel",
                SILVER_PICKAXE, "silver_pickaxe", "Silver Pickaxe",
                SILVER_AXE, "silver_axe", "Silver Axe",
                SILVER_HOE, "silver_hoe", "Silver Hoe",
                SILVER_HAMMER, "silver_hammer", "Silver Hammer"
        );

        // armor
        SILVER_HELMET = ModItems.ITEMS.register(
                "silver_helmet",
                () -> new SilverArmorItem(
                        SILVER_MATERIAL,
                        ArmorItem.Type.HELMET,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
                )
        );

        SILVER_CHESTPLATE = ModItems.ITEMS.register(
                "silver_chestplate",
                () -> new SilverArmorItem(
                        SILVER_MATERIAL,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
                )
        );

        SILVER_LEGGINGS = ModItems.ITEMS.register(
                "silver_leggings",
                () -> new SilverArmorItem(
                        SILVER_MATERIAL,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
                )
        );

        SILVER_BOOTS = ModItems.ITEMS.register(
                "silver_boots",
                () -> new SilverArmorItem(
                        SILVER_MATERIAL,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
                )
        );

        SILVER_ARMOR = new AMarmor.ArmorSet(
                SILVER_HELMET,"silver_helmet","Silver Helmet",
                SILVER_CHESTPLATE,"silver_chestplate","Silver Chestplate",
                SILVER_LEGGINGS,"silver_leggings","Silver Leggings",
                SILVER_BOOTS,"silver_boots","Silver Boots"
        );
    }
}
