/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.form;

import drivercardhostapp.HostAppHelper;
import drivercardhostapp.RSAAppletHelper;
import drivercardhostapp.model.Person;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.smartcardio.CardException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class ReviewCardInfoUI extends javax.swing.JFrame {

    private final File imageFile;
    private final Person person;

    public ReviewCardInfoUI(File imageFile, Person person) {
        super("XEM TRƯỚC");
        initComponents();
        this.imageFile = imageFile;
        this.person = person;

        asignInfoToText();

        asignAvatar();
    }

    private void asignAvatar() {
        if (imageFile == null) {
            image.setHorizontalAlignment(JTextField.CENTER);
            image.setText("Chưa cập nhật");
            return;
        }
        try {
            BufferedImage myPicture = ImageIO.read(this.imageFile);
            image.setIcon(new ImageIcon(myPicture));
        } catch (IOException ex) {
            Logger.getLogger(ReviewAvatarUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void asignInfoToText() {
        txtHoTen3.setText(person.getHoTen().toUpperCase());
        txtNS3.setText(person.getNgaySinh());
        txtCMND3.setText(person.getCmnd());
        txtGPLX3.setText(person.getGplx());
        txtPhuongTien3.setText(person.getPhuongTien());
        txtFailure3.setText(String.valueOf(person.getTotalFailure()));
        txtCardState3.setForeground(Color.GREEN);
        txtCardState3.setText("Đang hoạt động");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        txtHoTen3 = new javax.swing.JLabel();
        txtGPLX3 = new javax.swing.JLabel();
        txtNS3 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        txtCMND3 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        txtCardState3 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        txtFailure3 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        a3 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        txtPhuongTien3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        image = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel11.setBackground(new java.awt.Color(243, 209, 137));
        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel10.setBackground(new java.awt.Color(243, 209, 137));

        jLabel101.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel101.setText("Tình trạng thẻ:");

        txtHoTen3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtHoTen3.setText("Chưa cập nhật");

        txtGPLX3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtGPLX3.setText("Chưa cập nhật");

        txtNS3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtNS3.setText("Chưa cập nhật");

        jLabel102.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel102.setText("Ngày sinh: ");

        txtCMND3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtCMND3.setText("Chưa cập nhật");

        jLabel103.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel103.setText("CMND:");

        txtCardState3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtCardState3.setText("Chưa cập nhật");

        jLabel104.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel104.setText("Sỗ lỗi vi phạm:");

        txtFailure3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtFailure3.setText("Chưa cập nhật");

        jLabel105.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel105.setText("ID: ");

        a3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        a3.setText("Hạng:");

        jLabel106.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel106.setText("Họ tên: ");

        txtPhuongTien3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtPhuongTien3.setText("Chưa cập nhật");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(a3)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(jLabel104, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPhuongTien3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFailure3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel105)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGPLX3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel103)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCMND3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel102)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNS3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel101)
                            .addGap(18, 18, 18)
                            .addComponent(txtCardState3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel106)
                        .addGap(68, 68, 68)
                        .addComponent(txtHoTen3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105)
                    .addComponent(txtGPLX3))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel106)
                    .addComponent(txtHoTen3))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(txtNS3))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel103)
                    .addComponent(txtCMND3))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a3)
                    .addComponent(txtPhuongTien3))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFailure3)
                    .addComponent(jLabel104))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCardState3)
                    .addComponent(jLabel101))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(243, 209, 137));

        image.setBackground(new java.awt.Color(51, 204, 255));
        image.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        image.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(image, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(192, Short.MAX_VALUE))
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancel.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnCancel.setText("HỦY");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSave.setText("LƯU");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            if (HostAppHelper.getInstance().sendInfomation(person)) {
                if (imageFile != null) {
                    HostAppHelper.getInstance().uploadImage(imageFile, "jpg");
                }

                PublicKey publicKeys = RSAAppletHelper.getInstance(
                        HostAppHelper.getInstance().channel).getPublicKey();
                if (publicKeys != null) {
                    pushToMain();
                } else {
                    JOptionPane.showMessageDialog(null, "Khởi tạo thất bại");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Khởi tạo thất bại");
            }
        } catch (CardException ex) {
            Logger.getLogger(UpdateInfomationUI.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("LỖI KHỞI TẠO: " + ex);
            JOptionPane.showMessageDialog(null, "Xảy ra lỗi");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void pushToMain() {
        MainUI mainUI = new MainUI();
        this.setVisible(false);
        mainUI.setLocationRelativeTo(null);
        mainUI.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a;
    private javax.swing.JLabel a1;
    private javax.swing.JLabel a2;
    private javax.swing.JLabel a3;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel image;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel txtCMND;
    private javax.swing.JLabel txtCMND1;
    private javax.swing.JLabel txtCMND2;
    private javax.swing.JLabel txtCMND3;
    private javax.swing.JLabel txtCardState;
    private javax.swing.JLabel txtCardState1;
    private javax.swing.JLabel txtCardState2;
    private javax.swing.JLabel txtCardState3;
    private javax.swing.JLabel txtFailure;
    private javax.swing.JLabel txtFailure1;
    private javax.swing.JLabel txtFailure2;
    private javax.swing.JLabel txtFailure3;
    private javax.swing.JLabel txtGPLX;
    private javax.swing.JLabel txtGPLX1;
    private javax.swing.JLabel txtGPLX2;
    private javax.swing.JLabel txtGPLX3;
    private javax.swing.JLabel txtHoTen;
    private javax.swing.JLabel txtHoTen1;
    private javax.swing.JLabel txtHoTen2;
    private javax.swing.JLabel txtHoTen3;
    private javax.swing.JLabel txtNS;
    private javax.swing.JLabel txtNS1;
    private javax.swing.JLabel txtNS2;
    private javax.swing.JLabel txtNS3;
    private javax.swing.JLabel txtPhuongTien;
    private javax.swing.JLabel txtPhuongTien1;
    private javax.swing.JLabel txtPhuongTien2;
    private javax.swing.JLabel txtPhuongTien3;
    // End of variables declaration//GEN-END:variables
}
