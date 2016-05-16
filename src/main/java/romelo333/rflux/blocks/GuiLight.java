package romelo333.rflux.blocks;

import mcjty.lib.container.EmptyContainer;
import mcjty.lib.container.GenericGuiContainer;
import mcjty.lib.gui.layout.VerticalLayout;
import mcjty.lib.gui.widgets.EnergyBar;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.gui.widgets.Widget;
import net.minecraft.util.EnumFacing;
import romelo333.rflux.RFLux;
import romelo333.rflux.network.PacketHandler;

import java.awt.*;

public class GuiLight extends GenericGuiContainer<LightTE> {
    public static final int WIDTH = 190;
    public static final int HEIGHT = 110;

    private EnergyBar energyBar;

    private int timer = 10;

    public GuiLight(LightTE endergenicTileEntity, EmptyContainer container) {
        super(RFLux.instance, PacketHandler.INSTANCE, endergenicTileEntity, container, 0, "power");
        endergenicTileEntity.setCurrentRF(endergenicTileEntity.getEnergyStored(EnumFacing.DOWN));
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void initGui() {
        super.initGui();

        int maxEnergyStored = tileEntity.getMaxEnergyStored(EnumFacing.DOWN);
        energyBar = new EnergyBar(mc, this).setFilledRectThickness(1).setVertical().setDesiredWidth(12).setMaxValue(maxEnergyStored).setShowText(true);
        energyBar.setValue(tileEntity.getCurrentRF());

        Widget toplevel = new Panel(mc, this).setFilledRectThickness(2).setLayout(new VerticalLayout()).addChild(energyBar);
        toplevel.setBounds(new Rectangle(guiLeft, guiTop, WIDTH, HEIGHT));
        window = new mcjty.lib.gui.Window(this, toplevel);
        tileEntity.requestRfFromServer(RFLux.MODID);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i2) {
        drawWindow();
        int currentRF = tileEntity.getCurrentRF();
        energyBar.setValue(currentRF);
        tileEntity.requestRfFromServer(RFLux.MODID);

    }
}
