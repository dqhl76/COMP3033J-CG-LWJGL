package GraphicsObjects;

import objects3D.TexCube;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import javax.xml.soap.Text;

public class Mine {
    public Mine() {

    }

    public void DrawMine(Texture mine, Texture blackTexture) {
        TexCube MyCube = new TexCube();

        GL11.glScalef(80f, 60f, 80f);
        GL11.glTranslatef(1, 4, 5);
        mine.bind();
        MyCube.DrawTexCube(mine);
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0, 1f, 0);
            GL11.glScalef(1f, 0.01f, 1f);
            blackTexture.bind();
            MyCube.DrawTexCube(blackTexture);
            GL11.glPopMatrix();
        }

    }
}
