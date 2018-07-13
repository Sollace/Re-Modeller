package com.minelittlepony.remodeller.serialization;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.minelittlepony.remodeller.ducks.IModelBiped;
import com.minelittlepony.remodeller.ducks.IModelRenderer;

public class RenderConfig extends Serializable {

    private static final ParameterizedType TYPE = new ParameterizedType() {
        public Type[] getActualTypeArguments() {
            return new Type[] {};
        }

        public Type getRawType() {
            return RenderConfig.class;
        }

        public Type getOwnerType() {
            return null;
        }
    };

    public static RenderConfig fromJson(InputStream stream) {
        return fromJson(stream, TYPE);
    }

    @Expose
    protected Map<String, StancedRenderContext> elements = new HashMap<String, StancedRenderContext>();

    public RenderConfig() {}

    public RenderConfig(IModelBiped model) {
        Map<String, IModelRenderer> contexts = model.getCompiledBoxList();

        for (String key : contexts.keySet()) {
            RenderContext ctx = contexts.get(key).getRenderingContext();
            if (ctx == null) ctx = new StancedRenderContext();

            elements.put(key, (StancedRenderContext)ctx);
        }
    }

    public void applyRewrites(IModelBiped obj) {
        Map<String, IModelRenderer> renderers = obj.getCompiledBoxList();

        for (String key : renderers.keySet()) {
            if (elements.containsKey(key)) {
                renderers.get(key).setRenderingContext(elements.get(key));
            }
        }
    }
}
