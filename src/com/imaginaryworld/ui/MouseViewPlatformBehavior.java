package com.imaginaryworld.ui;

import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import java.awt.*;

class MouseViewPlatformBehavior extends MouseWheelZoom {

    public MouseViewPlatformBehavior(Component parent, TransformGroup transformGroup){
        super(parent, transformGroup);
    }
    public void transformChanged(Transform3D transform) {
        System.out.println(transform.toString());
    }
}