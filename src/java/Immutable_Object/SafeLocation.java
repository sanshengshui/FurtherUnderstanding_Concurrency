package java.Immutable_Object;

/**
 * Created by jamesmsw on 17-1-17.
 * 将位置信息建模为状态不可变的对象
 */
public final class SafeLocation {
    public final double x;
    public final  double y;

    public SafeLocation(double x,double y){
        this.x=x;
        this.y=y;
    }
}
