package com.minelittlepony.remodeller.wrappers;

import com.minelittlepony.remodeller.ducks.IModelRenderer;
import com.minelittlepony.render.model.PonyRenderer;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class WrappedPonyRenderer extends PonyRenderer {

    private ModelRenderer wrappedObject;

    public WrappedPonyRenderer(ModelBase model, ModelRenderer merged, ModelRenderer inherited) {
        super(model);
        wrap(inherited);

        rotateAt(merged);
        rotateTo(merged);

       /* cubeList.addAll(merged.cubeList);
        if (merged.childModels != null) {
            if (childModels == null) {
                childModels = wrappedObject.childModels = merged.childModels;
            } else {
                childModels.addAll(merged.childModels);
            }
        }*/
    }

    private WrappedPonyRenderer(ModelBase model, int texX, int texY) {
        super(model, texX, texY);
    }

    public void wrap(ModelRenderer renderer) {
        wrappedObject = renderer;

        childModels = wrappedObject.childModels;
        cubeList = wrappedObject.cubeList;
        textureWidth = wrappedObject.textureWidth;
        textureHeight = wrappedObject.textureHeight;
        mirror = wrappedObject.mirror;
        showModel = wrappedObject.showModel;
        isHidden = wrappedObject.isHidden;

        setTextureOffset(((IModelRenderer)wrappedObject).getTextureOffsetX(),
                ((IModelRenderer)wrappedObject).getTextureOffsetY());
    }

    @Override
    protected PonyRenderer copySelf() {
        return new WrappedPonyRenderer(baseModel, textureOffsetX, textureOffsetY);
    }
}
