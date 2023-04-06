/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolisttry2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JCheckBox;

/**
 *
 * @author anime
 */
public class Task {
    
    private JCheckBox task;
    private boolean isTask;
    private ArrayList<Task> subtasks; 
    private int priority;
    private Date deadline;
    private DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    
    Task(String text, boolean isDone) {
        this.task = new JCheckBox(text);
        this.task.setSelected(isDone);
        this.isTask = false;
    }
    
    Task(String text, int priority, Date deadline, ArrayList<Task> subtasks) {
        this.task = new JCheckBox(text);
        this.priority = priority;
        this.deadline = deadline;
        this.isTask = true;
        this.subtasks = subtasks;
    }

    public ArrayList<Task> getSubtasks(){
        return subtasks;
    }
    
    public String getDeadline() {
        return format.format(deadline);
    }
    
    public Date getDeadlineDate() {
        return deadline;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public JCheckBox getTask() {
        return task;
    }

    public String getName() {
        return task.getText();
    }

    public boolean isTask() {
        return isTask;
    }

}
