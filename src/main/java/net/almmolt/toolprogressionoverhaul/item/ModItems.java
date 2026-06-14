package net.almmolt.toolprogressionoverhaul.item;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.custom.HammerItem;
import net.almmolt.toolprogressionoverhaul.item.grades.*;
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
    public static final DeferredItem<Item> IRON_CRUSHING_WHEEL    = registerWheel("iron_crushing_wheel", "Iron Crushing Wheel",1);
    public static final DeferredItem<Item> COKE                   = registerItem("coke", "Coke");
    public static final DeferredItem<Item> COAL_DUST              = registerDust("coal_dust", "Coal Dust");
    public static final DeferredItem<Item> ZINC_INGOT             = registerItem("zinc_ingot","Zinc Ingot");
    public static final DeferredItem<Item> RAW_ZINC               = registerItem("raw_zinc","Raw Zinc");
    public static final DeferredItem<Item> ZINC_DUST              = registerDust("zinc_dust", "Zinc Dust");
    public static final DeferredItem<Item> GALVANIZED_IRON_INGOT              = registerItem("galvanized_iron_ingot","Galvanized Iron Ingot");
    public static final DeferredItem<Item> GALVANIZED_IRON_DUST               = registerDust("galvanized_iron_dust", "Galvanized Iron Dust");
    public static final DeferredItem<Item> WOOTZ_STEEL_INGOT                  = registerItem("wootz_steel_ingot","Wootz Steel Ingot");
    public static final DeferredItem<Item> WOOTZ_STEEL_DUST                   = registerDust("wootz_steel_dust", "Wootz Steel Dust");
    public static final DeferredItem<Item> SAND_FLUX_DUST                     = registerDust("sand_flux_dust", "Sand Flux Dust");
    public static final DeferredItem<Item> BONE_FLUX_DUST                     = registerDust("bone_flux_dust", "Bone Flux Dust");
    public static final DeferredItem<Item> CALCITE_FLUX_DUST                  = registerDust("calcite_flux_dust", "Calcite Flux Dust");
    public static final DeferredItem<Item> WOOTZ_STEEL_CRUSHING_WHEEL         = registerWheel("wootz_steel_crushing_wheel", "Wootz Iron Crushing Wheel",2);
    public static final DeferredItem<Item> STEEL_INGOT            = registerItem("steel_ingot", "Steel Ingot");
    public static final DeferredItem<Item> STEEL_DUST             = registerDust("steel_dust", "Steel Dust");
    public static final DeferredItem<Item> GOLD_DUST              = registerDust("gold_dust", "Gold Dust");

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


    public static void register(IEventBus eventBus) {
        Bronze.register();
        Invar.register();
        Silver.register();
        GalvanizedIron.register();
        WootzSteel.register();
        Steel.register();

        registeredToolSets.add(Silver.SILVER_TOOLS);
        registeredArmorSets.add(Silver.SILVER_ARMOR);

        registeredToolSets.add(GalvanizedIron.GALVANIZED_IRON_TOOLS);
        registeredArmorSets.add(GalvanizedIron.GALVANIZED_IRON_ARMOR);

        registeredToolSets.add(WootzSteel.WOOTZ_STEEL_TOOLS);
        registeredArmorSets.add(WootzSteel.WOOTZ_STEEL_ARMOR);

        ITEMS.register(eventBus);
        ARMOR_MATERIALS.register(eventBus);
    }
}
