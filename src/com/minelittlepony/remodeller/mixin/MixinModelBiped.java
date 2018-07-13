package com.minelittlepony.remodeller.mixin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;

import com.minelittlepony.model.armour.PonyArmor;
import com.minelittlepony.remodeller.ducks.IModelBiped;
import com.minelittlepony.remodeller.ducks.IModelRenderer;
import com.minelittlepony.remodeller.wrappers.PonyArmourWrapper;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

@Mixin(ModelBiped.class)
public abstract class MixinModelBiped implements IModelBiped {

    private PonyArmor ponyArmourWrapper = null;

    private Map<String, IModelRenderer> compiledBoxList = null;

    @Override
    public PonyArmor getEquestrianArmour() {
        if (ponyArmourWrapper == null) {
            ponyArmourWrapper = PonyArmourWrapper.wrap((ModelBiped)(Object)this);
        }

        return ponyArmourWrapper;
    }

    @Override
    public Map<String, IModelRenderer> getCompiledBoxList() {
        if (compiledBoxList == null) {
            compiledBoxList = new HashMap<String, IModelRenderer>();

            Class<?> type = this.getClass();

            for (Field i : type.getDeclaredFields()) {
                if (ModelRenderer.class.isAssignableFrom(i.getType())) {
                    i.setAccessible(true);
                    IModelRenderer obj;
                    try {
                        obj = (IModelRenderer)i.get(this);
                        if (obj != null) {
                            compiledBoxList.put(i.getName(), obj);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        return compiledBoxList;
    }
}
