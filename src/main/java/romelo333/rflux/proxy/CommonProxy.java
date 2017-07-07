package romelo333.rflux.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Level;
import romelo333.rflux.*;
import romelo333.rflux.varia.WrenchChecker;

public abstract class CommonProxy {

    private Configuration mainConfig;

    public static SimpleNetworkWrapper network;

    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());

        mainConfig = RFLux.config;
        ModItems.init();
        ModBlocks.init();
        readMainConfig();
        ModCrafting.init();

        network = mcjty.lib.network.PacketHandler.registerMessages(RFLux.MODID, "rflux");
    }

    private void readMainConfig() {
        Configuration cfg = mainConfig;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(Config.POWER_CATEGORY, "Power configuration");
            Config.init(cfg);
        } catch (Exception e1) {
            RFLux.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (mainConfig.hasChanged()) {
                mainConfig.save();
            }
        }
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(RFLux.instance, new GuiProxy());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
        mainConfig = null;
        WrenchChecker.init();
    }

}
