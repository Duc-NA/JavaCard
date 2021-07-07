/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivercardhostapp.model;

import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class Person {

    private String hoTen, ngaySinh, cmnd, gplx, phuongTien, image;
    private int totalFailure;
    private int isBlocked;

    public Person(String hoTen, String ngaySinh, String cmnd, String gplx, String phuongTien) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.cmnd = cmnd;
        this.gplx = gplx;
        this.phuongTien = phuongTien;
    }

    public Person(byte[] buf) {
        int offSet = 0;
        int tempLen = buf[offSet];

        offSet++;
        this.hoTen = new String(Arrays.copyOfRange(buf, offSet, offSet + tempLen));

        offSet += tempLen;
        tempLen = buf[offSet];
        offSet++;
        this.ngaySinh = new String(Arrays.copyOfRange(buf, offSet, offSet + tempLen));

        offSet += tempLen;
        tempLen = buf[offSet];
        offSet++;
        this.cmnd = new String(Arrays.copyOfRange(buf, offSet, offSet + tempLen));

        offSet += tempLen;
        tempLen = buf[offSet];
        offSet++;
        this.gplx = new String(Arrays.copyOfRange(buf, offSet, offSet + tempLen));

        offSet += tempLen;
        tempLen = buf[offSet];
        offSet++;
        this.phuongTien = new String(Arrays.copyOfRange(buf, offSet, offSet + tempLen));
    }
    
    public void setNewInfo(Person person){
        this.hoTen = person.hoTen;
        this.cmnd = person.cmnd;
        this.ngaySinh = person.ngaySinh;
        this.phuongTien = person.phuongTien;
    }

    public int isIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public int getTotalFailure() {
        return totalFailure;
    }

    public void setTotalFailure(int totalFailure) {
        this.totalFailure = totalFailure;
    }

    @Override
    public String toString() {
        return this.hoTen + "!" + this.ngaySinh + "!" + this.cmnd + "!" + this.gplx + "!" + this.phuongTien;
    }

    public byte[] toBytes() {
        return this.toString().getBytes();
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getGplx() {
        return gplx;
    }

    public void setGplx(String gplx) {
        this.gplx = gplx;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public void setPhuongTien(String phuongTien) {
        this.phuongTien = phuongTien;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
