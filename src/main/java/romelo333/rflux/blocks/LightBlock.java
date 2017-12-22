package romelo333.rflux.blocks;


import mcjty.lib.container.BaseBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import romelo333.rflux.ModBlocks;

public class LightBlock extends GenericLightBlock<LightTE> {

    public LightBlock(boolean onOff, Class<? extends LightTE> clazz) {
        super("lightblock_" + (onOff ? "on" : "off"), clazz, onOff);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return onOff ? new LightTE() : new LightTEOff();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return onOff ? new LightTE() : new LightTEOff();
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
    public GenericLightBlock getLitBlock() {
        return ModBlocks.lightBlockOn;
    }

    @Override
    public GenericLightBlock getUnlitBlock() {
        return ModBlocks.lightBlockOff;
    }

    @Override
    public RotationType getRotationType() {
        return RotationType.NONE;
    }
}
