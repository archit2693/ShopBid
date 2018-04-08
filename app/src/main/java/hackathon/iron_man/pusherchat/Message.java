package hackathon.iron_man.pusherchat;


public class Message {


    public Message(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public String text;
    public String name;
    public String thumbnail;
    public long time;

}