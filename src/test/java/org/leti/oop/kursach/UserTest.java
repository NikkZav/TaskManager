package org.leti.oop.kursach;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testGetName() {
        User user = new User("John Doe", 25);
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetAge() {
        User user = new User("John Doe", 25);
        assertEquals(25, user.getAge());
    }

    @Test
    public void testAddTask() {
        User user = new User("John Doe", 25);
        Task task = new Task();
        user.addTask(task);
        assertTrue(user.getTasks().contains(task));
    }

    @Test
    public void testToString() {
        User user = new User("John Doe", 25);
        String expectedString = "\nUser_№0 ; name: John Doe ; age: 25 ; login: null ; password: null ; tasks: []\n";
        assertEquals(expectedString, user.toString());
    }

}