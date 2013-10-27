package com.example.Object;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class DiaDiem implements Serializable {

	public DiaDiem() {
		// TODO Auto-generated constructor stub
	}

	private String id;
	private String ten;
	private String diaChi;
	private String moTa;
	private float diemDanhGia;
	private LatLng toaDo;
	private ArrayList<BinhLuan> danhSachBinhLuan;
	private ArrayList<ChiTietDichVu> danhSachDichVu;
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
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public float getDiemDanhGia() {
		return diemDanhGia;
	}
	public void setDiemDanhGia(float diemDanhGia) {
		this.diemDanhGia = diemDanhGia;
	}
	public LatLng getToaDo() {
		return toaDo;
	}
	public void setToaDo(LatLng toaDo) {
		this.toaDo = toaDo;
	}
	public ArrayList<BinhLuan> getDanhSachBinhLuan() {
		return danhSachBinhLuan;
	}
	public void setDanhSachBinhLuan(ArrayList<BinhLuan> danhSachBinhLuan) {
		this.danhSachBinhLuan = danhSachBinhLuan;
	}
	public ArrayList<ChiTietDichVu> getDanhSachDichVu() {
		return danhSachDichVu;
	}
	public void setDanhSachDichVu(ArrayList<ChiTietDichVu> danhSachDichVu) {
		this.danhSachDichVu = danhSachDichVu;
	}
	
	
}
