package net.almmolt.toolprogressionoverhaul.item.grades;

import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.almmolt.toolprogressionoverhaul.item.custom.GalvanizedIronArmorItem;
import net.almmolt.toolprogressionoverhaul.item.custom.HammerItem;
import net.almmolt.toolprogressionoverhaul.tag.ModTags;
import net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.almmolt.toolprogressionoverhaul.item.custom.GalvanizedIronArmorItem.GALVANIZED_IRON_CHANCE;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMarmor.registerArmorMaterial;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.*;
import static net.almmolt.toolprogressionoverhaul.util.AmmoltUtilities.AMtool.setAttackSpeed;

public class GalvanizedIron {
    // Galvanized Iron down
    static float galvanizedIronChance = 0.5f;
    public static Tier GALVANIZED_IRON_TIER;
    public static  Holder<ArmorMaterial> GALVANIZED_IRON_MATERIAL;

    public static  DeferredItem<SwordItem> GALVANIZED_IRON_SWORD;

    public static  DeferredItem<ShovelItem> GALVANIZED_IRON_SHOVEL;

    public static  DeferredItem<PickaxeItem> GALVANIZED_IRON_PICKAXE;

    public static  DeferredItem<AxeItem> GALVANIZED_IRON_AXE;

    public static  DeferredItem<HoeItem> GALVANIZED_IRON_HOE;

    public static  DeferredItem<HammerItem> GALVANIZED_IRON_HAMMER;

    public static  ToolSet GALVANIZED_IRON_TOOLS;

    public static  DeferredItem<ArmorItem> GALVANIZED_IRON_HELMET;

    public static  DeferredItem<ArmorItem> GALVANIZED_IRON_CHESTPLATE;

    public static  DeferredItem<ArmorItem> GALVANIZED_IRON_LEGGINGS;

    public static  DeferredItem<ArmorItem> GALVANIZED_IRON_BOOTS;

    public static  AMarmor.ArmorSet GALVANIZED_IRON_ARMOR;

    public static void register() {
        // tier
        GALVANIZED_IRON_TIER = new SimpleTier(
                ModTags.INCORRECT_FOR_GALVANIZED_IRON_TOOL,
                400,
                5.0f,
                1.5f,
                19,
                () -> Ingredient.of(ModItems.GALVANIZED_IRON_INGOT)
        );

        // armor material
        GALVANIZED_IRON_MATERIAL = registerArmorMaterial(
                "galvanized_iron",ModItems.GALVANIZED_IRON_INGOT,19,
                2,
                5,
                4,
                2,
                5
        );

        // tools
        GALVANIZED_IRON_SWORD = ModItems.ITEMS.register(
                "galvanized_iron_sword",
                () -> new SwordItem(
                        GALVANIZED_IRON_TIER,
                        new Item.Properties().attributes(
                                SwordItem.createAttributes(
                                        GALVANIZED_IRON_TIER,
                                        setSwordAttackDamage(6.0f),
                                        setAttackSpeed(1.6f)
                                )
                        )
                ) {
                    @Override
                    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
                        Tool tool = stack.get(DataComponents.TOOL);
                        if (tool == null) {
                            return false;
                        } else {
                            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                                if (Math.random() < galvanizedIronChance) stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                            }
                            return true;
                        }
                    }
                }
        );

        GALVANIZED_IRON_SHOVEL = ModItems.ITEMS.register(
                "galvanized_iron_shovel",
                () -> new ShovelItem(
                        GALVANIZED_IRON_TIER,
                        new Item.Properties().attributes(
                                ShovelItem.createAttributes(
                                        GALVANIZED_IRON_TIER,
                                        setAttackDamage(5.0f),
                                        setAttackSpeed(1.0f)
                                )
                        )
                ) {
                    @Override
                    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
                        Tool tool = stack.get(DataComponents.TOOL);
                        if (tool == null) {
                            return false;
                        } else {
                            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                                if (Math.random() < GALVANIZED_IRON_CHANCE) stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                            }
                            return true;
                        }
                    }
                }
        );

        GALVANIZED_IRON_PICKAXE = ModItems.ITEMS.register(
                "galvanized_iron_pickaxe",
                () -> new PickaxeItem(
                        GALVANIZED_IRON_TIER,
                        new Item.Properties().attributes(
                                PickaxeItem.createAttributes(
                                        GALVANIZED_IRON_TIER,
                                        setAttackDamage(4.5f),
                                        setAttackSpeed(1.2f)
                                )
                        )
                ) {
                    @Override
                    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
                        Tool tool = stack.get(DataComponents.TOOL);
                        if (tool == null) {
                            return false;
                        } else {
                            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                                if (Math.random() < GALVANIZED_IRON_CHANCE) stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                            }
                            return true;
                        }
                    }
                }
        );

        GALVANIZED_IRON_AXE = ModItems.ITEMS.register(
                "galvanized_iron_axe",
                () -> new AxeItem(
                        GALVANIZED_IRON_TIER,
                        new Item.Properties().attributes(
                                AxeItem.createAttributes(
                                        GALVANIZED_IRON_TIER,
                                        setSwordAttackDamage(9.0f),
                                        setAttackSpeed(0.9f)
                                )
                        )
                ) {
                    @Override
                    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
                        Tool tool = stack.get(DataComponents.TOOL);
                        if (tool == null) {
                            return false;
                        } else {
                            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                                if (Math.random() < GALVANIZED_IRON_CHANCE) stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                            }
                            return true;
                        }
                    }
                }
        );

        GALVANIZED_IRON_HOE = ModItems.ITEMS.register(
                "galvanized_iron_hoe",
                () -> new HoeItem(
                        GALVANIZED_IRON_TIER,
                        new Item.Properties().attributes(
                                HoeItem.createAttributes(
                                        GALVANIZED_IRON_TIER,
                                        setSwordAttackDamage(1.0f),
                                        setAttackSpeed(2.5f)
                                )
                        )
                ) {
                    @Override
                    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
                        Tool tool = stack.get(DataComponents.TOOL);
                        if (tool == null) {
                            return false;
                        } else {
                            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                                if (Math.random() < GALVANIZED_IRON_CHANCE) stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                            }
                            return true;
                        }
                    }
                }
        );

        GALVANIZED_IRON_HAMMER = ModItems.ITEMS.register(
                "galvanized_iron_hammer",
                () -> new HammerItem(
                        GALVANIZED_IRON_TIER,
                        new Item.Properties().attributes(
                                HammerItem.createAttributes(
                                        GALVANIZED_IRON_TIER,
                                        setAttackDamage(5.0f),
                                        setAttackSpeed(0.6f)
                                )
                        )
                ) {
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
                                    if (tool == null) {
                                        return false;
                                    } else {
                                        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                                            if (Math.random() < GALVANIZED_IRON_CHANCE) stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                                        }
                                    }
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
        );

        GALVANIZED_IRON_TOOLS = new ToolSet(
                GALVANIZED_IRON_SWORD,"galvanized_iron_sword","Galvanized Iron Sword",
                GALVANIZED_IRON_SHOVEL,"galvanized_iron_shovel", "Galvanized Iron Shovel",
                GALVANIZED_IRON_PICKAXE, "galvanized_iron_pickaxe", "Galvanized Iron Pickaxe",
                GALVANIZED_IRON_AXE, "galvanized_iron_axe", "Galvanized Iron Axe",
                GALVANIZED_IRON_HOE, "galvanized_iron_hoe", "Galvanized Iron Hoe",
                GALVANIZED_IRON_HAMMER, "galvanized_iron_hammer", "Galvanized Iron Hammer"
        );

        // armor
        GALVANIZED_IRON_HELMET = ModItems.ITEMS.register(
                "galvanized_iron_helmet",
                () -> new GalvanizedIronArmorItem(
                        GALVANIZED_IRON_MATERIAL,
                        ArmorItem.Type.HELMET,
                        new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
                )
        );

        GALVANIZED_IRON_CHESTPLATE = ModItems.ITEMS.register(
                "galvanized_iron_chestplate",
                () -> new GalvanizedIronArmorItem(
                        GALVANIZED_IRON_MATERIAL,
                        ArmorItem.Type.CHESTPLATE,
                        new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
                )
        );

        GALVANIZED_IRON_LEGGINGS = ModItems.ITEMS.register(
                "galvanized_iron_leggings",
                () -> new GalvanizedIronArmorItem(
                        GALVANIZED_IRON_MATERIAL,
                        ArmorItem.Type.LEGGINGS,
                        new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
                )
        );

        GALVANIZED_IRON_BOOTS = ModItems.ITEMS.register(
                "galvanized_iron_boots",
                () -> new GalvanizedIronArmorItem(
                        GALVANIZED_IRON_MATERIAL,
                        ArmorItem.Type.BOOTS,
                        new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
                )
        );

        GALVANIZED_IRON_ARMOR = new AMarmor.ArmorSet(
                GALVANIZED_IRON_HELMET,"galvanized_iron_helmet","Galvanized Iron Helmet",
                GALVANIZED_IRON_CHESTPLATE,"galvanized_iron_chestplate","Galvanized Iron Chestplate",
                GALVANIZED_IRON_LEGGINGS,"galvanized_iron_leggings","Galvanized Iron Leggings",
                GALVANIZED_IRON_BOOTS,"galvanized_iron_boots","Galvanized Iron Boots"
        );
    }
}
