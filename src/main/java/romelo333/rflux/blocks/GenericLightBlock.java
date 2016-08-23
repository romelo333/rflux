package romelo333.rflux.blocks;


import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericGuiContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import romelo333.rflux.RFLux;

public abstract class GenericLightBlock extends GenericBlock {
    private final boolean onOff;

    @Override
    public boolean needsRedstoneCheck() {
        return true;
    }

    public GenericLightBlock(String name, Class<? extends TileEntity> c, boolean onOff) {
        super(RFLux.instance, Material.IRON, c, EmptyContainer.class, name, false);
        this.onOff = onOff;
        if (!this.onOff) {
            setCreativeTab(RFLux.tabRFLux);
        }
        setSoundType(SoundType.GLASS);
        setLightLevel(this.onOff ? 1.0f : 0.0f);
    }

    public GenericLightBlock(String name, Class<? extends TileEntity> c, Class<? extends ItemBlock> itemBlockClass, boolean onOff) {
        super(RFLux.instance, Material.IRON, c, EmptyContainer.class, itemBlockClass, name, false);
        this.onOff = onOff;
        if (!this.onOff) {
            setCreativeTab(RFLux.tabRFLux);
        }
        setSoundType(SoundType.GLASS);
        setLightLevel(this.onOff ? 1.0f : 0.0f);
    }

    public abstract GenericLightBlock getLitBlock();

    public abstract GenericLightBlock getUnlitBlock();

    public static boolean isSameBlock(GenericLightBlock b1, GenericLightBlock b2) {
        return b1.getLitBlock() == b2.getLitBlock() || b1.getLitBlock() == b2.getUnlitBlock();
    }

    public static final AxisAlignedBB BLOCK_DOWN = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.3F, 1.0F);
    public static final AxisAlignedBB BLOCK_UP = new AxisAlignedBB(0.0F, 0.7F, 0.0F, 1.0F, 1.0F, 1.0F);
    public static final AxisAlignedBB BLOCK_NORTH = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.3F);
    public static final AxisAlignedBB BLOCK_SOUTH = new AxisAlignedBB(0.0F, 0.0F, 0.7F, 1.0F, 1.0F, 1.0F);
    public static final AxisAlignedBB BLOCK_WEST = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.3F, 1.0F, 1.0F);
    public static final AxisAlignedBB BLOCK_EAST = new AxisAlignedBB(0.7F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (hasNoRotation()) {
            return super.getBoundingBox(state, world, pos);
        }
        IBlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof GenericLightBlock) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof LightTE) {
                LightTE lightTE = (LightTE) te;
                EnumFacing side = getOrientation(blockState.getBlock().getMetaFromState(blockState));
                switch (side) {
                    case DOWN:
                        return BLOCK_DOWN;
                    case UP:
                        return BLOCK_UP;
                    case NORTH:
                        return BLOCK_NORTH;
                    case SOUTH:
                        return BLOCK_SOUTH;
                    case WEST:
                        return BLOCK_WEST;
                    case EAST:
                        return BLOCK_EAST;
                }
            }
        }
        return BLOCK_DOWN;
    }


    @Override
    public int getGuiID() {
        return RFLux.GUI_LIGHT;
    }

    @Override
    public Class<? extends GenericGuiContainer> getGuiClass() {
        return GuiLight.class;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new LightTE();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new LightTE();
    }
}
