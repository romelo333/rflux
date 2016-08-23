package romelo333.rflux.blocks;

import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.entity.GenericEnergyStorageTileEntity;
import mcjty.lib.gui.layout.HorizontalLayout;
import mcjty.lib.gui.layout.VerticalLayout;
import mcjty.lib.gui.widgets.*;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.network.Argument;
import mcjty.lib.varia.RedstoneMode;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import romelo333.rflux.Config;
import romelo333.rflux.RFLux;
import romelo333.rflux.proxy.CommonProxy;

import java.awt.*;

public class GuiLight extends GenericGuiContainer<LightTE> {
    public static final int WIDTH = 160;
    public static final int HEIGHT = 80;

    private EnergyBar energyBar;
    private ImageChoiceLabel redstoneMode;
    private ChoiceLabel mode;

    private static final ResourceLocation iconGuiElements = new ResourceLocation(RFLux.MODID, "textures/gui/guielements.png");


    private int timer = 10;

    public GuiLight(LightTE endergenicTileEntity, EmptyContainer container) {
        super(RFLux.instance, CommonProxy.network, endergenicTileEntity, container, 0, "light block");
        GenericEnergyStorageTileEntity.setCurrentRF(endergenicTileEntity.getEnergyStored(EnumFacing.DOWN));
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        int maxEnergyStored = tileEntity.getMaxEnergyStored(EnumFacing.DOWN);
        energyBar = new EnergyBar(mc, this).setFilledRectThickness(1).setVertical().setDesiredWidth(10).setMaxValue(maxEnergyStored).setShowText(false);
        energyBar.setValue(GenericEnergyStorageTileEntity.getCurrentRF());

        initMode();
        initRedstoneMode();
        Panel controlPanel = new Panel(mc, this).setLayout(new VerticalLayout()).addChild(mode).addChild(redstoneMode);

        Widget toplevel = new Panel(mc, this).setFilledRectThickness(2).setLayout(new HorizontalLayout()).addChild(energyBar).addChild(controlPanel);
        toplevel.setBounds(new Rectangle(guiLeft, guiTop, WIDTH, HEIGHT));
        window = new mcjty.lib.gui.Window(this, toplevel);
        tileEntity.requestRfFromServer(RFLux.MODID);
    }

    private void initMode() {
        mode = new ChoiceLabel(mc, this).addChoices("Normal mode", "Extended mode", "Super mode")
                .setDesiredWidth(130)
                .addChoiceEvent((parent, newChoice) -> changeMode());
        mode.setChoiceTooltip("Normal mode", "Same light value as a glowstone", "block (" + Config.LIGHTBLOCK_RFPERTICK_L0 + " RF/tick)");
        mode.setChoiceTooltip("Extended mode", "Light up a bigger area", "blocks (" + Config.LIGHTBLOCK_RFPERTICK_L1 + " RF/tick)");
        mode.setChoiceTooltip("Super mode", "Light up the largest area", "blocks (" + Config.LIGHTBLOCK_RFPERTICK_L2 + " RF/tick)");
        switch (tileEntity.getMode()) {
            case 0: mode.setChoice("Normal mode"); break;
            case 1: mode.setChoice("Extended mode"); break;
            case 2: mode.setChoice("Super mode"); break;
        }
    }

    private void changeMode() {
        tileEntity.setRSMode(RedstoneMode.values()[redstoneMode.getCurrentChoiceIndex()]);
        sendChangeToServer();
    }

    private void initRedstoneMode() {
        redstoneMode = new ImageChoiceLabel(mc, this)
                .addChoiceEvent((parent, newChoice) -> changeRedstoneMode())
                .addChoice(RedstoneMode.REDSTONE_IGNORED.getDescription(), "Redstone mode:\nIgnored", iconGuiElements, 0, 0)
                .addChoice(RedstoneMode.REDSTONE_OFFREQUIRED.getDescription(), "Redstone mode:\nOff to activate", iconGuiElements, 16, 0)
                .addChoice(RedstoneMode.REDSTONE_ONREQUIRED.getDescription(), "Redstone mode:\nOn to activate", iconGuiElements, 32, 0);
        redstoneMode.setDesiredWidth(16).setDesiredHeight(16);
        redstoneMode.setCurrentChoice(tileEntity.getRSMode().ordinal());
    }

    private void changeRedstoneMode() {
        tileEntity.setRSMode(RedstoneMode.values()[redstoneMode.getCurrentChoiceIndex()]);
        sendChangeToServer();
    }

    private void sendChangeToServer() {
        String choice = mode.getCurrentChoice();
        sendServerCommand(CommonProxy.network, LightTE.CMD_MODE,
                new Argument("rs", RedstoneMode.values()[redstoneMode.getCurrentChoiceIndex()].getDescription()),
                new Argument("mode", choice.equals("Normal mode") ? 0 : (choice.equals("Extended mode") ? 1 : 2)));
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        drawWindow();
        int currentRF = GenericEnergyStorageTileEntity.getCurrentRF();
        energyBar.setValue(currentRF);
        tileEntity.requestRfFromServer(RFLux.MODID);
    }
}
