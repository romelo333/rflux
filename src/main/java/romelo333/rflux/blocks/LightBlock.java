package romelo333.rflux.blocks;


import romelo333.rflux.ModBlocks;

public class LightBlock extends GenericLightBlock {

    public LightBlock(boolean onOff) {
        super("lightblock_" + (onOff ? "on" : "off"), LightTE.class, onOff);
    }

    @Override
    public GenericLightBlock getLitBlock() {
        return ModBlocks.lightBlockOn;
    }

    @Override
    public GenericLightBlock getUnlitBlock() {
        return ModBlocks.lightBlockOff;
    }

    @Override
    public boolean hasNoRotation() {
        return true;
    }
}
