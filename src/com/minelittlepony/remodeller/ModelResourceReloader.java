package com.minelittlepony.remodeller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.minelittlepony.remodeller.serialization.RenderConfig;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

abstract class ModelResourceReloader implements IResourceManagerReloadListener {

    protected final Map<String, RenderConfig> rewrites = new HashMap<String, RenderConfig>();

    @Override
    public void onResourceManagerReload(IResourceManager manager) {
        rewrites.clear();

        Set<String> rewritersToLoad = new HashSet<String>();

        for (String domain : manager.getResourceDomains()) {
            try {
                for (IResource resource : manager.getAllResources(new ResourceLocation(domain, "modelrewrites/modelrewriters.txt"))) {
                    try (InputStream s = resource.getInputStream()) {
                        rewritersToLoad.addAll(IOUtils.readLines(s, Charset.defaultCharset()));
                    }
                }
            } catch (IOException e) {
                LiteModReModeller.LOGGER.error("Could not load modelrewriters.txt", e);
            }
        }

        for (String domain : manager.getResourceDomains()) {
            try {
                for (String className : rewritersToLoad) {
                    for (IResource resource : manager.getAllResources(new ResourceLocation(domain, String.format("modelrewrites/%s.json", className)))) {
                        try (InputStream s = resource.getInputStream()) {
                            RenderConfig obj = RenderConfig.fromJson(s);
                            if (obj != null) {
                                rewrites.put(className, obj);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                LiteModReModeller.LOGGER.error("Could not load modelrewriter", e);
            }
        }
    }
}
