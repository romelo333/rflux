package romelo333.rflux.proxy;

import mcjty.lib.setup.DefaultCommonSetup;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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

import java.io.File;

public class CommonSetup extends DefaultCommonSetup {

    public static SimpleNetworkWrapper network;

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());

        mainConfig = new Configuration(new File(modConfigDir, "rflux.cfg"));

        ModItems.init();
        ModBlocks.init();
        readMainConfig();

        network = mcjty.lib.network.PacketHandler.registerMessages(RFLux.MODID, "rflux");
    }

    @Override
    public void createTabs() {
        createTab("RFLux", new ItemStack(Items.GLOWSTONE_DUST));
    }

    private void readMainConfig() {
        Configuration cfg = mainConfig;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(Config.POWER_CATEGORY, "Power configuration");
            Config.init(cfg);
        } catch (Exception e1) {
            getLogger().log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (mainConfig.hasChanged()) {
                mainConfig.save();
            }
        }
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        NetworkRegistry.INSTANCE.registerGuiHandler(RFLux.instance, new GuiProxy());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
        mainConfig = null;
        WrenchChecker.init();
    }

}
