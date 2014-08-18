package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * test
 * @author normenhansen
 */
public class MainDissolve extends SimpleApplication {
    Material mat;
    
    public static void main(String[] args) {
        MainDissolve app = new MainDissolve();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        Box shape = new Box(0.5f, 0.5f, 0.5f);
        //Sphere shape = new Sphere(16, 16, 0.5f);
        
        Geometry geom = new Geometry("obj", shape);

        //Material mat = assetManager.loadMaterial("Materials/matcap_0.j3m");
        mat = new Material(assetManager, "ShaderBlow/MatDefs/Dissolve/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Pink);
        //mat.setTexture("DissolveMap", assetManager.loadTexture("Textures/mapcap_0.png"));
        mat.setTexture("DissolveMap", assetManager.loadTexture("Textures/burnMap.png"));
        mat.setVector2("DissolveParams", new Vector2f(0.5f, 0.0f));
        
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-1.0f, -0.75f, -0.5f).normalizeLocal());
        rootNode.addLight(light);
        
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
        float ratio = getTimer().getTimeInSeconds() / 2.0f;
        ratio = ratio - (float) Math.floor(ratio);
        mat.setVector2("DissolveParams", new Vector2f(ratio, 0.0f));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
