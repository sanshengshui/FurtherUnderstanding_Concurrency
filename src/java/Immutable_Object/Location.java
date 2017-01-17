package java.Immutable_Object;

/**
 * Created by jamesmsw on 17-1-17.
 * 状态可变的位置信息模型(非线程安全)
 */
public class Location {
    private double x;
    private double y;

    public Location(double x,double y){
        this.x=y;
        this.y=y;

    }
    public double getX(){
        return  x;
    }
    public double getY(){
        return y;
    }
    public void setXY(double x,double y){
        this.x=x;
        this.y=y;
    }
}
