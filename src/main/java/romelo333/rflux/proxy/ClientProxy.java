package romelo333.rflux.proxy;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import romelo333.rflux.*;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        OBJLoader.INSTANCE.addDomain(RFLux.MODID);
        ModBlocks.initModels();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        KeyBindings.init();
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
    }
}
