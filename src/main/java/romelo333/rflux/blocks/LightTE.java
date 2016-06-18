package romelo333.rflux.blocks;

import mcjty.lib.entity.GenericEnergyReceiverTileEntity;
import mcjty.lib.network.Argument;
import mcjty.lib.varia.RedstoneMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.chunk.Chunk;
import romelo333.rflux.Config;

import java.util.Map;

import static romelo333.rflux.blocks.GenericLight.LIT;

public class LightTE extends GenericEnergyReceiverTileEntity implements ITickable {

    public static final String CMD_MODE = "mode";

    private int mode = 0;

    @Override
    protected boolean needsRedstoneMode() {
        return true;
    }

    public LightTE() {
        super(Config.LIGHTBLOCK_MAXRF, Config.LIGHTBLOCK_RECEIVEPERTICK);
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            boolean lit = isMachineEnabled();

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

                if (lit) {
                    System.out.println("ON: lit = " + lit);
                    for (int x = -30 ; x <= 30 ; x++) {
                        for (int y = -30; y <= 30; y++) {
                            for (int z = -30; z <= 30; z++) {
                                BlockPos p = getPos().add(x, y, z);
                                Chunk chunk = worldObj.getChunkFromBlockCoords(p);
                                chunk.setLightFor(EnumSkyBlock.BLOCK, p, 15);
                                worldObj.notifyLightSet(p);
                            }
                        }
                    }
                } else {
                    System.out.println("OFF: lit = " + lit);
                    for (int x = -30 ; x <= 30 ; x++) {
                        for (int y = -30; y <= 30; y++) {
                            for (int z = -30; z <= 30; z++) {
                                BlockPos p = getPos().add(x, y, z);
                                Chunk chunk = worldObj.getChunkFromBlockCoords(p);
                                chunk.setLightFor(EnumSkyBlock.BLOCK, p, 0);
                                worldObj.notifyLightSet(p);
                            }
                        }
                    }
                }
            }
        }
    }

    public void setMode(int mode) {
        this.mode = mode;
        markDirtyClient();
    }

    public int getMode() {
        return mode;
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound tagCompound) {
        super.readRestorableFromNBT(tagCompound);
        mode = tagCompound.getByte("mode");
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound tagCompound) {
        super.writeRestorableToNBT(tagCompound);
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
            setRSMode(RedstoneMode.getMode(m));
            setMode(args.get("mode").getInteger());
            return true;
        }
        return false;
    }


}
