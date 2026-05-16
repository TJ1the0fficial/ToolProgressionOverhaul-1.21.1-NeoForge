package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class AMarmor {
    public static final List<ArmorSet> registeredArmorSets = new ArrayList<>();

    public record ArmorSet(
            DeferredItem<ArmorItem> helmetItem, String helmetId, String helmetDisplayName,
            DeferredItem<ArmorItem> chestplateItem, String chestplatetId, String chestplateDisplayName,
            DeferredItem<ArmorItem> leggingsItem, String leggingsId, String leggingsDisplayName,
            DeferredItem<ArmorItem> bootsItem, String bootsId, String bootsDisplayName
    ) {
        public List<DeferredItem<? extends Item>> asList() {
            return List.of(helmetItem, chestplateItem, leggingsItem, bootsItem);
        }

        public List<String> displayNamesAsList() {
            return List.of(helmetDisplayName, chestplateDisplayName, leggingsDisplayName, bootsDisplayName);
        }
    }

    public static Holder<ArmorMaterial> registerArmorMaterial(
            String id,DeferredItem<Item> material,int enchantibility,
            int helmetDefenseValue,
            int chestplateDefenseValue,
            int leggingsDefenseValue,
            int bootsDefenseValue,
            int bodyDefenseValue
    ) {
        return ModItems.ARMOR_MATERIALS.register(id, () -> new ArmorMaterial(
                // Determines the defense value of this AMarmor material, depending on what AMarmor piece it is.
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.BOOTS, helmetDefenseValue);
                    map.put(ArmorItem.Type.LEGGINGS, chestplateDefenseValue);
                    map.put(ArmorItem.Type.CHESTPLATE, leggingsDefenseValue);
                    map.put(ArmorItem.Type.HELMET, bootsDefenseValue);
                    map.put(ArmorItem.Type.BODY, bodyDefenseValue);
                }),
                // Determines the enchantability of the tier. This represents how good the enchantments on this AMarmor will be.
                // Gold uses 25, we put copper slightly below that.
                enchantibility,
                // Determines the sound played when equipping this AMarmor.
                // This is wrapped with a Holder.
                SoundEvents.ARMOR_EQUIP_GENERIC,
                // Determines the repair item for this AMarmor.
                () -> Ingredient.of(material),
                // Determines the texture locations of the AMarmor to apply when rendering
                // This can also be specified by overriding 'IItemExtension#getArmorTexture' on your item if the AMarmor texture needs to be more dynamic
                List.of(
                        // Creates a new AMarmor texture that will be located at:
                        // - 'assets/mod_id/textures/models/AMarmor/copper_layer_1.png' for the outer texture
                        // - 'assets/mod_id/textures/models/AMarmor/copper_layer_2.png' for the inner texture (only legs)
                        new ArmorMaterial.Layer(
                                ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, id)
                        )
                ),
                // Returns the toughness value of the AMarmor. The toughness value is an additional value included in
                // damage calculation, for more information, refer to the Minecraft Wiki's article on AMarmor mechanics:
                // https://minecraft.wiki/w/Armor#Armor_toughness
                // Only diamond and netherite have values greater than 0 here, so we just return 0.
                0,
                // Returns the knockback resistance value of the AMarmor. While wearing this AMarmor, the player is
                // immune to knockback to some degree. If the player has a total knockback resistance value of 1 or greater
                // from all AMarmor pieces combined, they will not take any knockback at all.
                // Only netherite has values greater than 0 here, so we just return 0.
                0
        ));
    }

    public static ArmorSet registerArmor(String tierId, String tierDisplayName, int durabilityFactor, Holder<ArmorMaterial> armorMaterialHolder) {
        DeferredItem<ArmorItem> HELMET = ModItems.ITEMS.register(
                tierId+"_helmet",
                () -> new ArmorItem(
                        armorMaterialHolder,
                        ArmorItem.Type.HELMET,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(durabilityFactor))
                )
        );

        DeferredItem<ArmorItem> CHESTPLATE = ModItems.ITEMS.register(
                tierId+"_chestplate",
                () -> new ArmorItem(
                        armorMaterialHolder,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
                )
        );

        DeferredItem<ArmorItem> LEGGINGS = ModItems.ITEMS.register(
                tierId+"_leggings",
                () -> new ArmorItem(
                        armorMaterialHolder,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
                )
        );

        DeferredItem<ArmorItem> BOOTS = ModItems.ITEMS.register(
                tierId+"_boots",
                () -> new ArmorItem(
                        armorMaterialHolder,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
                )
        );

        ArmorSet armorSet = new ArmorSet(
                HELMET, tierId + "_helmet", tierDisplayName + " Helmet",
                CHESTPLATE, tierId + "_chestplate", tierDisplayName + " Chestplate",
                LEGGINGS, tierId + "_leggings", tierDisplayName + " Leggings",
                BOOTS, tierId + "_boots", tierDisplayName + " Boots"
        );
        registeredArmorSets.add(armorSet);

        return armorSet;
    }
}
