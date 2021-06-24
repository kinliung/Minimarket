/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimarket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DriftKing
 */
public class logTransaksi extends javax.swing.JFrame {

    File f = new File("c:\\minimarket\\data");
    String jenisBarang, namaBarang, namaBarang2, stokBarang2, tanggalMasuk, selectedComboBox;
    int ln, c1, c2, hargaBarang, stokBarang, totalharga;
    
    /**
     * Creates new form logTransaksi
     */
    public logTransaksi() {
        initComponents();
    }

    void checkStock(String namaFile){
        try {
            DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
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
                DefaultTableModel model = (DefaultTableModel) tabelLogTransaksi.getModel();
                int rowCount = model.getRowCount();
                //Remove rows one by one from the end of the table
                for (int i = rowCount - 1; i >= 0; i--){
                    model.removeRow(i);
                }
            }
            else{
                DefaultTableModel model = (DefaultTableModel) tabelLogTransaksi.getModel();
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
            Logger.getLogger(Kasir.notepad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Kasir.notepad.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    public void editRecord2(String namaBarang2, String stokBarang2){
        countLines2("\\transaksiJualTercatat.txt"); //WARNING : ini bisa create file secara otomatis
        c1 = ln;
        String cekData1 = ""; String cekData2 = ""; String cekData3 = ""; String cekData4 = "";
        int jumlah = 0; int jumlah2 = 0; int jumlah3 = 0; int total = 0; int salah=1;
        try{
            FileWriter fw = new FileWriter("c:\\minimarket\\data\\tmp3.txt",true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            RandomAccessFile raf = new RandomAccessFile("c:\\minimarket\\data\\transaksiJualTercatat.txt", "rw");
            for(int i=1; i<(c1-3); i+=4){
                cekData1 = raf.readLine().substring(7);
                if(cekData1.equals(namaBarang2)){
                    cekData2 = raf.readLine().substring(9);
                    if(cekData2.equals(stokBarang2)){
                        jumlah2 = Integer.parseInt(cekData2);
                        RandomAccessFile raf2 = new RandomAccessFile("c:\\minimarket\\data\\DataStok.txt", "rw");
                        for(int j=1; j<(c2-3); j+=7){
                            cekData3 = raf2.readLine();
                            pw.println(cekData3);
                            cekData3 = raf2.readLine().substring(14);
                            cekData4 = "Nama Barang : "+cekData3;
                            if(cekData1.equals(cekData3)){
                                pw.println(cekData4);
                                cekData3 = raf2.readLine();
                                pw.println(cekData3);
                                cekData3 = raf2.readLine().substring(8);
                                jumlah = Integer.parseInt(cekData3);
                                jumlah3 = jumlah2 + jumlah;
                                pw.println("Stock : "+jumlah3);
                                for(int k=1; k<=3; k++){
                                    cekData3 = raf2.readLine();
                                    pw.println(cekData3);
                                }
                            }
                            else{
                                pw.println(cekData4);
                                for(int k=1; k<=5; k++){
                                    cekData3 = raf2.readLine();
                                    pw.println(cekData3);
                                }
                            }
                        }
                        raf2.close();
                    }
                    else{
                        cekData2 = raf.readLine();
                        cekData2 = raf.readLine();
                    }
                }
                else{
                    cekData1 = raf.readLine();
                    cekData1 = raf.readLine();
                    cekData1 = raf.readLine();
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
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelLogTransaksi = new javax.swing.JTable();
        cancelAllButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        loginCashButton = new javax.swing.JButton();
        stokBarangLabel = new javax.swing.JLabel();
        inputBarangButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        updateStockButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Log Transaksi");

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        stockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Jenis", "Nama Barang", "Stok", "Harga", "Tanggal Masuk"
            }
        ));
        jScrollPane1.setViewportView(stockTable);

        tabelLogTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "Jumlah", "Subtotal"
            }
        ));
        tabelLogTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelLogTransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelLogTransaksi);

        cancelAllButton.setText("Cancel All Transaktion");
        cancelAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAllButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel Transaktion");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        loginCashButton.setText("Login Kasir");
        loginCashButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginCashButtonActionPerformed(evt);
            }
        });

        stokBarangLabel.setText("Tabel Stok Barang");

        inputBarangButton.setText("Input Barang Baru");
        inputBarangButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputBarangButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        updateStockButton.setText("Update Stok Baru");
        updateStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateStockButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputBarangButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateStockButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loginCashButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(stokBarangLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cancelAllButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(30, 30, 30)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backButton)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelAllButton)
                    .addComponent(cancelButton))
                .addGap(8, 8, 8)
                .addComponent(stokBarangLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginCashButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputBarangButton)
                    .addComponent(updateStockButton))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        setVisible(false);
        Manager mn = new Manager();
        mn.launchFrame();
    }//GEN-LAST:event_backButtonActionPerformed

    private void cancelAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAllButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelAllButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        countLines2("\\DataStok.txt");
        c2 = ln;
        editRecord2(namaBarang2, stokBarang2);
        Kasir ks = new Kasir();
        ks.gantiFile("\\tmp3.txt");
        cekStock cs = new cekStock();
        cs.createFolder();
        cs.readFile("\\DataStok.txt");
        countLines2("\\DataStok.txt");
        System.out.println(ln);
        checkStock("\\DataStok.txt");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void inputBarangButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputBarangButtonActionPerformed
        setVisible(false);
        inputBarang ib = new inputBarang();
        ib.setVisible(true);
    }//GEN-LAST:event_inputBarangButtonActionPerformed

    private void loginCashButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginCashButtonActionPerformed
        setVisible(false);
        LogCAS log = new LogCAS();
        log.launchFrame();
        setVisible(false);
    }//GEN-LAST:event_loginCashButtonActionPerformed

    private void updateStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateStockButtonActionPerformed
        setVisible(false);
        cekStock cekstk = new cekStock();
        cekstk.setVisible(true);
    }//GEN-LAST:event_updateStockButtonActionPerformed

    private void tabelLogTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelLogTransaksiMouseClicked
        int i = tabelLogTransaksi.getSelectedRow();
        namaBarang2 = tabelLogTransaksi.getValueAt(i, 0).toString();
        stokBarang2 = tabelLogTransaksi.getValueAt(i, 1).toString();
        System.out.println(namaBarang2);
        System.out.println(stokBarang2);
    }//GEN-LAST:event_tabelLogTransaksiMouseClicked

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
            java.util.logging.Logger.getLogger(logTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(logTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(logTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(logTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                logTransaksi lg = new logTransaksi();
                lg.launch();
            }
            
        });
    }
    public void launch() {
        logTransaksi lg = new logTransaksi();
        lg.setVisible(true);
        lg.setTitle("Manager - Cek Log Transaksi");
        cekStock cs = new cekStock();
        cs.createFolder();
        cs.readFile("\\DataStok.txt");
        lg.countLines2("\\DataStok.txt");
        lg.checkStock("\\DataStok.txt");
        
        cs.readFile("\\transaksiJualTercatat.txt");
        lg.countLines2("\\transaksiJualTercatat.txt");
        lg.checkLogJual("\\transaksiJualTercatat.txt");
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton cancelAllButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton inputBarangButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loginCashButton;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable stockTable;
    private javax.swing.JLabel stokBarangLabel;
    private javax.swing.JTable tabelLogTransaksi;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton updateStockButton;
    // End of variables declaration//GEN-END:variables
}
