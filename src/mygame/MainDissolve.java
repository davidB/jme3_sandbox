package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.input.ChaseCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.ui.Picture;

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
        Geometry pic = new Geometry("pic", new Quad(10f, 10f));
        pic.setMaterial(assetManager.loadMaterial("Materials/image.j3m"));
        pic.setLocalRotation(new Quaternion(new float[]{0.0f, 90f * FastMath.DEG_TO_RAD, 0.0f * FastMath.DEG_TO_RAD}));
        pic.setLocalTranslation(-6.0f, -4.0f, 5.0f);
        rootNode.attachChild(pic);
        
        Node target = new Node("target");

        Geometry[] geoms = {
            new Geometry("obj", new Box(0.5f, 0.5f, 0.5f))
            , new Geometry("obj", new Sphere(16, 16, 0.5f))
        };
        
        //FIXME: dissolv should une 1.0 * normal for backface
        //mat = new Material(assetManager, "ShaderBlow/MatDefs/Dissolve/Lighting.j3md");
        mat = new Material(assetManager, "MatDefs/DLighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Diffuse", ColorRGBA.Pink);
        mat.setTexture("DissolveMap", assetManager.loadTexture("Textures/burnMap.png"));
        mat.setVector2("DissolveParams", new Vector2f(0.0f, 0.0f));
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

        for( int i = 0; i < geoms.length; i++) {
            Geometry g = geoms[i];
            g.setMaterial(mat);
            float pos = ((i+0.5f) - 0.5f * geoms.length) * 2.5f;
            g.setLocalTranslation(Vector3f.UNIT_Z.mult(pos));
            target.attachChild(g);
        }

        rootNode.attachChild(target);
        DirectionalLight light = new DirectionalLight();
        light.setDirection(new Vector3f(-1.0f, -0.75f, -0.5f).normalizeLocal());
        rootNode.addLight(light);
        
//        flyCam.setEnabled(true);
//        flyCam.setMoveSpeed(30);
//        flyCam.setRotationSpeed(10);
//        flyCam.setDragToRotate(true);

        flyCam.setEnabled(false);
        ChaseCamera chaseCam = new ChaseCamera(cam, target, inputManager);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setMinVerticalRotation((float)-Math.PI / 2);
        //TODO calculate de distance from the boundingbox (+ margin) and projection
        //chaseCam.setDefaultDistance(2.0f + target.getQuantity() * 2.0f);
        BoundingVolume bv = target.getWorldBound();
        float d = 0.5f;
        switch (bv.getType()) {
        case Sphere:
            d += ((BoundingSphere)bv).getRadius();
            System.out.println("sphere :" + d);
            break;
        case AABB:
            BoundingBox bb = ((BoundingBox) bv);
            d += Math.max(bb.getYExtent(), Math.max(bb.getZExtent(), bb.getXExtent()));
            System.out.println("AABB :" + d);
            break;
        default:
            d += target.getQuantity();
            System.out.println("??? :" + d);
        }
        chaseCam.setDefaultDistance(d * 2.0f);
    }

    @Override
    public void simpleUpdate(float tpf) {
        float part = getTimer().getTimeInSeconds() / 3.0f; // full dissolve every 3s
        float direction = (float)Math.floor(part) % 2f; // 0.0 or 1.0
        float ratio = part - (float) Math.floor(part);
        mat.setVector2("DissolveParams", new Vector2f(ratio, direction));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
