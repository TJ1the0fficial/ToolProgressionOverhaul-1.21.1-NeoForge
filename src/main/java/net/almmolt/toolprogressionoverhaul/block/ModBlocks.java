package net.almmolt.toolprogressionoverhaul.block;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterBlockEntity;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterEntityBlock;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterMenu;
import net.almmolt.toolprogressionoverhaul.gui.ModMenus;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
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

    public static DeferredBlock<Block> DEEPSLATE_TIN_ORE = BLOCKS.register(
            "deepslate_tin_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .destroyTime(4.0f)
            )
    );
    public static DeferredItem<BlockItem> DEEPSLATE_TIN_ORE_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            DEEPSLATE_TIN_ORE
    );

    public static DeferredBlock<Block> TIN_BLOCK = BLOCKS.register(
            "tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .destroyTime(4.0f))
    );
    public static DeferredItem<BlockItem> TIN_BLOCK_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            TIN_BLOCK
    );

    public static DeferredBlock<Block> RAW_TIN_BLOCK = BLOCKS.register(
            "raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .destroyTime(4.0f))
    );
    public static DeferredItem<BlockItem> RAW_TIN_BLOCK_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            RAW_TIN_BLOCK
    );

    public static DeferredBlock<Block> BRONZE_BLOCK = BLOCKS.register(
            "bronze_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .requiresCorrectToolForDrops()
                    .destroyTime(4.0f))
    );
    public static DeferredItem<BlockItem> BRONZE_BLOCK_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            BRONZE_BLOCK
    );

    // Blocks for BlockEntities
    public static DeferredBlock<Block> ALLOYING_SMELTER = ModBlocks.BLOCKS.register(
            "alloying_smelter",
            () -> new AlloyingSmelterEntityBlock(BlockBehaviour.Properties.of())
            {
                // Inside your Block Registration
                @Override
                public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
                    if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
                        BlockEntity be = level.getBlockEntity(pos);
                        if (be instanceof AlloyingSmelterBlockEntity smelterBE) {
                            // This is the correct way to open a BlockEntity menu
                            serverPlayer.openMenu(smelterBE, pos);
                        }
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
    );
    public static DeferredItem<BlockItem> ALLOYING_SMELTER_ASITEM = ModItems.ITEMS.registerSimpleBlockItem(
            ALLOYING_SMELTER
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
