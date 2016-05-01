package romelo333.rflux.blocks;


import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class LightBlock extends GenericLight {

    public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public LightBlock() {
        super("lightblock", LightTE.class);
    }

    @Override
    public int quantityDropped(Random rnd) {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LightTE();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new LightTE();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING).getIndex() - 2) + (state.getValue(LIT) ? 8 : 0);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING, LIT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, getFacingHoriz(meta&7)).withProperty(LIT, (meta&8) != 0);
    }

    public static EnumFacing getFacingHoriz(int meta) {
        return EnumFacing.values()[meta + 2];
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
}
