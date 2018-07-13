package com.minelittlepony.remodeller.serialization;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.minelittlepony.remodeller.ducks.IModelBiped;
import com.minelittlepony.remodeller.ducks.IModelRenderer;

public class Model extends Serializable {

    @Expose
    Map<String, Component> namedComponents = new HashMap<String, Component>();

    public Model() {}

    public Model(IModelBiped model) {

        Map<String, IModelRenderer> boxes = model.getCompiledBoxList();

        for (String key : boxes.keySet()) {
            namedComponents.put(key, new Component(boxes.get(key)));

            Component.instanceMap.get(boxes.get(key)).uniqueName = key;
        }

        Component.instanceMap.clear();
    }
}
