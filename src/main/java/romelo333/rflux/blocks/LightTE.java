package romelo333.rflux.blocks;

import mcjty.lib.entity.GenericEnergyReceiverTileEntity;
import mcjty.lib.network.Argument;
import mcjty.lib.varia.RedstoneMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import romelo333.rflux.Config;
import romelo333.rflux.ModBlocks;

import java.util.Map;

public class LightTE extends GenericEnergyReceiverTileEntity implements ITickable {

    public static final String CMD_MODE = "mode";

    private boolean lit = false;

    private int mode = 0;
    private int checkLitCounter = 10;

    @Override
    protected boolean needsRedstoneMode() {
        return true;
    }

    public LightTE() {
        super(Config.LIGHTBLOCK_MAXRF, Config.LIGHTBLOCK_RECEIVEPERTICK);
    }

    public boolean isLit() {
        return lit;
    }

//    @Override
//    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
//        boolean oldlit = lit;
//
//        super.onDataPacket(net, packet);
//
//        if (worldObj.isRemote) {
//            // If needed send a render update.
//            if (lit != oldlit) {
//                worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
//            }
//        }
//    }
//

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            boolean newlit = isMachineEnabled();

            if (newlit) {
                // We are still potentially lit so do this.
                int rf = mode == 2 ? Config.LIGHTBLOCK_RFPERTICK_L2 : (mode == 1 ? Config.LIGHTBLOCK_RFPERTICK_L1 : Config.LIGHTBLOCK_RFPERTICK_L0);
                if (storage.getEnergyStored() >= rf) {
                    storage.extractEnergy(rf, false);
                } else {
                    newlit = false;
                }
            }

            if (newlit != lit) {
                // State has changed so we must update.
                lit = newlit;
                markDirty();
                updateLightBlocks(lit);
            } else if (lit) {
                // We are lit, check that our blocks are still there.
                checkLitCounter--;
                if (checkLitCounter <= 0) {
                    checkLitCounter = 10;
                    updateLightBlocks(lit);
                }
            }
        }
    }

    private void updateLightBlocks(boolean lit) {
        BlockPos.MutableBlockPos lpos = new BlockPos.MutableBlockPos();
        int range = mode == 0 ? 1 : mode == 1 ? 3 : 4;
        for (int x = -range; x <= range; x += range) {
            for (int y = -range; y <= range; y += range) {
                for (int z = -range; z <= range; z += range) {
                    if (x != 0 || y != 0 || z != 0) {
                        if (lit) {
                            lpos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (worldObj.getBlockState(lpos).getBlock() != ModBlocks.invisibleLightBlock) {
                                if (worldObj.isAirBlock(lpos)) {
                                    // This is not a light block but it is air. We can place a block
                                    worldObj.setBlockState(lpos, ModBlocks.invisibleLightBlock.getDefaultState(), 3);
                                } else {
                                    // Not a light block and not air. Check adjacent locations
                                    for (EnumFacing facing : EnumFacing.VALUES) {
                                        BlockPos npos = lpos.offset(facing);
                                        if (worldObj.getBlockState(npos).getBlock() != ModBlocks.invisibleLightBlock && worldObj.isAirBlock(npos)) {
                                            worldObj.setBlockState(npos, ModBlocks.invisibleLightBlock.getDefaultState(), 3);
                                        }
                                    }

                                }
                            }
                        } else {
                            lpos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                            if (worldObj.getBlockState(lpos).getBlock() == ModBlocks.invisibleLightBlock) {
                                worldObj.setBlockToAir(lpos);
                            }
                            for (EnumFacing facing : EnumFacing.VALUES) {
                                BlockPos npos = lpos.offset(facing);
                                if (worldObj.getBlockState(npos).getBlock() == ModBlocks.invisibleLightBlock) {
                                    worldObj.setBlockToAir(npos);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onBlockBreak(World workd, BlockPos pos, IBlockState state) {
        updateLightBlocks(false);
    }

    public void setMode(int mode) {
        if (mode == this.mode) {
            return;
        }
        lit = false;    // Force a relight
        updateLightBlocks(lit);
        this.mode = mode;
        markDirty();
    }

    public int getMode() {
        return mode;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        lit = tagCompound.getBoolean("lit");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("lit", lit);
        return tagCompound;
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
