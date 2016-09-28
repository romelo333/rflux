package romelo333.rflux;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.blocks.FlatLightBlock;
import romelo333.rflux.blocks.InvisibleLightBlock;
import romelo333.rflux.blocks.LightBlock;

public class ModBlocks {
    public static LightBlock lightBlockOn;
    public static LightBlock lightBlockOff;
    public static FlatLightBlock flatLightBlockOn;
    public static FlatLightBlock flatLightBlockOff;
    public static InvisibleLightBlock invisibleLightBlock;

    public static void init() {
        lightBlockOn = new LightBlock(true);
        lightBlockOff = new LightBlock(false);
        flatLightBlockOn = new FlatLightBlock(true);
        flatLightBlockOff = new FlatLightBlock(false);
        invisibleLightBlock = new InvisibleLightBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightBlockOn.initModel();
        lightBlockOff.initModel();
        flatLightBlockOn.initModel();
        flatLightBlockOff.initModel();
        invisibleLightBlock.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initClientPost() {
        lightBlockOn.initBlockColors();
        lightBlockOff.initBlockColors();
    }
}
