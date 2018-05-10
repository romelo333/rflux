package romelo333.rflux.blocks;


import mcjty.lib.container.EmptyContainer;
import mcjty.lib.blocks.GenericBlock;
import mcjty.lib.gui.GenericGuiContainer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.RFLux;

import java.util.Map;

public abstract class GenericLightBlock<T extends LightTE> extends GenericBlock<T, EmptyContainer> {
    protected final boolean onOff;

    public static final PropertyEnum<BlockColor> COLOR = PropertyEnum.<BlockColor>create("color", BlockColor.class);

    @Override
    public boolean needsRedstoneCheck() {
        return true;
    }

    public GenericLightBlock(String name, Class<? extends T> c, boolean onOff) {
        super(RFLux.instance, Material.IRON, c, EmptyContainer.class, name, false);
        this.onOff = onOff;
        if (!this.onOff) {
            setCreativeTab(RFLux.tabRFLux);
        }
        setSoundType(SoundType.GLASS);
        setLightLevel(this.onOff ? 1.0f : 0.0f);
        setDefaultState(getDefaultState().withProperty(COLOR, BlockColor.WHITE));
    }

    public GenericLightBlock(String name, Class<? extends T> c, Class<? extends ItemBlock> itemBlockClass, boolean onOff) {
        super(RFLux.instance, Material.IRON, c, EmptyContainer.class, itemBlockClass, name, false);
        this.onOff = onOff;
        if (!this.onOff) {
            setCreativeTab(RFLux.tabRFLux);
        }
        setSoundType(SoundType.GLASS);
        setLightLevel(this.onOff ? 1.0f : 0.0f);
        setDefaultState(getDefaultState().withProperty(COLOR, BlockColor.WHITE));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initModel() {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(this), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                int col = 0;
                if (stack.hasTagCompound()) {
                    col = stack.getTagCompound().getInteger("color");
                }
                BlockColor color = BlockColor.values()[col];
                BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                Map<IBlockState, ModelResourceLocation> variants = dispatcher.getBlockModelShapes().getBlockStateMapper().getVariants(GenericLightBlock.this);
                return variants.get(GenericLightBlock.this.getDefaultState().withProperty(GenericLightBlock.COLOR, color));
            }
        });
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

    public static ItemStack makeColoredBlock(Block block, BlockColor color, int amount) {
        ItemStack i = new ItemStack(block, amount);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("color", color.ordinal());
        i.setTagCompound(nbt);
        return i;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (getRotationType() == RotationType.NONE) {
            return super.getBoundingBox(state, world, pos);
        }
        IBlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof GenericLightBlock) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof LightTE) {
                EnumFacing side = getFrontDirection(blockState);
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        BlockColor color = BlockColor.WHITE;
        if (te instanceof LightTE) {
            color = ((LightTE) te).getColor();
        }
        return state.withProperty(COLOR, color);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        switch (getRotationType()) {
            case HORIZROTATION:
                return new BlockStateContainer(this, FACING_HORIZ, COLOR);
            case ROTATION:
                return new BlockStateContainer(this, FACING, COLOR);
            case NONE:
                return new BlockStateContainer(this, COLOR);
        }
        return new BlockStateContainer(this, COLOR);
    }

}
