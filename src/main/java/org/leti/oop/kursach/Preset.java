package org.leti.oop.kursach;
import javax.persistence.*;

@Entity
@Table(name = "my_task_manager.presets")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "preset_id")),
        @AttributeOverride(name = "deadline", column = @Column(name = "preset_deadline")),
        @AttributeOverride(name = "notice", column = @Column(name = "preset_notice")),
        @AttributeOverride(name = "completness", column = @Column(name = "preset_completness")),
        @AttributeOverride(name = "rating", column = @Column(name = "preset_rating")),
})
public class Preset extends Container{
    @Column(name = "preset_name")
    private String name;


    public Preset (String name){
        this.name = name;
    }
    public Preset () {}

    public String getName() {
        return name;
    }

    public void setName(String login) {
        this.name = name;
    }


}

