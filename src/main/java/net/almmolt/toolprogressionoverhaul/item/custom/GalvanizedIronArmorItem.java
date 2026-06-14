package net.almmolt.toolprogressionoverhaul.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GalvanizedIronArmorItem extends ArmorItem {
    public GalvanizedIronArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public static final float GALVANIZED_IRON_CHANCE = 0.3f;

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        if (Math.random() < GALVANIZED_IRON_CHANCE) return amount;
        return 0;
    }
}
