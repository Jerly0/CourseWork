/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolisttry2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author anime
 */
public class HistoryController {
    ArrayList<String> historyList = new ArrayList<>();
    HistoryForm historyForm;
    
    HistoryController (HistoryForm historyForm) {
        this.historyForm = historyForm;
    }
    
    public void addToHistory(Task task) {
        
        Date dateNow = new Date();
        
        if (task.getDeadlineDate().after(dateNow)) {
            this.historyList.add("Задача " + task.getName() + " была выполнена вовремя!");
        } else {
            this.historyList.add("Задача " + task.getName() + " была просрочена.");
            
        }
        
        this.updateTasks();
    }
    
    public void clearHistory() {
        this.historyList.clear();
        this.updateTasks();
    }
    
    public void updateTasks() {
        this.historyForm.showTasks(this.historyList);
    }
}
