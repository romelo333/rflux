package romelo333.rflux.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.RFLux;

import java.util.List;
import java.util.Random;

public class LightBlock extends Block implements ITileEntityProvider {

    public static PropertyBool LIT = PropertyBool.create("lit");
    public static PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public LightBlock() {
        super(Material.portal);
        setHardness(0.0f);
        setUnlocalizedName("flatlight");
        setRegistryName("flatlight");
        setCreativeTab(RFLux.tabRFLux);
        setStepSound(Block.soundTypeCloth);
        GameRegistry.registerBlock(this);
        GameRegistry.registerTileEntity(LightTE.class, RFLux.MODID + ":flatlight");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    public int quantityDropped(Random rnd) {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LightTE();
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new LightTE();
    }

    @Override
    public int getLightValue() {
        return 15;
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        if (blockState.getValue(LIT)) {
            return getLightValue();
        } else {
            return 0;
        }
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
    }


    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, EffectRenderer effectRenderer) {
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(FACING).getIndex() - 2) + (state.getValue(LIT) ? 8 : 0);
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, FACING, LIT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, getFacingHoriz(meta&7)).withProperty(LIT, (meta&8) != 0);
    }

    public static EnumFacing getFacingHoriz(int meta) {
        return EnumFacing.values()[meta + 2];
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
}
