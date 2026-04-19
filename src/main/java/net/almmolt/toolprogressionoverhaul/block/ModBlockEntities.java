package net.almmolt.toolprogressionoverhaul.block;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.alloyingsmelter.AlloyingSmelterBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ToolProgressionOverhaul.MODID);

    public static final Supplier<BlockEntityType<AlloyingSmelterBlockEntity>> ALLOYING_SMELTER_BLOCK_ENTITY = ModBlockEntities.BLOCK_ENTITY_TYPES.register(
            "alloying_smelter_block_entity",
            () -> BlockEntityType.Builder.of(
                            AlloyingSmelterBlockEntity::new,
                            ModBlocks.ALLOYING_SMELTER.get()
                    )
                    .build(null)
    );

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
