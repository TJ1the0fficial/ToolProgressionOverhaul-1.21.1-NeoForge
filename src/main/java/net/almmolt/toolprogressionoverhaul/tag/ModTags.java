package net.almmolt.toolprogressionoverhaul.tag;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final TagKey<Item> TPO_ITEMS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"toolprogressionoverhaul_items")
    );

    public static final TagKey<Block> MINEABLE_BY_PICKAXE = TagKey.create(
            // The registry key. The type of the registry must match the generic type of the tag.
            Registries.BLOCK,
            // The location of the tag. This example will put our tag at data/examplemod/tags/blocks/example_tag.json.
            ResourceLocation.withDefaultNamespace("mineable/pickaxe")
    );

    public static final TagKey<Block> MINEABLE_BY_SHOVEL = TagKey.create(
            // The registry key. The type of the registry must match the generic type of the tag.
            Registries.BLOCK,
            // The location of the tag. This example will put our tag at data/examplemod/tags/blocks/example_tag.json.
            ResourceLocation.withDefaultNamespace("mineable/shovel")
    );

    public static final TagKey<Block> TIN_ORES = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID,"tin_ores")
    );

    // Bronze
    public static final TagKey<Block> INCORRECT_FOR_BRONZE_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.withDefaultNamespace("incorrect_for_bronze_tool")
    );
    public static final TagKey<Block> NEEDS_BRONZE_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.withDefaultNamespace("needs_bronze_tool")
    );

    // Silver
    public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.withDefaultNamespace("incorrect_for_silver_tool")
    );
    public static final TagKey<Block> NEEDS_SILVER_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.withDefaultNamespace("needs_silver_tool")
    );

    // Invar
    public static final TagKey<Block> INCORRECT_FOR_INVAR_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.withDefaultNamespace("incorrect_for_invar_tool")
    );
    public static final TagKey<Block> NEEDS_INVAR_TOOL = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.withDefaultNamespace("needs_invar_tool")
    );

    public static final TagKey<Item> HAMMERS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "hammers")
    );

    public static final TagKey<Item> CRUSHING_WHEELS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "crushing_wheels")
    );

    public static final TagKey<Item> INCORRECT_FOR_CRUSHER = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "incorrect_for_crusher")
    );

    public static final TagKey<Item> STONES = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ToolProgressionOverhaul.MODID, "stones")
    );
}
