package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Boat {
public Boat() {
    }
    public void DrawBoat(Texture planks) {
       planks.bind();
       GL11.glPushMatrix();
       GL11.glTranslatef(2100.0f, 200.0f, -400.0f);
       GL11.glScalef(90.0f, 10.0f, 90.0f);
       TexCube cube = new TexCube();
       cube.DrawTexCube(planks);
        {
            GL11.glPushMatrix();

            GL11.glPopMatrix();
        }


        {
            GL11.glPushMatrix();
            GL11.glTranslatef(1.0f, 3.5f, 0.0f);
            GL11.glScalef(0.1f, 3f, 1f);
            cube.DrawTexCube(planks);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(-1.0f, 3.5f, 0.0f);
            GL11.glScalef(0.1f, 3f, 1f);
            cube.DrawTexCube(planks);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 3.5f, 1.0f);
            GL11.glScalef(1f, 3f, 0.1f);
            cube.DrawTexCube(planks);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 3.5f, -1.0f);
            GL11.glScalef(1f, 3f, 0.1f);
            cube.DrawTexCube(planks);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(-1.0f, 8.0f, 0.0f);
            GL11.glRotatef(45, 1.0f, 0.0f, 1.0f);
            GL11.glTranslatef(1.0f, -8.0f, 0.0f);
            GL11.glTranslatef(1f, 8f, 0);
            GL11.glScalef(0.1f, 5.0f, 0.2f);
            cube.DrawTexCube(planks);
            GL11.glPopMatrix();
        }


       GL11.glPopMatrix();
    }
}
