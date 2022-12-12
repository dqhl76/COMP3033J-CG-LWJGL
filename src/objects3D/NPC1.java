package objects3D;

import GraphicsObjects.Utils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class NPC1 {

    // color part

    // basic colours
    static float[] black = {0.0f, 0.0f, 0.0f, 1.0f};
    static float[] white = {1.0f, 1.0f, 1.0f, 1.0f};

    static float[] grey = {0.5f, 0.5f, 0.5f, 1.0f};
    static float[] spot = {0.1f, 0.1f, 0.1f, 0.5f};

    // primary colours
    static float[] red = {1.0f, 0.0f, 0.0f, 1.0f};
    static float[] green = {0.0f, 1.0f, 0.0f, 1.0f};
    static float[] blue = {0.0f, 0.0f, 1.0f, 1.0f};

    // secondary colours
    static float[] yellow = {1.0f, 1.0f, 0.0f, 1.0f};
    static float[] magenta = {1.0f, 0.0f, 1.0f, 1.0f};
    static float[] cyan = {0.0f, 1.0f, 1.0f, 1.0f};

    // other colours
    static float[] orange = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float[] brown = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float[] dkgreen = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float[] pink = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};



    // Implement using notes  in Animation lecture
    // pass three texture from MainWindow, it is not effective if the texture import in this class
    // To make the GL11.glPushMatrix() and GL11.glPopMatrix() more clearly,
    // I write comment of them for the stack size
    // For example: (stack 1: pelvis stack 2: chest)
    public void DrawHuman(float delta,boolean walk, boolean cut,boolean command,boolean working, Texture headTexture, Texture tnt, Texture grenade) {
        float theta = (float) (delta * 2 * Math.PI);



        float WalkRotation = (float) Math.cos(theta * 5) * 70; // I speed up the rotation of the human's leg, it looks more natural
        float CutTreeRotation = (float) Math.cos(theta * 5) * 45; // -45 ~ 0 ~ 45
        float CommandRotation = (float) Math.cos(theta * 5) * 45; // -45 ~ 0 ~ 45
        if(!walk){
            WalkRotation = 0.0f;
        }
        if(!cut){
            CutTreeRotation = 0.0f;
        }

        float ArmRotation2 = (float) Math.cos(theta * 5) * 20f;

        Sphere sphere = new Sphere();
        Cylinder cylinder = new Cylinder();


        GL11.glPushMatrix(); // stack 1 push

        {
            // pelvis
            TexSphere texSphere = new TexSphere();
            GL11.glTranslatef(0.0f, 1.5f, 1.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
            Color.white.bind();
            tnt.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            texSphere.DrawTexSphere(0.5f, 32, 32, tnt); // draw the pelvis
            GL11.glRotatef(-90, 1.0f, 0.0f, 0.0f); // anti - pelvis rotate // rotate stack 1 pop

            //  chest
            GL11.glPushMatrix(); // stack 2 push
            {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                GL11.glScalef(1.0f, 1.0f, 1.0f);
                GL11.glRotatef(90, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right // rotate stack 1 push
                GL11.glRotatef(CutTreeRotation/4, 0.0f, 0.0f, 1.0f); // rotate to make the texture looks right // rotate stack 1 push
                Color.white.bind();
                tnt.bind();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                texSphere.DrawTexSphere(0.5f, 32, 32, tnt);
                GL11.glRotatef(-90, 1.0f, 0.0f, 0.0f); // anti - chest rotate // rotate stack 1 pop


                // neck
                GL11.glColor3f(orange[0], orange[1], orange[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                GL11.glPushMatrix(); // stack 3 push
                {
                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                    cylinder.DrawCylinder(0.15f, 0.7f, 32);


                    // head
                    GL11.glPushMatrix(); // stack 4 push
                    {
                        TexSphere head = new TexSphere();
                        GL11.glTranslatef(0.0f, 0.0f, 1.0f);
                        GL11.glScalef(1.0f, 1.0f, 1.0f);
                        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f); // rotate to make the texture looks right
                        // because this is the last Matrix pushed, so it is no need to anti-rotate
                        Color.white.bind();
                        headTexture.bind();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                        head.DrawTexSphere(0.5f, 32, 32, headTexture); // draw the head
                        {
                            GL11.glPushMatrix();
                            GL11.glTranslatef(-0.4f, 0.0f, -3.2f);
                            GL11.glColor3f(black[0], black[1], black[2]);
                            GL11.glScalef(1.0f, 1.5f, 0.1f);
                            Sphere sphere1 = new Sphere();
                            sphere1.DrawSphere(0.8f, 32, 32);
                            GL11.glPopMatrix();
                            Color.white.bind();
                        }
                        GL11.glPopMatrix(); // stack 4 pop
                    }
                    GL11.glPopMatrix(); // stack 3 pop


                    // left shoulder
                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                    GL11.glPushMatrix(); // stack 3 push
                    {

                        GL11.glTranslatef(0.5f, 0.4f, 0.0f);
                        sphere.DrawSphere(0.25f, 32, 32);

                        // left arm
                        GL11.glColor3f(orange[0], orange[1], orange[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));

                        GL11.glPushMatrix(); // stack 4 push
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                            GL11.glRotatef(WalkRotation, 1.0f, 0.0f, 0.0f); // rotate the arm, it has limited rotation degree
                            if(cut)
                                GL11.glRotatef(CutTreeRotation/2+22.5f, 0.0f, 1.0f, 1.0f);
                            if(command){
                                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                            }
                            if(working)
                                GL11.glRotatef(30.0f, 1.0f, 0.0f, 0.0f);


                            cylinder.DrawCylinder(0.15f, 0.7f, 32);

                            // left elbow
                            GL11.glColor3f(blue[0], blue[1], blue[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                            GL11.glPushMatrix(); // stack 5 push
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                sphere.DrawSphere(0.2f, 32, 32);

                                //left forearm
                                GL11.glColor3f(orange[0], orange[1], orange[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                GL11.glPushMatrix(); // stack 6 push
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                                    if(command){
                                        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
                                    }
                                    if(working){
                                        GL11.glRotatef(ArmRotation2-25, 1.0f, 0.0f, 0.0f);
                                    }
                                    cylinder.DrawCylinder(0.1f, 0.7f, 32);

                                    // left hand
                                    GL11.glPushMatrix(); // stack 7 push
                                    {
                                        GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                        GL11.glScalef(1.0f, 1.0f, 1.0f);
                                        GL11.glRotatef(270.0f, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right
                                        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f); // rotate to make the texture looks right
                                        // because this is the last Matrix pushed, so it is no need to anti-rotate
                                        Color.white.bind();
                                        grenade.bind();
                                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                        texSphere.DrawTexSphere(0.2f, 32, 32, grenade);

                                    }
                                        { // saw
                                            GL11.glPushMatrix();
                                            GL11.glScalef(1.0f, 0.1f, 0.1f);
                                            GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
                                            tnt.bind();
                                            TexCube saw = new TexCube();
                                            saw.DrawTexCube(tnt);
                                            GL11.glPopMatrix();
                                        }

                                    GL11.glPopMatrix(); // stack 7 pop
                                }
                                GL11.glPopMatrix(); // stack 6 pop
                            }
                            GL11.glPopMatrix(); // stack 5 pop
                        }
                        GL11.glPopMatrix(); // stack 4 pop
                    }
                    GL11.glPopMatrix(); // stack 3 pop

                    // to chest (stack 2)

                    // right shoulder
                    GL11.glColor3f(blue[0], blue[1], blue[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                    GL11.glPushMatrix(); // stack 3 push
                    {
                        GL11.glTranslatef(-0.5f, 0.4f, 0.0f);
                        sphere.DrawSphere(0.25f, 32, 32);

                        // right arm
                        GL11.glColor3f(orange[0], orange[1], orange[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                        GL11.glPushMatrix(); // stack 4 push
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                            GL11.glRotatef(-WalkRotation, 1.0f, 0.0f, 0.0f); // rotate the arm, it has limited rotation degree
                            if(cut) {
                                GL11.glRotatef(-45, 0.0f, 1.0f, 1.0f); // cut
                                GL11.glRotatef(45, 1.0f, 0.0f, 0.0f); // cut
                            }
                            if(command){
                                GL11.glRotatef(90.0f, 0.0f, -1.0f, 0.0f);
                                GL11.glRotatef(0.6f*(CommandRotation+45), 1.0f, 0.0f, 1.0f);
                            }
                            if(working)
                                GL11.glRotatef(30.0f, 1.0f, 0.0f, 0.0f);

                            cylinder.DrawCylinder(0.15f, 0.7f, 32);

                            // right elbow
                            GL11.glColor3f(blue[0], blue[1], blue[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                            GL11.glPushMatrix(); // stack 5 push
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                sphere.DrawSphere(0.2f, 32, 32);

                                // right forearm
                                GL11.glColor3f(orange[0], orange[1], orange[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                                GL11.glPushMatrix(); // stack 6 push
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                                    if(cut)
                                        GL11.glRotatef(55, 0.0f, 1.0f, 0.0f); // cut
                                    if(command){
                                        GL11.glRotatef(90.0f, -1.0f, 0.0f, 0.0f);
//                                        GL11.glRotatef(CommandRotation+45, 0.0f, -1.0f, 1.0f);
                                        GL11.glRotatef(1.5f*(CommandRotation+45), 1.0f, 0.0f, 0.0f);

                                    }
                                    if(working){
                                        GL11.glRotatef(-ArmRotation2-25, 1.0f, 0.0f, 0.0f);
                                    }
                                    cylinder.DrawCylinder(0.1f, 0.7f, 32);

                                    // right hand
                                    GL11.glPushMatrix(); // stack 7 push
                                    {
                                        GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                        GL11.glScalef(1.0f, 1.0f, 1.0f);
                                        GL11.glRotatef(270.0f, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right
                                        // because this is the last Matrix pushed, so it is no need to anti-rotate
                                        Color.white.bind();
                                        grenade.bind();
                                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                        texSphere.DrawTexSphere(0.2f, 32, 32, grenade);
                                    }
                                    GL11.glPopMatrix(); // stack 7 pop
                                }
                                GL11.glPopMatrix(); // stack 6 pop
                            }
                            GL11.glPopMatrix(); // stack 5 pop
                        }
                        GL11.glPopMatrix(); // stack 4 pop
                    }
                    GL11.glPopMatrix(); // stack 3 pop
                }
                GL11.glPopMatrix(); // stack 2 pop

                // to pelvis (stack 1)

                // left hip
                GL11.glColor3f(blue[0], blue[1], blue[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                GL11.glPushMatrix(); // stack 2 push
                {
                    GL11.glTranslatef(-0.5f, -0.2f, 0.0f);
                    sphere.DrawSphere(0.25f, 32, 32);

                    // left high leg
                    GL11.glColor3f(orange[0], orange[1], orange[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    GL11.glPushMatrix(); // stack 3 push
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                        GL11.glRotatef((+WalkRotation / 2) + 90, 1.0f, 0.0f, 0.0f); // rotate the leg, it has limited rotation degree
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);

                        // left knee
                        GL11.glColor3f(blue[0], blue[1], blue[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                        GL11.glPushMatrix(); // stack 4 push
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                            GL11.glRotatef(-Math.abs(WalkRotation) / 2, 1.0f, 0.0f, 0.0f);
                            if(cut)
                                GL11.glRotatef(-25, 1.0f, 0.0f, 0.0f); // cut bottom
                            // this rotates degree from -22.5 to 0
                            // the knee cannot bend backward, so it has to lower than 0
                            sphere.DrawSphere(0.25f, 32, 32);

                            //left low leg
                            GL11.glColor3f(orange[0], orange[1], orange[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            GL11.glPushMatrix(); // stack 5 push
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                cylinder.DrawCylinder(0.15f, 0.7f, 32);

                                // left foot
                                GL11.glPushMatrix(); // stack 6 push
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                    GL11.glScalef(1.0f, 1.0f, 1.0f);
                                    GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right
                                    // because this is the last Matrix pushed, so it is no need to anti-rotate
                                    Color.white.bind();
                                    grenade.bind();
                                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                                    texSphere.DrawTexSphere(0.3f, 32, 32, grenade);
                                }
                                GL11.glPopMatrix(); // stack 6 pop
                            }
                            GL11.glPopMatrix();  // stack 5 pop
                        }
                        GL11.glPopMatrix(); // stack 4 pop
                    }
                    GL11.glPopMatrix(); // stack 3 pop
                }
                GL11.glPopMatrix(); // stack 2 pop

                // to pelvis (stack 1)
                // right hip

                GL11.glColor3f(blue[0], blue[1], blue[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                GL11.glPushMatrix(); // stack 2 push
                {
                    GL11.glTranslatef(0.5f, -0.2f, 0.0f);
                    sphere.DrawSphere(0.25f, 32, 32);

                    // right high leg
                    GL11.glColor3f(orange[0], orange[1], orange[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                    GL11.glPushMatrix(); // stack 3 push
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                        GL11.glRotatef((-WalkRotation / 2) + 90, 1.0f, 0.0f, 0.0f); // rotate the leg, it has limited rotation degree
                        if(cut)
                            GL11.glRotatef(25, 1.0f, 0.0f, 0.0f); // cut bottom
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);


                        // right knee
                        GL11.glColor3f(blue[0], blue[1], blue[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                        GL11.glPushMatrix(); // stack 4 push
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                            GL11.glRotatef(-Math.abs(WalkRotation) / 2, 0.0f, 0.0f, 0.0f); // this rotates degree from -22.5 to 0
                            if(cut)
                                GL11.glRotatef(-25, 0.0f, 0.0f, 0.0f); // cut bottom
                            sphere.DrawSphere(0.25f, 32, 32);

                            // right low leg
                            GL11.glColor3f(orange[0], orange[1], orange[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
                            GL11.glPushMatrix(); // stack 5 push
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                cylinder.DrawCylinder(0.15f, 0.7f, 32);

                                // right foot
                                GL11.glColor3f(blue[0], blue[1], blue[2]);
                                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
                                GL11.glPushMatrix(); // stack 6 push
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                    GL11.glScalef(1.0f, 1.0f, 1.0f);
                                    GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f); // rotate to make the texture looks right
                                    // because this is the last Matrix pushed, so it is no need to anti-rotate
                                    Color.white.bind();
                                    grenade.bind();
                                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                                    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

                                    texSphere.DrawTexSphere(0.3f, 32, 32, grenade);

                                }
                                GL11.glPopMatrix(); // stack 6 pop
                            }
                            GL11.glPopMatrix(); // stack 5 pop
                        }
                        GL11.glPopMatrix(); // stack 4 pop
                    }
                    GL11.glPopMatrix(); // stack 3 pop
                }
                GL11.glPopMatrix(); // stack 2 pop
            }
            GL11.glPopMatrix(); // stack 1 pop
        }

        // all stack pop, stack size = 0
    }


}

	/*


}

	*/
