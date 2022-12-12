package GraphicsObjects;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.util.glu.GLU.gluLookAt;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Camera {
    public Point4f position;
    public Vector4f rotation;
    public Integer OrthNumber;

    public Camera(Point4f position, Vector4f rotation,Integer OrthNumber) {
        this.position = position;
        this.rotation = rotation;
        this.OrthNumber = OrthNumber;
    }

    public void update(){
        GL11.glLoadIdentity();
        GL11.glRotatef(rotation.x, 1, 0, 0);
        GL11.glRotatef(rotation.y, 0, 1, 0);
        GL11.glRotatef(rotation.z, 0, 0, 1);
        GL11.glTranslatef(position.x, position.y, position.z);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
//        gluLookAt(position.x, position.y, position.z, 0, 0, 0, 0, 1, 0);

        GL11.glOrtho(1200 - OrthNumber, OrthNumber, (800 - (OrthNumber * 0.66f)), (OrthNumber * 0.66f), 100000, -100000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//        System.out.println("OrthNumber: " + OrthNumber);
//        System.out.println("position: " + position);
//        System.out.println("rotation: " + rotation);
    }


}
