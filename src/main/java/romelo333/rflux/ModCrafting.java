package romelo333.rflux;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
    public static void init() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightBlock), "rgr", "gBg", "rgr", 'r', Items.REDSTONE, 'g', Items.GLOWSTONE_DUST, 'B', Blocks.GLASS);
    }
}
