package net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities;

import net.almmolt.toolprogressionoverhaul.block.ModBlocks;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;

public class AMblock {
    public static record BlockSet(
            DeferredBlock<Block> block, DeferredItem<BlockItem> blockItem, String blockId, String blockDisplayName
    ) {}

    public static final List<BlockSet> registeredBlocks = new ArrayList<>();

    public static BlockSet registerBlock(
            String blockId, String blockDisplayName
    ) {
        DeferredBlock<Block> BLOCK = ModBlocks.BLOCKS.register(
                blockId,
                () -> new Block(BlockBehaviour.Properties.of()
                        .requiresCorrectToolForDrops()
                        .destroyTime(4.0f)
                )
        );
        DeferredItem<BlockItem> BLOCK_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
                BLOCK
        );

        registeredBlocks.add(new BlockSet(BLOCK,BLOCK_ASITEM,blockId,blockDisplayName));

        return new BlockSet(BLOCK,BLOCK_ASITEM,blockId,blockDisplayName);
    }

    public static void registerNewAdvancementTab() {

    }
}
