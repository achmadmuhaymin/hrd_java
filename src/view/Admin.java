package view;


import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;
import controller.KoneksiDB;
import controller.ManajemenAbsensi;
import controller.ManajemenDasbor;
import controller.ManajemenPegawai;
import java.awt.Dimension;
import java.awt.List;
import java.awt.Toolkit;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.ModelAbsensi;
import model.ModelFilter;
import model.ModelPegawai;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/** Jasper report 
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
**/

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dendi
 */
public class Admin extends javax.swing.JFrame {
    KoneksiDB konek = new KoneksiDB();
    JasperReport JasRep;
    JasperPrint JasPri;
    Map param = new HashMap();
    JasperDesign JasDes;
    
    /**
     * Creates new form Admin
     */
    DefaultTableModel tabelpegawai;
    public void loadTblPegawai() throws SQLException{
        ManajemenPegawai mptl = new ManajemenPegawai();
        mptl.loadDataPegawai(tabelpegawai, null);
        
    }
    
    DefaultTableModel tabeldasbor;
     public void loadTblDasbor() throws SQLException{
        ManajemenDasbor mdtl = new ManajemenDasbor();
        mdtl.loadDataDasbor(tabeldasbor);
        try {
            int pegawai = mdtl.getJumlahPegawai();
            
            
            int hadir = mdtl.getTotalHadir();
            int hari = mdtl.getTotalHari();
            
            double persentase = hadir / hari;
            double persentase2 = persentase / pegawai;
            
            double persentase3 = persentase2 * 100;
            DecimalFormat df = new DecimalFormat("##,##");
                        
            
                    
        } catch (SQLException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    DefaultTableModel tabelabsen;
    
    public void loadTblAbsen() throws SQLException{
        ManajemenAbsensi matl = new ManajemenAbsensi();
        matl.loadDataAbsen(tabelabsen);
       
    }
    
    
    public Admin() throws SQLException {
        initComponents();
        //inisialisasi combo jabatan
        cmbJabatan.addItem("Pilih jabatan");
        
        //Agar memulai tampilan aplikasi berada di tengah layar
        Dimension ss = Toolkit.getDefaultToolkit ().getScreenSize ();
        Dimension frameSize = new Dimension ( 760, 513 );
        this.setBounds ( ss.width / 2 - frameSize.width / 2, 
                  ss.height / 2 - frameSize.height / 2,
                  frameSize.width, frameSize.height );

        //Membuat TableModel Pegawai
        tabelpegawai = new DefaultTableModel();
        //menambahkan TableModel ke Tabel pegawai
        jtabelpegawai.setModel(tabelpegawai);        
        tabelpegawai.addColumn("ID Pegawai");
        tabelpegawai.addColumn("Nama");
        tabelpegawai.addColumn("Jabatan");
        tabelpegawai.addColumn("Foto");
        tabelpegawai.addColumn("Shift");
        
        //Membuat TableModel Dasbor
        tabeldasbor = new DefaultTableModel();
        
        //menambahkan TableModel ke Tabel
               
        tabeldasbor.addColumn("Tanggal");
        tabeldasbor.addColumn("Nama Pegawai");
        tabeldasbor.addColumn("Jabatan");
        tabeldasbor.addColumn("Masuk");
        tabeldasbor.addColumn("Keluar");
        
        //Membuat TableModel Izin
        tabelabsen = new DefaultTableModel();
        
        //menambahkan TableModel ke Tabel
        jtabelabsen.setModel(tabelabsen);
        tabelabsen.addColumn("No");
        tabelabsen.addColumn("Tanggal");
        tabelabsen.addColumn("NIP");
        tabelabsen.addColumn("Nama");
        tabelabsen.addColumn("Jabatan");
        tabelabsen.addColumn("Izin/Cuti");
        tabelabsen.addColumn("Keterangan");
        tabelabsen.addColumn("Foto");
        
        
        //memanggil method tabel-tabel agar terisi dgn database
        loadTblPegawai();
        loadJabatan();
        loadTblAbsen();
    }
    
    // Controller SQL mengambil langsung isi tabel jabatan di sql
    
    public void loadJabatan() throws SQLException {
        String sql = "SELECT * FROM tbljabatan";    
        PreparedStatement pst = KoneksiDB.getKoneksi().prepareStatement(sql);
        ResultSet rs;
        rs = pst.executeQuery(sql);
        while(rs.next()){           
            cmbJabatan.addItem(rs.getString("namajabatan"));
        }
    }

    public void runReportDefault(String sourcefilename, HashMap hash) {
        Connection con = KoneksiDB.getKoneksi();
        try {
            InputStream report;
            report = getClass().getResourceAsStream(sourcefilename);
            JasperPrint jprint = JasperFillManager.fillReport(report,hash, con);
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setFitPageZoomRatio();
            viewer.setVisible(true);
        } catch (Exception e) {
            System.out.print(e.getMessage());
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

        jenis_absen = new javax.swing.ButtonGroup();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        panel_izin = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtabelabsen = new javax.swing.JTable();
        txtAbsen_keterangan = new javax.swing.JTextField();
        btnAbsen_simpan = new javax.swing.JButton();
        radAbsen_cuti = new javax.swing.JRadioButton();
        radAbsen_izin = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        btnAbsen_ubah = new javax.swing.JButton();
        btnAbsen_tambah = new javax.swing.JButton();
        btnAbsen_hapus = new javax.swing.JButton();
        txtAbsen_tanggal = new com.toedter.calendar.JDateChooser();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtAbsen_nip = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        img_foto_absen = new javax.swing.JLabel();
        txtAbsen_no = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        radAbsen_tidak = new javax.swing.JRadioButton();
        panel_about = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        panel_Pegawai = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btn_tambahpegawai = new javax.swing.JButton();
        btn_ubahdata = new javax.swing.JButton();
        btn_hapusdata = new javax.swing.JButton();
        btn_simpandata = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtabelpegawai = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txt_IDPegawai = new javax.swing.JTextField();
        txt_AddNamaPegawai = new javax.swing.JTextField();
        txt_foto = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        cmbJabatan = new javax.swing.JComboBox();
        img_foto = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        Shift = new javax.swing.JComboBox();
        panel_Laporan_Harian = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jcal_tanggalhadir = new com.toedter.calendar.JCalendar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu2.setText("jMenu2");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROGRAM HRD PT XYZ");
        setBackground(new java.awt.Color(204, 204, 204));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new java.awt.CardLayout());

        panel_izin.setBackground(new java.awt.Color(204, 204, 204));
        panel_izin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_izin.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 108, -1, -1));

        jtabelabsen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "NIP", "Nama", "Jabatan", "Cuti/Izin/Tidak Masuk", "Keterangan", "Foto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtabelabsen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtabelabsenMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jtabelabsen);
        if (jtabelabsen.getColumnModel().getColumnCount() > 0) {
            jtabelabsen.getColumnModel().getColumn(0).setResizable(false);
            jtabelabsen.getColumnModel().getColumn(2).setResizable(false);
            jtabelabsen.getColumnModel().getColumn(3).setResizable(false);
            jtabelabsen.getColumnModel().getColumn(4).setResizable(false);
            jtabelabsen.getColumnModel().getColumn(5).setResizable(false);
            jtabelabsen.getColumnModel().getColumn(6).setResizable(false);
        }

        panel_izin.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 730, 230));

        txtAbsen_keterangan.setBackground(new java.awt.Color(249, 249, 249));
        txtAbsen_keterangan.setEnabled(false);
        txtAbsen_keterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbsen_keteranganActionPerformed(evt);
            }
        });
        panel_izin.add(txtAbsen_keterangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 180, 180, 36));

        btnAbsen_simpan.setText("Simpan");
        btnAbsen_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbsen_simpanActionPerformed(evt);
            }
        });
        panel_izin.add(btnAbsen_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 180, 100, 40));

        radAbsen_cuti.setBackground(new java.awt.Color(204, 204, 204));
        jenis_absen.add(radAbsen_cuti);
        radAbsen_cuti.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        radAbsen_cuti.setText("Cuti");
        radAbsen_cuti.setEnabled(false);
        radAbsen_cuti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radAbsen_cutiActionPerformed(evt);
            }
        });
        panel_izin.add(radAbsen_cuti, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, -1, -1));

        radAbsen_izin.setBackground(new java.awt.Color(204, 204, 204));
        jenis_absen.add(radAbsen_izin);
        radAbsen_izin.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        radAbsen_izin.setText("Izin");
        radAbsen_izin.setEnabled(false);
        radAbsen_izin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radAbsen_izinActionPerformed(evt);
            }
        });
        panel_izin.add(radAbsen_izin, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, -1, -1));
        panel_izin.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 108, -1, -1));

        btnAbsen_ubah.setText("Ubah");
        btnAbsen_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbsen_ubahActionPerformed(evt);
            }
        });
        panel_izin.add(btnAbsen_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 70, -1));

        btnAbsen_tambah.setText("Tambah");
        btnAbsen_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbsen_tambahActionPerformed(evt);
            }
        });
        panel_izin.add(btnAbsen_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 80, -1, -1));

        btnAbsen_hapus.setText("Hapus");
        btnAbsen_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbsen_hapusActionPerformed(evt);
            }
        });
        panel_izin.add(btnAbsen_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 70, -1));

        txtAbsen_tanggal.setBackground(new java.awt.Color(249, 249, 249));
        txtAbsen_tanggal.setToolTipText("");
        txtAbsen_tanggal.setDateFormatString("yyyy-MM-d");
        txtAbsen_tanggal.setEnabled(false);
        txtAbsen_tanggal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAbsen_tanggalPropertyChange(evt);
            }
        });
        panel_izin.add(txtAbsen_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 180, -1));

        jLabel32.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel32.setText("Keterangan :");
        panel_izin.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, -1, 30));

        jLabel33.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel33.setText("NIP :");
        panel_izin.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, 30));

        jLabel34.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel34.setText("Tanggal :");
        panel_izin.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, 30));

        txtAbsen_nip.setBackground(new java.awt.Color(249, 249, 249));
        txtAbsen_nip.setEnabled(false);
        txtAbsen_nip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbsen_nipActionPerformed(evt);
            }
        });
        panel_izin.add(txtAbsen_nip, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 180, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel27.setText("Absensi");
        panel_izin.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jLabel35.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel35.setText("Jenis :");
        panel_izin.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, 30));

        img_foto_absen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_foto_absen.setText("foto");
        panel_izin.add(img_foto_absen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 180, 160));

        txtAbsen_no.setBackground(new java.awt.Color(249, 249, 249));
        txtAbsen_no.setEnabled(false);
        txtAbsen_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbsen_noActionPerformed(evt);
            }
        });
        panel_izin.add(txtAbsen_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 40, 30));

        jLabel6.setText("No_Record");
        panel_izin.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, 20));

        radAbsen_tidak.setBackground(new java.awt.Color(204, 204, 204));
        jenis_absen.add(radAbsen_tidak);
        radAbsen_tidak.setText("Tidak Hadir");
        radAbsen_tidak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radAbsen_tidakActionPerformed(evt);
            }
        });
        panel_izin.add(radAbsen_tidak, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, -1));

        jPanel1.add(panel_izin, "card11");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel15.setText("Tentang Software");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel4.setText("Absensi Pegawai Versi 0.8.6 (Beta)");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("  Absensi pegawai ini di buat agar kami belajar lebih dalam\nmata kuliah pemrograman berbasis objek. Bahasa pemrograman\nyang kami perdalam adalah bahasa pemrograman JAVA. \n\n  Versi Java yang kami gunakan adalah 7.2 dengan bantuan\nNetbeans IDE 7.2 sebagai Software Development-nya, XAMPP\nsebagai bantuan Web Server & Database dan Navicat \nsebagai tambahan software untuk mengelola Database.\n \nSemoga aplikasi Absensi Pegawai ini dapat bermanfaat bagi \nsemua orang.");
        jTextArea1.setBorder(null);
        jTextArea1.setFocusable(false);
        jTextArea1.setOpaque(false);
        jScrollPane9.setViewportView(jTextArea1);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo-aplikasi.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(167, 167, 167))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(138, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tentang Aplikasi", jPanel2);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dendi.jpg"))); // NOI18N

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setText("\n\n\n\n\tNama   : Dendi Abdul Rohim\n\tNIM     : J3D112093\n\tKelas    : TEK A P1\n");
        jTextArea2.setOpaque(false);
        jScrollPane10.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pengembang Java", jPanel3);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/amin.jpg"))); // NOI18N

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextArea3.setRows(5);
        jTextArea3.setText("\n\n\n\n\tNama   : Amin Shaum Saefulloh\n\tNIM     : J3D112092\n\tKelas    : TEK A P1\n");
        jTextArea3.setOpaque(false);
        jScrollPane11.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pengembang Database", jPanel4);

        jTextArea4.setEditable(false);
        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jTextArea4.setRows(5);
        jTextArea4.setText("\n\n\n\n\tNama   : Triwandha Febian R R\n\tNIM     : J3D112062\n\tKelas    : TEK A P1\n");
        jTextArea4.setOpaque(false);
        jScrollPane12.setViewportView(jTextArea4);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tri.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(126, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pengembang Report", jPanel5);

        javax.swing.GroupLayout panel_aboutLayout = new javax.swing.GroupLayout(panel_about);
        panel_about.setLayout(panel_aboutLayout);
        panel_aboutLayout.setHorizontalGroup(
            panel_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_aboutLayout.createSequentialGroup()
                .addGroup(panel_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_aboutLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15))
                    .addGroup(panel_aboutLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        panel_aboutLayout.setVerticalGroup(
            panel_aboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_aboutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1))
        );

        jPanel1.add(panel_about, "card9");

        panel_Pegawai.setBackground(new java.awt.Color(204, 204, 204));
        panel_Pegawai.setFont(new java.awt.Font("Segoe UI Light", 0, 11)); // NOI18N
        panel_Pegawai.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel20.setText("ID Pegawai");
        panel_Pegawai.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 70, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel7.setText("Manajemen Pegawai");
        panel_Pegawai.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jLabel21.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel21.setText("Nama ");
        panel_Pegawai.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, 30));

        jLabel22.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel22.setText("Jabatan");
        panel_Pegawai.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, 30));

        btn_tambahpegawai.setBackground(new java.awt.Color(204, 204, 204));
        btn_tambahpegawai.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        btn_tambahpegawai.setForeground(new java.awt.Color(51, 51, 51));
        btn_tambahpegawai.setText("Tambah Pegawai");
        btn_tambahpegawai.setToolTipText("Menambah Pegawai");
        btn_tambahpegawai.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.darkGray));
        btn_tambahpegawai.setContentAreaFilled(false);
        btn_tambahpegawai.setFocusPainted(false);
        btn_tambahpegawai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_tambahpegawai.setRequestFocusEnabled(false);
        btn_tambahpegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahpegawaiActionPerformed(evt);
            }
        });
        panel_Pegawai.add(btn_tambahpegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 140, -1));

        btn_ubahdata.setBackground(new java.awt.Color(204, 204, 204));
        btn_ubahdata.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        btn_ubahdata.setForeground(new java.awt.Color(51, 51, 51));
        btn_ubahdata.setText("Ubah Data");
        btn_ubahdata.setToolTipText("Mengubah data yang terseleksi");
        btn_ubahdata.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.darkGray));
        btn_ubahdata.setContentAreaFilled(false);
        btn_ubahdata.setFocusPainted(false);
        btn_ubahdata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_ubahdata.setRequestFocusEnabled(false);
        btn_ubahdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahdataActionPerformed(evt);
            }
        });
        panel_Pegawai.add(btn_ubahdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 140, -1));

        btn_hapusdata.setBackground(new java.awt.Color(204, 204, 204));
        btn_hapusdata.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        btn_hapusdata.setForeground(new java.awt.Color(51, 51, 51));
        btn_hapusdata.setText("Hapus Data");
        btn_hapusdata.setToolTipText("Menghapus data yang terseleksi");
        btn_hapusdata.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.darkGray));
        btn_hapusdata.setContentAreaFilled(false);
        btn_hapusdata.setFocusPainted(false);
        btn_hapusdata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_hapusdata.setRequestFocusEnabled(false);
        btn_hapusdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusdataActionPerformed(evt);
            }
        });
        panel_Pegawai.add(btn_hapusdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 140, -1));

        btn_simpandata.setBackground(new java.awt.Color(204, 204, 204));
        btn_simpandata.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        btn_simpandata.setForeground(new java.awt.Color(51, 51, 51));
        btn_simpandata.setText("Simpan Data");
        btn_simpandata.setToolTipText("Menyimpan data baru atau perubahan");
        btn_simpandata.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.gray, java.awt.Color.gray, java.awt.Color.darkGray));
        btn_simpandata.setContentAreaFilled(false);
        btn_simpandata.setFocusPainted(false);
        btn_simpandata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_simpandata.setRequestFocusEnabled(false);
        btn_simpandata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpandataActionPerformed(evt);
            }
        });
        panel_Pegawai.add(btn_simpandata, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, 140, -1));

        jtabelpegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pegawai", "Nama", "Jabatan", "Foto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtabelpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtabelpegawaiMouseClicked(evt);
            }
        });
        jtabelpegawai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtabelpegawaiFocusGained(evt);
            }
        });
        jScrollPane7.setViewportView(jtabelpegawai);

        panel_Pegawai.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 277, 730, 180));

        jLabel23.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel23.setText("Jam Kerja");
        panel_Pegawai.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 230, -1, 30));

        txt_IDPegawai.setBackground(new java.awt.Color(249, 249, 249));
        txt_IDPegawai.setEnabled(false);
        txt_IDPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_IDPegawaiActionPerformed(evt);
            }
        });
        panel_Pegawai.add(txt_IDPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 180, 30));

        txt_AddNamaPegawai.setBackground(new java.awt.Color(249, 249, 249));
        txt_AddNamaPegawai.setEnabled(false);
        panel_Pegawai.add(txt_AddNamaPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 230, 30));

        txt_foto.setBackground(new java.awt.Color(249, 249, 249));
        txt_foto.setEnabled(false);
        panel_Pegawai.add(txt_foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 190, 190, 30));
        panel_Pegawai.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        cmbJabatan.setEnabled(false);
        cmbJabatan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJabatanItemStateChanged(evt);
            }
        });
        panel_Pegawai.add(cmbJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 150, 180, 30));

        img_foto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        img_foto.setText("foto");
        panel_Pegawai.add(img_foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 180, 160));

        jLabel36.setFont(new java.awt.Font("Lucida G", 0, 13)); // NOI18N
        jLabel36.setText("Foto");
        panel_Pegawai.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, 30));

        Shift.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "D" }));
        Shift.setEnabled(false);
        Shift.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ShiftItemStateChanged(evt);
            }
        });
        Shift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShiftActionPerformed(evt);
            }
        });
        panel_Pegawai.add(Shift, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 50, 30));

        jPanel1.add(panel_Pegawai, "card10");

        panel_Laporan_Harian.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel30.setText("Data Absensi Pegawai Harian");
        panel_Laporan_Harian.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 24, -1, -1));
        panel_Laporan_Harian.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 24, -1, -1));

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jButton4.setText("Buka Laporan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        panel_Laporan_Harian.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, 181, 31));
        panel_Laporan_Harian.add(jcal_tanggalhadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(177, 85, -1, -1));

        jPanel1.add(panel_Laporan_Harian, "card2");

        jMenu1.setText("File");

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Pegawai");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Keluar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Absensi");

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Izin/Cuti");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Laporan");

        jMenuItem2.setText("Laporan Pegawai");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItem4.setText("Laporan Izin Pegawai");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

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
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        
        //Pengaturan panel yang tampil
        panel_Pegawai.setVisible(true);
        panel_about.setVisible(false);       
     
        panel_Laporan_Harian.setVisible(false);
        panel_izin.setVisible(false);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void cmbJabatanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJabatanItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbJabatanItemStateChanged

    private void txt_IDPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_IDPegawaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_IDPegawaiActionPerformed

    private void jtabelpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtabelpegawaiMouseClicked
        // TODO add your handling code here:

        //ubah keadaan field-field menjadi tidak berfungsi
        txt_IDPegawai.setEnabled(false);
        txt_AddNamaPegawai.setEnabled(false);
        cmbJabatan.setEnabled(false);
        txt_foto.setEnabled(false);
        Shift.setEnabled(false);
       
        
        //cek apakah salah satu baris sudah diklik
        int i = jtabelpegawai.getSelectedRow();
        if(i== -1){
            //tidak melakukan apa-apa
            return;
        }
        
        /*
          mengambil isi dari tabel yang di klik, lalu menampilkannya
          * di field-field
        */
        String nip = (String) tabelpegawai.getValueAt(i, 0);
        txt_IDPegawai.setText(nip);

        String nama = (String) tabelpegawai.getValueAt(i, 1);
        txt_AddNamaPegawai.setText(nama);

        Object jabatan = (Object) tabelpegawai.getValueAt(i, 2);
        cmbJabatan.setSelectedItem(jabatan);

        String foto = (String) tabelpegawai.getValueAt(i, 3);
        txt_foto.setText(foto);
        img_foto.setIcon((new javax.swing.ImageIcon("../foto/"+foto)));
        
        Object shift = (Object) tabelpegawai.getValueAt(i, 4);
        Shift.setSelectedItem(shift);
        

    }//GEN-LAST:event_jtabelpegawaiMouseClicked

    private void btn_simpandataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpandataActionPerformed
        // TODO add your handling code here:
        
        //inisialisasi variabel
        String id_pegawai = txt_IDPegawai.getText();
        String nama_pegawai = txt_AddNamaPegawai.getText();
        String jabatan_pegawai = "";

        switch(cmbJabatan.getSelectedIndex()){
            case 1: jabatan_pegawai = "B01"; break;
            case 2: jabatan_pegawai = "B02"; break;
            case 3: jabatan_pegawai = "B03"; break;
            case 4: jabatan_pegawai = "C01"; break;
            case 5: jabatan_pegawai = "C02"; break;
            case 6: jabatan_pegawai = "C03"; break;
            case 7: jabatan_pegawai = "C04"; break;
            case 8: jabatan_pegawai = "D01"; break;
            case 9: jabatan_pegawai = "D02"; break;
            default:jabatan_pegawai = "";break;
        }

        String foto_pegawai = txt_foto.getText();
        String shift_pegawai = "";
        switch(Shift.getSelectedIndex()){
           case 0: shift_pegawai = "1"; break;
           case 1: shift_pegawai = "2"; break;
           case 2: shift_pegawai = "3"; break;
           case 3: shift_pegawai = "D"; break; 
            
        }
        
        //logika agar semua field terisi
        if(id_pegawai.equals("") | nama_pegawai.equals("") | jabatan_pegawai.equals("")
            | foto_pegawai.equals("") ){
            JOptionPane.showMessageDialog(rootPane, "Harap isi semua field");
        }
        else{
            //panggil model, set model sesuai dgn variabel
            ModelPegawai modp = new ModelPegawai();
            modp.setIdpegawai(id_pegawai);
            modp.setNama(nama_pegawai);
            modp.setJabatan(jabatan_pegawai);
            modp.setFoto(foto_pegawai);    
            modp.setShift(shift_pegawai);
            
            //memulai mengkoneksikan menginputkan ke database
            try{
                ManajemenPegawai manp = new ManajemenPegawai();
                
                //Cek apakah pegawai tersebut ada di database
                //Jika NIP pegawai tersebut sudah ada di data, maka..
                if(manp.cekPegawai(modp) == true){
                    
                    //tampilkan peringatan bahwa data sudah data, dan mau diubah?
                    int konfirmasiUbah = JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin akan mengubah data Pegawai NIP "+modp.getIdpegawai()+" menjadi \nNama : "+modp.getNama()+" \nJabatan : "+modp.getJabatan()+" \nFoto : "+modp.getFoto()+" \nYakin ubah data pegawai ? ","Ubah Data Pegawai", JOptionPane.YES_NO_OPTION);
                    //jika user klik yes
                    if(konfirmasiUbah == JOptionPane.YES_OPTION){
                        //lakukan mekanisme update informasi kedatabase 
                        try{                            
                            manp.updatePegawai(modp);
                            JOptionPane.showMessageDialog(rootPane, "Data berhasil diubah", "Data berhasil diubah",JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch(SQLException ex){
                            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan mengubah data","Kesalahan Mengubah Data",JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        finally{
                            try {
                                loadTblPegawai();

                                txt_IDPegawai.setEnabled(false);
                                txt_IDPegawai.setText("");

                                txt_AddNamaPegawai.setEnabled(false);
                                txt_AddNamaPegawai.setText("");

                                cmbJabatan.setEnabled(false);
                                cmbJabatan.setSelectedIndex(0);

                                txt_foto.setEnabled(false);
                                txt_foto.setText("");
                                
                                Shift.setEnabled(false);
                                Shift.setSelectedIndex(0);

                            } catch (SQLException ex) {
                                
                                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                else{
                    //jika nip belum ada di database, maka 
                    //lakukan mekanisme menambah data baru di database
                    try{
                        manp.tambahPegawai(modp);
                        JOptionPane.showMessageDialog(rootPane, "Data berhasil ditambahkan", "Berhasil Menambahkan Dambahkan",JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan memasukan data pegawai baru","Kesalahan Menambah Data",JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                        try {
                            loadTblPegawai();

                            txt_IDPegawai.setEnabled(false);
                            txt_IDPegawai.setText("");

                            txt_AddNamaPegawai.setEnabled(false);
                            txt_AddNamaPegawai.setText("");

                            cmbJabatan.setEnabled(false);
                            cmbJabatan.setSelectedIndex(0);

                            txt_foto.setEnabled(false);
                            txt_foto.setText("");
                            
                            Shift.setEnabled(false);
                            Shift.setSelectedIndex(0);

                        } catch (SQLException ex) {
                            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
            }catch(SQLException ex){
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btn_simpandataActionPerformed

    private void btn_hapusdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusdataActionPerformed
        // TODO add your handling code here:
        int i = jtabelpegawai.getSelectedRow();
        if(i== -1){
            return;
        }
        String nip = (String) tabelpegawai.getValueAt(i, 0);
        String nama = (String) tabelpegawai.getValueAt(i, 1);
        
        ModelPegawai modp = new ModelPegawai();
        modp.setIdpegawai(nip);
        modp.setNama(nama);

        int konfirmasiHapus = JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin akan menghapus data pegawai "
            + "dengan \n NIP "+nip+" yang bernama "+nama+"? ", "Hapus Pegawai", JOptionPane.YES_NO_OPTION);
        if(konfirmasiHapus == JOptionPane.YES_OPTION){
            try{
                ManajemenPegawai manp = new ManajemenPegawai();
                manp.hapusPegawai(modp);
                JOptionPane.showMessageDialog(rootPane, "Data berhasil dihapus", "Berhasil Menghapus Data",JOptionPane.INFORMATION_MESSAGE);
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan menghapus data","Kesalahan Menghapus Data",JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    loadTblPegawai();
                    
                    txt_IDPegawai.setEnabled(false);
                    txt_IDPegawai.setText("");

                    txt_AddNamaPegawai.setEnabled(false);
                    txt_AddNamaPegawai.setText("");

                    cmbJabatan.setEnabled(false);
                    cmbJabatan.setSelectedIndex(0);

                    txt_foto.setEnabled(false);
                    txt_foto.setText("");

                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btn_hapusdataActionPerformed

    private void btn_ubahdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahdataActionPerformed
        // TODO add your handling code here:
        int i = jtabelpegawai.getSelectedRow();
        if(i== -1){
            JOptionPane.showMessageDialog(rootPane, "Tidak ada data pegawai yang dipilih");
        }

        txt_IDPegawai.setEnabled(false);
        txt_AddNamaPegawai.setEnabled(true);
        cmbJabatan.setEnabled(true);
        txt_foto.setEnabled(true);
        Shift.setEnabled(true);

        String nip = (String) tabelpegawai.getValueAt(i, 0);
        txt_IDPegawai.setText(nip);

        String nama = (String) tabelpegawai.getValueAt(i, 1);
        txt_AddNamaPegawai.setText(nama);

        Object jabatan = (Object) tabelpegawai.getValueAt(i, 2);
        cmbJabatan.setSelectedItem(jabatan);

        String foto = (String) tabelpegawai.getValueAt(i, 3);
        txt_foto.setText(foto);
        
        Object shift = (Object) tabelpegawai.getValueAt(i, 4);
        Shift.setSelectedItem(shift);

    }//GEN-LAST:event_btn_ubahdataActionPerformed

    private void btn_tambahpegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahpegawaiActionPerformed
        // TODO add your handling code here:

        txt_IDPegawai.setEnabled(true);
        txt_IDPegawai.setText("");
        txt_IDPegawai.requestFocus();

        txt_AddNamaPegawai.setEnabled(true);
        txt_AddNamaPegawai.setText("");

        cmbJabatan.setEnabled(true);
        cmbJabatan.setSelectedIndex(0);

        txt_foto.setEnabled(true);
        txt_foto.setText("");
        
        img_foto.setIcon(null);
        
        Shift.setEnabled(true);
        

    }//GEN-LAST:event_btn_tambahpegawaiActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        panel_izin.setVisible(true);
        panel_about.setVisible(false);       
        panel_Laporan_Harian.setVisible(false);
        panel_Pegawai.setVisible(false);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void radAbsen_izinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radAbsen_izinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radAbsen_izinActionPerformed

    private void txtAbsen_keteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbsen_keteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbsen_keteranganActionPerformed

    private void jtabelpegawaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtabelpegawaiFocusGained
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jtabelpegawaiFocusGained

    private void btnAbsen_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsen_simpanActionPerformed
        // TODO add your handling code here:
        
        String absen_nip = txtAbsen_nip.getText();
        String absen_tanggal = "";
        if(txtAbsen_tanggal.getDate() != null){
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        absen_tanggal = Format.format(txtAbsen_tanggal.getDate());
        }
        String absen_izin = "";
        
        if(jenis_absen.getSelection().equals(radAbsen_cuti.getModel())){
            absen_izin = "Cuti";
        }
        else if(jenis_absen.getSelection().equals(radAbsen_izin.getModel())){
            absen_izin = "Izin";
        }
        else if(jenis_absen.getSelection().equals(radAbsen_tidak.getModel())){
            absen_izin = "Tidak Masuk";
        }else{
            JOptionPane.showMessageDialog(rootPane, "Harap isi semua field");  
        }
        
        String absen_keterangan = txtAbsen_keterangan.getText();
        
        
        
        if( "".equals(absen_nip) | txtAbsen_tanggal.getDate()== null | jenis_absen.getSelection() == null | "".equals(absen_keterangan) ){
            JOptionPane.showMessageDialog(rootPane, "Harap isi semua field");               
        }else{
            
            int confirmSimpan = JOptionPane.showConfirmDialog(rootPane,"Apakah anda data tersebut sudah benar?","Mengimput data",JOptionPane.YES_NO_OPTION );
            if (confirmSimpan == JOptionPane.YES_OPTION){
            
            ModelAbsensi moda = new ModelAbsensi();
            
            moda.setNip(absen_nip);
            moda.setTanggal(absen_tanggal);
            moda.setAbsen(absen_izin);
            moda.setKeterangan(absen_keterangan);
            
            try{
                ManajemenAbsensi mana = new ManajemenAbsensi();
                if(mana.cekPegawai(moda) == true){
                    
                    if(mana.cekDuplikasi(moda) == true){
                        int ubahData = JOptionPane.showConfirmDialog(this, 
                                "Data tersebut sudah ada di database. \n"
                                + "apakah anda yakin mau merubahnya?", "Ubah data absen", 
                                JOptionPane.YES_NO_CANCEL_OPTION);
                        if(ubahData == JOptionPane.YES_OPTION){
                            try{
                                int no = Integer.parseInt(txtAbsen_no.getText());
                                moda.setNo(no);
                                mana.ubahAbsen(moda);   
                                JOptionPane.showMessageDialog(rootPane, "Data berhasil diubah", "Data berhasil diubah",JOptionPane.INFORMATION_MESSAGE);
                            }catch(SQLException ex){
                                JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan mengubah data","Kesalahan Mengubah data",JOptionPane.ERROR_MESSAGE);
                                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                            }finally{
                                loadTblAbsen();                               
                                
                            }
                            
                        }else if(ubahData == JOptionPane.NO_OPTION){
                            
                            txtAbsen_nip.setEnabled(false);

                            txtAbsen_tanggal.setEnabled(false);

                            radAbsen_izin.setEnabled(false);
                            radAbsen_cuti.setEnabled(false);
                            radAbsen_tidak.setEnabled(false);

                            txtAbsen_keterangan.setEnabled(false);
                        }
                        
                    }else{
                        try{
                            mana.tambahAbsen(moda);
                            JOptionPane.showMessageDialog(rootPane, "Data berhasil ditambah", "Data berhasil ditambah",JOptionPane.INFORMATION_MESSAGE);
                        }catch(SQLException ex){
                            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan menambahkan data","Kesalahan Menambah Data",JOptionPane.ERROR_MESSAGE);
                            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                        }finally{
                            loadTblAbsen();
                            
                            txtAbsen_no.setText("");
                            
                            txtAbsen_nip.setEnabled(false);
                            txtAbsen_nip.setText("");
                            txtAbsen_nip.requestFocus();

                            txtAbsen_tanggal.setEnabled(false);
                            txtAbsen_tanggal.setDate(null);

                            radAbsen_izin.setEnabled(false);
                            radAbsen_cuti.setEnabled(false);
                            radAbsen_tidak.setEnabled(false);
                            radAbsen_izin.setSelected(false);
                            radAbsen_cuti.setSelected(false);
                            radAbsen_tidak.setSelected(false);


                            txtAbsen_keterangan.setEnabled(false);
                            txtAbsen_keterangan.setText("");
                        }
                    }
                          
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Pegawai dengan nip "+moda.getNip()+" tidak ditemukan","Tidak ditemukan",JOptionPane.ERROR_MESSAGE);
                }
            }catch(SQLException ex){
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        }
    }//GEN-LAST:event_btnAbsen_simpanActionPerformed

    private void txtAbsen_tanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAbsen_tanggalPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtAbsen_tanggalPropertyChange

    private void txtAbsen_nipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbsen_nipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbsen_nipActionPerformed

    private void btnAbsen_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsen_tambahActionPerformed
        // TODO add your handling code here:
        
        txtAbsen_no.setText("");
        txtAbsen_nip.setEnabled(true);
        txtAbsen_nip.setText("");
        txtAbsen_nip.requestFocus();
        
        txtAbsen_tanggal.setEnabled(true);
        txtAbsen_tanggal.setDate(null);
        
        radAbsen_izin.setEnabled(true);
        radAbsen_cuti.setEnabled(true);
        radAbsen_tidak.setEnabled(true);
        radAbsen_izin.setSelected(false);
        radAbsen_cuti.setSelected(false);
        radAbsen_tidak.setSelected(false);
        
        
        txtAbsen_keterangan.setEnabled(true);
        txtAbsen_keterangan.setText("");
        img_foto_absen.setText("foto");
        img_foto_absen.setIcon(null);
        AutoNumber();
        
    }//GEN-LAST:event_btnAbsen_tambahActionPerformed

    private void btnAbsen_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsen_ubahActionPerformed
        // TODO add your handling code here:
        
        int i = jtabelabsen.getSelectedRow();
        if(i== -1 | "".equals(txtAbsen_no.getText())){
            JOptionPane.showMessageDialog(rootPane, "Tidak ada data absen yang dipilih");
            
            txtAbsen_no.setText("");
            
        }
        
        txtAbsen_nip.setEnabled(true);
        
        txtAbsen_tanggal.setEnabled(true);
        
        radAbsen_izin.setEnabled(true);
        radAbsen_cuti.setEnabled(true);
        radAbsen_tidak.setEnabled(true);
 
        txtAbsen_keterangan.setEnabled(true);
    }//GEN-LAST:event_btnAbsen_ubahActionPerformed

    private void btnAbsen_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsen_hapusActionPerformed
        // TODO add your handling code here:
        int i = jtabelabsen.getSelectedRow();
        if(i== -1){
            return;
        }
        
        int no = Integer.parseInt(txtAbsen_no.getText());
        ModelAbsensi moda = new ModelAbsensi();
        moda.setNo(no);

        int konfirmasiHapus = JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin akan menghapus data absensi tersebut?", "Hapus Pegawai", JOptionPane.YES_NO_OPTION);
        if(konfirmasiHapus == JOptionPane.YES_OPTION){
            try{
                ManajemenAbsensi mana = new ManajemenAbsensi();
                mana.hapusPegawai(moda);
                JOptionPane.showMessageDialog(rootPane, "Data berhasil dihapus", "Berhasil Menghapus Data",JOptionPane.INFORMATION_MESSAGE);
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan menghapus data","Kesalahan Menghapus Data",JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                try {
                    loadTblAbsen();
                    
                    txtAbsen_no.setText("");
                    txtAbsen_nip.setEnabled(false);
                    txtAbsen_nip.setText("");
        
                    txtAbsen_tanggal.setEnabled(false);
                    txtAbsen_tanggal.setDate(null);
                    
                    radAbsen_izin.setEnabled(false);
                    radAbsen_cuti.setEnabled(false);
                    radAbsen_tidak.setEnabled(false);
                    radAbsen_izin.setSelected(false);
                    radAbsen_cuti.setSelected(false);
                    radAbsen_tidak.setSelected(false);
                    
                    txtAbsen_keterangan.setEnabled(true);
                    txtAbsen_keterangan.setText("");

                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_btnAbsen_hapusActionPerformed

    private void jtabelabsenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtabelabsenMouseClicked
        // TODO add your handling code here:
        
        //ubah keadaan field-field menjadi tidak berfungsi
        txtAbsen_nip.setEnabled(false);      
        txtAbsen_tanggal.setEnabled(false);        
        radAbsen_izin.setEnabled(false);
        radAbsen_cuti.setEnabled(false);
        radAbsen_tidak.setEnabled(false);
        txtAbsen_keterangan.setEnabled(false);
        
        
        //cek apakah salah satu baris sudah diklik
        int i = jtabelabsen.getSelectedRow();
        if(i== -1){
            //tidak melakukan apa-apa
            return;
        }
        
        /*
          mengambil isi dari tabel yang di klik, lalu menampilkannya
          * di field-field
        */
        
        String No_rec_absen = (tabelabsen.getValueAt(i, 0)).toString();
        txtAbsen_no.setText(No_rec_absen);
        
        Date tanggal = ( Date )tabelabsen.getValueAt(i, 1);
        txtAbsen_tanggal.setDate(tanggal);
        
        String nip = (String) tabelabsen.getValueAt(i, 2);
        txtAbsen_nip.setText(nip);

        String cuti_izin = (String) tabelabsen.getValueAt(i, 5);
        if("Cuti".equals(cuti_izin)){
            radAbsen_cuti.setSelected(true);
        }else if ("Izin".equals(cuti_izin)){
            radAbsen_izin.setSelected(true);
        }
        else {
            radAbsen_tidak.setSelected(true);
        }
        String keterangan = (String) tabelabsen.getValueAt(i, 6);
        txtAbsen_keterangan.setText(keterangan);
        
        String foto = (String) tabelabsen.getValueAt(i, 7);
        
        img_foto_absen.setIcon((new javax.swing.ImageIcon("../foto/"+foto)));
        
    }//GEN-LAST:event_jtabelabsenMouseClicked

    private void txtAbsen_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbsen_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbsen_noActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = sdf.format(jcal_tanggalhadir.getDate());

        
        Connection con = KoneksiDB.getKoneksi();
            String NamaFile = "/report/reportharian.jasper";
            HashMap hash = new HashMap();
        try {
            hash.put("tanggalhadir", tanggal);
            runReportDefault(NamaFile, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        

    }//GEN-LAST:event_jButton4ActionPerformed

    private void ShiftItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ShiftItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ShiftItemStateChanged

    private void ShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShiftActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ShiftActionPerformed

    private void radAbsen_tidakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radAbsen_tidakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radAbsen_tidakActionPerformed

    private void radAbsen_cutiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radAbsen_cutiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radAbsen_cutiActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        try {
            File report = new File("src/report/report1.jrxml");
            JasDes = JRXmlLoader.load(report);
            param.clear();
            JasRep = JasperCompileManager.compileReport(JasDes);
             JasPri = JasperFillManager.fillReport(JasRep,param,konek.getKoneksi());
             JasperViewer.viewReport(JasPri,false);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed
public void AutoNumber() {
        try {

            java.sql.Connection line_konek = (com.mysql.jdbc.Connection) controller.KoneksiDB.getKoneksi();
            java.sql.Statement line_statemen = line_konek.createStatement();
            String query_bukaTabel = "select max(right(no,4)) as no_urut from tblabsen";
            java.sql.ResultSet line_result = line_statemen.executeQuery(query_bukaTabel);
            if (line_result.first() == false) {
                txtAbsen_no.setText("1");
            } else {
                line_result.last();
                int no = line_result.getInt(1) + 1;
                String nomor = String.valueOf(no);
                int oto = nomor.length();
                txtAbsen_no.setText(nomor);
            }
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
         try {
            File report = new File("src/report/report2.jrxml");
            JasDes = JRXmlLoader.load(report);
            param.clear();
            JasRep = JasperCompileManager.compileReport(JasDes);
             JasPri = JasperFillManager.fillReport(JasRep,param,konek.getKoneksi());
             JasperViewer.viewReport(JasPri,false);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Admin().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox Shift;
    private javax.swing.JButton btnAbsen_hapus;
    private javax.swing.JButton btnAbsen_simpan;
    private javax.swing.JButton btnAbsen_tambah;
    private javax.swing.JButton btnAbsen_ubah;
    private javax.swing.JButton btn_hapusdata;
    private javax.swing.JButton btn_simpandata;
    private javax.swing.JButton btn_tambahpegawai;
    private javax.swing.JButton btn_ubahdata;
    private javax.swing.JComboBox cmbJabatan;
    private javax.swing.JLabel img_foto;
    private javax.swing.JLabel img_foto_absen;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private com.toedter.calendar.JCalendar jcal_tanggalhadir;
    private javax.swing.ButtonGroup jenis_absen;
    private javax.swing.JTable jtabelabsen;
    private javax.swing.JTable jtabelpegawai;
    private javax.swing.JPanel panel_Laporan_Harian;
    private javax.swing.JPanel panel_Pegawai;
    private javax.swing.JPanel panel_about;
    private javax.swing.JPanel panel_izin;
    private javax.swing.JRadioButton radAbsen_cuti;
    private javax.swing.JRadioButton radAbsen_izin;
    private javax.swing.JRadioButton radAbsen_tidak;
    private javax.swing.JTextField txtAbsen_keterangan;
    private javax.swing.JTextField txtAbsen_nip;
    private javax.swing.JTextField txtAbsen_no;
    private com.toedter.calendar.JDateChooser txtAbsen_tanggal;
    private javax.swing.JTextField txt_AddNamaPegawai;
    private javax.swing.JTextField txt_IDPegawai;
    private javax.swing.JTextField txt_foto;
    // End of variables declaration//GEN-END:variables
}
