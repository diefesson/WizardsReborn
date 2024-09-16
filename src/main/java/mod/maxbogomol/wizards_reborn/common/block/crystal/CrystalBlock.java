package mod.maxbogomol.wizards_reborn.common.block.crystal;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;
import java.util.stream.Stream;

public class CrystalBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public PolishingType polishing;
    public CrystalType type;
    private static Random random = new Random();

    private static final VoxelShape FACETED_SHAPE = Block.box(5, 0, 5, 11, 9, 11);
    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 0, 5, 11, 9, 11),
            Block.box(3, 0, 4, 6, 3, 7),
            Block.box(9, 0, 3, 12, 3, 6),
            Block.box(9, 0, 10, 12, 3, 13),
            Block.box(4, 0, 9, 7, 3, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public CrystalBlock(CrystalType type, PolishingType polishing, Properties properties) {
        super(properties);
        this.type = type;
        this.polishing = polishing;
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (polishing == WizardsRebornCrystals.CRYSTAL) {
            return SHAPE;
        }

        return FACETED_SHAPE;
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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return getBlockConnected(state).getOpposite() == direction && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = getBlockConnected(state).getOpposite();
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    protected static Direction getBlockConnected(BlockState state) {
        return Direction.UP;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (polishing.hasParticle()) {
            Color color = polishing.getColor();
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.015f)
                    .flatRandomOffset(0.25f, 0.25f, 0.25f)
                    .spawn(level, pos.getX() + 0.5F, pos.getY() + 0.35F, pos.getZ() + 0.5F);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.isClientSide()) {
            if (!player.isCreative()) {
                Color color = type.getColor();
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.35f, 0).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .randomVelocity(0.035f)
                        .randomOffset(0.25f)
                        .repeat(level, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 25);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrystalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof BlockSimpleInventory) {
                Containers.dropContents(level, pos, ((BlockSimpleInventory) tile).getItemHandler());
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}