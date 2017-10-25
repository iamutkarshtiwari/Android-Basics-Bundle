package iamutkarshtiwari.github.io.habtrac.activity.models;

/**
 * Created by utkarshtiwari on 17/10/17.
 */

public class Habit {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id, name, date, frequency;

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFrequency() {
        return frequency;
    }

    public Habit(String id, String name, String date, String frequency) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.frequency = frequency;
    }
}
