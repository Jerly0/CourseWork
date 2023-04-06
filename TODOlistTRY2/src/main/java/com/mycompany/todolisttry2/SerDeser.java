/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolisttry2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author anime
 */
public class SerDeser {
    
    public void saveTasks(ArrayList<Task> tasksList) {
        FileWriter file;
        
        try {
           file = new FileWriter("tasks.txt", false);
           
           for(int i = 0; i < tasksList.size(); i++) {
               String taskName = tasksList.get(i).getName();
               int priority = tasksList.get(i).getPriority();
               long deadline = tasksList.get(i).getDeadlineDate().getTime();
               int subtCount = tasksList.get(i).getSubtasks().size();
               
               file.write(taskName + '\n' + priority + '\n' + deadline + '\n' + subtCount + '\n');
               
               for (int j = 0; j < subtCount; j++) {
                   String subName = tasksList.get(i).getSubtasks().get(j).getName();
                   boolean isDone = tasksList.get(i).getSubtasks().get(j).getTask().isSelected();
                   file.write(subName + '\n' + isDone + '\n');
               }
           }
           
           file.flush();
           file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveHistory (ArrayList<String> historyList) {
        FileWriter file;
        
        try {
           file = new FileWriter("history.txt", false);
           
           for (int i = 0; i < historyList.size(); i++) {
               String historyString = historyList.get(i);
               
               file.write(historyString + '\n');
           }
           file.flush();
           file.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void uploadTasks(ArrayList<Task> tasksList) {
        try {
            FileReader file = new FileReader("tasks.txt");
            BufferedReader fileReader = new BufferedReader(file);
            
            String line;
            while ((line = fileReader.readLine()) != null) {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                JXDatePicker picker = new JXDatePicker();
                picker.setFormats(format);
                
                String taskName = line;
                int priority = Integer.parseInt(fileReader.readLine());
                long deadline = Long.parseLong(fileReader.readLine());
                int subCount = Integer.parseInt(fileReader.readLine());
                
                ArrayList<Task> subtasks = new ArrayList<>();                
                for (int i = 0; i < subCount; i++) {
                    String subName = fileReader.readLine();
                    boolean isDone = Boolean.parseBoolean(fileReader.readLine());
                    Task subt = new Task(subName, isDone);
                    
                    subtasks.add(subt);
                }
                
                Date deadlineDate = new Date(deadline);
                picker.setDate(deadlineDate);
                
                
                Task task = new Task(taskName, priority, picker.getDate(), subtasks);
                
                tasksList.add(task);
                
            }            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.io.EOFException ex) {
            System.out.println("error");
        } catch (IOException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void uploadHistory(ArrayList<String> historyList) {
        try {
            
            FileReader history = new FileReader("history.txt");
            FileReader historySize = new FileReader("history.txt");
            BufferedReader historyReader = new BufferedReader(history);
            BufferedReader historySizeReader = new BufferedReader(historySize);
            
            int lineCount = 0;
            while(historySizeReader.readLine() != null){
                lineCount += 1;
            }
            
            String historyString;
            for (int i = 0; i < lineCount; i++) {
                historyString = historyReader.readLine();
                historyList.add(historyString);
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.io.EOFException ex) {
            System.out.println("error");
        } catch (IOException ex) {
            Logger.getLogger(SerDeser.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
