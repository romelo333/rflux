package romelo333.rflux.blocks;

import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlatLightItemBlock extends GenericItemBlock {

    public FlatLightItemBlock(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        boolean rc = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof LightTE) {
            side = side.getOpposite();
            world.setBlockState(pos, newState.getBlock().getDefaultState().withProperty(GenericBlock.FACING, side));
        }
        return rc;
    }
}
