package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Sign {
    public Sign() {
    }
    public void DrawSign(Texture sign) {
        TexCube cube = new TexCube();
        Cylinder cylinder = new Cylinder();
        GL11.glPushMatrix();
        GL11.glTranslatef(1000f,700f,900f);
        GL11.glScalef(8f, 8f, 8f);
        GL11.glRotatef(90, 1, 0, 0);
        cylinder.DrawCylinder(0.5f, 70f, 32);
        GL11.glPushMatrix();
        GL11.glScalef(15f, 1f, 15f);
        GL11.glRotatef(180, 1, 0, 0);
        sign.bind();
        cube.DrawTexCubeEx(sign,5);
        GL11.glPopMatrix();
        GL11.glPopMatrix();

    }
}
