package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class AlloyingSmelterEntityBlock extends Block implements EntityBlock {
    public AlloyingSmelterEntityBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static Properties getProperties() {
        return Properties.of()
                .destroyTime(4.0f)
                .requiresCorrectToolForDrops()
                .mapColor(MapColor.STONE)
                .strength(3.5f)
                // 13 is the brightness (0-15).
                // state.getValue(LIT) ? 13 : 0 means "if lit is true, light is 13, otherwise 0"
                .lightLevel(state -> state.getValue(AlloyingSmelterEntityBlock.LIT) ? 13 : 0);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (containerId, playerInventory, player) -> new AlloyingSmelterMenu(containerId, playerInventory, (AlloyingSmelterBlockEntity) newBlockEntity(pos,state)),
                Component.translatable("block.toolprogressionoverhaul.alloying_smelter_block"));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult result) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof AlloyingSmelterBlockEntity smelterBE) {
                // Note the third parameter 'pos' here!
                // This sends the position to the client automatically.
                serverPlayer.openMenu(new SimpleMenuProvider(
                        (id, inv, p) -> new AlloyingSmelterMenu(id, inv, smelterBE),
                        Component.translatable("block.toolprogressionoverhaul.alloying_smelter_block")), pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        // 1. Check if the AMblock is actually being replaced by a DIFFERENT AMblock
        // (This prevents items from dropping if you just rotate the AMblock)
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof AlloyingSmelterBlockEntity smelterBE) {
                // 2. Drop the contents of the ItemStackHandler
                for (int i = 0; i < smelterBE.inventory.getSlots(); i++) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), smelterBE.inventory.getStackInSlot(i));
                }
                // 3. Update comparators if any are attached
                level.updateNeighbourForOutputSignal(pos, this);
            }

            // 4. Important: Call super AFTER dropping items, but before finishing
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof AlloyingSmelterBlockEntity smelterBE) {
            // This is a helper method to calculate signal strength based on inventory fullness
            return net.minecraft.world.inventory.AbstractContainerMenu.getRedstoneSignalFromBlockEntity(be);
        }
        return 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // .getHorizontalDirection() is the way the player is looking.
        // .getOpposite() makes the "front" of the AMblock face the player.
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // This tells Minecraft that this AMblock is allowed to have a 'facing' property
        builder.add(FACING, LIT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AlloyingSmelterBlockEntity(blockPos,blockState);
    }

    private static <E extends BlockEntity, A extends BlockEntity> @Nullable BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> type, BlockEntityType<E> checkedType, BlockEntityTicker<? super E> ticker
    ) {
        return checkedType == type ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return createTickerHelper(type, ModBlockEntities.ALLOYING_SMELTER_BLOCK_ENTITY.get(), AlloyingSmelterBlockEntity::tick);
    }
}
