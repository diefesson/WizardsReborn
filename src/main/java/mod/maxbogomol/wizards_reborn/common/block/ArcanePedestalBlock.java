package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.common.item.ArcanemiconItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcanePedestalTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.TileSimpleInventory;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ArcanePedestalBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public static Map<Block, Block> blocksList = new HashMap<>();

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 13, 5, 11, 14, 11),
            Block.box(2, 11, 2, 14, 13, 14),
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(2, 0, 2, 14, 2, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public ArcanePedestalBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileSimpleInventory) {
                Containers.dropContents(world, pos, ((TileSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ArcanePedestalTileEntity tile = (ArcanePedestalTileEntity) world.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (player.isShiftKeyDown()) {
            if (stack.getItem() instanceof ArcanemiconItem) {
                return InteractionResult.PASS;
            }
        }

        if ((!stack.isEmpty()) && (tile.getItemHandler().getItem(0).isEmpty())) {
            if (stack.getCount() > 1) {
                player.getItemInHand(hand).setCount(stack.getCount() - 1);
                stack.setCount(1);
                tile.getItemHandler().setItem(0, stack);
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(tile);
                world.playSound(null, pos, SoundEvents.BAMBOO_WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 0.75f);
                return InteractionResult.SUCCESS;
            } else {
                tile.getItemHandler().setItem(0, stack);
                player.getInventory().removeItem(player.getItemInHand(hand));
                world.updateNeighbourForOutputSignal(pos, this);
                PacketUtils.SUpdateTileEntityPacket(tile);
                world.playSound(null, pos, SoundEvents.BAMBOO_WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 0.75f);
                return InteractionResult.SUCCESS;
            }
        }

        if (!tile.getItemHandler().getItem(0).isEmpty()) {
            if (player.getInventory().getSlotWithRemainingSpace(tile.getItemHandler().getItem(0)) != -1 || player.getInventory().getFreeSlot() > -1) {
                player.getInventory().add(tile.getItemHandler().getItem(0).copy());
            } else {
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, tile.getItemHandler().getItem(0).copy()));
            }
            tile.getItemHandler().removeItem(0, 1);
            world.updateNeighbourForOutputSignal(pos, this);
            PacketUtils.SUpdateTileEntityPacket(tile);
            world.playSound(null, pos, SoundEvents.BAMBOO_WOOD_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ArcanePedestalTileEntity(pPos, pState);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        TileSimpleInventory tile = (TileSimpleInventory) level.getBlockEntity(pos);
        return AbstractContainerMenu.getRedstoneSignalFromContainer(tile.getItemHandler());
    }
}
