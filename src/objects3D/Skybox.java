package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glNewList;

public class Skybox {
    Texture negx, negy, negz, posx, posy, posz;
    public Skybox() throws IOException {
        negx = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/skybox/negx.jpg"));
        negy = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/skybox/negy.jpg"));
        negz = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/skybox/negz.jpg"));
        posx = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/skybox/posx.jpg"));
        posy = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/skybox/posy.jpg"));
        posz = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("res/skybox/posz.jpg"));

    }
    public void DrawSkybox() {

        GL11.glPushMatrix();
        GL11.glScalef(30000f, 30000f, 30000f);
        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
        negy.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glEnd();

        posx.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glEnd();

        posz.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glEnd();

        negz.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glEnd();

        negx.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glEnd();

        posy.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glEnd();
        GL11.glPopMatrix();

    }
}
