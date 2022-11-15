package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class TexSphere {
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	static float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
	static float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };
	static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	
	public TexSphere() {

	}
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	// 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works 
	public void DrawTexSphere(float radius,float nSlices,float nSegments,Texture myTexture) {
		float x,y,z;
		float s,t; // texture coordinates
		
		   float inctheta = (float) ((2.0f*Math.PI )/ nSlices );
		    float incphi   = (float) (Math.PI/ nSegments );
		    
		   
		    
		    GL11.glBegin(GL11.GL_QUADS);
		    for(float theta=(float) -Math.PI; theta<Math.PI; theta+=inctheta)
		    {
		        for(float phi=(float) -(Math.PI/2.0f); phi<(Math.PI/2.0f); phi+=incphi)
		        {
		            x = (float) (Math.cos(phi)* Math.cos(theta)*radius);
		            y =  (float) (Math.cos(phi)* Math.sin(theta)*radius);
		            z =  (float) (Math.sin(phi)*radius);
		            
		            t =   (float) (phi/(float)Math.PI) +0.5f; 
		            s =    (float) (theta/Math.PI*2.0f) +0.5f ; 
		            
		            //GL11.glTexCoord2f(s,t);  // should be here but seems to be a bug in LWJGL 
		            GL11.glNormal3f(x,y,z); 
		            GL11.glVertex3f(x,y,z);  
			           
		            x = (float) (Math.cos(phi)*Math.cos(theta+inctheta)*radius);
		            y = (float) (Math.cos(phi)*Math.sin(theta+inctheta)*radius);
		            z = (float) (Math.sin(phi)*radius);
		            t = (float) (((float)phi/(float)Math.PI)+0.5f); 
		            s = (float) ((((float)theta+inctheta)/((float)Math.PI*2.0f)))+0.5f; 
		           
		            GL11.glTexCoord2f(s,t);
		            
		         GL11.glNormal3f(x,y,z); //Mistake in previous version fixed ( abey 11/1/2018) 
		            GL11.glVertex3f(x,y,z);  // Top Right corner

		            x = (float) (Math.cos(phi+incphi)*Math.cos(theta+inctheta)*radius);
		            y = (float) (Math.cos(phi+incphi)*Math.sin(theta+inctheta)*radius);
		            z = (float) (Math.sin(phi+incphi)*radius);
		            t = (float) ((((float)phi+incphi)/(float)Math.PI)+0.5f);
		            s = (float) ((((float)theta+inctheta)/((float)Math.PI*2.0f))+0.5f);
		         
		            GL11.glTexCoord2f(s,t);
		            GL11.glNormal3f(x,y,z);
		            GL11.glVertex3f(x,y,z); 

		            x = (float) (Math.cos(phi+incphi)*Math.cos(theta)*radius);
		            y = (float) (Math.cos(phi+incphi)*Math.sin(theta)*radius);
		            z = (float) (Math.sin(phi+incphi)*radius);
		            t = (float) ((((float)phi+incphi)/(float)Math.PI)+0.5f);
		            s = (float) (((float)theta/((float)Math.PI*2.0f))+0.5f);
		            
		            GL11.glTexCoord2f(s,t);
		            GL11.glNormal3f(x,y,z);
		            GL11.glVertex3f(x,y,z);  
		        }
		    }
		    GL11.glEnd();
	}
}
 