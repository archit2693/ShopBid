package hackathon.iron_man;

public class GridCardModel {

    int image;
    String name;
    Boolean choice;

    public GridCardModel(int image, String name,Boolean choice) {
        this.image = image;
        this.name = name;
        this.choice = choice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChoice() {
        return choice;
    }

    public void setChoice(Boolean choice) {
        this.choice = choice;
    }
}
