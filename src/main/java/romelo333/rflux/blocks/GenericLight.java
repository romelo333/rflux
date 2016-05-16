package romelo333.rflux.blocks;


import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.varia.Logging;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.RFLux;

import java.lang.reflect.Constructor;

public class GenericLight extends GenericBlock {
    public static enum PlacementType {
        NONE,
        HORIZONTAL,
        ALL,
        CEALING,
        FLOOR
    }

    public static PropertyBool LIT = PropertyBool.create("lit");

    public GenericLight(String name, Class<? extends TileEntity> c) {
        super(RFLux.instance, Material.PORTAL, c, false);
        setHardness(0.0f);
        setUnlocalizedName(RFLux.MODID + "." + name);
        setRegistryName(name);
        setCreativeTab(RFLux.tabRFLux);
        setSoundType(SoundType.CLOTH);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(c, RFLux.MODID + "_" + name);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int getGuiID() {
        return RFLux.GUI_LIGHT;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return 15;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state.getValue(LIT)) {
            return getLightValue(state);
        } else {
            return 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer createClientGui(EntityPlayer entityPlayer, TileEntity tileEntity) {
        return new GuiLight((LightTE)tileEntity, new EmptyContainer(entityPlayer));
    }

    @Override
    public Container createServerContainer(EntityPlayer entityPlayer, TileEntity tileEntity) {
        return new EmptyContainer(entityPlayer);
    }


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
