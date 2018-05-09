package romelo333.rflux.blocks;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import mcjty.lib.container.BaseBlock;
import mcjty.lib.container.GenericBlock;
import mcjty.lib.entity.DefaultValue;
import mcjty.lib.entity.GenericEnergyReceiverTileEntity;
import mcjty.lib.entity.IValue;
import mcjty.lib.typed.Key;
import mcjty.lib.typed.Type;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import romelo333.rflux.Config;
import romelo333.rflux.ModBlocks;

@Optional.InterfaceList({
        @Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
})
public class LightTE extends GenericEnergyReceiverTileEntity implements ITickable, ILightProvider {

    private BlockColor color = BlockColor.WHITE;

    private Object light = null;

    private boolean lit = false;

    private LightMode mode = LightMode.MODE_NORMAL;
    private int checkLitCounter = 10;

    public static final Key<Integer> VALUE_MODE = new Key<>("mode", Type.INTEGER);

    @Override
    public IValue[] getValues() {
        return new IValue[] {
                new DefaultValue<>(VALUE_RSMODE, LightTE::getRSModeInt, LightTE::setRSModeInt),
                new DefaultValue<>(VALUE_MODE, te -> ((LightTE)te).getMode().ordinal(), (te,v) -> ((LightTE)te).setMode(LightMode.values()[v])),
        };
    }

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

    @Optional.Method(modid = "albedo")
    @Override
    public Light provideLight() {
        if (light == null) {
            if (lit) {
                light = new Light(pos.getX(), pos.getY(), pos.getZ(), color.getR(), color.getG(), color.getB(), 1.0f,
                        mode == LightMode.MODE_NORMAL ? 16.0f :
                                mode == LightMode.MODE_EXTENDED ? 20.0f :
                                        24.0f);
            }
        }
        return (Light) light;
    }

    @Override
    public void update() {
        if (!getWorld().isRemote) {
            boolean newlit = isMachineEnabled();

            if (newlit) {
                // We are still potentially lit so do this.
                int rf = mode.getRfUsage();
                if (storage.getEnergyStored() >= rf) {
                    storage.extractEnergy(rf, false);
                } else {
                    newlit = false;
                }
            }

            if (newlit != lit) {
                // State has changed so we must update.
                lit = newlit;
                light = null;
                IBlockState oldState = getWorld().getBlockState(pos);
                GenericLightBlock block = (GenericLightBlock) oldState.getBlock();
                if (block.getRotationType() == BaseBlock.RotationType.NONE) {
                    if (lit) {
                        getWorld().setBlockState(pos, block.getLitBlock().getDefaultState(), 3);
                    } else {
                        getWorld().setBlockState(pos, block.getUnlitBlock().getDefaultState(), 3);
                    }
                } else {
                    if (lit) {
                        getWorld().setBlockState(pos, block.getLitBlock().getDefaultState().withProperty(GenericBlock.FACING, oldState.getValue(GenericBlock.FACING)), 3);
                    } else {
                        getWorld().setBlockState(pos, block.getUnlitBlock().getDefaultState().withProperty(GenericBlock.FACING, oldState.getValue(GenericBlock.FACING)), 3);
                    }
                }

                // Restore the TE, needed since our block has changed
                this.validate();
                getWorld().setTileEntity(pos, this);

                markDirtyClient();
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
        int range = mode.getRange();
        if (range == 0) {
            return;
        } else {
            for (int x = -range; x <= range; x += range) {
                for (int y = -range; y <= range; y += range) {
                    for (int z = -range; z <= range; z += range) {
                        if (x != 0 || y != 0 || z != 0) {
                            if (lit) {
                                lpos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                                if (!isInvisibleLight(lpos)) {
                                    if (getWorld().isAirBlock(lpos)) {
                                        // This is not a light block but it is air. We can place a block
                                        setInvisibleBlock(lpos);
                                    } else {
                                        // Not a light block and not air. Check adjacent locations
                                        for (EnumFacing facing : EnumFacing.VALUES) {
                                            BlockPos npos = lpos.offset(facing);
                                            if (!isInvisibleLight(npos) && getWorld().isAirBlock(npos)) {
                                                setInvisibleBlock(npos);
                                            }
                                        }
                                    }
                                }
                            } else {
                                lpos.setPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                                if (isInvisibleLight(lpos)) {
                                    getWorld().setBlockToAir(lpos);
                                }
                                for (EnumFacing facing : EnumFacing.VALUES) {
                                    BlockPos npos = lpos.offset(facing);
                                    if (isInvisibleLight(npos)) {
                                        getWorld().setBlockToAir(npos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean setInvisibleBlock(BlockPos npos) {
        return getWorld().setBlockState(npos, ModBlocks.invisibleLightBlock.getDefaultState(), 3);
    }

    private boolean isInvisibleLight(BlockPos lpos) {
        return getWorld().getBlockState(lpos).getBlock() == ModBlocks.invisibleLightBlock;
    }

    @Override
    public void onBlockBreak(World workd, BlockPos pos, IBlockState state) {
        updateLightBlocks(false);
    }

    public void setMode(LightMode mode) {
        if (mode == this.mode) {
            return;
        }
        light = null;
        boolean oldlit = lit;
        this.lit = false;    // Force a relight
        updateLightBlocks(lit);
        this.mode = mode;
        this.lit = oldlit;
        markDirty();
    }

    public LightMode getMode() {
        return mode;
    }

    public BlockColor getColor() {
        return color;
    }

    public void setColor(BlockColor color) {
        this.color = color;
        this.light = null;
        markDirtyClient();
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
        mode = LightMode.values()[tagCompound.getByte("mode")];
        color = BlockColor.values()[tagCompound.getInteger("color")];
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound tagCompound) {
        super.writeRestorableToNBT(tagCompound);
        tagCompound.setInteger("color", color.ordinal());
        tagCompound.setByte("mode", (byte) mode.ordinal());
    }
}
