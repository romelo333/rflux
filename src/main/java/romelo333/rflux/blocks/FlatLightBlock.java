package romelo333.rflux.blocks;


import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import romelo333.rflux.ModBlocks;

import java.util.List;

public class FlatLightBlock extends GenericLightBlock<LightTE> {

    public FlatLightBlock(boolean onOff, Class<? extends LightTE> clazz) {
        super("flatlightblock_" + (onOff ? "on" : "off"), clazz, FlatLightItemBlock.class, onOff);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return onOff ? new LightTEFlatOn() : new LightTEFlatOff();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return onOff ? new LightTEFlatOn() : new LightTEFlatOff();
    }


    @Override
    public GenericLightBlock getLitBlock() {
        return ModBlocks.flatLightBlockOn;
    }

    @Override
    public GenericLightBlock getUnlitBlock() {
        return ModBlocks.flatLightBlockOff;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        if (!onOff) {
            for (BlockColor color : BlockColor.values()) {
                items.add(makeColoredBlock(this, color, 1));
            }
        }
    }

    @Override
    public boolean hasNoRotation() {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
