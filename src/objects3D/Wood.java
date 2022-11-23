package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Wood {
    public Wood(){

    }

    public void DrawWood(Texture planks){
        TexCube cube = new TexCube();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f,(float) (48400-50000)/1000*6f, 0.0f);
        GL11.glTranslatef((float) ((48000 - 27000) / 1000 * 1.25), 0, 0);
        GL11.glTranslatef(0.0f, 1.0f, -1.0f);
        GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, -1.0f, 1.0f);
        GL11.glTranslatef(0.0f, 0.0f, -(23000 - 22000) / 10000.0f * 18);
        cube.DrawTexCube(planks);
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, -2.0f, 0.0f);
            cube.DrawTexCube(planks);
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, -2.0f, 0.0f);
                cube.DrawTexCube(planks);
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();

        }
        GL11.glPopMatrix();
    }
}
