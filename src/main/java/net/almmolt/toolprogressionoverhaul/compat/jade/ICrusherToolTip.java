package net.almmolt.toolprogressionoverhaul.compat.jade;

import net.almmolt.toolprogressionoverhaul.ToolProgressionOverhaul;
import net.almmolt.toolprogressionoverhaul.block.custom.crusher.CrusherBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ICrusherToolTip implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(
            ITooltip tooltip,
            BlockAccessor accessor,
            IPluginConfig config
    ) {
        if (accessor.getServerData().contains("progress")) {
            tooltip.add(
                    Component.translatable(
                            ToolProgressionOverhaul.MODID+".crusher_progress",
                            accessor.getServerData().getInt("progress")
                    )
            );
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        CrusherBlockEntity crusher = (CrusherBlockEntity) accessor.getBlockEntity();
        data.putInt("progress", Math.round((float) crusher.progress / (float) crusher.maxProgress * 100));
    }

    @Override
    public ResourceLocation getUid() {
        return ITPOJadePlugin.CRUSHER_PROGRESS;
    }
}