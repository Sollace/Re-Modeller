package com.minelittlepony.remodeller.serialization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mumfrey.liteloader.modconfig.Exposable;

public abstract class Serializable implements Exposable {

    static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String toString() {
        return GSON.toJson(this);
    }

    public void saveToFile(File output) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(output);

            GSON.toJson(this, writer);

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            IOUtils.closeQuietly(writer);
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T fromJson(InputStream stream, ParameterizedType type) {
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            reader.setLenient(true);

            return (T)GSON.getAdapter(TypeToken.get(type)).read(reader);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stream);
        }
        return null;
    }
}
