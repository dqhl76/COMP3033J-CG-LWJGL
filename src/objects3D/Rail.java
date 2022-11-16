package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Rail {
    public Rail() {

    }

    public void DrawRail(Texture rail) {


        TexCube cube = new TexCube();
        GL11.glScalef(80.0f, 80.0f, 80.0f);
        GL11.glTranslatef(0.0f,1.3f, 5.0f);
        rail.bind();
        cube.DrawTexCube(rail);

    }
}
