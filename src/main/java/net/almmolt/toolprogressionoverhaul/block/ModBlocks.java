package net.almmolt.toolprogressionoverhaul.block;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterEntityBlock;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrusherEntityBlock;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMblock;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMblock.registerBlock;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ToolProgressionOverhaul.MODID);

    public static AMblock.BlockSet TIN_ORE = registerBlock("tin_ore","Tin Ore");
    public static AMblock.BlockSet DEEPSLATE_TIN_ORE = registerBlock("deepslate_tin_ore","Deepslate Tin Ore");
    public static AMblock.BlockSet TIN_BLOCK = registerBlock("tin_block","Block of Tin");
    public static AMblock.BlockSet RAW_TIN_BLOCK = registerBlock("raw_tin_block","Block Of Raw Tin");
    public static AMblock.BlockSet BRONZE_BLOCK = registerBlock("bronze_block","Block Of Bronze");
    public static AMblock.BlockSet NICKEL_ORE = registerBlock("nickel_ore","Nickel Ore");
    public static AMblock.BlockSet DEEPSLATE_NICKEL_ORE = registerBlock("deepslate_nickel_ore","Deepslate Nickel Ore");
    public static AMblock.BlockSet NICKEL_BLOCK = registerBlock("nickel_block","Block Of Nickel");
    public static AMblock.BlockSet RAW_NICKEL_BLOCK = registerBlock("raw_nickel_block","Block Of Raw Nickel");
    public static AMblock.BlockSet SILVER_ORE = registerBlock("silver_ore","Silver Ore");
    public static AMblock.BlockSet DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore","Deepslate Silver Ore");
    public static AMblock.BlockSet SILVER_BLOCK = registerBlock("silver_block","Block Of Silver");
    public static AMblock.BlockSet RAW_SILVER_BLOCK = registerBlock("raw_silver_block","Block Of Raw Silver");
    public static AMblock.BlockSet INVAR_BLOCK = registerBlock("invar_block","Block Of Invar");

    // ModBlockEntites
    public static DeferredBlock<AlloyingSmelterEntityBlock> ALLOYING_SMELTER = BLOCKS.register(
            "alloying_smelter_block",
            () -> new AlloyingSmelterEntityBlock(AlloyingSmelterEntityBlock.getProperties())
    );
    public static DeferredItem<BlockItem> ALLOYING_SMELTER_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            ALLOYING_SMELTER
    );

    public static DeferredBlock<CrusherEntityBlock> CRUSHER = BLOCKS.register(
            "crusher_block",
            () -> new CrusherEntityBlock(CrusherEntityBlock.getProperties())
    );
    public static DeferredItem<BlockItem> CRUSHER_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            CRUSHER
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
