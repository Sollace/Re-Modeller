package com.minelittlepony.remodeller.model;

import net.minecraft.client.model.ModelRenderer;

public enum RewriteAction {
    NONE {
        @Override
        public void apply(ModelRenderer renderer, Arithmetic arith, BasicRewrite values) {

        }
    },
    ROTATE_ANGLE {
        @Override
        public void apply(ModelRenderer renderer, Arithmetic arith, BasicRewrite values) {
            renderer.rotateAngleX = arith.apply(renderer.rotateAngleX, values.x);
            renderer.rotateAngleX = arith.apply(renderer.rotateAngleY, values.y);
            renderer.rotateAngleX = arith.apply(renderer.rotateAngleZ, values.z);
        }
    },
    OFFSET {
        @Override
        public void apply(ModelRenderer renderer, Arithmetic arith, BasicRewrite values) {
            renderer.offsetX = arith.apply(renderer.offsetX, values.x);
            renderer.offsetX = arith.apply(renderer.offsetY, values.y);
            renderer.offsetX = arith.apply(renderer.offsetZ, values.z);
        }
    },
    ROTATION_POINT {
        @Override
        public void apply(ModelRenderer renderer, Arithmetic arith, BasicRewrite values) {
            renderer.rotationPointX = arith.apply(renderer.rotationPointX, values.x);
            renderer.rotationPointX = arith.apply(renderer.rotationPointY, values.y);
            renderer.rotationPointX = arith.apply(renderer.rotationPointZ, values.z);
        }
    };

    public abstract void apply(ModelRenderer renderer, Arithmetic arith, BasicRewrite values);
}
