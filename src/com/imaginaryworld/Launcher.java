package com.imaginaryworld;

import com.imaginaryworld.ui.Painter;
import com.imaginaryworld.world.World;

import javax.swing.*;

public class Launcher {

    private final Painter painter;
    private final World world;

    public Launcher() {
        world = new World();
        painter = new Painter();
    }

    public void launch(){
        painter.initializeUi();
        painter.buildUniverse();
        painter.showFrame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Launcher().launch();
            }
        });
    }

}
