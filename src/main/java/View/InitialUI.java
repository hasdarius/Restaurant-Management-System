package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialUI extends JFrame implements ActionListener {
    Controller controller;
    private JButton waiter;
    private JButton admin;
    private JButton chef;
    private JLabel title;
    public InitialUI(Controller controller){
        this.controller=controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        this.setBounds(400, 50, 1000, 1000);


        title=new JLabel("Restaurant App");
        title.setFont(new Font("Tisa",Font.ITALIC,25));
        title.setBounds(400,60,200,100);
        getContentPane().add(title);

        waiter=new JButton("Waiter");
        waiter.addActionListener(this);
        admin=new JButton("Administrator");
        admin.addActionListener(this);
        chef=new JButton("Chef");
        chef.addActionListener(this);

        waiter.setBounds(100,700,200,100);
        admin.setBounds(400,700,200,100);
        chef.setBounds(700,700,200,100);
        getContentPane().add(waiter);
        getContentPane().add(admin);
        getContentPane().add(chef);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == waiter){
            this.setVisible(false);
            controller.getWaiterGUI().setVisible(true);
        }
        if(source == chef){
            this.setVisible(false);
            controller.getChefGUI().setVisible(true);
        }
        if(source == admin){
            this.setVisible(false);
            controller.getAdministratorGUI().setVisible(true);
        }
    }
}
