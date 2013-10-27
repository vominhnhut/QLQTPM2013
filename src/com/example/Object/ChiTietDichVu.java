package com.example.Object;

import java.io.Serializable;

public class ChiTietDichVu implements Serializable {

	public ChiTietDichVu() {
		// TODO Auto-generated constructor stub
	}

	private String id;
	private String ten;
	private String donGia;
	private String moTa;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getDonGia() {
		return donGia;
	}
	public void setDonGia(String donGia) {
		this.donGia = donGia;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	
}
