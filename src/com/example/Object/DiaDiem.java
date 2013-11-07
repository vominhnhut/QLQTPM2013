package com.example.Object;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class DiaDiem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DiaDiem() {
		// TODO Auto-generated constructor stub
		id="";
		ten="";
		diaChi="";
		moTa="";
		diemDanhGia=0;
		//toaDo=new LatLng(0, 0);
		latitude = 0;
		longitude = 0;
		danhSachBinhLuan= new ArrayList<BinhLuan>();
		danhSachDichVu=new ArrayList<ChiTietDichVu>();
		isLiked=false;
		isSaved=false;

	}

	public String id;
	public String ten;
	public String diaChi;
	public String moTa;
	public float diemDanhGia;
	public double latitude;
	public double longitude;
	public ArrayList<BinhLuan> danhSachBinhLuan;
	public ArrayList<ChiTietDichVu> danhSachDichVu;
	public Boolean isLiked;
	public Boolean isSaved;

	public LatLng getLatLng(){
		return new LatLng(latitude, longitude);
	}
}
