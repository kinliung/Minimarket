package minimarket;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Kasir extends javax.swing.JFrame {

    File f = new File("C:\\minimarket\\data");
    private static String nama;
    String hasilakhir;
    String jenisBarang, namaBarang, tanggalMasuk, selectedComboBox, oldNama;
    int ln, hargaBarang, stokBarang, jumlahPembelian, stokTersedia, price, outSubtotal, totalharga;
    int c1 = 0; int c2 = 0;
    int jumlah;
    int total;
    
    public Kasir() {
        initComponents();
    }
    
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
                Logger.getLogger(cekStock.notepad.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(cekStock.class.getName()).log(Level.SEVERE, null, ex);
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
    
    void checkCombo(String namaFile){
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            raf.readLine();
            for(int i=0; i<(ln-2); i+=7){
                namaBarang = raf.readLine().substring(14);
                jComboBox1.addItem(namaBarang);
                if (i==(ln-6)){
                    break;
                }
                for(int k=1; k<=6; k++){
                    raf.readLine();
                }
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(cekStock.notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cekStock.notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editRecord(String oldNama, String newStok){
        //File oldFile = new File("c:\\minimarket\\data\\DataStok.txt");
        //File newFile = new File("c:\\minimarket\\data\\tmp.txt");
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
                    cekData = raf.readLine();
                    pw.println(cekData);
                    pw.println(newStok);
                    raf.readLine();
                    i+=2;
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

    public void editRecord2(){
        //File oldFile1 = new File("c:\\minimarket\\data\\DataStok.txt");
        //File oldFile2 = new File("c:\\minimarket\\data\\tempLogJualan.txt");
        countLines2("\\tempLogJualan.txt"); //WARNING : ini bisa create file secara otomatis
        c2 = ln;
        //File newFile = new File("c:\\minimarket\\data\\tmp2.txt");
        String cekData1 = ""; String cekData2 = ""; String cekData3 = "";
        int jumlah = 0; int jumlah2 = 0; int jumlah3 = 0; int total = 0; int salah=1;
        try{
            FileWriter fw = new FileWriter("c:\\minimarket\\data\\tmp2.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            RandomAccessFile raf = new RandomAccessFile("c:\\minimarket\\data\\DataStok.txt", "rw");
            for(int i=1; i<(c1-3); i+=7){
                int p = c2-3;
                cekData1 = raf.readLine();
                pw.println(cekData1);
                cekData1 = raf.readLine();
                RandomAccessFile raf2 = new RandomAccessFile("c:\\minimarket\\data\\tempLogJualan.txt", "rw");
                for(int j=1; j<(c2-3); j+=4){
                    p-=4;
                    cekData2 = "Nama Barang : "+raf2.readLine().substring(7);
                    if(cekData1.equals(cekData2)){
                        cekData2 = raf2.readLine().substring(9);
                        jumlah2 = Integer.parseInt(cekData2);
                        jumlah3 += jumlah2;
                        raf2.readLine();
                        raf2.readLine();
                        for(int k=1; k<p; k+=4){
                            cekData2 = "Nama Barang : "+raf2.readLine().substring(7);
                            if(cekData1.equals(cekData2)){
                                cekData2 = raf2.readLine().substring(9);
                                jumlah2 = Integer.parseInt(cekData2);
                                jumlah3 += jumlah2;
                                raf2.readLine();
                                raf2.readLine();
                            }
                            else{
                                raf2.readLine();
                                raf2.readLine();
                                raf2.readLine();
                            }
                        }
                        pw.println(cekData1);
                        cekData1 = raf.readLine();
                        pw.println(cekData1);
                        cekData1 = raf.readLine().substring(8);
                        jumlah = Integer.parseInt(cekData1);
                        total = jumlah + jumlah3;
                        jumlah3 = 0;
                        pw.println("Stock : "+total);
                        cekData1 = raf.readLine();
                        pw.println(cekData1);
                        cekData1 = raf.readLine();
                        pw.println(cekData1);
                        cekData1 = raf.readLine();
                        pw.println(cekData1);
                        raf2.close();
                        break;
                    }
                    else{
                        raf2.readLine();
                        raf2.readLine();
                        raf2.readLine();
                        for(int m=1; m<p; m+=4){
                            cekData2 = "Nama Barang : "+raf2.readLine().substring(7);
                            if(cekData1.equals(cekData2)){
                                cekData2 = raf2.readLine().substring(9);
                                jumlah2 = Integer.parseInt(cekData2);
                                jumlah3 += jumlah2;
                                raf2.readLine();
                                raf2.readLine();
                                salah = 0;
                            }
                            else{
                                raf2.readLine();
                                raf2.readLine();
                                raf2.readLine();
                            }
                        }
                        if (salah == 0){
                            pw.println(cekData1);
                            cekData1 = raf.readLine();
                            pw.println(cekData1);
                            cekData1 = raf.readLine().substring(8);
                            jumlah = Integer.parseInt(cekData1);
                            total = jumlah + jumlah3;
                            jumlah3 = 0;
                            pw.println("Stock : "+total);
                            cekData1 = raf.readLine();
                            pw.println(cekData1);
                            cekData1 = raf.readLine();
                            pw.println(cekData1);
                            cekData1 = raf.readLine();
                            pw.println(cekData1);
                            raf2.close();
                            break;
                        }
                        else{
                            pw.println(cekData1);
                            for(int q=1; q<=5; q++){
                                cekData1 = raf.readLine();
                                pw.println(cekData1);
                            }
                            raf2.close();
                            break;
                        }
                    }
                }
            }
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
    
    public void printLog(){
        try{
            String cekData;
            FileWriter fw = new FileWriter("c:\\minimarket\\data\\transaksiJualTercatat.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            countLines2("\\tempLogJualan.txt");
            RandomAccessFile raf = new RandomAccessFile("c:\\minimarket\\data\\tempLogJualan.txt", "rw");
            for(int i=0; i<(ln-2); i++){
                cekData = raf.readLine();
                pw.println(cekData);
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
    
    void gantiFile(String namaFile){
        try{
            File oldFile = new File(f+"\\DataStok.txt");
            oldFile.delete();
            File dump = new File(f+"\\DataStok.txt");
            File newFile = new File(f+namaFile);
            newFile.renameTo(dump);
        }
        catch(Exception e){
            System.out.println("gagal");
        }
    }
    
    void checkStock(String namaFile){
        try {
            DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
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
            Logger.getLogger(cekStock.notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cekStock.notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void checkLogJual(String namaFile){
        try {
            File checkEmptyFile = new File(f+namaFile);
            if (checkEmptyFile.length() == 0) {
                DefaultTableModel model = (DefaultTableModel) tabelDaftar.getModel();
                int rowCount = model.getRowCount();
                //Remove rows one by one from the end of the table
                for (int i = rowCount - 1; i >= 0; i--){
                    model.removeRow(i);
                }
            }
            else{
                DefaultTableModel model = (DefaultTableModel) tabelDaftar.getModel();
                int rowCount = model.getRowCount();
                //Remove rows one by one from the end of the table
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
                for(int i=1; i<=ln; i+=4){
                    namaBarang = raf.readLine().substring(7);
                    stokBarang = Integer.parseInt(raf.readLine().substring(9));
                    totalharga = Integer.parseInt(raf.readLine().substring(11));
                    model.addRow(new Object[]{namaBarang, stokBarang, totalharga});
                    if (i==(ln-4)){
                        break;
                    }
                    for(int k=1; k<=1; k++){
                        raf.readLine();
                    }
                }
                raf.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int checkData2(String namaBrg, String namaFile){
        int nilai = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            namaBarang = raf.readLine();
            for(int i=0; i<(ln-2); i+=7){
                namaBarang = raf.readLine();
                if(namaBrg.equals(namaBarang)){
                    price = Integer.parseInt(raf.readLine().substring(8));
                    stokTersedia = Integer.parseInt(raf.readLine().substring(8));
                    nilai = 1;
                    break;
                }
                else if (i==(ln-6)){
                    nilai = 0;
                    break;
                }
                for(int k=1; k<=6; k++){
                    raf.readLine();
                }
            }
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Minimarket.notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Minimarket.notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nilai;
    }
    
    void calcAndSetTotal() {
                int sum = price;
                if (!jumlahTextField.getText().isEmpty()) {
                    sum *= Integer.parseInt(jumlahTextField.getText());//we must add this
                    subtotalTextField.setText(String.valueOf(sum));
                }
                else{
                    subtotalTextField.setText("0");
                }
            }
    
    void addData2(String namaBrg, int jumlahbrg, int subtotal, String namaFile){
        try {
            RandomAccessFile raf = new RandomAccessFile(f+namaFile, "rw");
            for(int i=0; i<ln; i++){
                raf.readLine();
            }
            raf.writeBytes("Nama : "+namaBrg+"\r\n");
            raf.writeBytes("Jumlah : "+jumlahbrg+"\r\n");
            raf.writeBytes("Subtotal : "+subtotal+"\r\n");
            raf.writeBytes("\r\n");
            raf.close();
            }
        catch (FileNotFoundException ex) {
            Logger.getLogger(Minimarket.notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Minimarket.notepad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unhecked")                
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBarang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jumlahTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        subtotalTextField = new javax.swing.JTextField();
        clearBtn = new javax.swing.JButton();
        tambahBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelDaftar = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        totalFieldText = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cashFieldText = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ByrButton = new javax.swing.JButton();
        kembalianFieldText = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        totalBtn = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Kasir");

        tabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jenis", "Nama", "Stok", "Harga", "Tgl Masuk"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBarang);

        jLabel2.setText("Nama");

        jumlahTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Jumlah");

        jLabel5.setText("SubTotal");
        subtotalTextField.setEditable(false);
        totalFieldText.setEditable(false);
        kembalianFieldText.setEditable(false);

        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        tambahBtn.setText("Tambah");
        tambahBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahBtnActionPerformed(evt);
            }
        });

        jLabel6.setText("Daftar Pembelian");

        tabelDaftar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Jumlah", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelDaftar);

        jLabel7.setText("Total Tagihan");

        totalFieldText.setText("0");
        totalFieldText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalFieldTextActionPerformed(evt);
            }
        });

        jLabel8.setText("Cash");

        jLabel9.setText("Kembali");

        ByrButton.setText("Bayar");
        ByrButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ByrButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        totalBtn.setText("Totalkan");
        totalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBtnActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        
        jComboBox1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jComboBox1PopupMenuWillBecomeVisible(evt);
            }
        });
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(290, 290, 290)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(refreshButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel5))
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jumlahTextField)
                                            .addComponent(subtotalTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(clearBtn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                                        .addComponent(tambahBtn)
                                        .addGap(16, 16, 16))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCancel)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addComponent(totalFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(totalBtn)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cashFieldText, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                    .addComponent(kembalianFieldText))
                                .addGap(38, 38, 38)
                                .addComponent(ByrButton)
                                .addGap(25, 25, 25)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(2, 2, 2)
                        .addComponent(refreshButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnLogout)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jumlahTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(subtotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clearBtn)
                            .addComponent(tambahBtn))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cashFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ByrButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(kembalianFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(btnCancel)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(totalFieldText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalBtn))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation(((screenSize.width - frameSize.width) / 2),
        ((screenSize.height - frameSize.height) / 2));	
        
        setTitle("Kasir");
        addComboBox();
        checkStock("\\DataStok.txt");
        oldNama = "Nama Barang : "+selectedComboBox;
        checkData2(oldNama,"\\DataStok.txt");
        DocumentFilter df = new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int i, String string, AttributeSet as) throws BadLocationException {

                if (isDigit(string)) {
                    super.insertString(fb, i, string, as);
                    calcAndSetTotal();
                }
            }
            @Override
            public void remove(DocumentFilter.FilterBypass fb, int i, int i1) throws BadLocationException {
                super.remove(fb, i, i1);
                calcAndSetTotal();
            }
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int i, int i1, String string, AttributeSet as) throws BadLocationException {
                if (isDigit(string)) {
                    super.replace(fb, i, i1, string, as);
                    calcAndSetTotal();

                }
            }
            private boolean isDigit(String string) {
                for (int n = 0; n < string.length(); n++) {
                    char c = string.charAt(n);//get a single character of the string
                    //System.out.println(c);
                    if (!Character.isDigit(c)) {//if its an alphabetic character or white space
                        return false;
                    }
                }
                return true;
            }
        };
        ((AbstractDocument) (jumlahTextField.getDocument())).setDocumentFilter(df);
        ((AbstractDocument) (cashFieldText.getDocument())).setDocumentFilter(df);
        jumlahTextField.setText("1");
    }                      

    void addComboBox(){
        createFolder();
        readFile("\\DataStok.txt");
        countLines2("\\DataStok.txt");
        checkCombo("\\DataStok.txt");
    }
    private void tabelBarangMouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jumlahTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:

    }                                               

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {
        jumlahTextField.setText("");
        subtotalTextField.setText("");
    }                                        

    private void ByrButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (cashFieldText.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Masukkan Cash Duit Anda Dulu!");
        }
        else if (totalFieldText.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Anda Masih Belum Mengtotalkan Pembelian!");
        }
        else {
            int cash = outSubtotal = Integer.parseInt(cashFieldText.getText());
            if (cash<jumlah){
                JOptionPane.showMessageDialog(null, "Duit Anda Tidak Cukup!");
            }
            else{
                int kembali = cash - jumlah;
                jumlah = 0;
                kembalianFieldText.setText(String.valueOf(kembali));
                printLog();
                File test = new File("c:\\minimarket\\data\\tempLogJualan.txt");
                test.delete();
            }
        }
    }                                         

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        createFolder();
        readFile("\\DataStok.txt");
        countLines2("\\DataStok.txt");
        checkStock("\\DataStok.txt");
    }                                             

    private void tambahBtnActionPerformed(java.awt.event.ActionEvent evt) {
        Minimarket minimarket = new Minimarket();
        minimarket.createFolder();
        minimarket.readFile("\\tempLogJualan.txt");
        String stokAkhir;
        if(jumlahTextField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Masukkan Jumlah Barang Yang Ingin Dibeli!");
        }
        else{
            jumlahPembelian = Integer.parseInt(jumlahTextField.getText());
            outSubtotal = Integer.parseInt(subtotalTextField.getText());
            oldNama = "Nama Barang : "+selectedComboBox;
            checkData2(oldNama,"\\DataStok.txt");
            stokAkhir = String.valueOf(stokTersedia-jumlahPembelian);
            if(jumlahPembelian>stokTersedia){
                JOptionPane.showMessageDialog(null, "Jumlah Barang Yang ingin Anda Beli Melibihi Stok Yang Tersedia!");
            }
            else{
                addData2(selectedComboBox, jumlahPembelian, outSubtotal, "\\tempLogJualan.txt");
                String filepath = "c:\\minimarket\\data\\DataStok.txt";
                String newStok = "Stock : "+stokAkhir;
                editRecord(oldNama,newStok);
                gantiFile("\\tmp.txt");
                countLines2("\\tempLogJualan.txt");
                checkLogJual("\\tempLogJualan.txt");
                countLines2("\\DataStok.txt");
                checkStock("\\DataStok.txt");
                jumlah += outSubtotal;
                jumlahTextField.setText("");
                subtotalTextField.setText("");
                totalFieldText.setText("");
                kembalianFieldText.setText("");
            }
        }
    }                                         

    private void totalBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        totalFieldText.setText(String.valueOf(jumlah));
    }                                        

    private void totalFieldTextActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        LogCAS log = new LogCAS();
        log.launchFrame();
        setVisible(false);
    }                                         

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        File checkEmptyFile = new File("c:\\minimarket\\data\\tempLogJualan.txt");
        if (checkEmptyFile.length() == 0) {
            DefaultTableModel model = (DefaultTableModel) tabelDaftar.getModel();
            int rowCount = model.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--){
                model.removeRow(i);
            }
            totalFieldText.setText("");
            kembalianFieldText.setText("");
            cashFieldText.setText("");
        }
        else{
            countLines2("\\DataStok.txt");
            c1 = ln;
            editRecord2();
            gantiFile("\\tmp2.txt");
            File test = new File("c:\\minimarket\\data\\tempLogJualan.txt");
            test.delete();
            countLines2("\\tempLogJualan.txt");
            checkLogJual("\\tempLogJualan.txt");
            countLines2("\\DataStok.txt");
            checkStock("\\DataStok.txt");
            jumlah = 0;
            totalFieldText.setText("");
        }
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        File checkEmptyFile = new File("c:\\minimarket\\data\\DataStok.txt");
        if (checkEmptyFile.length() == 0) {
            DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
            int rowCount = model.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--){
                model.removeRow(i);
            }
        }
        else{
            selectedComboBox = jComboBox1.getSelectedItem().toString();
            oldNama = "Nama Barang : "+selectedComboBox;
            checkData2(oldNama,"\\DataStok.txt");
            calcAndSetTotal();
        }
    }                                          

    private void jComboBox1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
        File checkEmptyFile = new File("c:\\minimarket\\data\\DataStok.txt");
        if (checkEmptyFile.length() == 0) {
            DefaultTableModel model = (DefaultTableModel) tabelBarang.getModel();
            int rowCount = model.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--){
                model.removeRow(i);
            }
        }
        else{
            selectedComboBox = jComboBox1.getSelectedItem().toString();
            try{
                jumlahTextField.setText("1");
            }
            catch(Exception e){
            }
        }
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
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    javax.swing.JButton ByrButton;
    javax.swing.JButton btnCancel;
    javax.swing.JButton btnLogout;
    javax.swing.JTextField cashFieldText;
    javax.swing.JButton clearBtn;
    javax.swing.JComboBox<String> jComboBox1;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JPanel jPanel1;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JTextField jumlahTextField;
    javax.swing.JTextField kembalianFieldText;
    javax.swing.JButton refreshButton;
    javax.swing.JTextField subtotalTextField;
    javax.swing.JTable tabelBarang;
    javax.swing.JTable tabelDaftar;
    javax.swing.JButton tambahBtn;
    javax.swing.JButton totalBtn;
    javax.swing.JTextField totalFieldText;
    // End of variables declaration                   
}
