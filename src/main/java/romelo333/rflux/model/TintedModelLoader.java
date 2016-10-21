package romelo333.rflux.model;

import com.google.common.base.Function;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.model.IModelState;

import java.util.Collection;
import java.util.List;

public class TintedModelLoader implements ICustomModelLoader {

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return OBJLoader.INSTANCE.accepts(modelLocation);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        IModel model = OBJLoader.INSTANCE.loadModel(modelLocation);

//        return new IModel() {
//            @Override
//            public Collection<ResourceLocation> getDependencies() {
//                return model.getDependencies();
//            }
//
//            @Override
//            public Collection<ResourceLocation> getTextures() {
//                return model.getTextures();
//            }
//
//            @Override
//            public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
//                IBakedModel bakedModel = model.bake(state, format, bakedTextureGetter);
//                List<BakedQuad> quads = bakedModel.getQuads(null, null, 0);
//
//
//                return ;
//            }
//
//            @Override
//            public IModelState getDefaultState() {
//                return model.getDefaultState();
//            }
//        };
        return model;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
