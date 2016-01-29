package romelo333.rflux.blocks;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import romelo333.rflux.RFLux;

@SideOnly(Side.CLIENT)
public class LightRenderer extends TileEntitySpecialRenderer<LightTE> {
//    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(RFLux.MODID, "obj/flatlight.obj"));
    ResourceLocation blockTexture = new ResourceLocation(RFLux.MODID, "textures/blocks/white.png");

    public LightRenderer() {
    }

    @Override
    public void renderTileEntityAt(LightTE te, double x, double y, double z, float partialTicks, int destroyStage) {
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_ENABLE_BIT | GL11.GL_LIGHTING_BIT | GL11.GL_TEXTURE_BIT);

        bindTexture(blockTexture);

        GL11.glPushMatrix();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.0F, (float) z + 0.5F);

//            model.renderAll();
        GL11.glPopMatrix();

        GL11.glPopAttrib();
    }


}
