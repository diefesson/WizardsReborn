package mod.maxbogomol.wizards_reborn.common.block.flammable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class FlammableBlock extends Block {
    public int fireSpeed;
    public int flammability;

    public FlammableBlock(Properties properties, int fireSpeed, int flammability) {
        super(properties);
        this.fireSpeed = fireSpeed;
        this.flammability = flammability;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return flammability;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return fireSpeed;
    }
}
