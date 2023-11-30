package org.leti.oop.kursach;
import javax.persistence.*;

@Entity
@Table(name = "my_task_manager.notes")
public class Note{
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "note_text")
    private String text;
//    @OneToOne
//    @JoinColumn(name = "task_id")
//    private Task task;

    public Note (String text){
        this.text = text;
    }
    public Note () {
        this.text = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Task getTask() {
//        return task;
//    }
//
//    public void setTask(Task task) {
//        this.task = task;
//    }

    @Override
    public String toString() {
        return this.text;
    }
}

