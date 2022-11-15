package objects3D;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

public class Cylinder {

	
	public Cylinder() { 
	}
	
	// remember to use Math.PI isntead PI 
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	public void DrawCylinder(float radius, float height, int nSegments ) 
	{
		// every face have 3 points
		GL11.glBegin(GL11.GL_TRIANGLES);

		// this loop contains two triangle in the side of a cylinder
		// and two triangles in the top and bottom side of a cylinder
		for (float i = 0.0f; i < nSegments; i += 1.0f) {
			// a loop around circumference of a tube
			float angle = (float) (Math.PI * i * 2.0 / nSegments);
			float nextAngle = (float) (Math.PI * (i + 1) * 2 / nSegments);

			// compute sin and cos of both angle and nextAngle
			float x1 = (float) Math.sin(angle) * radius;
			float y1 = (float) Math.cos(angle) * radius;
			float x2 = (float) Math.sin(nextAngle) * radius;
			float y2 = (float) Math.cos(nextAngle) * radius;

			Point4f[] vertices = {
					new Point4f(x1, y1, height, 0.0f),
					new Point4f(x1, y1, 0.0f, 0.0f),
					new Point4f(x2, y2, height, 0.0f),
					new Point4f(x2, y2, 0.0f, 0.0f),
			};

			// top triangle made by point (x1,y1,hei) (x1,y1,0) (x2,y2,hei)
			// normal should be set to point from inside the cylinder to outside the cylinder
			GL11.glNormal3f(vertices[1].x, vertices[1].y, vertices[1].z);
			GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);

			GL11.glNormal3f(vertices[1].x, vertices[1].y, vertices[1].z);
			GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);

			GL11.glNormal3f(vertices[3].x, vertices[3].y, vertices[3].z);
			GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);

			// bottom triangle made by point (x2,y2,hei) (x1,y1,0) (x2,y2,0)
			// normal should be set to point from inside the cylinder to outside the cylinder
			GL11.glNormal3f(vertices[3].x, vertices[3].y, vertices[3].z);
			GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);

			GL11.glNormal3f(vertices[1].x, vertices[1].y, vertices[1].z);
			GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);

			GL11.glNormal3f(vertices[3].x, vertices[3].y, vertices[3].z);
			GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);


			// a triangle piece of the bottom side of cylinder contain of point (x1,y1,0) (x2,y2,0) (0,0,0)
			Vector4f v = (vertices[1]).MinusPoint(new Point4f(0.0f, 0.0f, 0.0f, 0.0f));
			Vector4f w = (vertices[3]).MinusPoint(vertices[1]);
			Vector4f Normal = v.cross(w).Normal();
			GL11.glNormal3f(Normal.x, Normal.y, Normal.z);
			GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);
			GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);

			// a triangle piece of the bottom side of cylinder contain of point (x1,y1,hei) (x2,y2,hei) (0,0,hei)
			Vector4f Normal2 = Normal.NegateVector();
			// this normal should be set to negate direction
			GL11.glNormal3f(Normal2.x, Normal2.y, Normal2.z);
			GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
			GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);
			GL11.glVertex3f(0.0f, 0.0f, height);


		}
		GL11.glEnd();
	}
}
