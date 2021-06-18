package minimarket;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Minimarket extends JFrame {
    public void launchApp()	{
        Login login = new Login();
        login.launchFrame();
    }
    public static void main(String args[]) {
        Minimarket cashier = new Minimarket();
        cashier.launchApp();
    }	
    File f = new File("c:\\minimarket\\data");
    String Nama, Alamat, Username, Password;
    int ln;
    
    class notepad {

        public notepad() {
        }
    }
    
    void createFolder(){
        if(!f.exists()){
            f.mkdirs();
        }
    }
    
    void readFile(String namaFile){
        try {
            FileReader fr = new FileReader(f+namaFile);
            System.out.println("File Exist!");
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(f+namaFile);
                System.out.println("File Created!");
            } catch (IOException ex1) {
                Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
    
    void addData(String nama, String alamat, String usr, String pass, String namaFile){
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            for(int i=0; i<ln; i++){
                raf.readLine();
            }
            raf.writeBytes("Nama : "+nama+"\r\n");
            raf.writeBytes("Alamat : "+alamat+"\r\n");
            raf.writeBytes("Username : "+usr+"\r\n");
            raf.writeBytes("Password : "+pass+"\r\n");
            raf.writeBytes("\r\n");
            JOptionPane.showMessageDialog(null, "Registrasi Berhasil, Silahkan Login!");
            }
        catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int checkData(String usr, String pass, String namaFile){
        int nilai = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            Username = raf.readLine();
            Password = raf.readLine();
            for(int i=0; i<(ln-2); i+=5){
                Username = raf.readLine().substring(11);
                Password = raf.readLine().substring(11);
                if(usr.equals(Username) & pass.equals(Password)){
                    JOptionPane.showMessageDialog(null, "Login Berhasil!");
                    nilai = 1;
                    break;
                }
                else if (i==(ln-6)){
                    JOptionPane.showMessageDialog(null, "Incorrect Username/Password!");
                    nilai = 0;
                    break;
                }
                for(int k=1; k<=3; k++){
                    raf.readLine();
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nilai;
    }
    
    
    /*
    This logic methods is not useed, only for references
    */
    void logic(String usr, String pass, String namaFile){
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            raf.readLine();
            raf.readLine();
            for(int i=0; i<(ln-2); i+=5){
                Username = raf.readLine().substring(11);
                Password = raf.readLine().substring(11);
                if(usr.equals(Username) & pass.equals(Password)){
                    JOptionPane.showMessageDialog(null, "Password Matched!");
                    break;
                }
                else if (i==(ln-6)){
                    JOptionPane.showMessageDialog(null, "Incorrect Username/Password!");
                    break;
                }
                for(int k=1; k<=3; k++){
                    raf.readLine();
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    void countLines(String namaFile){
        try {
            ln = 1;
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            for(int i=0; raf.readLine()!=null; i++){
                ln++;
            }
            System.out.println("Number of lines: "+ln);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
}


class Login extends JFrame implements ActionListener {
    private JFrame loginFrame;
    private JButton Kasir, Manager;

    public Login() {
        loginFrame = new JFrame("Pengguna");
        Kasir = new JButton("Kasir");
        Manager = new JButton("Manager");

        Kasir.addActionListener(this);
        Manager.addActionListener(this);

        JPanel buttonPane = new JPanel();
        buttonPane.add(Kasir);
        buttonPane.add(Manager);

        loginFrame.add(buttonPane, BorderLayout.CENTER);
        loginFrame.setLocationRelativeTo(null);
    }

    public void launchFrame() {	
        loginFrame.setSize(250,200);
        loginFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,40,40));
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = loginFrame.getSize();
        loginFrame.setLocation(((screenSize.width - frameSize.width) / 2),
        ((screenSize.height - frameSize.height) / 2));
    }
        
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == Kasir) {
            loginFrame.setVisible(false);
            RegCAS regcas = new RegCAS();
            regcas.launchFrame();
        }
        else if (ae.getSource() == Manager){
            loginFrame.setVisible(false);
            RegMAN regman = new RegMAN();
            regman.launchFrame();
        }
    }
}

class RegCAS extends JFrame implements ActionListener {
    public static String cashierName;
    private JFrame regCASFrame;
    private JPanel loginPanel;
    private JButton masuk, reg, back;
    private JLabel nama, alamat, username, pass;
    private JTextField namaText, alamatText, usernameText;
    private JPasswordField passText;

    public RegCAS() {
        regCASFrame = new JFrame("Registrasi Kasir");
        loginPanel = new JPanel();

        nama = new JLabel("Nama Kasir");
        nama.setBounds(10,20,80,25);
        loginPanel.add(nama);
        namaText = new JTextField(20);
        namaText.setFont(new Font("",Font.BOLD,12));
        namaText.setBounds(100,20,190,25);
        loginPanel.add(namaText);

        alamat = new JLabel("Alamat");
        alamat.setBounds(10,50,80,25);
        loginPanel.add(alamat);
        alamatText = new JTextField(20);
        alamatText.setBounds(100,50,190,25);
        loginPanel.add(alamatText);

        username = new JLabel("Username");
        username.setBounds(10,80,80,25);
        loginPanel.add(username);
        usernameText = new JTextField(20);
        usernameText.setBounds(100,80,190,25);
        loginPanel.add(usernameText);

        pass = new JLabel("Password");
        pass.setBounds(10,110,80,25);
        loginPanel.add(pass);
        passText = new JPasswordField(20);
        passText.setBounds(100,110,190,25);
        loginPanel.add(passText);

        reg = new JButton("Registrasi");
        reg.setBounds(10,140,100,25);
        loginPanel.add(reg);
        reg.addActionListener(this);
        
        masuk = new JButton("Login");
        masuk.setBounds(120,140,80,25);
        loginPanel.add(masuk);
        masuk.addActionListener(this);
        
        back = new JButton("Back");
        back.setBounds(210,140,80,25);
        loginPanel.add(back);
        back.addActionListener(this);  
    }

    public void launchFrame() {
        regCASFrame.setSize(400,250);
        regCASFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        loginPanel.setLayout(null);

        // Centering the screen on the desktop
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = regCASFrame.getSize();
        regCASFrame.setLocation(((screenSize.width - frameSize.width) / 2),
        ((screenSize.height - frameSize.height) / 2));		

        regCASFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regCASFrame.setVisible(true);
    }

    public void actionPerformed (ActionEvent ae) {
        if(ae.getSource() == reg){
            if(namaText.getText().isEmpty() || alamatText.getText().isEmpty() 
                    || usernameText.getText().isEmpty() || passText.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Registrasi Gagal, Ada Field Yang Kosong!");
            }
            else{
                Minimarket minimarket = new Minimarket();
                minimarket.createFolder();
                minimarket.readFile("\\loginsCAS.txt");
                minimarket.countLines("\\loginsCAS.txt");
                minimarket.addData(namaText.getText(),alamatText.getText(),usernameText.getText(),passText.getText(),"\\loginsCAS.txt");
                namaText.setText("");
                alamatText.setText("");
                usernameText.setText("");
                passText.setText("");
                //Menu menu = new Menu();
                //menu.launchFrame();
            }
        }
        else if(ae.getSource() == masuk){
            regCASFrame.setVisible(false);
            LogCAS logcas = new LogCAS();
            logcas.launchFrame();
        }
        else if(ae.getSource() == back){
            regCASFrame.setVisible(false);
            Login login = new Login();
            login.launchFrame();
        }    
    }
}

class LogCAS extends JFrame implements ActionListener {
    public static String cashierName;
    private JFrame logCASFrame;
    private JPanel loginPanel;
    private JButton masuk, back;
    private JLabel username, pass;
    private JTextField usernameText;
    private JPasswordField passText;

    public LogCAS() {
        logCASFrame = new JFrame("Login Kasir");
        loginPanel = new JPanel();

        username = new JLabel("Username");
        username.setBounds(10,20,80,25);
        loginPanel.add(username);
        usernameText = new JTextField(20);
        usernameText.setBounds(100,20,190,25);
        loginPanel.add(usernameText);

        pass = new JLabel("Password");
        pass.setBounds(10,50,80,25);
        loginPanel.add(pass);
        passText = new JPasswordField(20);
        passText.setBounds(100,50,190,25);
        loginPanel.add(passText);
        
        masuk = new JButton("Login");
        masuk.setBounds(120,140,80,25);
        loginPanel.add(masuk);
        masuk.addActionListener(this);
        
        back = new JButton("Back");
        back.setBounds(210,140,80,25);
        loginPanel.add(back);
        back.addActionListener(this);  
    }

    public void launchFrame() {
        logCASFrame.setSize(400,250);
        logCASFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        loginPanel.setLayout(null);

        // Centering the screen on the desktop
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = logCASFrame.getSize();
        logCASFrame.setLocation(((screenSize.width - frameSize.width) / 2),
        ((screenSize.height - frameSize.height) / 2));		

        logCASFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logCASFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == masuk){
            cashierName = usernameText.getText();
            Minimarket minimarket = new Minimarket();
            minimarket.createFolder();
            minimarket.readFile("\\loginsCAS.txt");
            minimarket.countLines("\\loginsCAS.txt");
            //minimarket.checkData(usernameText.getText(),passText.getText());
            if (minimarket.checkData(usernameText.getText(),passText.getText(),"\\loginsCAS.txt")==1){
                logCASFrame.setVisible(false);
                //Menu menu = new Menu();
                //menu.launchFrame();
            }
            else{
                logCASFrame.setVisible(true);
            } 
        }
        else if(ae.getSource() == back){
            logCASFrame.setVisible(false);
            RegCAS regcas = new RegCAS();
            regcas.launchFrame();
        }    
    }
}

class RegMAN extends JFrame implements ActionListener {
    public static String managerName;
    private JFrame RegMANFrame;
    private JPanel loginPanel;
    private JButton masuk, reg, back;
    private JLabel nama, alamat, username, pass;
    private JTextField namaText, alamatText, usernameText;
    private JPasswordField passText;

    public RegMAN() {
        RegMANFrame = new JFrame("Registrasi Manager");
        loginPanel = new JPanel();


        nama = new JLabel("Nama Manager");
        nama.setBounds(10,20,100,25);
        loginPanel.add(nama);
        namaText = new JTextField(20);
        namaText.setFont(new Font("",Font.BOLD,12));
        namaText.setBounds(120,20,170,25);
        loginPanel.add(namaText);

        alamat = new JLabel("Alamat");
        alamat.setBounds(10,50,100,25);
        loginPanel.add(alamat);
        alamatText = new JTextField(20);
        alamatText.setBounds(120,50,170,25);
        loginPanel.add(alamatText);

        username = new JLabel("Username");
        username.setBounds(10,80,100,25);
        loginPanel.add(username);
        usernameText = new JTextField(20);
        usernameText.setBounds(120,80,170,25);
        loginPanel.add(usernameText);

        pass = new JLabel("Password");
        pass.setBounds(10,110,100,25);
        loginPanel.add(pass);
        passText = new JPasswordField(20);
        passText.setBounds(120,110,170,25);
        loginPanel.add(passText);

        reg = new JButton("Registrasi");
        reg.setBounds(10,140,100,25);
        loginPanel.add(reg);
        reg.addActionListener(this);
        
        masuk = new JButton("Login");
        masuk.setBounds(120,140,80,25);
        loginPanel.add(masuk);
        masuk.addActionListener(this);
        
        back = new JButton("Back");
        back.setBounds(210,140,80,25);
        loginPanel.add(back);
        back.addActionListener(this);
    }

    public void launchFrame() {	
        RegMANFrame.setSize(400,250);
        loginPanel.setLayout(null);
        RegMANFrame.add(loginPanel);

        // Centering the screen on the desktop
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = RegMANFrame.getSize();
        RegMANFrame.setLocation(((screenSize.width - frameSize.width) / 2),
        ((screenSize.height - frameSize.height) / 2));		

        RegMANFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RegMANFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == reg){
            if(namaText.getText().isEmpty() || alamatText.getText().isEmpty() 
                    || usernameText.getText().isEmpty() || passText.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Registrasi Gagal, Ada Field Yang Kosong!");
            }
            else{
                Minimarket minimarket = new Minimarket();
                minimarket.createFolder();
                minimarket.readFile("\\loginsMAN.txt");
                minimarket.countLines("\\loginsMAN.txt");
                minimarket.addData(namaText.getText(),alamatText.getText(),usernameText.getText(),passText.getText(),"\\loginsMAN.txt");
                namaText.setText("");
                alamatText.setText("");
                usernameText.setText("");
                passText.setText("");
                //Menu menu = new Menu();
                //menu.launchFrame();
            }
        }
        else if(ae.getSource() == masuk){
            RegMANFrame.setVisible(false);
            LogMAN logman = new LogMAN();
            logman.launchFrame();
        }
        else if(ae.getSource() == back){
            RegMANFrame.setVisible(false);
            Login login = new Login();
            login.launchFrame();
        }
    }
}

class LogMAN extends JFrame implements ActionListener {
    public static String managerName;
    private JFrame logMANFrame;
    private JPanel loginPanel;
    private JButton masuk, back;
    private JLabel username, pass;
    private JTextField usernameText;
    private JPasswordField passText;

    public LogMAN() {
        logMANFrame = new JFrame("Login Manager");
        loginPanel = new JPanel();

        username = new JLabel("Username");
        username.setBounds(10,20,80,25);
        loginPanel.add(username);
        usernameText = new JTextField(20);
        usernameText.setBounds(100,20,190,25);
        loginPanel.add(usernameText);

        pass = new JLabel("Password");
        pass.setBounds(10,50,80,25);
        loginPanel.add(pass);
        passText = new JPasswordField(20);
        passText.setBounds(100,50,190,25);
        loginPanel.add(passText);
        
        masuk = new JButton("Login");
        masuk.setBounds(120,140,80,25);
        loginPanel.add(masuk);
        masuk.addActionListener(this);
        
        back = new JButton("Back");
        back.setBounds(210,140,80,25);
        loginPanel.add(back);
        back.addActionListener(this);  
    }

    public void launchFrame() {
        logMANFrame.setSize(400,250);
        logMANFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        loginPanel.setLayout(null);

        // Centering the screen on the desktop
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = logMANFrame.getSize();
        logMANFrame.setLocation(((screenSize.width - frameSize.width) / 2),
        ((screenSize.height - frameSize.height) / 2));		

        logMANFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logMANFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == masuk){
            managerName = usernameText.getText();
            Minimarket minimarket = new Minimarket();
            minimarket.createFolder();
            minimarket.readFile("\\loginsMAN.txt");
            minimarket.countLines("\\loginsMAN.txt");
            //minimarket.checkData(usernameText.getText(),passText.getText());
            if (minimarket.checkData(usernameText.getText(),passText.getText(),"\\loginsMAN.txt")==1){
                logMANFrame.setVisible(false);
                Manager manager = new Manager();
                manager.launchFrame();
            }
        }
        else if(ae.getSource() == back){
            logMANFrame.setVisible(false);
            RegMAN regman = new RegMAN();
            regman.launchFrame(); 
        }
    }
}





class Manager extends JFrame implements ActionListener {
    private JFrame loginFrame;
    private JButton inputBarang, cekStok, logOut;
    private JLabel managerLabel;
    private JPanel managerPanel, buttonPane;

    public Manager() {
        loginFrame = new JFrame("Manager");
        loginFrame.getContentPane().setLayout(new BorderLayout(0,0));
        inputBarang = new JButton("Input Barang");
        cekStok = new JButton("Cek Stock");
        logOut = new JButton("Logout");
        
        inputBarang.addActionListener(this);
        cekStok.addActionListener(this);
        logOut.addActionListener(this);

        buttonPane = new JPanel();
        buttonPane.add(inputBarang);
        buttonPane.add(cekStok);
        buttonPane.add(logOut);

        managerLabel = new JLabel("   Selamat Datang Manager : " + LogMAN.managerName);
        managerLabel.setForeground(Color.red);
        managerLabel.setFont(new Font("Verdana",Font.BOLD,14));

        managerPanel = new JPanel();
        managerPanel.setLayout(new BorderLayout(10,10));
        managerPanel.setBackground(new Color(137,195,232));
        managerPanel.add(managerLabel, BorderLayout.NORTH);  
    }

    public void launchFrame() {	
        loginFrame.setSize(400,250);
        loginFrame.add(buttonPane, BorderLayout.CENTER);
        loginFrame.add(managerPanel, BorderLayout.NORTH);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }
          
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == inputBarang) {
            loginFrame.setVisible(false);
            inputBarang ib = new inputBarang();
            ib.launchFrame();
        }
        else if (ae.getSource() == cekStok){
            loginFrame.setVisible(false);
            //LogMAN logman = new LogMAN();
            //logman.launchFrame();
        }
        else if (ae.getSource() == logOut){
            loginFrame.setVisible(false);
            LogMAN logman = new LogMAN();
            logman.launchFrame();
        }
    }
}

