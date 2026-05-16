package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AlloyingRecipeInput(ItemStack stack1, ItemStack stack2, ItemStack stack3) implements RecipeInput {
    // Method to get an item from a specific slot. We have one stack and no concept of slots, so we just assume
    // that slot 0 holds our item, and throw on any other slot. (Taken from SingleRecipeInput#getItem.)
    @Override
    public ItemStack getItem(int slot) {
        if (slot == 0) return this.stack1();
        else if (slot == 1) return this.stack2();
        else if (slot == 2) return this.stack3();
        else throw new IllegalArgumentException("No item for index " + slot);
    }

    // The slot size our input requires. Again, we don't really have a concept of slots, so we just return 1
    // because we have one item stack involved. Inputs with multiple items should return the actual count here.
    @Override
    public int size() {
        return 3;
    }
}
