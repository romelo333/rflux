package romelo333.rflux.blocks;


import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericGuiContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import romelo333.rflux.RFLux;

public abstract class GenericLightBlock extends GenericBlock {
    public static enum PlacementType {
        NONE,
        HORIZONTAL,
        ALL,
        CEALING,
        FLOOR
    }

    private final boolean onOff;

    @Override
    public boolean needsRedstoneCheck() {
        return true;
    }

    public GenericLightBlock(String name, Class<? extends TileEntity> c, boolean onOff) {
        super(RFLux.instance, Material.IRON, c, EmptyContainer.class, name, false);
        if (!onOff) {
            setCreativeTab(RFLux.tabRFLux);
        }
        setSoundType(SoundType.GLASS);
        this.onOff = onOff;
        setLightLevel(onOff ? 1.0f : 0.0f);
    }

    public abstract GenericLightBlock getLitBlock();

    public abstract GenericLightBlock getUnlitBlock();

    public static boolean isSameBlock(GenericLightBlock b1, GenericLightBlock b2) {
        return b1.getLitBlock() == b2.getLitBlock() || b1.getLitBlock() == b2.getUnlitBlock();
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
}
