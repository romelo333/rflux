package romelo333.rflux.blocks;


import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LightBlock extends GenericLight {

    public LightBlock() {
        super("lightblock", LightTE.class);
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
