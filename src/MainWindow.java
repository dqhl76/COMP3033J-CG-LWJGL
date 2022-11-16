import GraphicsObjects.Arcball;
import GraphicsObjects.Mine;
import GraphicsObjects.Utils;
import objects3D.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

// Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment.
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment.
//

// Do not touch this class, I will be making a version of it for your 3rd Assignment
public class MainWindow {

    private boolean MouseOnepressed = true;
    private boolean dragMode = false;

    /**
     * position of pointer
     */
    float x = 400, y = 300;
    /**
     * angle of rotation
     */
    float rotation = 0;
    /**
     * time at last frame
     */
    long lastFrame;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;

    long myDelta = 0; //to use for animation
    float Alpha = 0; //to use for animation
    long StartTime; // beginAnimiation

    Arcball MyArcball = new Arcball();

    boolean DRAWGRID = false;
    boolean waitForKeyrelease = true;
    /**
     * Mouse movement
     */
    int LastMouseX = -1;
    int LastMouseY = -1;

    float pullX = 0.0f; // arc ball  X cord.
    float pullY = 0.0f; // arc ball  Y cord.


    int OrthoNumber = 1200; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2 // do not change this for assignment 3 but you can change everything for your project

    // basic colours
    static float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
    static float white[] = {1.0f, 1.0f, 1.0f, 1.0f};

    static float grey[] = {0.5f, 0.5f, 0.5f, 1.0f};
    static float spot[] = {0.1f, 0.1f, 0.1f, 0.5f};

    // primary colours
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    static float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    static float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};

    // secondary colours
    static float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};
    static float magenta[] = {1.0f, 0.0f, 1.0f, 1.0f};
    static float cyan[] = {0.0f, 1.0f, 1.0f, 1.0f};

    // other colours
    static float orange[] = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float brown[] = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float dkgreen[] = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float pink[] = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};

    // static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

    //support method to aid in converting a java float array into a Floatbuffer which is faster for the opengl layer to process


    public void start() {

        StartTime = getTime();
        try {
            Display.setDisplayMode(new DisplayMode(1200, 800));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            update(delta);
            renderGL();
            Display.update();
            Display.sync(120); // cap fps to 120fps
        }

        Display.destroy();
    }

    public void update(int delta) {
        // rotate quad
        //rotation += 0.01f * delta;


        int MouseX = Mouse.getX();
        int MouseY = Mouse.getY();
        int WheelPostion = Mouse.getDWheel();


        boolean MouseButonPressed = Mouse.isButtonDown(0);


        if (MouseButonPressed && !MouseOnepressed) {
            MouseOnepressed = true;
            //  System.out.println("Mouse drag mode");
            MyArcball.startBall(MouseX, MouseY, 1200, 800);
            dragMode = true;


        } else if (!MouseButonPressed) {
            // System.out.println("Mouse drag mode end ");
            MouseOnepressed = false;
            dragMode = false;
        }

        if (dragMode) {
            MyArcball.updateBall(MouseX, MouseY, 1200, 800);
        }

        if (WheelPostion > 0) {
            OrthoNumber += 10;

        }

        if (WheelPostion < 0) {
            OrthoNumber -= 10;
            if (OrthoNumber < 610) {
                OrthoNumber = 610;
            }

            //  System.out.println("Orth nubmer = " +  OrthoNumber);

        }

        /** rest key is R*/
        if (Keyboard.isKeyDown(Keyboard.KEY_R))
            MyArcball.reset();

        /* bad animation can be turn on or off using A key)*/

        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            x += 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            y += 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            y -= 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_Q))
            rotation += 0.35f * delta;

        if (waitForKeyrelease) // check done to see if key is released
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_G)) {

                DRAWGRID = !DRAWGRID;
                Keyboard.next();
                if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
                    waitForKeyrelease = true;
                } else {
                    waitForKeyrelease = false;

                }
            }
        }

        /** to check if key is released */
        if (Keyboard.isKeyDown(Keyboard.KEY_G) == false) {
            waitForKeyrelease = true;
        } else {
            waitForKeyrelease = false;

        }


        // keep quad on the screen
        if (x < 0)
            x = 0;
        if (x > 1200)
            x = 1200;
        if (y < 0)
            y = 0;
        if (y > 800)
            y = 800;

        updateFPS(); // update FPS Counter

        LastMouseX = MouseX;
        LastMouseY = MouseY;
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        changeOrth();
        MyArcball.startBall(0, 0, 1200, 800);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
        lightPos.put(10000f).put(1000f).put(1000).put(0).flip();

        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
        lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();

        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
        lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();

        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
        lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
        // position
        // of the
        // light
        // GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup specific materials so in real light it will look abit strange

        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));

        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
        // the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
        // the
        // position
        // of the
        // light
        GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

        GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
        GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
        // on
        GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        try {
            init();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //load in texture


    }


    public void changeOrth() {

        GL11.glMatrixMode(GL11.GL_PROJECTION); // Apply subsequent matrix operations to the projection matrix stack
        GL11.glLoadIdentity(); // Resets the currently specified matrix to a unit matrix
        GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);

        //	if(MouseOnepressed)
        //	{

        MyArcball.getMatrix(CurrentMatrix);
        //	}

        GL11.glLoadMatrix(CurrentMatrix);

    }

    /*
     * You can edit this method to add in your own objects /  remember to load in textures in the INIT method as they take time to load
     *
     */
    public void renderGL() {
        changeOrth();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        myDelta = getTime() - StartTime;
        float delta = ((float) myDelta) / 10000;
        int millisecond = ((int) myDelta); // 1000 milliseconds in a second
        System.out.println(millisecond);
        // code to aid in animation
        float theta = (float) (delta * 2 * Math.PI);
        float thetaDeg = delta * 360;
        float posn_x = (float) Math.cos(theta); // same as your circle code in your notes
        float posn_y = (float) Math.sin(theta);

        /*
         * This code draws a grid to help you view the human models movement
         *  You may change this code to move the grid around and change its starting angle as you please
         */
        if (DRAWGRID) {
            GL11.glPushMatrix();
            Grid MyGrid = new Grid();
            GL11.glTranslatef(600, 400, 0);
            GL11.glScalef(200f, 200f, 200f);
            MyGrid.DrawGrid();
            GL11.glPopMatrix();
        }

        {
            Floor f = new Floor();
            f.draw(floor);
        }


        {
            Tree MyTree = new Tree();
            if(millisecond>=12000) {
                MyTree.DrawTree(planks, leaves,true,millisecond);
            }else{
                MyTree.DrawTree(planks,leaves,false,millisecond);
            }
        }

        {
            Rail MyRail = new Rail();
            for (int i = 0; i < 30; i++) {
                GL11.glPushMatrix();
                GL11.glTranslatef(i*100, 0, 0);
                MyRail.DrawRail(rail);
                GL11.glPopMatrix();
            }
        }

        {
            GL11.glPushMatrix();
            Mine mine1 = new Mine();
            mine1.DrawMine(mine, blackTexture);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            Mine mine1 = new Mine();
            mine1.DrawMine(mine, blackTexture);
            GL11.glPopMatrix();
        }

//        System.out.println("timestamp: " + millisecond);
        // draw the human
        {
            GL11.glPushMatrix();
            Human MyHuman = new Human();
            GL11.glTranslatef(1000, 200, 0);
            GL11.glScalef(90f, 90f, 90f);
            if(millisecond <= 9500){
                GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,(float) (-millisecond/1000.0) );
                MyHuman.DrawHuman(delta,true,false, headTexture, tnt, grenade); // give a delta for the Human object ot be animated

            }else if(millisecond <= 16000 && millisecond >9500){
                GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,-9.5f );
                MyHuman.DrawHuman(delta,false,true, headTexture, tnt, grenade); // give a delta for the Human object ot be animated
            }else{
                GL11.glRotatef(-45, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,-9.5f );
                MyHuman.DrawHuman(delta,true,false, headTexture, tnt, grenade); // give a delta for the Human object ot be animated
            }
//            GL11.glTranslatef(-posn_x * 3, 0.0f, -posn_y * 3);
            // insert your animation code to correct the postion for the human rotating
            GL11.glPopMatrix();
        }


        {

        }

    }

    public static void main(String[] argv) {
        MainWindow hello = new MainWindow();
        hello.start();
    }

    Texture valley_2022, headTexture, tnt, grenade, planks, leaves, floor, rail, mine, blackTexture;

    /*
     * Any additional textures for your assignment should be written in here.
     * Make a new texture variable for each one so they can be loaded in at the beginning
     */
    public void init() throws IOException {
        Logger logger = Logger.getGlobal();
        logger.info("Loading textures...");

        mine = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/minecart.png"));

        floor = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/floor.png"));

        leaves = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/dark_oak_leaves.png"));

        planks = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/planks_jungle.png"));

        tnt = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/tnt.png"));

        grenade = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/grenade.png"));

        headTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/head.png"));

        valley_2022 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/valley_2022.png"));

        rail = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/rail.png"));

        blackTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/black.png"));

        logger.info("Textures loaded.");
    }
}
