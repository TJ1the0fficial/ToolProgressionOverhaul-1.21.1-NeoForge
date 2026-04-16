package net.almmolt.toolprogressionoverhaul.item;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(ToolProgressionOverhaul.MODID);

    public static final DeferredItem<Item> TIN_INGOT = ITEMS.register(
            "tin_ingot",
            () -> new Item(new Item.Properties())
    );

    public static final DeferredItem<Item> RAW_TIN = ITEMS.register(
            "raw_tin",
            () -> new Item(new Item.Properties())
    );

    public static final DeferredItem<Item> BRONZE_INGOT = ITEMS.register(
            "bronze_ingot",
            () -> new Item(new Item.Properties())
    );

    // Bronze Tools
    public static final Tier BRONZE_TIER = new SimpleTier(
            ModTags.INCORRECT_FOR_BRONZE_TOOL,
            216,
            5.0f,
            1.5f,
            20,
            () -> Ingredient.of(BRONZE_INGOT)
    );

    public static final DeferredItem<SwordItem> BRONZE_SWORD = ITEMS.register(
            "bronze_sword",
            () -> new SwordItem(
                    BRONZE_TIER,
                    new Item.Properties().attributes(
                            SwordItem.createAttributes(
                                    BRONZE_TIER,
                                    setAttackDamage(5.5f),
                                    setAttackSpeed(1.6f)
                            )
                    )
            )
    );

    public static final DeferredItem<AxeItem> BRONZE_AXE = ITEMS.register(
            "bronze_axe",
            () -> new AxeItem(
                    BRONZE_TIER,
                    new Item.Properties().attributes(
                            AxeItem.createAttributes(
                                    BRONZE_TIER,
                                    setAttackDamage(9.0f),
                                    setAttackSpeed(0.9f)
                            )
                    )
            )
    );

    public static final DeferredItem<PickaxeItem> BRONZE_PICKAXE = ITEMS.register(
            "bronze_pickaxe",
            () -> new PickaxeItem(
                    BRONZE_TIER,
                    new Item.Properties().attributes(
                            PickaxeItem.createAttributes(
                                    BRONZE_TIER,
                                    setAttackDamage(3.5f),
                                    setAttackSpeed(1.2f)
                            )
                    )
            )
    );

    public static final DeferredItem<ShovelItem> BRONZE_SHOVEL = ITEMS.register(
            "bronze_shovel",
            () -> new ShovelItem(
                    BRONZE_TIER,
                    new Item.Properties().attributes(
                            ShovelItem.createAttributes(
                                    BRONZE_TIER,
                                    setAttackDamage(4.0f),
                                    setAttackSpeed(1.0f)
                            )
                    )
            )
    );

    public static final DeferredItem<HoeItem> BRONZE_HOE = ITEMS.register(
            "bronze_hoe",
            () -> new HoeItem(
                    BRONZE_TIER,
                    new Item.Properties().attributes(
                            HoeItem.createAttributes(
                                    BRONZE_TIER,
                                    setAttackDamage(1.0f),
                                    setAttackSpeed(2.5f)
                            )
                    )
            )
    );
    //

    // Bronze Armor
    // ARMOR_MATERIALS is a DeferredRegister<ArmorMaterial>

    // We place copper somewhere between chainmail and iron.
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(
            BuiltInRegistries.ARMOR_MATERIAL,
            ToolProgressionOverhaul.MODID
    );

    public static final Holder<ArmorMaterial> BRONZE_ARMOR_MATERIAL =
            ARMOR_MATERIALS.register("bronze", () -> new ArmorMaterial(
                    // Determines the defense value of this armor material, depending on what armor piece it is.
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.LEGGINGS, 4);
                        map.put(ArmorItem.Type.CHESTPLATE, 6);
                        map.put(ArmorItem.Type.HELMET, 2);
                        map.put(ArmorItem.Type.BODY, 4);
                    }),
                    // Determines the enchantability of the tier. This represents how good the enchantments on this armor will be.
                    // Gold uses 25, we put copper slightly below that.
                    20,
                    // Determines the sound played when equipping this armor.
                    // This is wrapped with a Holder.
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    // Determines the repair item for this armor.
                    () -> Ingredient.of(ModItems.BRONZE_INGOT),
                    // Determines the texture locations of the armor to apply when rendering
                    // This can also be specified by overriding 'IItemExtension#getArmorTexture' on your item if the armor texture needs to be more dynamic
                    List.of(
                            // Creates a new armor texture that will be located at:
                            // - 'assets/mod_id/textures/models/armor/copper_layer_1.png' for the outer texture
                            // - 'assets/mod_id/textures/models/armor/copper_layer_2.png' for the inner texture (only legs)
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "bronze")
                            )
                    ),
                    // Returns the toughness value of the armor. The toughness value is an additional value included in
                    // damage calculation, for more information, refer to the Minecraft Wiki's article on armor mechanics:
                    // https://minecraft.wiki/w/Armor#Armor_toughness
                    // Only diamond and netherite have values greater than 0 here, so we just return 0.
                    0,
                    // Returns the knockback resistance value of the armor. While wearing this armor, the player is
                    // immune to knockback to some degree. If the player has a total knockback resistance value of 1 or greater
                    // from all armor pieces combined, they will not take any knockback at all.
                    // Only netherite has values greater than 0 here, so we just return 0.
                    0
            ));

    public static final Supplier<ArmorItem> BRONZE_HELMET = ITEMS.register(
            "bronze_helmet",
            () -> new ArmorItem(
                BRONZE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET,
                new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
            )
    );

    public static final Supplier<ArmorItem> BRONZE_CHESTPLATE = ITEMS.register(
            "bronze_chestplate",
            () -> new ArmorItem(
                    BRONZE_ARMOR_MATERIAL,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
            )
    );

    public static final Supplier<ArmorItem> BRONZE_LEGGINGS = ITEMS.register(
            "bronze_leggings",
            () -> new ArmorItem(
                    BRONZE_ARMOR_MATERIAL,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
            )
    );

    public static final Supplier<ArmorItem> BRONZE_BOOTS = ITEMS.register(
            "bronze_boots",
            () -> new ArmorItem(
                    BRONZE_ARMOR_MATERIAL,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
            )
    );
    //

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ARMOR_MATERIALS.register(eventBus);
    }

    public static float setAttackSpeed(float attackSpeed) {
        return -(4.0f - attackSpeed);
    }

    public static float setAttackDamage(float attackDamage) {
        return -(2.5f - attackDamage);
    }
}
