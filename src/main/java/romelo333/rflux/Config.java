package romelo333.rflux;


import net.minecraftforge.common.config.Configuration;
import romelo333.rflux.Items.GenericWand;

public class Config {
    public static String CATEGORY_WANDS = "wands";
    public static String CATEGORY_MOVINGBLACKLIST = "movingblacklist";

    public static void init(Configuration cfg) {
        GenericWand.setupConfig(cfg);
    }
}
