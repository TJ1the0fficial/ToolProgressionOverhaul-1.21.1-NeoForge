package net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class AlloyingSmelterEntityBlock extends Block implements EntityBlock {
    // Constructor deferring to super.
    public AlloyingSmelterEntityBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    // 1. ADD THE PROPERTY HERE
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    // 3. PUT "C" (createBlockStateDefinition) HERE
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    // 4. PUT "D" (getStateForPlacement) HERE
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    // Return a new instance of our block entity here.
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AlloyingSmelterBlockEntity(pos, state);
    }
}
