/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.form;

import drivercardhostapp.HostAppHelper;
import drivercardhostapp.RSAAppletHelper;
import drivercardhostapp.commons.constants.AUTH_CONSTANTS;
import drivercardhostapp.commons.constants.ISO7816;
import drivercardhostapp.commons.utils.RSAData;
import drivercardhostapp.commons.utils.RandomUtil;
import drivercardhostapp.model.Person;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.CardException;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class LogFailureUI extends javax.swing.JFrame {

    private final MainUI mainUI;

    /**
     * Creates new form LogErrorUI
     *
     * @param mainUI
     */
    public LogFailureUI(MainUI mainUI) {
        initComponents();
        this.mainUI = mainUI;
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbLoi = new javax.swing.JComboBox();
        btnXacNhan = new javax.swing.JButton();
        tfPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GHI LỖI");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Chọn loại lỗi:");

        cbLoi.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbLoi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vượt đèn đỏ", "Đi sai làn", "Đi ngược chiều", "Quá tốc độ", "Uống rượu bia lái xe (Nặng)" }));

        btnXacNhan.setText("XÁC NHẬN");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Mật khẩu:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGap(96, 96, 96))
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbLoi, 0, 187, Short.MAX_VALUE)
                    .addComponent(btnXacNhan)
                    .addComponent(tfPassword))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbLoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addComponent(btnXacNhan)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed

        switch (cbLoi.getSelectedIndex()) {
            case 0:
            case 1:
            case 2:
            case 3: {
                int input = JOptionPane.showOptionDialog(
                        null,
                        "Xác nhận lưu lỗi vi phạm?", "GHI LỖI",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION && verifyPIN()) {
                    if (rsaAuthentication()) {
                        try {
                            if (HostAppHelper.getInstance().getTotalFailure() < 3) {
                                if (HostAppHelper.getInstance().incrementFailure()) {
                                    mainUI.updateFailure(false);
                                    this.setVisible(false);
                                } else {
                                    showFailureDialog();
                                }
                            } else {
                                if (HostAppHelper.getInstance().blockInfo()
                                        && HostAppHelper.getInstance().incrementFailure()) {
                                    mainUI.updateFailure(true);
                                    JOptionPane.showMessageDialog(null, "Bạn đã vi phạm quá nhiều. Thẻ sẽ bị khóa!");
                                    this.setVisible(false);
                                } else {
                                    showFailureDialog();
                                }
                            }
                        } catch (CardException ex) {
                            Logger.getLogger(LogFailureUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Xác thực RSA thất bại");
                    }
                }
            }
            break;
            case 4: {
                int input = JOptionPane.showOptionDialog(
                        null,
                        "Lỗi quá nặng, thẻ sẽ bị khóa?", "GHI LỖI",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION && verifyPIN()) {
                    if (rsaAuthentication()) {
                        try {
                            if (HostAppHelper.getInstance().blockInfo()
                                    && HostAppHelper.getInstance().incrementFailure()) {
                                mainUI.updateFailure(true);
                                this.setVisible(false);
                            } else {
                                showFailureDialog();
                            }
                        } catch (CardException ex) {
                            Logger.getLogger(LogFailureUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Xác thực RSA thất bại");
                    }
                }
            }
            break;

        }

    }//GEN-LAST:event_btnXacNhanActionPerformed

    private boolean rsaAuthentication() {
        try {
            PublicKey publicKeys = RSAAppletHelper.getInstance(
                    HostAppHelper.getInstance().channel).getPublicKey();
            if (publicKeys == null) {
                return false;
            }
            System.out.println("publicKeys: " + Arrays.toString(publicKeys.getEncoded()));
            byte[] data = RandomUtil.randomData(20);

            byte[] signed = RSAAppletHelper.getInstance(
                    HostAppHelper.getInstance().channel).requestSign(data);

            if (signed == null) {
                return false;
            }

            System.out.println("signed: " + Arrays.toString(signed));

            return RSAData.verify(publicKeys, signed, data);
        } catch (CardException ex) {
            Logger.getLogger(LogFailureUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private boolean verifyPIN() {
        char[] pinText = tfPassword.getPassword();

        if (pinText.length == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã PIN");
            return false;
        }

        try {
            String check = HostAppHelper.getInstance().enterPin(pinText);
            System.out.println(check);

            switch (check) {
                case ISO7816.SW_NO_ERROR:
                    return true;
                case AUTH_CONSTANTS.SW_WRONG_PIN_LEN:
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng 6 ký tự mã PIN");
                    return false;
                case AUTH_CONSTANTS.SW_OVER_TRY_TIMES:
                    JOptionPane.showMessageDialog(null, "Qúa số lần thử. Tài khoản đã bị khóa !");
                    this.setVisible(false);
                    this.mainUI.setVisible(false);
                    EnterPinGUI enterPinGUI = new EnterPinGUI();
                    enterPinGUI.setLocationRelativeTo(null);
                    enterPinGUI.setVisible(true);
                    return false;
                case AUTH_CONSTANTS.SW_VERIFICATION_FAILED:
                    JOptionPane.showMessageDialog(null, "Sai mã PIN");
                    return false;
            }
        } catch (CardException ex) {
            Logger.getLogger(EnterPinGUI.class.getName()).log(Level.SEVERE, null, ex);
            showFailureDialog();
        }
        return false;
    }

    private void showFailureDialog() {
        JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JComboBox cbLoi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField tfPassword;
    // End of variables declaration//GEN-END:variables
}
