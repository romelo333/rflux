package romelo333.rflux.blocks;


import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericGuiContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import romelo333.rflux.RFLux;

public class GenericLight extends GenericBlock {
    public static enum PlacementType {
        NONE,
        HORIZONTAL,
        ALL,
        CEALING,
        FLOOR
    }

    @Override
    public boolean needsRedstoneCheck() {
        return true;
    }

    public GenericLight(String name, Class<? extends TileEntity> c) {
        super(RFLux.instance, Material.IRON, c, EmptyContainer.class, name, false);
        setCreativeTab(RFLux.tabRFLux);
        setSoundType(SoundType.GLASS);
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
        return null;
    }

//    @Override
//    public int getLightValue(IBlockState state) {
//        return 15;
//    }
//
//    @Override
//    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
//        System.out.println("GenericLight.getLightValue: 1");
//        TileEntity te = world.getTileEntity(pos);
//        if (te instanceof LightTE) {
//            if (((LightTE) te).isLit()) {
//                System.out.println("GenericLight.getLightValue: 2");
//                return 15;
//            }
//        }
//        return 0;
//    }



//    @Override
//    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn) {
//    }
//
//    @Override
//    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
//        return false;
//    }
//
//    @Override
//    public boolean isBlockNormalCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }
//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, EffectRenderer effectRenderer) {
//        return true;
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {
//        return true;
//    }
//
}
