package romelo333.rflux.setup;


import mcjty.lib.McJtyRegister;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import romelo333.rflux.RFLux;

@Mod.EventBusSubscriber
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        McJtyRegister.registerBlocks(RFLux.instance, event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        McJtyRegister.registerItems(RFLux.instance, event.getRegistry());
    }
}
