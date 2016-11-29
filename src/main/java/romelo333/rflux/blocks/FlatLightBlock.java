package romelo333.rflux.blocks;


import net.minecraft.block.state.IBlockState;
import romelo333.rflux.ModBlocks;

public class FlatLightBlock extends GenericLightBlock {

    public FlatLightBlock(boolean onOff, Class<? extends LightTE> clazz) {
        super("flatlightblock_" + (onOff ? "on" : "off"), clazz, FlatLightItemBlock.class, onOff);
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
