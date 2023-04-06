/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.todolisttry2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author anime
 */
public class MainForm extends javax.swing.JFrame {

    MainController mainContr;
    SerDeser saveUpload;
    JPanel mainPanel;
    JPopupMenu taskPopupMenu = new JPopupMenu();
    JPopupMenu settingsMenu = new JPopupMenu();
    Task myTask;
    HistoryForm historyForm;
    JButton settingsButton = new JButton(); //кнопка настроек
    JButton addTaskButton = new JButton(); //кнопка новой задачи
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        
        this.mainContr = new MainController(this);
        this.historyForm = new HistoryForm();
        saveUpload = new SerDeser();
        
        this.setTitle("Записывалка задач");
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(800, 600);
        //this.setResizable(false);
        this.add(settingsButton);
        this.add(addTaskButton);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(20, 70, 760, 450);
        mainPanel.setVisible(true);
        mainPanel.setBackground(Color.lightGray);
        this.add(mainPanel); 
        
        //---------------------------------------------
        
        JMenuItem tasksHistory = new JMenuItem("История задач");
        tasksHistory.addActionListener((ActionEvent e) -> {
            historyForm.setVisible(true);
        });
        settingsMenu.add(tasksHistory);
        
        JMenuItem changeThemeButton = new JMenuItem("Сменить тему");
        changeThemeButton.addActionListener((ActionEvent e) -> {
            System.out.println("Сменил тему");
        });
        settingsMenu.add(changeThemeButton);
        
        //---------------------------------------------
        
        settingsButton.setFont(new Font("Arial", Font.BOLD, 12)); //кастомизация кнопки
        settingsButton.setBounds(670, 20, 100, 30); //кастомизация кнопки
        settingsButton.setText("Настройки"); //кастомизация кнопки
        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {//показ всплыв меню
                    settingsMenu.show(settingsButton, evt.getX(), evt.getY());
                }
            }
        });
        
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 12)); //кастомизация кнопки
        addTaskButton.setBounds(650, 540, 140, 30); //кастомизация кнопки
        addTaskButton.setText("Добавить задачу"); //кастомизация кнопки
        addTaskButton.addActionListener((ActionEvent e) -> {
            //открытие окна добавления задачи
            AddTaskForm addTaskForm = new AddTaskForm(mainContr);
        });
        
        //-----------------------------------------
        
        JMenuItem addSub = new JMenuItem("Добавить подзадачу");
        addSub.addActionListener((ActionEvent e) -> {
            AddSubtaskForm addSubForm = new AddSubtaskForm(myTask.getName(), mainContr);
        });
        taskPopupMenu.add(addSub);
        
        JMenuItem deleteTask = new JMenuItem("Удалить задачу");
        deleteTask.addActionListener((ActionEvent e) -> {
                DeleteTaskForm deleteTaskPopup = new DeleteTaskForm(mainContr, myTask);
        });
        taskPopupMenu.add(deleteTask);
        
    }
    
    public void showTasks(ArrayList<Task> tasks){
        mainPanel.removeAll();
        
        for (int i = 0; i < tasks.size()-1; i++) {
            Task taski = tasks.get(i);
            for (int j = i; j < tasks.size(); j++) {
                Task taskj = tasks.get(j);
                if (taski.getDeadlineDate().after(taskj.getDeadlineDate())) {
                    Task temp = taski;
                    tasks.set(i, taskj);
                    tasks.set(j, temp);
                } else if(taski.getDeadlineDate().equals(taskj.getDeadlineDate()) && taski.getPriority() > taskj.getPriority()) {
                    Task temp = taski;
                    tasks.set(i, taskj);
                    tasks.set(j, temp);
                }
            }
        }
        int chbCount = 0;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            
            JLabel deadline = new JLabel("Сделать до " + task.getDeadline());
                
            task.getTask().setBounds(0, 30*(chbCount), 300, 30);
            deadline.setBounds(350, 30*(chbCount), 300, 30);
            mainPanel.add(deadline);
                
            for (MouseListener mouseListener : task.getTask().getMouseListeners()) {
                task.getTask().removeMouseListener(mouseListener);
            }
            task.getTask().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    if(evt.getButton() == MouseEvent.BUTTON3) {
                        myTask = task;
                        taskPopupMenu.show(task.getTask(), evt.getX(), evt.getY());
                    } else if (evt.getButton() == MouseEvent.BUTTON1 && !task.getTask().isSelected()) {
                        task.getTask().setSelected(true);
                    } else if (evt.getButton() == MouseEvent.BUTTON1 && task.getTask().isSelected()) {
                        task.getTask().setSelected(false);
                    }
                }
            });
            
            task.getTask().addItemListener((ItemEvent e) -> {
                if (task.getTask().isSelected()) {
                    String taskName = task.getName();
                    Task testTask = mainContr.doneTask(taskName);
                    if (testTask != null) {
                        historyForm.historyContr.addToHistory(testTask);
                    }
                        
                }
            });
            task.getTask().setVisible(true);
            mainPanel.add(task.getTask());
            
            for (int j = 0; j < tasks.get(i).getSubtasks().size(); j++) {
                Task subt = tasks.get(i).getSubtasks().get(j);
                
                subt.getTask().setBounds(30, 30*(chbCount +1), 760, 30);
                
                for (MouseListener mouseListener : subt.getTask().getMouseListeners()) {
                    subt.getTask().removeMouseListener(mouseListener);
                }
                
                subt.getTask().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        if(evt.getButton() == MouseEvent.BUTTON3) {
                            myTask = subt;
                            DeleteTaskForm deleteTaskPopup = new DeleteTaskForm(mainContr, myTask);
                        } else if (evt.getButton() == MouseEvent.BUTTON1 && !subt.getTask().isSelected()) {
                            subt.getTask().setSelected(true);
                        } else if (evt.getButton() == MouseEvent.BUTTON1 && subt.getTask().isSelected()) {
                            subt.getTask().setSelected(false);
                        }
                    }
                });
                
                subt.getTask().addItemListener((ItemEvent e) -> {
                    if (subt.getTask().isSelected()) {
                        
                        String taskName = subt.getName();
                        mainContr.doneSubtask(taskName);
                        
                    }
                });
                subt.getTask().setVisible(true);
                mainPanel.add(subt.getTask());
                chbCount += 1;
            }
            chbCount += 1;
        }
        mainPanel.updateUI();
        mainPanel.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        saveUpload.uploadTasks(this.mainContr.tasksList);
        saveUpload.uploadHistory(historyForm.historyContr.historyList);
        this.mainContr.updateTasks();
        historyForm.historyContr.updateTasks();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveUpload.saveTasks(this.mainContr.tasksList);
        saveUpload.saveHistory(this.historyForm.historyContr.historyList);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
