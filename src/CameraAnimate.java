import GraphicsObjects.Camera;
import org.lwjgl.Sys;

public class CameraAnimate extends Thread{
    private Camera camera;
    private int delta;
    private boolean isRunning = true;
    private final long StartTime;
    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public CameraAnimate(Camera camera, int delta,long StartTime){
        this.StartTime = StartTime;
        this.camera = camera;
        this.delta = delta;
    }
    @Override
    public void run() {
        while (isRunning){
            long millis = getTime() - StartTime;
            if(millis<=9500){

            }
            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopThread(){
        isRunning = false;
    }
}
