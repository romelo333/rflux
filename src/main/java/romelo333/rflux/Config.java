package romelo333.rflux;


import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String POWER_CATEGORY = "power";

    public static int LIGHTBLOCK_RFPERTICK_L0 = 2;
    public static int LIGHTBLOCK_RFPERTICK_L1 = 100;
    public static int LIGHTBLOCK_RECEIVEPERTICK = 100;
    public static int LIGHTBLOCK_MAXRF = 1000;

    public static void init(Configuration cfg) {
        LIGHTBLOCK_RFPERTICK_L0 = cfg.getInt("lightblock_rfpertick_l0", POWER_CATEGORY, LIGHTBLOCK_RFPERTICK_L0, 0, 10000, "Power usage for a level 0 light");
        LIGHTBLOCK_RFPERTICK_L1 = cfg.getInt("lightblock_rfpertick_l1", POWER_CATEGORY, LIGHTBLOCK_RFPERTICK_L1, 0, 10000, "Power usage for a level 1 light");
        LIGHTBLOCK_RECEIVEPERTICK = cfg.getInt("lightblock_receivepertick", POWER_CATEGORY, LIGHTBLOCK_RECEIVEPERTICK, 0, 10000, "RF Per tick the light block can receive");
        LIGHTBLOCK_MAXRF = cfg.getInt("lightblock_maxrf", POWER_CATEGORY, LIGHTBLOCK_MAXRF, 0, 10000, "Maximum RF the light block can store");
    }
}
