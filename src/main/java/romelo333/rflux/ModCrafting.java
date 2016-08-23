package romelo333.rflux;


import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
    public static void init() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.lightBlockOff), "rgr", "gBg", "rgr", 'r', Items.REDSTONE, 'g', Blocks.GLOWSTONE, 'B', Blocks.GLASS);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.flatLightBlockOff, 3), "bbb", 'b', ModBlocks.lightBlockOff);
    }
}
