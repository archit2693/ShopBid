package hackathon.iron_man.Model;

/**
 * Created by sakshi on 7/4/18.
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public class Item_fields implements Serializable {

    String userId,startTime,endTime,imageURL,category,description,name,status;
    long bidValue;

    public  Item_fields()
    {

    }

    public Item_fields(String userId, String startTime, String endTime, String imageURL, String category, String description, String name, String status, long bidValue) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.imageURL = imageURL;
        this.category = category;
        this.description = description;
        this.name = name;
        this.status = status;
        this.bidValue = bidValue;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBidValue() {
        return bidValue;

    }

    public void setBidValue(long bidValue) {
        this.bidValue = bidValue;
    }
}
