package com.imaginaryworld.world;

import com.imaginaryworld.ui.Painter;

import javax.media.j3d.Group;

public class CustomUniverse {
    public int x;
    public int y;
    public int z;
    public int numIter;

    private Painter painter;

    public CustomUniverse(Painter painter, int x, int y, int z, int numIter) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.numIter = numIter;
        this.painter = painter;
    }

    public Group getScene() {
        return painter.buildScene(x, y, z, numIter);
    }
}
