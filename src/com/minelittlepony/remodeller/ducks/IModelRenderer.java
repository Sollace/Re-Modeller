package com.minelittlepony.remodeller.ducks;

import com.minelittlepony.remodeller.serialization.RenderContext;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public interface IModelRenderer {

    int getTextureOffsetX();

    int getTextureOffsetY();

    void setRenderingContext(RenderContext context);

    RenderContext getRenderingContext();

    void rerender(float scale);

    ModelBiped getOwningModel();

    default ModelRenderer unwrap() {
        return (ModelRenderer)this;
    }
}
