package romelo333.rflux;

import mcjty.lib.base.ModBase;
import mcjty.lib.compat.CompatCreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import romelo333.rflux.proxy.CommonProxy;

import java.io.File;

@Mod(modid = RFLux.MODID, name="RF Lux",
        dependencies =
                        "required-after:mcjtylib_ng@[" + RFLux.MIN_MCJTYLIB_VER + ",);" +
                        "required-after:compatlayer@[" + RFLux.COMPATLAYER_VER + ",);" +
                        "after:Forge@[" + RFLux.MIN_FORGE10_VER + ",);" +
                        "after:forge@[" + RFLux.MIN_FORGE11_VER + ",)",
        version = RFLux.VERSION,
        acceptedMinecraftVersions = "[1.10,1.12)")
public class RFLux implements ModBase {
    public static final String MODID = "rflux";
    public static final String VERSION = "0.0.1";
    public static final String MIN_FORGE10_VER = "12.18.1.2082";
    public static final String MIN_FORGE11_VER = "13.19.0.2157";
    public static final String MIN_MCJTYLIB_VER = "2.3.1";
    public static final String COMPATLAYER_VER = "0.1.3";

    @SidedProxy(clientSide="romelo333.rflux.proxy.ClientProxy", serverSide="romelo333.rflux.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance("rflux")
    public static RFLux instance;
    public static Logger logger;
    public static File mainConfigDir;
    public static File modConfigDir;
    public static Configuration config;
    public static final int GUI_LIGHT = 1;


    public static CreativeTabs tabRFLux = new CompatCreativeTabs("RFLux") {
        @Override
        protected Item getItem() {
            return Items.GLOWSTONE_DUST;
        }
    };

    /**
     * Run before anything else. Read your config, create blocks, items, etc, and
     * register them with the GameRegistry.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        mainConfigDir = e.getModConfigurationDirectory();
        modConfigDir = new File(mainConfigDir.getPath());
        config = new Configuration(new File(modConfigDir, "rflux.cfg"));
        proxy.preInit(e);

//        FMLInterModComms.sendMessage("Waila", "register", "mcjty.wailasupport.WailaCompatibility.load");
    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Override
    public String getModId() {
        return MODID;
    }

    @Override
    public void openManual(EntityPlayer entityPlayer, int i, String s) {

    }
}
