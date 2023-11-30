package org.leti.oop.kursach;

import static org.junit.Assert.*;
import javafx.scene.layout.AnchorPane;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FileUtils;

public class DataTest {

    private Data data;
    private User testUser;
    private Task testTask;

    @Before
    public void setUp() {
        data = Data.getInstance();
        testUser = new User("TestUser", 22);
        testTask = new Task("TestTask", "TestNote",30,2,LocalDate.now());
    }

    @Test
    public void testGetInstance() {
        assertNotNull(Data.getInstance());
        assertEquals(Data.getInstance(), data);
    }

    @Test
    public void testGetCurrentUser() {
        assertNull(Data.getCurrentUser());
        Data.setCurrentUser(testUser);
        assertEquals(testUser, Data.getCurrentUser());
    }

}
