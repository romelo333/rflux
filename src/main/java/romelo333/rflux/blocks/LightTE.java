package romelo333.rflux.blocks;

import mcjty.lib.entity.GenericEnergyReceiverTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;

public class LightTE extends GenericEnergyReceiverTileEntity implements ITickable {

    public LightTE() {
        super(10000, 100);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public void update() {
        if (!worldObj.isRemote){
            IBlockState state = worldObj.getBlockState(getPos());
            if (storage.getEnergyStored() >= 100) {
                worldObj.setBlockState(getPos(), state.withProperty(LightBlock.LIT, true), 3);
                storage.extractEnergy(100, false);
            } else {
                worldObj.setBlockState(getPos(), state.withProperty(LightBlock.LIT, false), 3);
            }
        }
    }
}
