package romelo333.rflux.blocks;


import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.ModBlocks;

import java.util.List;

public class LightBlock extends GenericLightBlock {

    public LightBlock(boolean onOff, Class<? extends TileEntity> clazz) {
        super("lightblock_" + (onOff ? "on" : "off"), clazz, onOff);
    }

    @SideOnly(Side.CLIENT)
    public void initBlockColors() {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
                if (pos != null && worldIn != null) {
                    TileEntity te = worldIn.getTileEntity(pos);
                    if (te instanceof LightTE) {
                        LightTE tileEntity = (LightTE) te;
                        return 0xffffff;    // @todo
//                        return tileEntity.getShieldColor();
                    }
                }
                return 0xffffff;
            }
        }, this);
    }

    @Override
    protected void clGetSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.clGetSubBlocks(itemIn, tab, subItems);
    }

    @Override
    public GenericLightBlock getLitBlock() {
        return ModBlocks.lightBlockOn;
    }

    @Override
    public GenericLightBlock getUnlitBlock() {
        return ModBlocks.lightBlockOff;
    }

    @Override
    public boolean hasNoRotation() {
        return true;
    }
}
