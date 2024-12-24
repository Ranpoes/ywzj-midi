package org.ywzj.midi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MusicStandBlock extends HorizontalBlock {

    private static final VoxelShape SHAPE_NORTH = Shapes.box(0.14, 0.01, 0.4, 0.86, 1.5, 0.95);
    private static final VoxelShape SHAPE_SOUTH = Shapes.box(0.14, 0.01, 0.05, 0.86, 1.5, 0.6);
    private static final VoxelShape SHAPE_EAST = Shapes.box(0.05, 0.01, 0.14, 0.6, 1.5, 0.86);
    private static final VoxelShape SHAPE_WEST = Shapes.box(0.4, 0.01, 0.14, 0.95, 1.5, 0.86);

    public MusicStandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            case DOWN, UP -> null;
        };
    }

}
