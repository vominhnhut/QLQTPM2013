package com.example.Object;

import java.io.Serializable;
import java.util.Date;

public class BinhLuan implements Serializable {

	public BinhLuan() {
		// TODO Auto-generated constructor stub
	}

	private String id;
	private String tenNguoiDang;
	private Date  thoiGianDang;
	private String noiDung;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTenNguoiDang() {
		return tenNguoiDang;
	}
	public void setTenNguoiDang(String tenNguoiDang) {
		this.tenNguoiDang = tenNguoiDang;
	}
	public Date getThoiGianDang() {
		return thoiGianDang;
	}
	public void setThoiGianDang(Date thoiGianDang) {
		this.thoiGianDang = thoiGianDang;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	
	
}
