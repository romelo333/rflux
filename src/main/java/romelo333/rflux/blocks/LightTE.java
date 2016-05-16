package romelo333.rflux.blocks;

import mcjty.lib.entity.GenericEnergyReceiverTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;
import romelo333.rflux.Config;

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
            if (storage.getEnergyStored() >= Config.LIGHTBLOCK_RFPERTICK) {
                worldObj.setBlockState(getPos(), state.withProperty(LightBlock.LIT, true), 3);
                storage.extractEnergy(Config.LIGHTBLOCK_RFPERTICK, false);
            } else {
                worldObj.setBlockState(getPos(), state.withProperty(LightBlock.LIT, false), 3);
            }
        }
    }
}
