package objects3D;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.glGenLists;

public class TexCube {


    public TexCube() {

    }
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

    // Implement using notes  and looking at TexSphere
    public void DrawTexCube(Texture texture) {
        // all same with Cube, except the texture part
        // I pass the texture in it because TexSphere also pass it in
        // It might be used in the future

        // vertices for a cube


        // one face has 4 vertices
        GL11.glBegin(GL11.GL_QUADS);

        for (int face = 0; face < 6; face++) { // 6 faces for a cube
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);
            Vector4f normal = v.cross(w).Normal(); // v and w is perpendicular, normal is the face's normal vector

            GL11.glNormal3f(normal.x, normal.y, normal.z); // set normal vector

            GL11.glTexCoord2f(0.0f, 1.0f); // s = 0 t = 1
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

            GL11.glTexCoord2f(0.0f, 0.0f); // s = 0 t = 0
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

            GL11.glTexCoord2f(1.0f, 0.0f); // s = 1 t = 0
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

            GL11.glTexCoord2f(1.0f, 1.0f); // s = 1 t = 1
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);

        }
        GL11.glEnd();
    }

    public void DrawTexCube(Texture texture,Integer number) {
        // all same with Cube, except the texture part
        // I pass the texture in it because TexSphere also pass it in
        // It might be used in the future

        // vertices for a cube


        // one face has 4 vertices
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
    }

    public void DrawTexCubeEx(Texture texture, Integer ex) {
        // all same with Cube, except the texture part
        // I pass the texture in it because TexSphere also pass it in
        // It might be used in the future

        // vertices for a cube


        // one face has 4 vertices
        GL11.glBegin(GL11.GL_QUADS);

        for (int face = 0; face < 6; face++) { // 6 faces for a cube
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);
            Vector4f normal = v.cross(w).Normal(); // v and w is perpendicular, normal is the face's normal vector

            GL11.glNormal3f(normal.x, normal.y, normal.z); // set normal vector

            if(face == ex){
                GL11.glTexCoord2f(0.0f, 1.0f); // s = 0 t = 1
                GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

                GL11.glTexCoord2f(0.0f, 0.0f); // s = 0 t = 0
                GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

                GL11.glTexCoord2f(1.0f, 0.0f); // s = 1 t = 0
                GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

                GL11.glTexCoord2f(1.0f, 1.0f); // s = 1 t = 1
                GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);

            }else{
                GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);

                GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);

                GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);

                GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);

            }


        }
        GL11.glEnd();
    }

}
 
	/*
	 
	 
}

	*/
	 