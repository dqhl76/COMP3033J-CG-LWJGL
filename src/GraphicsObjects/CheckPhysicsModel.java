package GraphicsObjects;

import javax.swing.plaf.SpinnerUI;

public class CheckPhysicsModel {

    float x,y,z;

    boolean mine = true;

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

    public void updateMine(boolean mine) {
        this.mine = mine;
    }

    public boolean checkBreakout(float delta_x, float delta_y, float delta_z) {
        if(checkWall(x+delta_x,y+delta_y,z+delta_z)){
            return true;
        }
        if(checkTreeNode(x+delta_x,y+delta_y,z+delta_z)){
            return true;
        }

        if(mine) {
            if (checkMine(x + delta_x, y + delta_y, z + delta_z)) {
                return true;
            }
        }else{
            if (checkMine2(x + delta_x, y + delta_y, z + delta_z)) {
                return true;
            }
            if(checkBoat(x+delta_x,y+delta_y,z+delta_z)){
                return true;
            }
        }

        if(checkMinecart(x+delta_x,y+delta_y,z+delta_z)){
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

    public boolean checkMine(float now_x, float now_y, float now_z){
        if(now_z<=805 && now_z>=300 && now_x>=-1206 && now_x<=-700){
            return true;
        }
        return false;
    }

    public boolean checkMine2(float now_x, float now_y,float now_z){
        if(now_z<=580 && now_z>=300 && now_x>=990 && now_x<=1380){
            return true;
        }
        return false;
    }


    public boolean checkMinecart(float now_x, float now_y, float now_z){
        if(now_z<=321 && now_z>=-234 && now_x>=610 && now_x<=960){
            return true;
        }
        return false;
    }

    public boolean checkBoat(float now_x, float now_y,float now_z){
        if(now_z<=230 && now_z>=-800 && now_x>=988 && now_x<=1383){
            return true;
        }
        return false;
    }
}
