package objects3D;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Floor {

    Point4f[] vertices = {new Point4f(-1.0f, -1.0f, -1.0f, 0.0f),
            new Point4f(-1.0f, -1.0f, 1.0f, 0.0f),
            new Point4f(-1.0f, 1.0f, -1.0f, 0.0f),
            new Point4f(-1.0f, 1.0f, 1.0f, 0.0f),
            new Point4f(1.0f, -1.0f, -1.0f, 0.0f),
            new Point4f(1.0f, -1.0f, 1.0f, 0.0f),
            new Point4f(1.0f, 1.0f, -1.0f, 0.0f),
            new Point4f(1.0f, 1.0f, 1.0f, 0.0f)};

    // faces for a cube
    int[][] faces = {{0, 4, 5, 1},
            {0, 2, 6, 4},
            {0, 1, 3, 2},
            {4, 6, 7, 5},
            {1, 5, 7, 3},
            {2, 3, 7, 6}};

    public void draw(Texture texture) {
        GL11.glPushMatrix();

        GL11.glTranslatef(0.0f, 100f, 1.0f);
        GL11.glScalef(5000.0f, 80.0f, 5000.0f);
        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
        Color.white.bind();
        texture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        int number = 60;

        GL11.glBegin(GL11.GL_QUADS);
        for (int face = 0; face < 6; face++) { // 6 faces for a cube
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);
            Vector4f normal = v.cross(w).Normal(); // v and w is perpendicular, normal is the face's normal vector

            GL11.glNormal3f(normal.x, normal.y, normal.z); // set normal vector

            GL11.glTexCoord2f(0.0f, number); // s = 0 t = 1
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

            GL11.glTexCoord2f(0.0f, 0.0f); // s = 0 t = 0
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

            GL11.glTexCoord2f(number, 0.0f); // s = 1 t = 0
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

            GL11.glTexCoord2f(number, number); // s = 1 t = 1
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);

        }
        GL11.glEnd();
        GL11.glPopMatrix();
    }
}
