package romelo333.rflux;


import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String Category = "power";
    public static int LIGHTBLOCK_RFPERTICK = 10;

    public static void init(Configuration cfg) {
        LIGHTBLOCK_RFPERTICK = cfg.getInt("lightblock_rfpertick", Category, LIGHTBLOCK_RFPERTICK, 0, 10000, "powerusage");
    }
}
