package romelo333.rflux;


import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.File;

public class ConfigSetup {

    public static String POWER_CATEGORY = "power";

    public static int LIGHTBLOCK_RFPERTICK_L0 = 0;
    public static int LIGHTBLOCK_RFPERTICK_L1 = 5;
    public static int LIGHTBLOCK_RFPERTICK_L2 = 20;
    public static int LIGHTBLOCK_RECEIVEPERTICK = 100;
    public static int LIGHTBLOCK_MAXRF = 1000;

    private static Configuration mainConfig;

    public static void init() {
        mainConfig = new Configuration(new File(RFLux.setup.getModConfigDir(), "rflux.cfg"));
        Configuration cfg = mainConfig;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(ConfigSetup.POWER_CATEGORY, "Power configuration");
            LIGHTBLOCK_RFPERTICK_L0 = cfg.getInt("lightblock_rfpertick_l0", POWER_CATEGORY, LIGHTBLOCK_RFPERTICK_L0, 0, 100000, "Power usage for a level 0 light");
            LIGHTBLOCK_RFPERTICK_L1 = cfg.getInt("lightblock_rfpertick_l1", POWER_CATEGORY, LIGHTBLOCK_RFPERTICK_L1, 0, 100000, "Power usage for a level 1 light");
            LIGHTBLOCK_RFPERTICK_L2 = cfg.getInt("lightblock_rfpertick_l2", POWER_CATEGORY, LIGHTBLOCK_RFPERTICK_L2, 0, 100000, "Power usage for a level 2 light");
            LIGHTBLOCK_RECEIVEPERTICK = cfg.getInt("lightblock_receivepertick", POWER_CATEGORY, LIGHTBLOCK_RECEIVEPERTICK, 0, 100000, "RF Per tick the light block can receive");
            LIGHTBLOCK_MAXRF = cfg.getInt("lightblock_maxrf", POWER_CATEGORY, LIGHTBLOCK_MAXRF, 0, 100000, "Maximum RF the light block can store");
        } catch (Exception e1) {
            RFLux.setup.getLogger().log(Level.ERROR, "Problem loading config file!", e1);
        }
    }

    public static void postInit() {
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
        mainConfig = null;
    }
}
