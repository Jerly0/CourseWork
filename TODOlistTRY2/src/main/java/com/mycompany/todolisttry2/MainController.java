/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolisttry2;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author anime
 */
public class MainController {
    ArrayList<Task> tasksList = new ArrayList<>();
    MainForm mainForm;
    
    MainController(MainForm mainForm) {
        this.mainForm = mainForm;
    }
    
    public int findTaskBySub(String subtName) {        
        for (int i = 0; i < this.tasksList.size(); i++) {
            Task task = this.tasksList.get(i);
            
            for (int j = 0; j < task.getSubtasks().size(); j++) {
                if(task.getSubtasks().get(j).getName().equals(subtName)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void addTask(String text, int priority, Date deadline) {
        ArrayList<Task> subtasks = new ArrayList<>();
        Task task = new Task(text, priority, deadline, subtasks);
        boolean isOnly = true;
        
        for (int i = 0; i < this.tasksList.size(); i++) {
            if (this.tasksList.get(i).getName().equals(text)) {
                System.out.println("you're already have same task");
                isOnly = false;
            }
        }
        if (isOnly) {
            this.tasksList.add(task);
        }
        this.updateTasks();
    }
    
    public void addSubtask(String subtName, String taskName, boolean isDone){
        Task subTask = new Task(subtName, isDone);
        int taskId = 0;
        boolean isOnly = true;
        
        for (int i = 0; i < this.tasksList.size(); i++) {
            Task task = this.tasksList.get(i);
            if(task.getName().equals(taskName)) {
                taskId = i;
            }
            for (int j = 0; j < task.getSubtasks().size(); j++) {
                if(task.getSubtasks().get(j).getName().equals(subtName)) {
                    isOnly = false;
                    System.out.println("you're already have same subtask");
                }
            }
            /*
            if (task.getName().equals(taskName) && task.isTask()) {
                taskId = i;
            } else if (task.getName().equals(subtName) && !(task.isTask())) {
                isOnly = false;
                System.out.println("you're already have same subtask");
            }*/
        }
        
        if(isOnly) {
            this.tasksList.get(taskId).getSubtasks().add(subTask);/*
            if(taskId == this.tasksList.size()-1){
                this.tasksList.add(subTask);
            } else {
                this.tasksList.add(taskId+1, subTask);
            }*/
        }
        
        this.updateTasks();
    }
    
    public void deleteTask(String taskName) {
        for(int i = 0; i < this.tasksList.size(); i++) {
            if (this.tasksList.get(i).getName().equals(taskName)) {
                this.tasksList.remove(i);
            }
        }
        this.updateTasks();
    }
    
    public void deleteSubtask(String subName) {
        int taskId = this.findTaskBySub(subName);
        if(taskId == -1) {
            System.out.println("Извините, подзадача уже была удалена");
        } else {
            ArrayList<Task> subtArr = this.tasksList.get(taskId).getSubtasks();
        
            for (int i = 0; i < subtArr.size(); i++) {
                if (subtArr.get(i).getName().equals(subName)) {
                    this.tasksList.get(taskId).getSubtasks().remove(i);
                }
            }
        }
        
        this.updateTasks();
    }
    
    public void updateTasks() {
        mainForm.showTasks(tasksList);
    }
    
    public Task doneTask(String taskName) {
        
        int taskI = 0;
        boolean isFound = false;
        Task task;
        
        for (int i = 0; i < this.tasksList.size(); i++) {
            task = this.tasksList.get(i);
            if (task.getName().equals(taskName)) {
                isFound = true;
                taskI = i;
                break;
            }
        }
        
        if (isFound) {
            task = tasksList.get(taskI);
            tasksList.remove(taskI);
            
            this.updateTasks();
            return task;
        } else {
            return null;
        }
        
    }
    
    public void doneSubtask(String subName) {
        int taskI = this.findTaskBySub(subName);
        boolean isTaskDone = true;
        Task task;
                
        for (int i = 0; i < this.tasksList.get(taskI).getSubtasks().size(); i++) {
            task = this.tasksList.get(taskI).getSubtasks().get(i);
            
            if (!(task.isTask())) {
                isTaskDone = task.getTask().isSelected() & isTaskDone;
            }
        }
        
        if(isTaskDone) {
            this.tasksList.get(taskI).getTask().setSelected(true);
        }
        
    }
}
