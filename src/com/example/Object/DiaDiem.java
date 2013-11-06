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
	}

	public String id="";
	public String ten="";
	public String diaChi="";
	public String moTa="";
	public float diemDanhGia=0;
	public LatLng toaDo=new LatLng(0, 0);
	public ArrayList<BinhLuan> danhSachBinhLuan= new ArrayList<BinhLuan>();
	public ArrayList<ChiTietDichVu> danhSachDichVu=new ArrayList<ChiTietDichVu>();
	public Boolean isLiked=false;
	public Boolean isSaved=false;

}
