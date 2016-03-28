package com.imaginaryworld;

import com.imaginaryworld.ui.UniverseViewer;
import com.imaginaryworld.ui.universe.ExamineViewerBehavior;
import com.imaginaryworld.ui.universe.WalkViewerBehavior;
import com.sun.j3d.exp.swing.JCanvas3D;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.concurrent.atomic.AtomicBoolean;

public class Universe implements AWTEventListener{

    private final Main main;
    private SimpleUniverse universe;
    private BranchGroup sceneRoot;
    private TransformGroup exampleViewTransform = null;
    private TransformGroup sceneTransform = null;
    protected ExamineViewerBehavior examineBehavior = null;
    private WalkViewerBehavior walkBehavior = null;
    private Painter painter;
    public static boolean headlightOnOff = true;

    public final static int Walk = 0;
    public final static int Examine = 1;
    private DirectionalLight headlight;
    private JCanvas3D canvas;

    public Universe(Main main){
        this.main = main;
        buildUniverse();
        long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK;
        Toolkit.getDefaultToolkit().addAWTEventListener(this, eventMask);
    }

    /**
     * Builds the 3D universe by constructing a virtual universe (via
     * SimpleUniverse), a view platform (via SimpleUniverse), and a view (via
     * SimpleUniverse). A headlight is added and a set of behaviors initialized
     * to handle navigation types.
     */
    public void buildUniverse() {
        canvas = new JCanvas3D(new GraphicsConfigTemplate3D());
        canvas.setSize(630, 460);
        universe = new SimpleUniverse(canvas.getOffscreenCanvas3D());

        exampleViewTransform = universe.getViewingPlatform().getViewPlatformTransform();

        BoundingSphere allBounds = new BoundingSphere(
                new Point3d(0.0, 0.0, 0.0), 100000.0);

        PlatformGeometry pg = new PlatformGeometry();
        headlight = new DirectionalLight();
        headlight.setColor(Painter.White);
        headlight.setDirection(new Vector3f(0.0f, 0.0f, -1.0f));
        headlight.setInfluencingBounds(allBounds);
        headlight.setCapability(Light.ALLOW_STATE_WRITE);
        pg.addChild(headlight);
        universe.getViewingPlatform().setPlatformGeometry(pg);

        sceneRoot = new BranchGroup();
        painter = new Painter(sceneRoot);

        setBehaviors(allBounds, main.getUniverseViewer().canvas);
        sceneRoot.addChild(painter.buildScene());

        universe.getViewingPlatform().setNominalViewingTransform();

        sceneRoot.compile();

        universe.addBranchGraph(sceneRoot);
        reset();

        main.addCanvas(canvas);
    }

    /**
     * Sets the headlight on/off state. The headlight faces forward in the
     * direction the viewer is facing. Example applications that add their own
     * lights will typically turn the headlight off. A standard menu item
     * enables the headlight to be turned on and off via user control.
     *
     * @param onOff
     *            a boolean turning the light on (true) or off (false)
     */
    public void setHeadlightEnable(boolean onOff) {
        headlightOnOff = onOff;
        if (headlight != null)
            headlight.setEnable(headlightOnOff);

        //headlightMenuItem.setState(headlightOnOff);
    }

    //--------------------------------------------------------------
    //  SCENE CONTENT
    //--------------------------------------------------------------

    public void setBehaviors(BoundingSphere allBounds, Component parentFrame) {
        // Build a transform that we can modify
        sceneTransform = new TransformGroup();
        sceneTransform
                .setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        sceneTransform
                .setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sceneTransform.setCapability(Group.ALLOW_CHILDREN_EXTEND);

        Group scene = painter.buildScene();
        sceneTransform.addChild(scene);
        sceneRoot.addChild(sceneTransform);

         // Parent frame for cusor changes
        examineBehavior = new ExamineViewerBehavior(sceneTransform, parentFrame);
        examineBehavior.setSchedulingBounds(allBounds);
        sceneRoot.addChild(examineBehavior);

        walkBehavior = new WalkViewerBehavior(exampleViewTransform, parentFrame);
        walkBehavior.setSchedulingBounds(allBounds);
        sceneRoot.addChild(walkBehavior);

        MouseWheelZoom mouseZoom = new MouseWheelZoom();
        mouseZoom.setFactor(2);
        mouseZoom.setTransformGroup(sceneTransform);
        mouseZoom.setSchedulingBounds(allBounds);
        sceneRoot.addChild(mouseZoom);

        mouseZoom.setEnable(true);
    }

    //  Reset transforms
    public void reset() {
        Transform3D trans = new Transform3D();
        sceneTransform.setTransform(trans);
        trans.set(new Vector3f(0.0f, 0.0f, 10.0f));
        exampleViewTransform.setTransform(trans);
    }

    /**
     * Gets the headlight on/off state.
     *
     * @return a boolean indicating if the headlight is on or off
     */
    public boolean getHeadlightEnable() {
        return headlightOnOff;
    }

    public void setBehaviour(int behaviour) {
        switch (behaviour){
            case Walk:
                walkBehavior.setEnable(true);
                examineBehavior.setEnable(false);
                break;
            case Examine:
                walkBehavior.setEnable(false);
                examineBehavior.setEnable(true);
                break;
        }
    }

    @Override
    public void eventDispatched(AWTEvent event) {
    }
}
