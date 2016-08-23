package romelo333.rflux.blocks;

import romelo333.rflux.Config;

import java.util.HashMap;
import java.util.Map;

public enum LightMode {
    MODE_NORMAL(0, "Normal mode"),
    MODE_EXTENDED(2, "Extended mode"),
    MODE_SUPER(4, "Super mode");

    private final int range;
    private final String name;

    private static final Map<String, LightMode> LIGHT_MODE_MAP = new HashMap<>();

    static {
        for (LightMode mode : values()) {
            LIGHT_MODE_MAP.put(mode.getName(), mode);
        }
    }

    LightMode(int range, String name) {
        this.range = range;
        this.name = name;
    }

    public int getRfUsage() {
        switch (this) {
            case MODE_NORMAL:
                return Config.LIGHTBLOCK_RFPERTICK_L0;
            case MODE_EXTENDED:
                return Config.LIGHTBLOCK_RFPERTICK_L1;
            case MODE_SUPER:
                return Config.LIGHTBLOCK_RFPERTICK_L2;
            default:
                return 0;
        }
    }

    public int getRange() {
        return range;
    }

    public String getName() {
        return name;
    }

    public static LightMode getModeByName(String name) {
        return LIGHT_MODE_MAP.get(name);
    }
}
