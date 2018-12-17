package com.minelittlepony.remodeller.ducks;

import java.util.Map;

import com.minelittlepony.model.armour.IEquestrianArmor;
import com.minelittlepony.model.armour.ModelPonyArmor;

import net.minecraft.client.model.ModelBiped;

public interface IModelBiped extends IEquestrianArmor<ModelPonyArmor> {
    Map<String, IModelRenderer> getCompiledBoxList();

    default ModelBiped unwrap() {
        return (ModelBiped)this;
    }
}
