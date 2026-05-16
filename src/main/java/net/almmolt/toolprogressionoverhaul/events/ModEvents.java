package net.almmolt.toolprogressionoverhaul.events;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.ModBlockEntities;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrusherBlockEntity;
import net.almmolt.toolprogressionoverhaul.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;

import java.util.Iterator;

@EventBusSubscriber(modid = ToolProgressionOverhaul.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.ALLOYING_SMELTER_BLOCK_ENTITY.get(),
                (blockEntity, side) -> {
                    // Return different views based on the side the hopper is touching
                    if (side == Direction.DOWN) {
                        // Output side: Only expose the output slot (slot 1)
                        return new RangedWrapper(blockEntity.inventory, 4, 5);
                    } else if (side == Direction.UP) {
                        // All other sides: Only expose the input slot (slot 1)
                        return new RangedWrapper(blockEntity.inventory, 1, 2);
                    } else if (side == Direction.WEST) {
                        // All other sides: Only expose the input slot (slot 2)
                        return new RangedWrapper(blockEntity.inventory, 2, 3);
                    } else if (side == Direction.EAST) {
                        // All other sides: Only expose the input slot (slot 0)
                        return new RangedWrapper(blockEntity.inventory, 0, 1);
                    } else if (side == Direction.SOUTH) {
                        // All other sides: Only expose the input slot (slot 3)
                        return new RangedWrapper(blockEntity.inventory, 3, 4);
                    } else return new RangedWrapper(blockEntity.inventory, 0, 3);
                }
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.CRUSHER_BLOCK_ENTITY.get(),
                CrusherBlockEntity::getItemHandlerCapability
        );
    }

    @SubscribeEvent // on the game event bus
    public static void decreaseArmor(LivingIncomingDamageEvent event) {
        // We only apply this decrease to players and leave zombies etc. unchanged
        if (
            event.getSource().getEntity() instanceof LivingEntity attacker &&
            attacker.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)
        ) {
            if (!event.getEntity().getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
                Iterable<ItemStack> armor = event.getEntity().getArmorSlots();
                int silverPieces = 0;
                for (ItemStack armorPiece : armor) {
                    if (
                            armorPiece.is(ModItems.SILVER_HELMET.get()) ||
                                    armorPiece.is(ModItems.SILVER_CHESTPLATE.get()) ||
                                    armorPiece.is(ModItems.SILVER_LEGGINGS.get()) ||
                                    armorPiece.is(ModItems.SILVER_BOOTS.get())
                    ) silverPieces++;
                }

                if (silverPieces == 4) {
//                    final float finalSilverPieces = silverPieces;

//                // Add our reduction modifier callback.
//                event.addReductionModifier(
//                        // The reduction to target. See the DamageContainer.Reduction enum for possible values.
//                        DamageContainer.Reduction.ARMOR,
//                        // The modification to perform. Gets the damage container and the base reduction as inputs,
//                        // and outputs the new reduction. Both input and output reductions are floats.
//                        (container, baseReduction) -> baseReduction * (1.0f - finalSilverPieces / 5.0f)
//                );

                    float reductionMultiplier = 1.0f - (silverPieces * 0.2f);

                    event.setAmount(event.getAmount() * reductionMultiplier);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerFuels(FurnaceFuelBurnTimeEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.is(ModItems.COKE.get())) {
            event.setBurnTime(3200);
        }
    }
}
