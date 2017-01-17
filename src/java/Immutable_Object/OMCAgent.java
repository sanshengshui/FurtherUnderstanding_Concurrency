package java.Immutable_Object;

/**
 * Created by jamesmsw on 17-1-17.
 */
public class OMCAgent extends Thread {
    @Override
    public void run() {
        boolean isTableModificationMsg=false;
        String updatedTableName=null;
        while (true){
            if (isTableModificationMsg){
                if ("MMSCInfo".equals(updatedTableName)){
                    MMSCRouter.setInstance(new MMSCRouter());
                }
            }
        }
    }
}
