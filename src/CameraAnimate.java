import GraphicsObjects.Camera;
import org.lwjgl.Sys;

public class CameraAnimate{
    private Camera camera;

    public CameraAnimate(Camera camera){

        this.camera = camera;

    }
    public void update(long time) {
        if(time>=33253 && time <= 46670){
            camera.position.x = (float) (camera.position.x - 1);
        }else if(time>=46700 && time <= 51700){
            camera.OrthNumber = camera.OrthNumber - 1;
            camera.rotation.y = camera.rotation.y - 0.01f;
            camera.position.y = camera.position.y + 1f;
        }
    }
}
