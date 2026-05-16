package net.almmolt.toolprogressionoverhaul.item.custom;

import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends DiggerItem {
    public HammerItem(Tier tier, Properties properties) {
        super(tier, BlockTags.MINEABLE_WITH_PICKAXE ,properties.component(DataComponents.TOOL, tier.createToolProperties(tier.getIncorrectBlocksForDrops())));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        // Check if the AMblock is a Pickaxe AMblock OR a Shovel AMblock
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return this.getTier().getSpeed();
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        // This ensures the hammer can get drops from blocks that require a shovel (like path blocks)
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    public Direction getHitFace(Player player) { // AI code
        // 1. Get the player's reach distance (usually 4.5 or 5.0)
        double reach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE);

        // 2. Perform a "pick" (ray trace)
        // true = hit liquids, false = ignore liquids
        BlockHitResult hitResult = (BlockHitResult) player.pick(reach, 1.0F, false);

        // 3. Get the face
        return hitResult.getDirection();
    }

    public List<BlockPos> get3x3Area(BlockPos origin, Direction face) { // AI code
        List<BlockPos> area = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    // If we hit Top/Bottom, we don't change Y
                    if (face.getAxis() == Direction.Axis.Y && y == 0) area.add(origin.offset(x, 0, z));
                        // If we hit North/South, we don't change Z
                    else if (face.getAxis() == Direction.Axis.Z && z == 0) area.add(origin.offset(x, y, 0));
                        // If we hit East/West, we don't change X
                    else if (face.getAxis() == Direction.Axis.X && x == 0) area.add(origin.offset(0, y, z));
                }
            }
        }
        return area;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        Tool tool = stack.get(DataComponents.TOOL);
        if (tool == null) {
            return false;
        } else {
            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                List<BlockPos> area;

                area = get3x3Area(pos,getHitFace((Player) miningEntity));

                for (BlockPos blockPos : area) if (!level.getBlockState(blockPos).is(Blocks.BEDROCK) && !level.getBlockState(blockPos).isEmpty()) {
                    level.destroyBlock(blockPos,true,miningEntity);
                    stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                }
            }
            return true;
        }
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) { // AI code
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) { // AI code
        // Create a copy of the hammer
        ItemStack damagedStack = stack.copy();

        // Increase damage by 1
        int newDamage = stack.getDamageValue() + 1;

        // Check if it should break
        if (newDamage >= stack.getMaxDamage()) {
            return ItemStack.EMPTY; // Item breaks and disappears
        }

        damagedStack.setDamageValue(newDamage);
        return damagedStack;
    }
}
