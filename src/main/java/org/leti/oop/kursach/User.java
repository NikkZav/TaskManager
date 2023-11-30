package org.leti.oop.kursach;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "my_task_manager.users")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "user_id")),
        @AttributeOverride(name = "login", column = @Column(name = "user_login")),
        @AttributeOverride(name = "password", column = @Column(name = "user_password")),
})
public class User extends Account{
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_age")
    private int age;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Task> tasks = new ArrayList<>(0);

    public User (String name, int age){
        this.name = name;
        this.age = age;
    }
    public User () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (!this.tasks.contains(task)) this.tasks.add(task);
    }


    @Override
    public String toString() {
        return "\nUser_№" + this.getId() + " ; " +
                "name: " + this.name + " ; " +
                "age: " + this.age + " ; " +
                "login: " + this.getLogin() + " ; " +
                "password: " + this.getPassword() + " ; " +
                "tasks: " + ( this.tasks!=null ? this.tasks.toString() : "null" ) + "\n";
    }
}
