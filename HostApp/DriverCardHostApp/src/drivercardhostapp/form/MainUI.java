/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.form;

import drivercardhostapp.HostAppHelper;
import drivercardhostapp.model.Person;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.CardException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Admin
 */
public final class MainUI extends javax.swing.JFrame {

    private Person person;

    public MainUI() {
        super("MÔ PHỎNG GIẤY PHÉP LÁI XE");
        initComponents();
        getInfomation();
        getImage();
    }

    public void getInfomation() {
        try {
            person = HostAppHelper.getInstance().getInfomation();

            asignInfoToText();

        } catch (CardException ex) {
            Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi");
        }
    }

    public void getImage() {
        BufferedImage imageBuf = HostAppHelper.getInstance().downloadImage();
        if (imageBuf != null) {
            image.setIcon(new ImageIcon(imageBuf));
        } else {
            image.setHorizontalAlignment(JTextField.CENTER);
            image.setText("Chưa cập nhật");
        }

    }

    public void updateFailure(boolean isBlocked) {
        getInfomation();
        if (isBlocked) {
            setStateCard(true);
        } else {
            txtFailure.setText(String.valueOf(person.getTotalFailure()));
        }

    }

    private void asignInfoToText() {
        txtHoTen.setText(person.getHoTen().toUpperCase());
        txtNS.setText(person.getNgaySinh());
        txtCMND.setText(person.getCmnd());
        txtGPLX.setText(person.getGplx());
        txtPhuongTien.setText(person.getPhuongTien());
        txtFailure.setText(String.valueOf(person.getTotalFailure()));

        switch (person.isIsBlocked()) {
            case 0:
                setStateCard(false);
                break;
            case 1:
                setStateCard(true);
                break;

        }

    }

    private void setStateCard(boolean isBlocked) {
        if (isBlocked) {
            btnUnlock.setEnabled(true);
            btnChangePIN.setEnabled(false);
            btnInit.setEnabled(false);
            btnLogError.setEnabled(false);
            btnSelectImage.setEnabled(false);
            btnBlock.setEnabled(false);
            txtCardState.setForeground(Color.RED);
            txtCardState.setText("Đã bị khóa");
        } else {
            btnUnlock.setEnabled(false);
            btnChangePIN.setEnabled(true);
            btnInit.setEnabled(true);
            btnLogError.setEnabled(true);
            btnSelectImage.setEnabled(true);
            btnBlock.setEnabled(true);
            txtCardState.setForeground(Color.GREEN);
            txtCardState.setText("Đang hoạt động");
        }
    }

    public class JPEGImageFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.getName().toLowerCase().endsWith(".jpeg")) {
                return true;
            }
            if (f.getName().toLowerCase().endsWith(".jpg")) {
                return true;
            }
            return f.isDirectory();
        }

        @Override
        public String getDescription() {
            return "JPEG files";
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnChangePIN = new javax.swing.JButton();
        btnInit = new javax.swing.JButton();
        btnLogError = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnBlock = new javax.swing.JButton();
        btnUnlock = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JLabel();
        txtGPLX = new javax.swing.JLabel();
        txtNS = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        txtCMND = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        txtCardState = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        txtFailure = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        a = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        txtPhuongTien = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnSelectImage = new javax.swing.JButton();
        image = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnChangePIN.setBackground(new java.awt.Color(153, 102, 0));
        btnChangePIN.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnChangePIN.setForeground(new java.awt.Color(255, 255, 255));
        btnChangePIN.setText("ĐỔI MÃ PIN");
        btnChangePIN.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnChangePIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePINActionPerformed(evt);
            }
        });

        btnInit.setBackground(new java.awt.Color(153, 102, 0));
        btnInit.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnInit.setForeground(new java.awt.Color(255, 255, 255));
        btnInit.setText("CẬP NHẬT THÔNG TIN");
        btnInit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInitActionPerformed(evt);
            }
        });

        btnLogError.setBackground(new java.awt.Color(153, 102, 0));
        btnLogError.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLogError.setForeground(new java.awt.Color(255, 255, 255));
        btnLogError.setText("GHI LỖI");
        btnLogError.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogErrorActionPerformed(evt);
            }
        });

        btnLogout.setBackground(new java.awt.Color(204, 0, 51));
        btnLogout.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setText("ĐĂNG XUẤT");
        btnLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnBlock.setBackground(new java.awt.Color(153, 102, 0));
        btnBlock.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnBlock.setForeground(new java.awt.Color(255, 255, 255));
        btnBlock.setText("KHÓA THẺ");
        btnBlock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBlockActionPerformed(evt);
            }
        });

        btnUnlock.setBackground(new java.awt.Color(153, 102, 0));
        btnUnlock.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUnlock.setForeground(new java.awt.Color(255, 255, 255));
        btnUnlock.setText("MỞ KHÓA THẺ");
        btnUnlock.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUnlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnlockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChangePIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUnlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInit)
                            .addComponent(btnLogError, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnInit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnLogError, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnChangePIN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnBlock, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnUnlock, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(243, 209, 137));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBackground(new java.awt.Color(243, 209, 137));

        jLabel88.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel88.setText("Tình trạng thẻ:");

        txtHoTen.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtHoTen.setText("Chưa cập nhật");

        txtGPLX.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtGPLX.setText("Chưa cập nhật");

        txtNS.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtNS.setText("Chưa cập nhật");

        jLabel85.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel85.setText("Ngày sinh: ");

        txtCMND.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtCMND.setText("Chưa cập nhật");

        jLabel86.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel86.setText("CMND:");

        txtCardState.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtCardState.setText("Chưa cập nhật");

        jLabel84.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel84.setText("Sỗ lỗi vi phạm:");

        txtFailure.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtFailure.setText("Chưa cập nhật");

        jLabel87.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel87.setText("ID: ");

        a.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        a.setText("Hạng:");

        jLabel83.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel83.setText("Họ tên: ");

        txtPhuongTien.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtPhuongTien.setText("Chưa cập nhật");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(a)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPhuongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFailure, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel87)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGPLX, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel86)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel85)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNS, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel88)
                            .addGap(18, 18, 18)
                            .addComponent(txtCardState, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel83)
                        .addGap(68, 68, 68)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel87)
                    .addComponent(txtGPLX))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel83)
                    .addComponent(txtHoTen))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel85)
                    .addComponent(txtNS))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel86)
                    .addComponent(txtCMND))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a)
                    .addComponent(txtPhuongTien))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFailure)
                    .addComponent(jLabel84))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCardState)
                    .addComponent(jLabel88))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(243, 209, 137));

        btnSelectImage.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnSelectImage.setText("Chọn ảnh");
        btnSelectImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectImageActionPerformed(evt);
            }
        });

        image.setBackground(new java.awt.Color(51, 204, 255));
        image.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnSelectImage)
                    .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSelectImage)
                .addContainerGap())
        );

        jLabel81.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(204, 0, 51));
        jLabel81.setText("GIẤY PHÉP LÁI XE");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(270, 270, 270))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(39, 39, 39)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Thông tin", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInitActionPerformed
        UpdateInfomationUI iiui;
        if (this.person == null) {
            iiui = new UpdateInfomationUI(this);
        } else {
            iiui = new UpdateInfomationUI(this, this.person);
        }
        iiui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        iiui.setLocationRelativeTo(null);
        iiui.setVisible(true);
    }//GEN-LAST:event_btnInitActionPerformed

    private void btnSelectImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectImageActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new JPEGImageFileFilter());
        jfc.showOpenDialog(this);
        File file = jfc.getSelectedFile();

        if (file != null) {
            if (file.length() > 10000) {
                JOptionPane.showMessageDialog(null, "Kích thước quá lớn. Vui lòng chọn ảnh khác!");
                return;
            }
            ReviewAvatarUI avatarUI = new ReviewAvatarUI(file, this);
            avatarUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            avatarUI.setLocationRelativeTo(null);
            avatarUI.setVisible(true);
        }


    }//GEN-LAST:event_btnSelectImageActionPerformed

    private void btnChangePINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePINActionPerformed
        UpdatePINUI updatePINUI = new UpdatePINUI();
        updatePINUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updatePINUI.setLocationRelativeTo(null);
        updatePINUI.setVisible(true);
    }//GEN-LAST:event_btnChangePINActionPerformed

    private void btnUnlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnlockActionPerformed
        int input = JOptionPane.showOptionDialog(
                null,
                "Xác nhận mở khóa thẻ?", "MỞ KHÓA THẺ",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            try {
                if (HostAppHelper.getInstance().unblockInfo()) {
                    getInfomation();
                }
            } catch (CardException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi");
            }
        }
    }//GEN-LAST:event_btnUnlockActionPerformed

    private void btnBlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBlockActionPerformed
        int input = JOptionPane.showOptionDialog(
                null,
                "Xác nhận khóa thẻ?", "KHÓA THẺ",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            try {
                if (HostAppHelper.getInstance().blockInfo()) {
                    getInfomation();
                }
            } catch (CardException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi");
            }
        }
    }//GEN-LAST:event_btnBlockActionPerformed

    private void btnLogErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogErrorActionPerformed
        LogFailureUI errorUI = new LogFailureUI(this);
        errorUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorUI.setLocationRelativeTo(null);
        errorUI.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_btnLogErrorActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        int input = JOptionPane.showOptionDialog(
                null,
                "Đăng xuất thẻ?", "ĐĂNG XUẤT",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (input == JOptionPane.OK_OPTION) {
            this.setVisible(false);
            EnterPinGUI enterPinGUI = new EnterPinGUI();
            enterPinGUI.setLocationRelativeTo(null);
            enterPinGUI.setVisible(true);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a;
    private javax.swing.JButton btnBlock;
    private javax.swing.JButton btnChangePIN;
    private javax.swing.JButton btnInit;
    private javax.swing.JButton btnLogError;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSelectImage;
    private javax.swing.JButton btnUnlock;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel txtCMND;
    private javax.swing.JLabel txtCardState;
    private javax.swing.JLabel txtFailure;
    private javax.swing.JLabel txtGPLX;
    private javax.swing.JLabel txtHoTen;
    private javax.swing.JLabel txtNS;
    private javax.swing.JLabel txtPhuongTien;
    // End of variables declaration//GEN-END:variables
}
