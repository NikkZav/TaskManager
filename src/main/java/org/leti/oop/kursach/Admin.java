package org.leti.oop.kursach;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "my_task_manager.admins")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "admin_id")),
        @AttributeOverride(name = "login", column = @Column(name = "admin_login")),
        @AttributeOverride(name = "password", column = @Column(name = "admin_password")),
})
public class Admin extends Account{
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private List<User> users = new ArrayList<>(0);

    public Admin () {}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (!this.users.contains(user)) this.users.add(user);
    }

    @Override
    public String toString() {
        return "Admin №" + this.getId() + "  " +
                ( this.users!=null ? this.users.toString() : "null" );
    }
}
