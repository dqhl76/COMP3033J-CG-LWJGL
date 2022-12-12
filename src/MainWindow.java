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
import org.newdawn.slick.Color;
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

    float x = 0;
    float y = 0;
    float z = 0;

    long myDelta = 0; //to use for animation
    float Alpha = 0; //to use for animation
    long StartTime; // beginAnimiation

    Arcball MyArcball = new Arcball();

    boolean DRAWGRID = false;
    boolean waitForKeyrelease = true;
    boolean walking = true;
    boolean train_begin = false;
    boolean cut = false;

    boolean cutting = false;
    boolean working = false;
    boolean craft = false;

    boolean operation = false;

    int face = 0;
    float local_time_train = 26500;
    float local_cat_time = 0;
    float local_work_time = 0;
    float local_boat_time = 0;

    CheckPhysicsModel checkPhysicsModel = new CheckPhysicsModel(0,0,0);
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
//        new CameraAnimate(camera,10,StartTime).start(); // start the camera animation thread
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

        checkPhysicsModel.updatePosition(x,y,z);

        int MouseX = Mouse.getX();
        int MouseY = Mouse.getY();
        int WheelPostion = Mouse.getDWheel();


        boolean MouseButonPressed = Mouse.isButtonDown(0);

        if(cut){
            local_cat_time += 1;
            if(local_cat_time <= 700) {
                cutting = true;
            }else{
                cutting = false;
            }
        }

        if(working){
            local_work_time += 1;
            if(local_work_time <= 1400){
                working = true;
            }else{
                working = false;
            }
        }

        if(craft){
            local_boat_time += 1;
        }

        if (MouseButonPressed && !MouseOnepressed) {
            MouseOnepressed = true;
            MyArcball.startBall(MouseX, MouseY, 1200, 800);
            dragMode = true;
        } else if (!MouseButonPressed) {
            MouseOnepressed = false;
            dragMode = false;
        }


        if (dragMode) {
            MyArcball.updateBall(MouseX, MouseY, 1200, 800);
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

        if(!craft&&!working&&!cutting && Keyboard.isKeyDown(Keyboard.KEY_W) && !checkPhysicsModel.checkBreakout(0,0,3)) {
            face = 2;
            z += 3f;
            walking = true;
            camera.position.z -= 2f;
        }else if(!craft&&!working&&!cutting&&Keyboard.isKeyDown(Keyboard.KEY_S)&& !checkPhysicsModel.checkBreakout(0,0,-3)) {
            face = 0;
            z -= 3f;
            walking = true;
            camera.position.z += 2f;
        }else if(!craft&&!working&&!cutting&&Keyboard.isKeyDown(Keyboard.KEY_A)&& !checkPhysicsModel.checkBreakout(-1.5f,0,0)) {
            face = 1;
            walking = true;
            x -= 1.5f;
            camera.position.x += 1.5f;
        }else if(!craft&&!working&&!cutting&&Keyboard.isKeyDown(Keyboard.KEY_D)&& !checkPhysicsModel.checkBreakout(1.5f,0,0)) {
            face = 3;
            walking = true;
            x += 1.5f;
            camera.position.x -= 1.5f;
        }else{
            walking = false;
        }

        if(x>=-830 && x<=-750 && z>=-130 && z<=130){ // cutting tree area
            if(!cut)
               operation =true;
        } else if(x>=-150 && x<=80 && z>=699 && z<=900){
            if(cut && !train_begin)
                operation = true;
        }
        else if(x>=493 && x<=609 && z>=-336 && z<=198){
            if(cut && train_begin)
                operation = true;
        }else{
            operation = false;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_F)){
//            train_begin = true;
            if(x>=-830 && x<=-750 && z>=-130 && z<=130){ // cutting tree area
                cut = true;
            }

            if(x>=-150 && x<=80 && z>=699 && z<=900){
                if(cut) {
                    train_begin = true;
                    working = true;
                    checkPhysicsModel.updateMine(false);
                }
            }

            if(x>=493 && x<=609 && z>=-336 && z<=198){
                if(cut && train_begin){
                    craft = true;
                }
            }

        }

        if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
            craft = true;
        }


        System.out.println(x + " " + y + " " + z);




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
            if(operation){
                Display.setTitle("You can press F");
            }else {
                Display.setTitle("FPS: " + fps);
            }
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {

        camera = new Camera(new Point4f(-350.0f,-175.0f,0.0f,0.0f),new Vector4f(-13.2f,3.34f,0,0),OrthoNumber);
        camera.OrthNumber = 1570;
        camera.update();

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
        lightPos.put(0f).put(1000f).put(0).put(-1000f).flip();

        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
        lightPos2.put(10000f).put(1000f).put(1000).put(0).flip(); // red

        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
        lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();

        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
        lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
        GL11.glEnable(GL11.GL_LIGHT0); // enable the light
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE,Utils.ConvertForGL(grey)); // specify the

        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos2); // specify the
        GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

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

//        myDelta = getTime() - StartTime;
        myDelta = getTime() - StartTime+0;
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
            GL11.glPushMatrix();
//            GL11.glTranslatef(0,400,0);
            GL11.glTranslatef(900,110,800);
            GL11.glScalef(80f,80f,80f);
            TexCube MyCube = new TexCube();
            wood.bind();
            MyCube.DrawTexCube(floor);
            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(1580,110,-30);
            GL11.glScalef(80f, 80f, 80f);
            TexCube cube = new TexCube();
            cube.DrawTexCube(wood);
            wood.bind();
            GL11.glPopMatrix();
        }



        {
            Tree MyTree = new Tree();
            MyTree.DrawTree(blackTexture,planks, leaves,cut,train_begin,local_boat_time);

            {
                GL11.glPushMatrix();
                GL11.glTranslatef(100, 180, 0);
                GL11.glScalef(100f, 2f, 100f);
                blackTexture.bind();
                Sphere sphere1 = new Sphere();
                sphere1.DrawSphere(1f, 32, 32);
                GL11.glPopMatrix();
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

//        {
//
//            GL11.glPushMatrix();
//            GL11.glTranslatef(2100, 400, 1000);
//            GL11.glRotatef(-45, 1, 0, 0);
//            if(millisecond >= 48000 && millisecond <= 48450) {
//                GL11.glTranslatef(0,-40,0);
//                GL11.glRotatef((float) (millisecond - 48000) / 10, -1, 0, 0); // -45 degree - 45 degree
//                GL11.glTranslatef(0,40,0);
//            }else if(millisecond>48450){
//                GL11.glTranslatef(0,-40,0);
//                GL11.glRotatef(45, -1, 0, 0);
//                GL11.glTranslatef(0,40,0);
//            }
//            Torch torch = new Torch();
//            torch.DrawTorch(wood,torch2);
//            GL11.glPopMatrix();
//        }

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
            GL11.glPushMatrix();
            Sign MySign = new Sign();
            MySign.DrawSign(train);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslatef(1040f,175f,900f);
            GL11.glScalef(50f, 8f, 8f);
            GL11.glRotatef(90, 1, 0, 0);
            blackTexture.bind();
            TexCube cube = new TexCube();
            cube.DrawTexCube(blackTexture);
            GL11.glPopMatrix();
        }

        {
            GL11.glPushMatrix();
            GL11.glTranslatef(2100f,700f,1100f);
            GL11.glScalef(80f, 80f, 80f);
            GL11.glRotatef(270, 1, 0, 0);
            Color.white.bind();
            TexCube cube = new TexCube();
            stop_sign.bind();
            cube.DrawTexCubeEx(stop_sign,5);
            GL11.glPopMatrix();
        }



        {
            GL11.glPushMatrix();
            Mine mine1 = new Mine();
            if(train_begin){
                local_time_train += 10;
            }
            if(local_time_train >= 27000 && local_time_train < 48000){
                GL11.glTranslatef((local_time_train-27000)/10,0,0);
            }else if(local_time_train >= 48000){
                GL11.glTranslatef((48000-27000)/10,0,0);
            }
            mine1.DrawMine(mine, blackTexture);
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(1.5f,-1f,0);
                GL11.glScalef(1f, 0.1f, 1f);
                blackTexture.bind();
                Sphere sphere1 = new Sphere();
                sphere1.DrawSphere(1f, 32, 32);
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
        }
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(1800,250,-30);
            GL11.glScalef(80f, 80f, 80f);
            TexCube cube = new TexCube();
            cube.DrawTexCubeForDifferentTextures(crafting_top,crafting_bottom,crafting_side);
            GL11.glPopMatrix();
            {
                GL11.glPushMatrix();
                GL11.glTranslatef(1920,180,-30);
                GL11.glScalef(80f, 0.1f, 80f);
                blackTexture.bind();
                Sphere sphere1 = new Sphere();
                sphere1.DrawSphere(1f, 32, 32);
                GL11.glPopMatrix();
            }

        }


//        System.out.println("timestamp: " + millisecond);
        // draw the NPC1
        {
            System.out.println("face:" + face);

            GL11.glPushMatrix();
            NPC1 npc1 = new NPC1();
            if(working){
                GL11.glTranslatef(885,200,735);
                GL11.glScalef(90f,90f,90f);
                GL11.glRotatef(0,0,1,0);
                face = 0;
            }else if(craft){
                GL11.glTranslatef(1700,200,-66);
                GL11.glScalef(90f,90f,90f);
                GL11.glRotatef(270,0,1,0);
                face = 3;
            }else if(cutting){
                GL11.glTranslatef(160,200,-33);
                GL11.glScalef(90f,90f,90f);
                GL11.glRotatef(90,0,1,0);
                face = 1;
            }else {
                GL11.glTranslatef(1000, 200, 0);
                GL11.glTranslatef(x, y, z);
                GL11.glScalef(90f, 90f, 90f);
                GL11.glRotatef(90 * face, 0, 1, 0);
            }
            npc1.DrawHuman(delta,walking,cutting,working,craft,headTexture,tnt,grenade);
            GL11.glPopMatrix();
        }

//        {
//            if(millisecond >= 41000) {
//                GL11.glPushMatrix();
//                NPC2 npc2 = new NPC2();
//                if(millisecond<=50000) {
//                    GL11.glTranslatef(2100, 200, 900);
//                    GL11.glScalef(90f, 90f, 90f);
//                    GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f); // rotate the human during the animation
//                    npc2.DrawHuman(delta, false,false, headTexture, tnt, grenade, millisecond);
//                }else if(millisecond<=53000){
//                    GL11.glTranslatef(2100, 200, 900);
//                    GL11.glTranslatef(-(millisecond-50000)/10,0,0);
//                    GL11.glScalef(90f, 90f, 90f);
//                    GL11.glRotatef(-90, 0.0f, -1.0f, 0.0f);
//                    npc2.DrawHuman(delta, true,false, headTexture, tnt, grenade, millisecond);
//                }else if(millisecond<=61000){
//                    GL11.glTranslatef(1800, 200, 900);
//                    GL11.glTranslatef(0,0,-(millisecond-53000)/10);
//                    GL11.glScalef(90f, 90f, 90f);
//                    npc2.DrawHuman(delta, true,false, headTexture, tnt, grenade, millisecond);
//                }else if(millisecond<=71000){
//                    GL11.glTranslatef(1800, 200, 0);
//                    GL11.glScalef(90f, 90f, 90f);
//                    npc2.DrawHuman(delta, false,true, headTexture, tnt, grenade, millisecond);
//                }else{
//                    GL11.glTranslatef(1800, 200, 0);
//                    GL11.glScalef(90f, 90f, 90f);
//                    npc2.DrawHuman(delta, false,false, headTexture, tnt, grenade, millisecond);
//                }
//                GL11.glPopMatrix();
//            }
//        }


        {
            if(local_boat_time>=200) {
                Boat boat = new Boat();
                boat.DrawBoat(planks);
                {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(2200.0f, 200.0f, -400.0f);
                    GL11.glScalef(90f, 0.1f, 90f);
                    blackTexture.bind();
                    Sphere sphere1 = new Sphere();
                    sphere1.DrawSphere(1f, 32, 32);
                    GL11.glPopMatrix();
                }

            }
            if(local_boat_time >= 400){
                craft = false;
            }
        }



    }

    public static void main(String[] argv) {
        MainWindow hello = new MainWindow();
        hello.start();
    }

    Texture valley_2022, headTexture, tnt, grenade, planks, leaves, floor, rail, mine, blackTexture, rail_activate,
            wall,train,wood,torch2,stop_sign,crafting_bottom,crafting_top,crafting_side;
    Skybox skybox;
    /*
     * Any additional textures for your assignment should be written in here.
     * Make a new texture variable for each one, so they can be loaded in at the beginning
     */
    public void init() throws IOException {
        Logger logger = Logger.getGlobal();
        logger.info("Loading textures...");
//        skybox = new Skybox();

        crafting_bottom = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/crafting_table/crafting_table_bottom.png"));

        crafting_top = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/crafting_table/crafting_table_top.png"));

        crafting_side = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/crafting_table/crafting_table_front.png"));

        stop_sign = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/stop_sign.png"));

        torch2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/torch2.png"));

        wood = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/planks_oak.png"));

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
