package GraphicsObjects;

public class CheckPhysicsModel {

    float x,y,z;

    public CheckPhysicsModel(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void updatePosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean checkBreakout(float delta_x, float delta_y, float delta_z) {
        if(checkWall(x+delta_x,y+delta_y,z+delta_z)){
            return true;
        }
        if(checkTreeNode(x+delta_x,y+delta_y,z+delta_z)){
            return true;
        }

        return false;
    }

    public boolean checkWall(float now_x, float now_y, float now_z) {
        if(now_z>900){
            return true;
        }
        return false;
    }

    public boolean checkTreeNode(float now_x, float now_y, float now_z){
        if(now_z<=237 && now_z>=-267 && now_x>=-1216 && now_x<=-800){
            return true;
        }
        return false;
    }

}
