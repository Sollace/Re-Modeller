package com.minelittlepony.remodeller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.google.gson.annotations.Expose;
import com.minelittlepony.remodeller.ducks.IModelBiped;
import com.minelittlepony.remodeller.serialization.Model;
import com.minelittlepony.remodeller.serialization.RenderConfig;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.Exposable;

public class ModelRewriteManager extends ModelResourceReloader implements Exposable {

    public static ModelRewriteManager INSTANCE = new ModelRewriteManager();

    @Expose
    public boolean debug = false;

    public void rewriteModel(IModelBiped obj) {
        String key = obj.getClass().getCanonicalName().toLowerCase();

        if (rewrites.containsKey(key)) {
            rewrites.get(key).applyRewrites(obj);
        }

        if (debug) {
            File output = new File(LiteLoader.getConfigFolder(), key + ".model");
            if (!output.exists()) {
                new Model(obj).saveToFile(output);
            }
            output = new File(LiteLoader.getConfigFolder(), key + ".json");
            if (!output.exists()) {
                new RenderConfig(obj).saveToFile(output);

                rewrites.remove(key);
            } else {
                try {
                    RenderConfig config = RenderConfig.fromJson(new FileInputStream(output));
                    if (config != null) {
                        rewrites.put(key, config);
                    }
                } catch (FileNotFoundException e) { }
            }
        }
    }
}
