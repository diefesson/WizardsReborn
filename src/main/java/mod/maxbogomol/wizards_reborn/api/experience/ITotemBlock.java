package mod.maxbogomol.wizards_reborn.api.experience;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface ITotemBlock {
    InteractionResult useTotem(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit);
}
