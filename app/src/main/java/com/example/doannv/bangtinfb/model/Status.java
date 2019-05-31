package com.example.doannv.bangtinfb.model;

public class Status {
    public int ID;
    public String Name;
    public String Anh;
    public int Phut;
    public int Gio;
    public int Ngay;
    public int Thang;
    public int Nam;
    public String Status;
    public int IDtaikhoan;
    public String Imgstatus;
    public int IDtaikhoanOnLine;

    public Status(int ID, String name, String anh, int phut, int gio, int ngay, int thang, int nam, String status, int IDtaikhoan, String imgstatus, int IDtaikhoanOnLine) {
        this.ID = ID;
        Name = name;
        Anh = anh;
        Phut = phut;
        Gio = gio;
        Ngay = ngay;
        Thang = thang;
        Nam = nam;
        Status = status;
        this.IDtaikhoan = IDtaikhoan;
        Imgstatus = imgstatus;
        this.IDtaikhoanOnLine = IDtaikhoanOnLine;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public int getPhut() {
        return Phut;
    }

    public void setPhut(int phut) {
        Phut = phut;
    }

    public int getGio() {
        return Gio;
    }

    public void setGio(int gio) {
        Gio = gio;
    }

    public int getNgay() {
        return Ngay;
    }

    public void setNgay(int ngay) {
        Ngay = ngay;
    }

    public int getThang() {
        return Thang;
    }

    public void setThang(int thang) {
        Thang = thang;
    }

    public int getNam() {
        return Nam;
    }

    public void setNam(int nam) {
        Nam = nam;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getIDtaikhoan() {
        return IDtaikhoan;
    }

    public void setIDtaikhoan(int IDtaikhoan) {
        this.IDtaikhoan = IDtaikhoan;
    }

    public String getImgstatus() {
        return Imgstatus;
    }

    public void setImgstatus(String imgstatus) {
        Imgstatus = imgstatus;
    }

    public int getIDtaikhoanOnLine() {
        return IDtaikhoanOnLine;
    }

    public void setIDtaikhoanOnLine(int IDtaikhoanOnLine) {
        this.IDtaikhoanOnLine = IDtaikhoanOnLine;
    }
}
