package org.leti.oop.kursach;
import javax.persistence.*;
import java.time.LocalDate;


@MappedSuperclass
public abstract class Container{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate deadline;
    private boolean notice = false;
    private int completness = 0;
    private int rating = 3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }

    public int getCompletness() {
        return completness;
    }

    public void setCompletness(int completness) {
        this.completness = completness;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return  "deadline: " + this.deadline + " | " +
                "completness: " + this.completness + " | " +
                "notice: " + this.notice + " | " +
                "rating: " + this.rating ;
    }

}

