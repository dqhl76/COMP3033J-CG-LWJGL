package objects3D;

import org.lwjgl.opengl.GL11;

public class Sphere {

	
	public Sphere() {

	}
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	// 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works 
	public void DrawSphere(float radius,float nSlices,float nSegments) {
		// the delta theta, this is the amount by which each theta changes
		float inctheta = (float) ((2.0f*Math.PI) / nSlices);

		// the delta phi, this is the amount by which each phi changes
		float incphi = (float) (Math.PI / nSegments);

		// every face contains 4 points
		// BeginMode
		GL11.glBegin(GL11.GL_QUADS);

		for(float theta = (float) -Math.PI; theta < Math.PI; theta += inctheta){
			// the loop from -pi to pi in horizontal order
			for(float phi = (float) -(Math.PI/2.0f); phi<(Math.PI/2.0f); phi += incphi){
				// the loop from -pi/2 to pi/2 in vertical order
				float nextTheta = theta + inctheta;
				float nextPhi = phi + incphi;

				// low left point
				float xLow = (float) (radius * Math.cos(phi) * Math.cos(theta));
				float yLow = (float) (radius * Math.cos(phi) * Math.sin(theta));
				float zLow = (float) (radius * Math.sin(phi));

				// low right point
				float xLowNext = (float) (radius * Math.cos(phi) * Math.cos(nextTheta));
				float yLowNext = (float) (radius * Math.cos(phi) * Math.sin(nextTheta));
				float zLowNext = (float) (radius * Math.sin(phi));

				// up left point
				float xUp = (float) (radius * Math.cos(nextPhi) * Math.cos(theta));
				float yUp = (float) (radius * Math.cos(nextPhi) * Math.sin(theta));
				float zUp = (float) (radius * Math.sin(nextPhi));

				// up right point
				float xUpNext = (float) (radius * Math.cos(nextPhi) * Math.cos(nextTheta));
				float yUpNext = (float) (radius * Math.cos(nextPhi) * Math.sin(nextTheta));
				float zUpNext = (float) (radius * Math.sin(nextPhi));

				// set normal
				GL11.glNormal3f(xLow,yLow,zLow);
				GL11.glNormal3f(xLowNext,yLowNext,zLowNext);
				GL11.glNormal3f(xUpNext,yUpNext,zUpNext);
				GL11.glNormal3f(xUp,yUp,zUp);

				// set 4 points
				GL11.glVertex3f(xLow,yLow,zLow);
				GL11.glVertex3f(xLowNext,yLowNext,zLowNext);
				GL11.glVertex3f(xUpNext,yUpNext,zUpNext);
				GL11.glVertex3f(xUp,yUp,zUp);


			}
		}
		GL11.glEnd();
	}
}

 