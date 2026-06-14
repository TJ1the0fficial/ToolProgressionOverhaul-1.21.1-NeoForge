package net.almmolt.toolprogressionoverhaul.item.custom;

import net.minecraft.core.Holder;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class SilverArmorItem extends ArmorItem {
    public SilverArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public static final float SILVER_FACTOR = 14.0f;

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
            if (target.isAlive()) target.hurt(attacker.damageSources().magic(),SILVER_FACTOR);
        }

        return super.hurtEnemy(stack, target, attacker);
    }
}
