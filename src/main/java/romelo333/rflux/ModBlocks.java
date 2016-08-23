package romelo333.rflux;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.blocks.InvisibleLightBlock;
import romelo333.rflux.blocks.LightBlock;

public class ModBlocks {
    public static LightBlock lightBlock;
    public static InvisibleLightBlock invisibleLightBlock;

    public static void init() {
        lightBlock = new LightBlock();
        invisibleLightBlock = new InvisibleLightBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightBlock.initModel();
        invisibleLightBlock.initModel();
    }
}
