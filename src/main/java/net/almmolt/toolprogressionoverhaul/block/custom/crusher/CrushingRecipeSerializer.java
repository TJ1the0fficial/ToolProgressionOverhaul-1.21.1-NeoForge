package net.almmolt.toolprogressionoverhaul.block.custom.crusher;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CrushingRecipeSerializer implements RecipeSerializer<CrushingRecipe> {
    public static final MapCodec<CrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.INT.fieldOf("duration").forGetter(net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getDuration),
            Ingredient.CODEC.fieldOf("crusher_wheel").forGetter(net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getCrushingWheel),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getIngredient),
            ItemStack.CODEC.fieldOf("result").forGetter(net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getResultItemStack)
    ).apply(inst, net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getDuration,
                    Ingredient.CONTENTS_STREAM_CODEC, net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getCrushingWheel,
                    Ingredient.CONTENTS_STREAM_CODEC, net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getIngredient,
                    ItemStack.STREAM_CODEC, net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::getResultItemStack,
                    net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrushingRecipe::new
    );

    @Override
    public MapCodec<CrushingRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CrushingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
