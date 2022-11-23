package objects3D;

import GraphicsObjects.Utils;
import jdk.jfr.internal.tool.Main;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Torch {
    static float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    public Torch() {

    }
    public void DrawTorch(Texture tree, Texture head) {
        GL11.glScalef(5f, 40f, 5f);
        TexCube cube = new TexCube();
        tree.bind();
        cube.DrawTexCube(tree);
        GL11.glPushMatrix();
        {
            GL11.glScalef(1f, 0.125f, 1f);
            GL11.glTranslatef(0, 9f, 0);
            GL11.glRotatef(90, 1, 0, 0);
            head.bind();
            cube.DrawTexCube(head);
        }
        GL11.glPopMatrix();
    }
}
