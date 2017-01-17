package java.Immutable_Object;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamesmsw on 17-1-17.
 */
public final class MMSCRouter {
    //用volatile修饰,保证多线程环境下该变量的可见性
    private static volatile  MMSCRouter instance=new MMSCRouter();
    //维护手机号码前缀到彩信中心之间的映射关系
    private final Map<String,MMSCInfo> routeMap;

    public MMSCRouter(){
        //将数据库中的数据加载到内存,存为Map
        this.routeMap=MMSCRouter.retrieveRouteMapFromDB();

    }
    private static Map<String,MMSCInfo> retrieveRouteMapFromDB(){
        Map<String,MMSCInfo> map=new HashMap<String,MMSCInfo>();
        return map;
    }

    public static MMSCRouter getInstance(){
        return instance;
    }
    public MMSCInfo getMMSc(String msisdnPrefix){
        return  routeMap.get(msisdnPrefix);
    }

    public static void setInstance(MMSCRouter newInstance){
        instance=newInstance;
    }

    private static Map<String,MMSCInfo> deepCopy(Map<String,MMSCInfo> m){
        Map<String,MMSCInfo> result=new HashMap<String,MMSCInfo>();
        for(String key:m.keySet()){
            result.put(key,new MMSCInfo(m.get(key)));

        }
        return result;


    }
    public Map<String,MMSCInfo> getRouteMap(){
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }

}
