package org.leti.oop.kursach;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

    @Test
    public void testGetName() {
        Task task = new Task("Sample Task", 123);
        assertEquals("Sample Task", task.getName());
    }

    @Test
    public void testGetTimeOfCreate() {
        Task task = new Task("Sample Task", 123);
        assertEquals(123, task.getTime_of_create());
    }

    @Test
    public void testAddFile() {
        Task task = new Task("Sample Task", 123);
        File file = new File("example.txt");
        task.addFile(file);
        assertTrue(task.getFiles().contains(file));
    }

    @Test
    public void testSetDone() {
        Task task = new Task("Sample Task", 123);
        assertFalse(task.isDone());
        task.setDone(true);
        assertTrue(task.isDone());
    }

    @Test
    public void testClone() {
        Task task = new Task("Sample Task", 123);
        try {
            Task clonedTask = task.clone();
            assertNotSame(task, clonedTask);
            assertEquals(task.getName(), clonedTask.getName());
            assertEquals(task.getTime_of_create(), clonedTask.getTime_of_create());
        } catch (CloneNotSupportedException e) {
            fail("Clone not supported");
        }
    }

    @Test
    public void testToString() {
        Task task = new Task("Sample Task", 123);
        String expectedString = "\n         { Task_№0 | name: Sample Task | time_of_create: 123 | note:  | files: [] | preset: null\n" +
                "                   | deadline: null | completness: 0 | notice: false | rating: 3 }  \n     ";
        assertEquals(expectedString, task.toString());
    }

    int test = 0;
}