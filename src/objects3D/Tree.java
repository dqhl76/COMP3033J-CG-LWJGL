package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Tree {
    static float militime = 12000;
    public void DrawTree(Texture blankT,Texture planks,Texture leaves, Boolean down, Boolean train_begin,float local_boat_time) {

        TexCube cube = new TexCube();
        GL11.glPushMatrix();




        if(down){
            if(militime==27000){

            }else {
                militime += 10;
            }
            if(train_begin){
                militime += 0.1f;
            }
            System.out.println(militime);
            GL11.glTranslatef(0.0f, 240f, 1.0f);
            GL11.glScalef(80.0f, 80.0f, 80.0f);
        }else{
            GL11.glTranslatef(0.0f, 480f, 1.0f);
            GL11.glScalef(80.0f, 320.0f, 80.0f);
        }
        GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
        Color.white.bind();
        planks.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        cube.DrawTexCube(planks);
        float time = (float) (militime * 1.2);
        {
            if (militime >= 48000 && militime < 50000) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f,(float) (48400-militime)/1000*6f, 0.0f);
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
                        {
                            GL11.glPushMatrix();
                            Sphere sphere = new Sphere();
                            blankT.bind();
                            GL11.glScalef(1f,2f,0.1f);
                            GL11.glTranslatef(1.0f, 0.6f, -8.0f);
                            sphere.DrawSphere(2f, 20, 20);
                            GL11.glPopMatrix();
                            planks.bind();
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();

                }
                GL11.glPopMatrix();
            }else if(militime >= 50000) {
                GL11.glPushMatrix();
                Wood wood = new Wood();
                if(local_boat_time != 0) {
                    if(local_boat_time<400) {
                        GL11.glScalef(1,1, 1 - local_boat_time / 400f);
                    }else{
                        GL11.glScalef(0f,0f, 0f);
                    }
                }
                wood.DrawWood(planks);
                {
                        GL11.glPushMatrix();
                        Sphere sphere = new Sphere();
                        blankT.bind();
                        GL11.glScalef(1f,2f,0.1f);
                        GL11.glTranslatef(27.0f, -2.5f, 7.0f);
                        sphere.DrawSphere(2f, 20, 20);
                        GL11.glPopMatrix();
                        planks.bind();

                }
                GL11.glPopMatrix();
            }
            else {
                if (down) {
                    GL11.glPushMatrix();
                    if (militime >= 27000) {
                        GL11.glTranslatef((float) ((militime - 27000) / 1000 * 1.25), 0, 0);
                    }
                    if (time <= 22000) {
                        GL11.glTranslatef(0.0f, 1.0f, -1.0f);
                        GL11.glScalef(1.0f, 1.0f, 1.0f);
                        GL11.glRotatef(time / 100 - 40, 1.0f, 0.0f, 0.0f);
                        GL11.glTranslatef(0.0f, -1.0f, 1.0f);
                    } else {
                        GL11.glTranslatef(0.0f, 1.0f, -1.0f);
                        GL11.glRotatef(180, 1.0f, 0.0f, 0.0f);
                        GL11.glTranslatef(0.0f, -1.0f, 1.0f);
                        {
                            GL11.glPushMatrix();
                            Sphere sphere = new Sphere();
                            blankT.bind();
                            GL11.glScalef(1f,2f,0.1f);
                            GL11.glTranslatef(1.0f, -1.5f, -28.0f);
                            sphere.DrawSphere(2f, 20, 20);
                            GL11.glPopMatrix();
                            planks.bind();
                        }
                        if (time <= 23000) {
                            GL11.glTranslatef(0.0f, 0.0f, -(time - 22000) / 10000.0f * 18);
                        } else {
                            GL11.glTranslatef(0.0f, 0.0f, -(23000 - 22000) / 10000.0f * 18);

                        }
                    }

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
        }
        GL11.glPopMatrix();








        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        if(!down) {
            GL11.glPushMatrix();
            TexCube leaveCube = new TexCube();
            GL11.glTranslatef(0.0f, 700f, 1.0f);
            GL11.glScalef(360.0f, 160.0f, 360.0f);
            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
            Color.white.bind();
            leaves.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            leaveCube.DrawTexCube(leaves, 6);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 800f, 1.0f);
            GL11.glScalef(240.0f, 240.0f, 240.0f);
            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
            leaveCube.DrawTexCube(leaves, 4);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 1080f, 1.0f);
            GL11.glScalef(80.0f, 80.0f, 160.0f);
            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
            leaveCube.DrawTexCube(leaves, 2);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 1080f, 1.0f);
            GL11.glScalef(160.0f, 80.0f, 80.0f);
            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
            leaveCube.DrawTexCube(leaves, 2);
            GL11.glPopMatrix();
        }

    }
}
