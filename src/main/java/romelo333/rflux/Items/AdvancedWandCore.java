package romelo333.rflux.Items;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import romelo333.rflux.RFLux;

public class AdvancedWandCore extends Item {
    public AdvancedWandCore(String name, String texture) {
        setMaxStackSize(64);
        setUnlocalizedName(name);
        setCreativeTab(RFLux.tabNew);
        setTextureName(RFLux.MODID + ":" + texture);
        GameRegistry.registerItem(this, name);
    }
}
