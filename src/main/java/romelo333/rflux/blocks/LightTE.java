package romelo333.rflux.blocks;

import mcjty.lib.entity.GenericEnergyReceiverTileEntity;
import mcjty.lib.network.Argument;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import romelo333.rflux.Config;
import romelo333.rflux.varia.RedstoneMode;

import java.util.Map;

import static romelo333.rflux.blocks.GenericLight.LIT;

public class LightTE extends GenericEnergyReceiverTileEntity implements ITickable {

    public static final String CMD_MODE = "mode";

    private RedstoneMode redstoneMode = RedstoneMode.REDSTONE_IGNORED;
    private int mode = 0;
    private boolean powered;

    public LightTE() {
        super(Config.LIGHTBLOCK_MAXRF, Config.LIGHTBLOCK_RECEIVEPERTICK);
    }

    @Override
    public void setPowered(int powered) {
        this.powered = powered > 0;
        markDirty();
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            boolean lit = true;

            if (redstoneMode != RedstoneMode.REDSTONE_IGNORED) {
                boolean rs = powered;
                if (redstoneMode == RedstoneMode.REDSTONE_OFFREQUIRED && rs) {
                    lit = false;
                } else if (redstoneMode == RedstoneMode.REDSTONE_ONREQUIRED && !rs) {
                    lit = false;
                }
            }

            IBlockState state = worldObj.getBlockState(getPos());

            if (lit) {
                // We are still potentially lit so do this.
                int rf = mode == 1 ? Config.LIGHTBLOCK_RFPERTICK_L1 : Config.LIGHTBLOCK_RFPERTICK_L0;
                if (storage.getEnergyStored() >= rf) {
                    storage.extractEnergy(rf, false);
                } else {
                    lit = false;
                }
            }

            boolean old = state.getValue(LIT);
            if (old != lit) {
                worldObj.setBlockState(getPos(), state.withProperty(LIT, lit), 3);
            }
        }
    }

    public RedstoneMode getRedstoneMode() {
        return redstoneMode;
    }

    public void setRedstoneMode(RedstoneMode redstoneMode) {
        this.redstoneMode = redstoneMode;
        markDirtyClient();
    }

    public void setMode(int mode) {
        this.mode = mode;
        markDirtyClient();
    }

    public int getMode() {
        return mode;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        powered = tagCompound.getBoolean("powered");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("powered", powered);
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound tagCompound) {
        super.readRestorableFromNBT(tagCompound);
        int m = tagCompound.getByte("rsMode");
        redstoneMode = RedstoneMode.values()[m];
        mode = tagCompound.getByte("mode");
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound tagCompound) {
        super.writeRestorableToNBT(tagCompound);
        tagCompound.setByte("rsMode", (byte) redstoneMode.ordinal());
        tagCompound.setByte("mode", (byte) mode);
    }

    @Override
    public boolean execute(EntityPlayerMP playerMP, String command, Map<String, Argument> args) {
        boolean rc = super.execute(playerMP, command, args);
        if (rc) {
            return true;
        }
        if (CMD_MODE.equals(command)) {
            String m = args.get("rs").getString();
            setRedstoneMode(RedstoneMode.getMode(m));
            setMode(args.get("mode").getInteger());
            return true;
        }
        return false;
    }


}
