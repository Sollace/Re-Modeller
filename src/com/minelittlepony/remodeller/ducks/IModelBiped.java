package com.minelittlepony.remodeller.ducks;

import java.util.Map;

import com.minelittlepony.api.armor.IEquestrianArmor;

import net.minecraft.client.model.ModelBiped;

public interface IModelBiped extends IEquestrianArmor {
    Map<String, IModelRenderer> getCompiledBoxList();

    default ModelBiped unwrap() {
        return (ModelBiped)this;
    }
}
