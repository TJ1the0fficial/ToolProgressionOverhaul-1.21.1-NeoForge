package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class AlloyingRecipeSerializer implements RecipeSerializer<AlloyingRecipe> {
    public static final MapCodec<AlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            // This creates an "ingredients": [...] array in your JSON
            ItemStack.CODEC.fieldOf("result").forGetter(AlloyingRecipe::getResultItemStack),
            Codec.INT.fieldOf("duration").forGetter(AlloyingRecipe::getDuration),
            Codec.INT.fieldOf("experience").forGetter(AlloyingRecipe::getExperience),
            SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(AlloyingRecipe::getInputItems)
    ).apply(inst, AlloyingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.STREAM_CODEC, AlloyingRecipe::getResultItemStack,
                    ByteBufCodecs.INT, AlloyingRecipe::getDuration,
                    ByteBufCodecs.INT, AlloyingRecipe::getExperience,
                    SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), AlloyingRecipe::getInputItems,
                    AlloyingRecipe::new
    );

    // Return our map codec.
    @Override
    public MapCodec<AlloyingRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}