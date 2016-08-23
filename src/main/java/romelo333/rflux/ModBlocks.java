package romelo333.rflux;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.blocks.InvisibleLightBlock;
import romelo333.rflux.blocks.LightBlock;

public class ModBlocks {
    public static LightBlock lightBlockOn;
    public static LightBlock lightBlockOff;
    public static InvisibleLightBlock invisibleLightBlock;

    public static void init() {
        lightBlockOn = new LightBlock(true);
        lightBlockOff = new LightBlock(false);
        invisibleLightBlock = new InvisibleLightBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightBlockOn.initModel();
        lightBlockOff.initModel();
        invisibleLightBlock.initModel();
    }
}
