package romelo333.rflux.blocks;

import mcjty.lib.container.EmptyContainer;
import mcjty.lib.gui.GenericGuiContainer;
import mcjty.lib.gui.layout.HorizontalLayout;
import mcjty.lib.gui.layout.VerticalLayout;
import mcjty.lib.gui.widgets.ChoiceLabel;
import mcjty.lib.gui.widgets.EnergyBar;
import mcjty.lib.gui.widgets.ImageChoiceLabel;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.tileentity.GenericEnergyStorageTileEntity;
import mcjty.lib.tileentity.GenericTileEntity;
import mcjty.lib.varia.RedstoneMode;
import net.minecraft.util.ResourceLocation;
import romelo333.rflux.Config;
import romelo333.rflux.RFLux;
import romelo333.rflux.proxy.CommonProxy;

import java.awt.*;

public class GuiLight extends GenericGuiContainer<LightTE> {
    public static final int WIDTH = 160;
    public static final int HEIGHT = 80;

    private EnergyBar energyBar;

    private static final ResourceLocation iconGuiElements = new ResourceLocation(RFLux.MODID, "textures/gui/guielements.png");


    private int timer = 10;

    public GuiLight(LightTEFlatOff te, EmptyContainer container) {
        this((LightTE) te, container);
    }

    public GuiLight(LightTEFlatOn te, EmptyContainer container) {
        this((LightTE) te, container);
    }

    public GuiLight(LightTEOff te, EmptyContainer container) {
        this((LightTE) te, container);
    }

    public GuiLight(LightTE te, EmptyContainer container) {
        super(RFLux.instance, CommonProxy.network, te, container, 0, "light block");
        GenericEnergyStorageTileEntity.setCurrentRF(te.getEnergyStored());
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        int maxEnergyStored = tileEntity.getMaxEnergyStored();
        energyBar = new EnergyBar(mc, this).setFilledRectThickness(1).setVertical().setDesiredWidth(10).setMaxValue(maxEnergyStored).setShowText(false);
        energyBar.setValue(GenericEnergyStorageTileEntity.getCurrentRF());

        ChoiceLabel mode = initMode();
        ImageChoiceLabel redstoneMode = initRedstoneMode();
        Panel controlPanel = new Panel(mc, this).setLayout(new VerticalLayout()).addChild(mode).addChild(redstoneMode);

        Panel toplevel = new Panel(mc, this).setFilledRectThickness(2).setLayout(new HorizontalLayout()).addChild(energyBar).addChild(controlPanel);
        toplevel.setBounds(new Rectangle(guiLeft, guiTop, WIDTH, HEIGHT));
        window = new mcjty.lib.gui.Window(this, toplevel);
        tileEntity.requestRfFromServer(RFLux.MODID);

        window.bind(CommonProxy.network, "redstone", tileEntity, GenericTileEntity.VALUE_RSMODE.getName());
        window.bind(CommonProxy.network, "mode", tileEntity, LightTE.VALUE_MODE.getName());
    }

    private ChoiceLabel initMode() {
        ChoiceLabel mode = new ChoiceLabel(mc, this)
                .setName("mode")
                .addChoices(LightMode.MODE_NORMAL.getName(), LightMode.MODE_EXTENDED.getName(), LightMode.MODE_SUPER.getName())
                .setDesiredWidth(130)
                .setDesiredHeight(15);
        mode.setChoiceTooltip(LightMode.MODE_NORMAL.getName(), "Same light value as a glowstone", "block (" + Config.LIGHTBLOCK_RFPERTICK_L0 + " RF/tick)");
        mode.setChoiceTooltip(LightMode.MODE_EXTENDED.getName(), "Light up a bigger area", "blocks (" + Config.LIGHTBLOCK_RFPERTICK_L1 + " RF/tick)");
        mode.setChoiceTooltip(LightMode.MODE_SUPER.getName(), "Light up the largest area", "blocks (" + Config.LIGHTBLOCK_RFPERTICK_L2 + " RF/tick)");
        mode.setChoice(tileEntity.getMode().getName());
        return mode;
    }

    private ImageChoiceLabel initRedstoneMode() {
        ImageChoiceLabel redstoneMode = new ImageChoiceLabel(mc, this)
                .setName("redstone")
                .addChoice(RedstoneMode.REDSTONE_IGNORED.getDescription(), "Redstone mode:\nIgnored", iconGuiElements, 0, 0)
                .addChoice(RedstoneMode.REDSTONE_OFFREQUIRED.getDescription(), "Redstone mode:\nOff to activate", iconGuiElements, 16, 0)
                .addChoice(RedstoneMode.REDSTONE_ONREQUIRED.getDescription(), "Redstone mode:\nOn to activate", iconGuiElements, 32, 0);
        redstoneMode.setDesiredWidth(16).setDesiredHeight(16);
        return redstoneMode;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        drawWindow();
        long currentRF = GenericEnergyStorageTileEntity.getCurrentRF();
        energyBar.setValue(currentRF);
        tileEntity.requestRfFromServer(RFLux.MODID);
    }
}
