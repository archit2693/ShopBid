package hackathon.iron_man.Model;

/**
 * Created by Iron_Man on 08/04/18.
 */

public class Cart_model {

    String name, imageUrl, status;
    long bidValue;

    public Cart_model(String name, String imageUrl, String status, long bidValue) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.status = status;
        this.bidValue = bidValue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }
}
