package romelo333.rflux;


import cpw.mods.fml.common.registry.GameRegistry;
import romelo333.rflux.blocks.LightBlock;
import romelo333.rflux.blocks.LightTE;

public class ModBlocks {
    public static LightBlock lightBlock;

    public static void init() {
        lightBlock = new LightBlock();
        GameRegistry.registerBlock(lightBlock, "lightBlock");
        GameRegistry.registerTileEntity(LightTE.class, "LightTileEntity");
    }
}
