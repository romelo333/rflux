package romelo333.rflux;


import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import romelo333.rflux.blocks.LightBlock;

public class ModBlocks {
    public static LightBlock lightBlock;

    public static void init() {
        lightBlock = new LightBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        lightBlock.initModel();
    }
}
