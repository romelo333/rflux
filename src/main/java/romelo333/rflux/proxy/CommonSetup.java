package romelo333.rflux.proxy;

import mcjty.lib.network.PacketHandler;
import mcjty.lib.setup.DefaultCommonSetup;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import romelo333.rflux.*;
import romelo333.rflux.varia.WrenchChecker;

public class CommonSetup extends DefaultCommonSetup {

    public static SimpleNetworkWrapper network;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        NetworkRegistry.INSTANCE.registerGuiHandler(RFLux.instance, new GuiProxy());

        network = PacketHandler.registerMessages(RFLux.MODID, "rflux");

        ConfigSetup.init();
        ModItems.init();
        ModBlocks.init();
    }

    @Override
    protected void setupModCompat() {

    }

    @Override
    public void createTabs() {
        createTab("RFLux", new ItemStack(Items.GLOWSTONE_DUST));
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        ConfigSetup.postInit();
        WrenchChecker.init();
    }

}
