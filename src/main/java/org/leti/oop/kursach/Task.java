package org.leti.oop.kursach;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "my_task_manager.tasks")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "task_id")),
        @AttributeOverride(name = "deadline", column = @Column(name = "task_deadline")),
        @AttributeOverride(name = "notice", column = @Column(name = "task_notice")),
        @AttributeOverride(name = "completness", column = @Column(name = "task_completness")),
        @AttributeOverride(name = "rating", column = @Column(name = "task_rating")),
})
public class Task extends Container implements Cloneable {
    @Column(name = "task_name")
    private String name = "";
    @Column(name = "task_time_of_create")
    private int time_of_create;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<File> files = new ArrayList<>(0);
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "note_id")
    private Note note;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preset_id")
    private Preset preset;
    @Column(name = "task_done")
    private boolean done = false;
    @Transient
    private boolean select = false;

    public Task () {
        this.note = new Note();
    }

    public Task (String name, int time_of_create){
        this();
        this.name = name;
        this.time_of_create = time_of_create;
    }

    public Task (String name, String note, int completness, int rating, LocalDate deadline){
        this.name = name;
        this.note = new Note(note);
        this.setCompletness(completness);
        this.setRating(rating);
        this.setDeadline(deadline);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime_of_create() {
        return time_of_create;
    }

    public void setTime_of_create(int time_of_create) {
        this.time_of_create = time_of_create;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Preset getPreset() {
        return preset;
    }

    public void setPreset(Preset preset) {
        this.preset = preset;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void addFile(File file) {
        if (!this.files.contains(file)) {
            this.files.add(file);
        }
    }


    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    @Override
    public String toString() {
        return "\n         { Task_№" + this.getId() + " | " +
                "name: " + this.name + " | " +
                "time_of_create: " + this.time_of_create + " | " +
                "note: " + ( this.note!=null ? this.note.toString() : "null" ) + " | " +
                "files: " + ( this.files!=null ? this.files.toString() : "null" ) + " | " +
                "preset: " + ( this.preset!=null ? this.preset.toString() : "null" ) +
                "\n                   | " + super.toString() +  " }  " + "\n     ";
    }
}

