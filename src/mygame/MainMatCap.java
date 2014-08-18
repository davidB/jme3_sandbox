package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

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
        
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

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
