package java.Immutable_Object;

/**
 * Created by jamesmsw on 17-1-17.
 */
public final class MMSCInfo {
    private final String deviceID;
    private final String url;
    private final int maxAttachmentSizeInBytes;

    public MMSCInfo(String deviceID,String url,int maxAttachmentSizeInBytes){
        this.deviceID=deviceID;
        this.url=url;
        this.maxAttachmentSizeInBytes=maxAttachmentSizeInBytes;
    }
    public MMSCInfo(MMSCInfo prototype){
        this.deviceID=prototype.getDeviceID();
        this.url=prototype.getUrl();
        this.maxAttachmentSizeInBytes=prototype.getMaxAttachmentSizeInBytes();
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachmentSizeInBytes() {
        return maxAttachmentSizeInBytes;
    }
}
