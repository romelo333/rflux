package romelo333.rflux.blocks;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import romelo333.rflux.ModBlocks;

public class LightTE extends TileEntity implements IEnergyReceiver, ITickable {

    private EnergyStorage storage = new EnergyStorage(1000);

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    public void update() {
        if (!worldObj.isRemote){
            if (storage.getEnergyStored() >= 100){
                worldObj.setBlockState(getPos(), ModBlocks.lightBlock.getDefaultState().withProperty(LightBlock.LIT, true));
                worldObj.markBlockForUpdate(getPos());
                storage.extractEnergy(100, false);
            } else {
                worldObj.setBlockState(getPos(), ModBlocks.lightBlock.getDefaultState().withProperty(LightBlock.LIT, true));
                worldObj.markBlockForUpdate(getPos());
            }
        }
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return 1000;
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

}
