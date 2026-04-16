package net.almmolt.toolprogressionoverhaul.block;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ToolProgressionOverhaul.MODID);

    public static DeferredBlock<Block> TIN_ORE = BLOCKS.register(
            "tin_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .destroyTime(4.0f)
            )
    );
    public static DeferredItem<BlockItem> TIN_ORE_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            TIN_ORE
    );

    public static DeferredBlock<Block> TIN_DEEPSLATE_ORE = BLOCKS.register(
            "tin_deepslate_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .destroyTime(4.0f)
            )
    );
    public static DeferredItem<BlockItem> TIN_DEEPSLATE_ORE_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            TIN_DEEPSLATE_ORE
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
