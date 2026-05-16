package net.almmolt.toolprogressionoverhaul.item;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.custom.HammerItem;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.*;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMsimpleItem.*;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.*;

public class ModItems {
    // Use AmmoltUtilities ONLY when it does not to something special!!!

    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(ToolProgressionOverhaul.MODID);

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(
            BuiltInRegistries.ARMOR_MATERIAL,
            ToolProgressionOverhaul.MODID
    );

    public static final DeferredItem<Item> TIN_INGOT              = registerItem("tin_ingot","Tin Ingot");
    public static final DeferredItem<Item> RAW_TIN                = registerItem("raw_tin","Raw Tin");
    public static final DeferredItem<Item> BRONZE_INGOT           = registerItem("bronze_ingot","Bronze Ingot");
    public static final DeferredItem<Item> TIN_DUST               = registerDust("tin_dust","Tin Dust");
    public static final DeferredItem<Item> BRONZE_DUST            = registerDust("bronze_dust","Bronze Dust");
    public static final DeferredItem<Item> COPPER_DUST            = registerDust("copper_dust","Copper Dust");
    public static final DeferredItem<Item> RAW_SILVER             = registerItem("raw_silver","Raw Silver");
    public static final DeferredItem<Item> SILVER_INGOT           = registerItem("silver_ingot", "Silver Ingot");
    public static final DeferredItem<Item> SILVER_DUST            = registerDust("silver_dust", "Silver Dust");
    public static final DeferredItem<Item> RAW_NICKEL             = registerItem("raw_nickel","Raw Nickel");
    public static final DeferredItem<Item> NICKEL_INGOT           = registerItem("nickel_ingot", "Nickel Ingot");
    public static final DeferredItem<Item> NICKEL_DUST            = registerDust("nickel_dust", "Nickel Dust");
    public static final DeferredItem<Item> INVAR_INGOT            = registerItem("invar_ingot", "Invar Ingot");
    public static final DeferredItem<Item> INVAR_DUST             = registerDust("invar_dust", "Invar Dust");
    public static final DeferredItem<Item> IRON_DUST              = registerDust("iron_dust", "Iron Dust");
    public static final DeferredItem<Item> IRON_CRUSHING_WHEEL    = registerWheel("iron_crushing_wheel", "Iron Crushing Wheel");
    public static final DeferredItem<Item> COKE                   = registerItem("coke", "Coke");
    public static final DeferredItem<Item> COAL_DUST              = registerDust("coal_dust", "Coal Dust");
    public static final DeferredItem<Item> SILVER_CRUSHING_WHEEL  = registerWheel("silver_crushing_wheel", "Silver Crushing Wheel");

    public static final DeferredItem<HammerItem> IRON_HAMMER = ModItems.ITEMS.register(
            "iron_hammer",
            () -> new HammerItem(
                    Tiers.IRON,
                    new Item.Properties().attributes(
                            HammerItem.createAttributes(
                                    Tiers.IRON,
                                    setAttackDamage(4.0f),
                                    setAttackSpeed(0.4f)
                            )
                    )
            )
    );

    public static final DeferredItem<HammerItem> STONE_HAMMER = ModItems.ITEMS.register(
            "stone_hammer",
            () -> new HammerItem(
                    Tiers.STONE,
                    new Item.Properties().attributes(
                            HammerItem.createAttributes(
                                    Tiers.STONE,
                                    setAttackDamage(4.0f),
                                    setAttackSpeed(0.7f)
                            )
                    )
            )
    );

    // Bronze down
    public static final Tier BRONZE_TIER = new SimpleTier(
            ModTags.INCORRECT_FOR_BRONZE_TOOL,
            350,
            5.0f,
            1.5f,
            20,
            () -> Ingredient.of(BRONZE_INGOT)
    );
    public static final ToolSet BRONZE_TOOLS = registerTools(
            "bronze","Bronze",BRONZE_TIER,
            5.5f,1.6f,
            4.0f,1.0f,
            3.5f,1.2f,
            9.0f,0.9f,
            1.0f,2.5f,
            4.0f,0.6f
    );
    public static final Holder<ArmorMaterial> BRONZE_MATERIAL = registerArmorMaterial(
            "bronze",BRONZE_INGOT,22,
            2,
            5,
            4,
            3,
            4
    );
    public static final ArmorSet BRONZE_ARMOR = registerArmor(
            "bronze","Bronze",13,BRONZE_MATERIAL
    );
    // Bronze up

    // #################################################################################################################

    // Silver down
    static float silverFactor = 14.0f;
    public static final Tier SILVER_TIER = new SimpleTier(
            ModTags.INCORRECT_FOR_SILVER_TOOL,
            200,
            7.0f,
            2.5f,
            24,
            () -> Ingredient.of(SILVER_INGOT)
    );
    public static final Holder<ArmorMaterial> SILVER_MATERIAL = registerArmorMaterial(
            "silver",SILVER_INGOT,25,
            2,
            5,
            4,
            2,
            5
    );

    public static final DeferredItem<SwordItem> SILVER_SWORD = ModItems.ITEMS.register(
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
                         if (target.isAlive()) target.hurt(attacker.damageSources().magic(),silverFactor);
                    }
                    return super.hurtEnemy(stack, target, attacker);
                }
            }
    );

    public static final DeferredItem<ShovelItem> SILVER_SHOVEL = ModItems.ITEMS.register(
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
                        if (target.isAlive()) target.hurt(attacker.damageSources().magic(),silverFactor);
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }
            }
    );

    public static final DeferredItem<PickaxeItem> SILVER_PICKAXE = ModItems.ITEMS.register(
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
                        if (target.isAlive()) target.hurt(attacker.damageSources().magic(),silverFactor);
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }
            }
    );

    public static final DeferredItem<AxeItem> SILVER_AXE = ModItems.ITEMS.register(
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
                        if (target.isAlive()) target.hurt(attacker.damageSources().magic(),silverFactor-10.0f);
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }
            }
    );

    public static final DeferredItem<HoeItem> SILVER_HOE = ModItems.ITEMS.register(
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
                @Override
                public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                        if (target.isAlive()) target.hurt(attacker.damageSources().magic(),silverFactor);
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }
            }
    );

    public static final DeferredItem<HammerItem> SILVER_HAMMER = ModItems.ITEMS.register(
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
                        if (target.isAlive()) target.hurt(attacker.damageSources().magic(),silverFactor);
                    }

                    return super.hurtEnemy(stack, target, attacker);
                }
            }
    );

    public static final ToolSet SILVER_TOOLS = new ToolSet(
            SILVER_SWORD,"silver_sword","Silver Sword",
            SILVER_SHOVEL,"silver_shovel", "Silver Shovel",
            SILVER_PICKAXE, "silver_pickaxe", "Silver Pickaxe",
            SILVER_AXE, "silver_axe", "Silver Axe",
            SILVER_HOE, "silver_hoe", "Silver Hoe",
            SILVER_HAMMER, "silver_hammer", "Silver Hammer"
    );

    public static final DeferredItem<ArmorItem> SILVER_HELMET = ModItems.ITEMS.register(
            "silver_helmet",
            () -> new ArmorItem(
                    SILVER_MATERIAL,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
            )
    );

    public static final DeferredItem<ArmorItem> SILVER_CHESTPLATE = ModItems.ITEMS.register(
            "silver_chestplate",
            () -> new ArmorItem(
                    SILVER_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
            )
    );

    public static final DeferredItem<ArmorItem> SILVER_LEGGINGS = ModItems.ITEMS.register(
            "silver_leggings",
            () -> new ArmorItem(
                    SILVER_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
            )
    );

    public static final DeferredItem<ArmorItem> SILVER_BOOTS = ModItems.ITEMS.register(
            "silver_boots",
            () -> new ArmorItem(
                    SILVER_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
            )
    );

    public static final ArmorSet SILVER_ARMOR = new ArmorSet(
            SILVER_HELMET,"silver_helmet","Silver Helmet",
            SILVER_CHESTPLATE,"silver_chestplate","Silver Chestplate",
            SILVER_LEGGINGS,"silver_leggings","Silver Leggings",
            SILVER_BOOTS,"silver_boots","Silver Boots"
    );
    //
    // Silver up
    // #################################################################################################################

    // Invar
    public static final Tier INVAR_TIER = new SimpleTier(
            ModTags.INCORRECT_FOR_INVAR_TOOL,
            600,
            4.0f,
            1.0f,
            18,
            () -> Ingredient.of(INVAR_INGOT)
    );
    public static final Holder<ArmorMaterial> INVAR_MATERIAL = registerArmorMaterial(
            "invar",INVAR_INGOT,18,
            3,
            5,
            4,
            2,
            6
    );
    public static final ToolSet INVAR_TOOLS = registerTools(
            "invar","Invar",INVAR_TIER,
            5.0f,1.6f,
            5.0f,1.0f,
            4.0f,1.2f,
            9.0f,0.9f,
            2.0f,2.5f,
            3.0f,0.6f
    );
    public static final ArmorSet INVAR_ARMOR = registerArmor(
            "invar","Invar",22,INVAR_MATERIAL
    );

    public static void register(IEventBus eventBus) {
        registeredToolSets.add(SILVER_TOOLS);
        registeredArmorSets.add(SILVER_ARMOR);

        ITEMS.register(eventBus);
        ARMOR_MATERIALS.register(eventBus);
    }
}
