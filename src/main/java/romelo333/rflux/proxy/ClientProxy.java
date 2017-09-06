package romelo333.rflux.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import romelo333.rflux.ClientEventHandler;
import romelo333.rflux.ModBlocks;
import romelo333.rflux.RFLux;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        super.preInit(e);
        OBJLoader.INSTANCE.addDomain(RFLux.MODID);
        ClientEventHandler.subscribeMe();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
    }


//    @SubscribeEvent
//    public void renderWorldLastEvent(RenderWorldLastEvent evt) {
//        Minecraft mc = Minecraft.getMinecraft();
//        EntityClientPlayerMP p = mc.thePlayer;
//        ItemStack heldItem = p.getHeldItem();
//        if (heldItem == null) {
//            return;
//        }
//        if (heldItem.getItem() instanceof GenericWand) {
//            GenericWand genericWand = (GenericWand) heldItem.getItem();
//            genericWand.renderOverlay(evt, p, heldItem);
//        }
//    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        ModBlocks.initClientPost();
    }
}
