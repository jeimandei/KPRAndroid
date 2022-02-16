<?php
session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		require_once('../koneksi.php');
		//Mendapatkan Nilai Variable

		$id_nsb = $_POST['id_nsb'];
		$tipe_dok = $_POST['tipe_dok'];
		$nama_dok = $_POST['nama_dok'];
		$status_dok = $_POST['status_dok'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO `tb_dokumen` 
		(`id_dok`, `id_nsb`, `tipe_dok`, `nama_dok`, `status_dok`) 
		VALUES 
		(NULL, '$id_nsb', '$tipe_dok', '$nama_dok', '$status_dok')";
		

		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Dokumen Nasabah';
		}else{
			echo 'Gagal Menambahkan Dokumen Nasabah';
		}
		mysqli_close($con);
	}
?>