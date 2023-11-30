package org.leti.oop.kursach;
import javax.persistence.*;

@Entity
@Table(name = "my_task_manager.files")
public class File{
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "file_path")
    private String file_path;

    public File (String file_path){
        this.file_path = file_path;
    }
    public File () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    @Override
    public String toString() {
        return this.file_path;
    }
}

