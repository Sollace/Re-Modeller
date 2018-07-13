package com.minelittlepony.remodeller.serialization;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.minelittlepony.remodeller.ducks.IModelRenderer;

import net.minecraft.client.model.ModelRenderer;

public class Component extends Serializable {

    static Map<Object, Component> instanceMap = new HashMap<Object, Component>();

    @Expose
    float[] textureSize;

    @Expose
    int[] textureOffset;

    @Expose
    double[] offset;

    @Expose
    double[] rotationPoint;

    @Expose
    double[] rotation;

    @Expose
    Box[] boxes;

    @Expose
    Component[] children;

    @Expose
    String uniqueName;

    public Component() {}

    public Component(IModelRenderer irenderer) {
        if (instanceMap.containsKey(irenderer)) {
            uniqueName = instanceMap.get(irenderer).uniqueName;
            return;
        }

        ModelRenderer renderer = irenderer.unwrap();

        instanceMap.put(renderer, this);

        textureSize = new float[] {
            renderer.textureWidth, renderer.textureHeight
        };

        offset = new double[] {
            renderer.offsetX, renderer.offsetY, renderer.offsetZ
        };

        rotationPoint = new double[] {
            renderer.rotationPointX, renderer.rotationPointY, renderer.rotationPointZ
        };

        rotation = new double[] {
            renderer.rotateAngleX, renderer.rotateAngleY, renderer.rotateAngleZ
        };

        boxes = new Box[renderer.cubeList.size()];
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new Box(renderer.cubeList.get(i));
        }

        int childCount = renderer.childModels == null ? 0 : renderer.childModels.size();
        children = new Component[childCount];
        for (int i = 0; i < childCount; i++) {
            children[i] = new Component((IModelRenderer)renderer.childModels.get(i));
        }

        textureOffset = new int[] {
            irenderer.getTextureOffsetX(), irenderer.getTextureOffsetY()
        };
    }

    @Override
    public String toString() {
        return Model.GSON.toJson(this);
    }
}
