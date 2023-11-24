package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FluidStorageBaseItem extends BlockItem implements IFluidItem {

    public FluidStorageBaseItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("BlockEntityTag")) {
            CompoundTag tileNbt = new CompoundTag();
            tileNbt.put("fluidTank", nbt.getCompound("fluidTank"));
            nbt.put("BlockEntityTag", tileNbt);
        }

        return BlockItem.updateCustomBlockEntityTag(level, player, pos, stack);
    }

    @Override
    public int getMaxFluid(ItemStack stack) {
        if (getBlock() instanceof EntityBlock tileBlock) {
            BlockEntity tile = tileBlock.newBlockEntity(new BlockPos(0, 0, 0), getBlock().defaultBlockState());
            if (tile instanceof IFluidTileEntity steamTile) {
                return steamTile.getFluidMaxAmount();
            }
        }
        return 0;
    }
}