package romelo333.rflux;


import romelo333.rflux.blocks.LightBlock;

public class ModBlocks {
    public static LightBlock lightBlock;

    public static void init() {
        lightBlock = new LightBlock();
    }
}
