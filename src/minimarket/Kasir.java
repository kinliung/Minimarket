/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author DriftKing
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Kasir implements ActionListener{
    JTextField tx_nama, tx_username, tx_pw, tx_alamat;
    JLabel lb_nama, lb_alamat, lb_username, lb_pw, lb_titik2;
    JButton bt_simpan_kasir;
    JPanel panel,panel2;
    
    public static void main(String[] args){
        Kasir gui = new Kasir();
        gui.form_kasir();
    }
    
    public void form_kasir(){
        JFrame frame = new JFrame("Kasir");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        frame.setSize(500,500);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        JPanel panel2 = new JPanel();
        
        FlowLayout layout = new FlowLayout();
        
        panel2.setLayout(layout);
        
        bt_simpan_kasir = new JButton("Simpan"); 
        bt_simpan_kasir.addActionListener(this);
       
        lb_nama = new JLabel("Nama:");
        tx_nama = new JTextField(10);
        
        lb_alamat = new JLabel("Alamat:");
        tx_alamat = new JTextField(10);
        
        lb_username = new JLabel("Username:");
        tx_username = new JTextField(10);
        
        lb_pw = new JLabel("Password:");
        tx_pw = new JTextField(10);
        
         
        panel.add(lb_nama);
        panel.add(tx_nama);
        
        panel.add(lb_alamat);
        panel.add(tx_alamat);
        
        panel.add(lb_username);
        panel.add(tx_username);
        
        panel.add(lb_pw);
        panel.add(tx_pw);
        
        panel2.add(bt_simpan_kasir);
        
        frame.add(panel,"North");
        frame.add(panel2,"South");
        frame.setSize(300, 500);
        frame.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == bt_simpan_kasir){
//         nanti di isi :)   
        }
    }
}