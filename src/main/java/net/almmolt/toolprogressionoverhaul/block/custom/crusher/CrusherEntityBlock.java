package net.almmolt.toolprogressionoverhaul.block.custom.crusher;

import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrusherEntityBlock extends Block implements EntityBlock {
    public CrusherEntityBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WORKING, false)); // Default is idle
    }

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WORKING = BooleanProperty.create("working");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,WORKING);
    }

    public static Properties getProperties() {
        return Properties.of()
                .destroyTime(4.0f)
                .requiresCorrectToolForDrops()
                .mapColor(MapColor.STONE)
                .strength(3.5f);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        // 1. Check if the AMblock is actually being replaced by a DIFFERENT AMblock
        // (This prevents items from dropping if you just rotate the AMblock)
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CrusherBlockEntity BE) {
                // 2. Drop the contents of the ItemStackHandler
                for (int i = 0; i < BE.inventory.getSlots(); i++) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), BE.inventory.getStackInSlot(i));
                }
                // 3. Update comparators if any are attached
                level.updateNeighbourForOutputSignal(pos, this);
            }

            // 4. Important: Call super AFTER dropping items, but before finishing
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        Player nearestPlayer = level.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 8.0, false);

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CrusherBlockEntity blockEntity) {
            ItemStack stackInHand = player.getItemInHand(InteractionHand.MAIN_HAND);

            // Example: Putting the item in
            if (!stackInHand.isEmpty() && blockEntity.inventory.getStackInSlot(0).isEmpty()) {
                if (!player.isCrouching() && stackInHand.is(ModTags.CRUSHING_WHEELS)) {
                    blockEntity.inventory.insertItem(0, stackInHand.split(1), false);

                    // 1. Mark the AMblock as changed so it saves to disk
                    blockEntity.setChanged();

                    // 2. Tell the world to sync this position to all nearby clients
                    level.sendBlockUpdated(pos, state, state, 3);

                    return InteractionResult.SUCCESS;
                }
            } else if (stackInHand.isEmpty() && !blockEntity.inventory.getStackInSlot(0).isEmpty() && player.isCrouching()) {
                ItemEntity resultEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, blockEntity.inventory.getStackInSlot(0));
                resultEntity.setPickUpDelay(10);
                if (nearestPlayer != null) {
                    // 2. Get the positions
                    Vec3 itemPos = new Vec3(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5);
                    Vec3 playerPos = nearestPlayer.position().add(0, 1.0, 0); // Aim for the player's chest, not their feet

                    // 3. Calculate the direction (Target - Start)
                    // .normalize() makes the speed consistent regardless of distance
                    // .scale(0.3) is the speed (0.1 is slow, 0.5 is very fast)
                    Vec3 motion = playerPos.subtract(itemPos).normalize().scale(0.3);

                    // 4. Set the movement
                    resultEntity.setDeltaMovement(motion);
                } else {
                    // Fallback: if no player is nearby, just pop it up like before
                    resultEntity.setDeltaMovement(0, 0.2, 0);
                }
                level.addFreshEntity(resultEntity);
                blockEntity.inventory.extractItem(0,blockEntity.inventory.getStackInSlot(0).getCount(),false);
                blockEntity.setChanged();

                level.sendBlockUpdated(pos, state, state, 3);

                return  InteractionResult.SUCCESS;
            } else if (stackInHand.isEmpty() && !blockEntity.inventory.getStackInSlot(1).isEmpty() && !player.isCrouching()) {
                ItemEntity resultEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, blockEntity.inventory.getStackInSlot(1));
                resultEntity.setPickUpDelay(10);
                if (nearestPlayer != null) {
                    // 2. Get the positions
                    Vec3 itemPos = new Vec3(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5);
                    Vec3 playerPos = nearestPlayer.position().add(0, 1.0, 0); // Aim for the player's chest, not their feet

                    // 3. Calculate the direction (Target - Start)
                    // .normalize() makes the speed consistent regardless of distance
                    // .scale(0.3) is the speed (0.1 is slow, 0.5 is very fast)
                    Vec3 motion = playerPos.subtract(itemPos).normalize().scale(0.3);

                    // 4. Set the movement
                    resultEntity.setDeltaMovement(motion);
                } else {
                    // Fallback: if no player is nearby, just pop it up like before
                    resultEntity.setDeltaMovement(0, 0.2, 0);
                }
                level.addFreshEntity(resultEntity);
                blockEntity.inventory.extractItem(1,blockEntity.inventory.getStackInSlot(1).getCount(),false);
                blockEntity.setChanged();

                level.sendBlockUpdated(pos, state, state, 3);

                return  InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CrusherBlockEntity BE) {
            // This is a helper method to calculate signal strength based on inventory fullness
            return net.minecraft.world.inventory.AbstractContainerMenu.getRedstoneSignalFromBlockEntity(be);
        }
        return 0;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrusherBlockEntity(blockPos,blockState);
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
        return createTickerHelper(type, ModBlockEntities.CRUSHER_BLOCK_ENTITY.get(), CrusherBlockEntity::tick);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    // 5. Add standard rotation/mirroring support (Good for world-gen/blueprints)
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
