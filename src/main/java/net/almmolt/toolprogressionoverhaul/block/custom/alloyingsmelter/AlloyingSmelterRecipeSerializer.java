package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AlloyingSmelterRecipeSerializer implements RecipeSerializer<AlloyingSmelterRecipe> {
    public static final MapCodec<AlloyingSmelterRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(AlloyingSmelterRecipe::getInputState),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(AlloyingSmelterRecipe::getInputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(AlloyingSmelterRecipe::getResult)
    ).apply(inst, AlloyingSmelterRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AlloyingSmelterRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), AlloyingSmelterRecipe::getInputState,
                    Ingredient.CONTENTS_STREAM_CODEC, AlloyingSmelterRecipe::getInputItem,
                    ItemStack.STREAM_CODEC, AlloyingSmelterRecipe::getResult,
                    AlloyingSmelterRecipe::new
            );

    // Return our map codec.
    @Override
    public MapCodec<AlloyingSmelterRecipe> codec() {
        return CODEC;
    }

    // Return our stream codec.
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, AlloyingSmelterRecipe> streamCodec() {
        return STREAM_CODEC;
    }

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ToolProgressionOverhaul.MODID);

    public static final Supplier<RecipeSerializer<AlloyingSmelterRecipe>> ALLOYING_SMELTER_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("alloying_smelter_recipe_serializer", AlloyingSmelterRecipeSerializer::new);
}
