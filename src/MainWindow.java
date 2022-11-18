import GraphicsObjects.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import objects3D.Mine;
import objects3D.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.security.Key;
import java.util.logging.Logger;

// Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment.
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment.
//

// Do not touch this class, I will be making a version of it for your 3rd Assignment
public class MainWindow {
    private Camera camera;

    private boolean MouseOnepressed = true;
    private boolean dragMode = false;

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

//    Arcball MyArcball = new Arcball();

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
        new CameraAnimate(camera,10,StartTime).start(); // start the camera animation thread
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

    public void initMusic(){
        //Music
        try {
            AudioClip clip = java.applet.Applet.newAudioClip(new File("res/music.mp3").toURI().toURL());
            clip.play();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
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
//            MyArcball.startBall(MouseX, MouseY, 1200, 800);
            dragMode = true;


        } else if (!MouseButonPressed) {
            // System.out.println("Mouse drag mode end ");
            MouseOnepressed = false;
            dragMode = false;
        }

        if (dragMode) {
//            MyArcball.updateBall(MouseX, MouseY, 1200, 800);
        }

        if (WheelPostion > 0) {
            camera.OrthNumber += 10;

        }

        if (WheelPostion < 0) {
            camera.OrthNumber -= 10;
            if (camera.OrthNumber < 610) {
                camera.OrthNumber = 610;
            }

            //  System.out.println("Orth nubmer = " +  OrthoNumber);

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            System.out.println("LEFT key pressed");
            camera.position.x += 1f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            System.out.println("RIGHT key pressed");
            camera.position.x -= 1f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            System.out.println("UP key pressed");
            camera.position.y -= 1f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            System.out.println("DOWN key pressed");
            camera.position.y += 1f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            System.out.println("SPACE key pressed");
            camera.position.z += 10f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            System.out.println("LShift key pressed");
            camera.position.z -= 10f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            System.out.println("W key pressed");
            camera.rotation.x -= 0.01f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            System.out.println("S key pressed");
            camera.rotation.x += 0.01f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            System.out.println("A key pressed");
            camera.rotation.y -= 0.05f;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            System.out.println("D key pressed");
            camera.rotation.y += 0.05f;
        }
//            MyArcball.reset();


        if (Keyboard.isKeyDown(255)) {
            System.out.println("D key pressed");
            camera.position.x += 0.1f;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W))

        if (Keyboard.isKeyDown(Keyboard.KEY_S))


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

        camera = new Camera(new Point4f(0,0,8,0),new Vector4f(1f,0,0,0),OrthoNumber);
        camera.update();

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

        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
        GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));

        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
        GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));


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


//    public void changeOrth() {
//
//        GL11.glMatrixMode(GL11.GL_PROJECTION); // Apply subsequent matrix operations to the projection matrix stack
//        GL11.glLoadIdentity(); // Resets the currently specified matrix to a unit matrix
//        GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber * 0.66f)), (OrthoNumber * 0.66f), 100000, -100000);
//        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//
//        FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
//        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);
//
//        //	if(MouseOnepressed)
//        //	{
//
//        MyArcball.getMatrix(CurrentMatrix);
//        //	}
//
//        GL11.glLoadMatrix(CurrentMatrix);
//
//    }

    /*
     * You can edit this method to add in your own objects /  remember to load in textures in the INIT method as they take time to load
     *
     */



    public void renderGL() {
        camera.update();

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        myDelta = getTime() - StartTime;
        float delta = ((float) myDelta) / 10000;
        int millisecond = ((int) myDelta); // 1000 milliseconds in a second
//        millisecond += 24000;
//        System.out.println(millisecond);
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
            if(millisecond <= 27000){
                for (int i = 0; i < 30; i++) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(i*160, 0, 0);
                    MyRail.DrawRail(rail);
                    GL11.glPopMatrix();
                }
            }else{
                for (int i = 0; i < 30; i++) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(i*160, 0, 0);
                    MyRail.DrawRail(rail_activate);
                    GL11.glPopMatrix();
                }
            }
        }

        {

            Wall MyWall = new Wall();
            for (int i = 0; i < 30; i++) {
                GL11.glPushMatrix();
                GL11.glTranslatef(i*160, 0, 160);
                MyWall.DrawWall(wall);
                GL11.glPopMatrix();
            }
        }

        {
            Picture MyPicture = new Picture();
            for(int i=0;i<5;++i) {
                GL11.glPushMatrix();
                GL11.glTranslatef(500+1000*i, 540, 1040);
                GL11.glScalef(80f, 80f, 8f);
                valley_2022.bind();
                MyPicture.DrawPicture(valley_2022);
                GL11.glPopMatrix();
            }
        }

        {
            Sign MySign = new Sign();
            MySign.DrawSign(train);
        }


        {
            boolean run = false;
            GL11.glPushMatrix();
            Mine mine1 = new Mine();
            if(millisecond >= 27000){
                GL11.glTranslatef((millisecond-27000)/10,0,0);
            }
            mine1.DrawMine(mine, blackTexture);
            GL11.glPopMatrix();
        }


//        System.out.println("timestamp: " + millisecond);
        // draw the human
        {
            GL11.glPushMatrix();
            NPC1 npc1 = new NPC1();
            GL11.glTranslatef(1000, 200, 0);
            GL11.glScalef(90f, 90f, 90f);
            if(millisecond <= 9500){
                GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,(float) (-millisecond/1000.0) );
                npc1.DrawHuman(delta,true,false,false, headTexture, tnt, grenade); // give a delta for the Human object ot be animated

            }else if(millisecond <= 16000 && millisecond >9500){
                GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,-9.5f );
                npc1.DrawHuman(delta,false,true,false, headTexture, tnt, grenade); // give a delta for the Human object ot be animated
            }else if(millisecond <= 25000 && millisecond >16000){
                GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,-9.5f );
                GL11.glRotatef(245, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,(float) (-(millisecond-16000)/1000.0) );
                npc1.DrawHuman(delta,true,false,false, headTexture, tnt, grenade); // give a delta for the Human object ot be animated
            }else{
                GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0,-9.5f );
                GL11.glRotatef(245, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
                GL11.glTranslatef(0,0, -9.0f);
                GL11.glRotatef(165, 0.0f, 1.0f, 0.0f);
                npc1.DrawHuman(delta,false,false,true, headTexture, tnt, grenade); // give a delta for the Human object ot be animated
            }
//            GL11.glTranslatef(-posn_x * 3, 0.0f, -posn_y * 3);
            // insert your animation code to correct the postion for the human rotating
            GL11.glPopMatrix();
        }



    }

    public static void main(String[] argv) {
        MainWindow hello = new MainWindow();
        hello.start();
    }

    Texture valley_2022, headTexture, tnt, grenade, planks, leaves, floor, rail, mine, blackTexture, rail_activate, wall,train;

    /*
     * Any additional textures for your assignment should be written in here.
     * Make a new texture variable for each one so they can be loaded in at the beginning
     */
    public void init() throws IOException {
        Logger logger = Logger.getGlobal();
        logger.info("Loading textures...");

        train = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/train.png"));

        wall = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/wall.png"));

        rail_activate = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/rail_activate.png"));

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
