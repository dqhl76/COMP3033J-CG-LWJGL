package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Wall {
    public Wall() {
    }
    public void DrawWall(Texture wall) {

        wall.bind();
        TexCube cube = new TexCube();
        GL11.glTranslatef(0.0f, 280.0f, 960.0f);
        GL11.glScalef(160.0f, 160.0f, 80.0f);
        cube.DrawTexCube(wall,2);
        {
            GL11.glPushMatrix();
            GL11.glTranslated(0.0f, 1.0f, 0.0f);
            cube.DrawTexCube(wall,2);
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 1.0f, 0.0f);
                cube.DrawTexCube(wall,2);
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0f, 1.0f, 0.0f);
                    cube.DrawTexCube(wall,2);
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }

    }
}
