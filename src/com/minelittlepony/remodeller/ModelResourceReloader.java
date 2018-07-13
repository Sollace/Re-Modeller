package com.minelittlepony.remodeller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.minelittlepony.remodeller.serialization.RenderConfig;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

abstract class ModelResourceReloader implements IResourceManagerReloadListener {

    private static final List<String> EMPTY = new ArrayList<>();

    protected final Map<String, RenderConfig> rewrites = new HashMap<String, RenderConfig>();

    private List<String> loadLines(InputStream stream) {
        try {
            return IOUtils.readLines(stream, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stream);
        }

        return EMPTY;
    }

    @Override
    public void onResourceManagerReload(IResourceManager manager) {
        rewrites.clear();

        List<String> rewritersToLoad = new ArrayList<String>();

        for (String domain : manager.getResourceDomains()) {
            try {
                for (IResource resource : manager.getAllResources(new ResourceLocation(domain, "modelrewrites/modelrewriters.txt"))) {
                    for (String i : loadLines(resource.getInputStream())) {
                        if (rewritersToLoad.contains(i)) rewritersToLoad.add(i);
                    }
                }
            } catch (IOException e) { }
        }

        for (String domain : manager.getResourceDomains()) {
            try {
                for (String className : rewritersToLoad) {
                    for (IResource resource : manager.getAllResources(new ResourceLocation(domain, String.format("modelrewrites/%s.json", className)))) {
                        RenderConfig obj = RenderConfig.fromJson(resource.getInputStream());
                        if (obj != null) {
                            rewrites.put(className, obj);
                        }
                    }
                }
            } catch (IOException e) {  }
        }
    }
}
