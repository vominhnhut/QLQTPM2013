package com.example.clientmanager;

import java.util.ArrayList;

import com.example.Object.BinhLuan;
import com.example.Object.ChiTietDichVu;
import com.example.Object.DiaDiem;

public class ClientManager {
	
	public static ArrayList<DiaDiem> RequestToGetListDiaDiemYeuThich(String userID)
	{
		 ArrayList<DiaDiem> listDiaDiem= new  ArrayList<DiaDiem>();
		 
		 
		 return listDiaDiem;
	}
	
	public static ArrayList<BinhLuan> RequestToGetListBinhLuan(String diadiemID)
	{
		 ArrayList<BinhLuan> listBinhLuan= new  ArrayList<BinhLuan>();
		 
		 
		 return listBinhLuan;
	}
	
	public static ArrayList<ChiTietDichVu> RequestToGetListChiTietDichVu(String diadiemID)
	{
		 ArrayList<ChiTietDichVu> listChiTietDichVu= new  ArrayList<ChiTietDichVu>();
		 
		 
		 return listChiTietDichVu;
	}
	
	public static DiaDiem RequestToGetDiaDiemChiTiet(String diadiemID)
	{
		DiaDiem diadiem = new DiaDiem();
		return diadiem;
	}

}
