package romelo333.rflux.blocks;


import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import romelo333.rflux.ModBlocks;

public class LightBlock extends GenericLightBlock {

    public LightBlock(boolean onOff) {
        super("lightblock_" + (onOff ? "on" : "off"), LightTE.class, onOff);
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

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LightTE();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new LightTE();
    }
}
