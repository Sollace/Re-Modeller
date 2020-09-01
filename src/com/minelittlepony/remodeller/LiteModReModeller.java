package com.minelittlepony.remodeller;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;

public class LiteModReModeller implements LiteMod {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String getName() {
        return "@NAME@";
    }

    @Override
    public String getVersion() {
        return "@VERSION@";
    }

    @Override
    public void init(File configPath) {
        ((IReloadableResourceManager)Minecraft.getMinecraft().getResourceManager()).registerReloadListener(ModelRewriteManager.INSTANCE);

        LiteLoader.getInstance().registerExposable(ModelRewriteManager.INSTANCE, null);
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {

    }
}
