package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * test
 * @author normenhansen
 */
public class MainMatCap extends SimpleApplication {

    public static void main(String[] args) {
        MainMatCap app = new MainMatCap();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        Box shape = new Box(0.5f, 0.5f, 0.5f);
        //Sphere shape = new Sphere(16, 16, 0.5f);
        
        Geometry geom = new Geometry("obj", shape);

        //Material mat = assetManager.loadMaterial("Materials/matcap_0.j3m");
        Material mat = new Material(assetManager, "ShaderBlow/MatDefs/MatCap/MatCap.j3md");
        mat.setTexture("DiffuseMap", assetManager.loadTexture("Textures/mapcap_0.png"));
        mat.setColor("Multiply_Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
//        flyCam.setEnabled(true);
//        flyCam.setMoveSpeed(30);
//        flyCam.setRotationSpeed(10);
//        flyCam.setDragToRotate(true);

        flyCam.setEnabled(false);
        ChaseCamera chaseCam = new ChaseCamera(cam, geom, inputManager);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setMinVerticalRotation((float)-Math.PI / 2);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
