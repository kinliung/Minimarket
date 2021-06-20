package minimarket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class cekStock extends javax.swing.JFrame {
    public cekStock() {
        initComponents();
    }
    
    String jenisBarang, namaBarang, tanggalMasuk, selectedComboBox;
    int ln, hargaBarang, stokBarang;
    String oldHarga, oldStock, oldTgglMsk, oldTgglKdlrs;
    
    
    
    File f = new File("c:\\minimarket\\data");
    
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
            fr.close();
        } catch (FileNotFoundException ex) {
            try {
                FileWriter fw = new FileWriter(f+namaFile);
                System.out.println("File Created!");
                fw.close();
            } catch (IOException ex1) {
                Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(cekStock.class.getName()).log(Level.SEVERE, null, ex);
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
    
    void checkStock(String namaFile){
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int rowCount = model.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            for(int i=0; i<(ln-2); i+=7){
                jenisBarang = raf.readLine().substring(15);
                namaBarang = raf.readLine().substring(14);
                hargaBarang = Integer.parseInt(raf.readLine().substring(8));
                stokBarang = Integer.parseInt(raf.readLine().substring(8));
                tanggalMasuk = raf.readLine().substring(16);
                model.addRow(new Object[]{jenisBarang, namaBarang, stokBarang, hargaBarang, tanggalMasuk});
                if (i==(ln-6)){
                    break;
                }
                for(int k=1; k<=2; k++){
                    raf.readLine();
                }
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void checkCombo(String namaFile){
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            raf.readLine();
            for(int i=0; i<(ln-2); i+=7){
                namaBarang = raf.readLine().substring(14);
                comboBox1.addItem(namaBarang);
                if (i==(ln-6)){
                    break;
                }
                for(int k=1; k<=6; k++){
                    raf.readLine();
                }
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editRecord(String oldNama, String newHarga, String newStok, String newTgglMsk, String newTgglKdlrsa){
        File oldFile = new File("c:\\minimarket\\data\\DataStok.txt");
        File newFile = new File("c:\\minimarket\\data\\tmp.txt");
        String cekData;
        try{
            FileWriter fw = new FileWriter("c:\\minimarket\\data\\tmp.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            countLines2("\\DataStok.txt");
            RandomAccessFile raf = new RandomAccessFile("c:\\minimarket\\data\\DataStok.txt", "rw");
            for(int i=0; i<(ln-2); i++){
                cekData = raf.readLine();
                if(cekData.equals(oldNama)){
                    pw.println(cekData);
                    pw.println(newHarga);
                    pw.println(newStok);
                    pw.println(newTgglMsk);
                    pw.println(newTgglKdlrsa);
                    raf.readLine();
                    raf.readLine();
                    raf.readLine();
                    raf.readLine();
                    i+=4;
                }
                else{
                    pw.println(cekData);
                } 
            }
            pw.println();
            pw.flush();
            raf.close();
            pw.close();
            bw.close();
            fw.close();
        }
        catch (Exception e) {
            Logger.getLogger(cekStock.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    void gantiFile(){
        try{
            File oldFile = new File("c:\\minimarket\\data\\DataStok.txt");
            oldFile.delete();
            File dump = new File("c:\\minimarket\\data\\DataStok.txt");
            File newFile = new File("c:\\minimarket\\data\\tmp.txt");
            newFile.renameTo(dump);
        }
        catch(Exception e){
            System.out.println("gagal");
        }
    }
    
    void countLines2(String namaFile){
        try {
            ln = 1;
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            for(int i=0; raf.readLine()!=null; i++){
                ln++;
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Minimarket.notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Minimarket.notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
                        
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboBox1 = new javax.swing.JComboBox<>();
        stokLabel = new javax.swing.JLabel();
        hargaLabel = new javax.swing.JLabel();
        tgglmskLabel = new javax.swing.JLabel();
        tgglkdlrsaLabel = new javax.swing.JLabel();
        stokTextField = new javax.swing.JTextField();
        hargaTextField = new javax.swing.JTextField();
        tgglmskTextField = new javax.swing.JTextField();
        tgglkdlrsaTextField = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        comboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox1ActionPerformed(evt);
            }
        });

        stokLabel.setText("Stok");

        hargaLabel.setText("Harga");

        tgglmskLabel.setText("Tanggal Masuk");

        tgglkdlrsaLabel.setText("Tanggal Kadaluarsa");

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });
        
        

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(hargaLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tgglmskLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tgglkdlrsaLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(stokLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgglmskTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tgglkdlrsaTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hargaTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(stokTextField, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(comboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                        .addComponent(updateButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stokLabel)
                    .addComponent(stokTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hargaLabel)
                    .addComponent(hargaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgglmskLabel)
                    .addComponent(tgglmskTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgglkdlrsaLabel)
                    .addComponent(tgglkdlrsaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearButton)
                    .addComponent(updateButton))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        
        
        
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jenis Barang", "Nama Barang", "Stok", "Harga", "Tanggal Masuk"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(20);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("STOK BARANG");

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(137, 137, 137)
                        .addComponent(refreshButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(backButton)
                    .addComponent(refreshButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
        setTitle("Manager - Cek Stok Barang");
        addComboBox();
        checkStock("\\DataStok.txt");
    }       
    
    
    void addComboBox(){
        createFolder();
        readFile("\\DataStok.txt");
        countLines2("\\DataStok.txt");
        checkCombo("\\DataStok.txt");
    }
    
    private void comboBox1ActionPerformed(java.awt.event.ActionEvent evt){
        selectedComboBox = comboBox1.getSelectedItem().toString();
    }
    
    

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) { 
        if(stokTextField.getText().isEmpty() || hargaTextField.getText().isEmpty() 
                || tgglmskTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Pastikan Tidak Ada Field Yang Kosong!");
            }
        else{
            String filepath = "c:\\minimarket\\data\\DataStok.txt";
            String oldNama = "Nama Barang : "+selectedComboBox;
            String newHarga = "Harga : "+hargaTextField.getText();
            String newStok = "Stock : "+stokTextField.getText();
            String newTgglMsk = "Tanggal Masuk : "+tgglmskTextField.getText();
            String newTgglKdlrsa = "Tanggal Kadaluarsa : "+tgglkdlrsaTextField.getText();

            cekStock cS = new cekStock();
            cS.editRecord(oldNama,newHarga,newStok,newTgglMsk,newTgglKdlrsa);
            gantiFile();
            JOptionPane.showMessageDialog(null, "Update Berhasil!");
            checkStock("\\DataStok.txt");
        }  
    }                                     

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        setVisible(false);
        Manager manager = new Manager();
        manager.launchFrame();
    }                                          

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        tgglmskTextField.setText("");
        stokTextField.setText("");
        hargaTextField.setText("");
        tgglkdlrsaTextField.setText("");
    }                                           

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {  
        createFolder();
        readFile("\\DataStok.txt");
        countLines2("\\DataStok.txt");
        checkStock("\\DataStok.txt");
    }      
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Minimarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Minimarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Minimarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Minimarket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cekStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton backButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox<String> comboBox1;
    private javax.swing.JLabel hargaLabel;
    private javax.swing.JTextField hargaTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel stokLabel;
    private javax.swing.JTextField stokTextField;
    private javax.swing.JLabel tgglkdlrsaLabel;
    private javax.swing.JTextField tgglkdlrsaTextField;
    private javax.swing.JLabel tgglmskLabel;
    private javax.swing.JTextField tgglmskTextField;
    private javax.swing.JButton updateButton;
    // End of variables declaration                   
}
