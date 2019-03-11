package romelo333.rflux.setup;

import mcjty.lib.setup.DefaultClientProxy;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import romelo333.rflux.ClientEventHandler;
import romelo333.rflux.ModBlocks;
import romelo333.rflux.RFLux;

public class ClientProxy extends DefaultClientProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        OBJLoader.INSTANCE.addDomain(RFLux.MODID);
        ClientEventHandler.subscribeMe();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        ModBlocks.initClientPost();
    }
}
