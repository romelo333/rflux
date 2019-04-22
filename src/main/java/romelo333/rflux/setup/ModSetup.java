package romelo333.rflux.setup;

import mcjty.lib.network.PacketHandler;
import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import romelo333.rflux.ConfigSetup;
import romelo333.rflux.ModBlocks;
import romelo333.rflux.ModItems;
import romelo333.rflux.RFLux;
import romelo333.rflux.varia.WrenchChecker;

public class ModSetup extends DefaultModSetup {

    public static SimpleNetworkWrapper network;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        NetworkRegistry.INSTANCE.registerGuiHandler(RFLux.instance, new GuiProxy());

        network = PacketHandler.registerMessages(RFLux.MODID, "rflux");

        ModItems.init();
        ModBlocks.init();
    }

    @Override
    protected void setupModCompat() {

    }

    @Override
    protected void setupConfig() {
        ConfigSetup.init();
    }

    @Override
    public void createTabs() {
        createTab("RFLux", () -> new ItemStack(Items.GLOWSTONE_DUST));
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        ConfigSetup.postInit();
        WrenchChecker.init();
    }

}
