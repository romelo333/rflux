package romelo333.rflux.blocks;

import net.minecraft.util.IStringSerializable;

public enum BlockColor implements IStringSerializable {
    WHITE("white"),
    BLUE("blue"),
    GREEN("green"),
    RED("red"),
    YELLOW("yellow");

    private final String name;

    BlockColor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
