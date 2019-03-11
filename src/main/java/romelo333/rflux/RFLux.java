package romelo333.rflux;

import mcjty.lib.base.ModBase;
import mcjty.lib.proxy.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import romelo333.rflux.setup.ModSetup;

@Mod(modid = RFLux.MODID, name="RF Lux",
        dependencies =
                        "required-after:mcjtylib_ng@[" + RFLux.MIN_MCJTYLIB_VER + ",);" +
                        "after:forge@[" + RFLux.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.12,1.13)",
        version = RFLux.VERSION)
public class RFLux implements ModBase {
    public static final String MODID = "rflux";
    public static final String VERSION = "0.3.2";
    public static final String MIN_FORGE11_VER = "13.19.0.2157";
    public static final String MIN_MCJTYLIB_VER = "3.1.1";

    @SidedProxy(clientSide="romelo333.rflux.setup.ClientProxy", serverSide="romelo333.rflux.setup.ServerProxy")
    public static IProxy proxy;
    public static ModSetup setup = new ModSetup();

    @Mod.Instance("rflux")
    public static RFLux instance;


    /**
     * Run before anything else. Read your config, create blocks, items, etc, and
     * register them with the GameRegistry.
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        setup.preInit(e);
        proxy.preInit(e);
    }

    /**
     * Do your mod setup. Build whatever data structures you care about. Register recipes.
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        setup.init(e);
        proxy.init(e);
    }

    /**
     * Handle interaction with other mods, complete your setup based on this.
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        setup.postInit(e);
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
