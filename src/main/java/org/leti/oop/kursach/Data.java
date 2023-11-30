package org.leti.oop.kursach;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FileUtils;

public class Data {
    private static final Data INSTANCE = new Data();

    private static User currentUser;

    private static EntityManager em;

    public static EntityManager getEm() {
        return em;
    }

    private Data() {
    }

    public static Data getInstance() {
        return INSTANCE;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Data.currentUser = currentUser;
    }

    public List<User> getUsers(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_persistence");
        em = emf.createEntityManager();
        Query query = em.createQuery("SELECT e FROM User e");
        List<User> entities = query.getResultList();
        em.getTransaction().begin();
        return entities;
    }

    public void saveData(){
        em.getTransaction().commit();
        em.getTransaction().begin();
    }

    public void removeData(Task task){
        Task found_task = em.find(Task.class, task.getId());
        em.remove(found_task);
    }


    public void savePDFReport(AnchorPane tasksField) {

        try {
            byte[] pdf = HTMLPrintUtil.printPdfReport(
                    "items_report.html", getTestData(tasksField));
            FileUtils.writeByteArrayToFile(new File(
                    "src/main/resources/print/" +
                    currentUser.getName() + "_report.pdf"), pdf);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> getTestData(AnchorPane tasksField) {

        Map<String, Object> data = new HashMap<>();
        List<Map<String,String>> table = new ArrayList<>();

        data.put("date", LocalDate.now().toString());
        data.put("user", currentUser.getName());

        Map<String, String> row;

        for (Node node : tasksField.getChildren()){
            if (node.isVisible()){
                Task task = (Task)node.getUserData();
                row = new HashMap<>();
                row.put("name", task.getName());
                row.put("note", task.getNote().getText());
                row.put("completness", Integer.toString(task.getCompletness()) + " %");
                row.put("rating", "# " + Integer.toString(task.getRating()));
                row.put("deadline", task.getDeadline()!=null ? task.getDeadline().toString() : "");
                table.add(row);
            }
        }

        data.put("goods-rows", table);

        return data;
    }

}
