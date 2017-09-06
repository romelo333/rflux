package romelo333.rflux.blocks;

import net.minecraft.util.IStringSerializable;

public enum BlockColor implements IStringSerializable {
    WHITE("white", 1.0f, 1.0f, 1.0f),
    BLUE("blue", 0.0f, 0.0f, 1.0f),
    GREEN("green", 0.0f, 1.0f, 0.0f),
    RED("red", 1.0f, 0.0f, 0.0f),
    YELLOW("yellow", 1.0f, 1.0f, 0.0f);

    private final String name;
    private final float r;
    private final float g;
    private final float b;

    BlockColor(String name, float r, float g, float b) {
        this.name = name;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public String getName() {
        return name;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }
}
