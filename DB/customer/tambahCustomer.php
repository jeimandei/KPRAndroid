<?php
session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		require_once('../koneksi.php');
		//Mendapatkan Nilai Variable

		$nama_nsb = $_POST['nama_nsb'];
		$ktp_nsb = $_POST['ktp_nsb'];
		$tmpt_lahir_nsb = $_POST['tmp_lahir_nsb'];
		$tgl_lahir_nsb = $_POST['tgl_lahir_nsb'];
		$hp_nsb = $_POST['hp_nsb'];
		$alamat_nsb = $_POST['alamat_nsb'];
		$id_emp = $_POST['id_emp'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO `tb_nasabah` 
		(`id_nsb`, `nama_nsb`, `ktp_nsb`, `tmpt_lahir_nsb`, `tgl_lahir_nsb`, `hp_nsb`, `alamat_nsb`, `id_emp`) 
		VALUES 
		(NULL, '$nama_nsb', '$ktp_nsb', '$tmpt_lahir_nsb', '$tgl_lahir_nsb', '$hp_nsb', '$alamat_nsb', '$id_emp')";
		

		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Data Nasabah';
		}else{
			echo 'Gagal Menambahkan Data Nasabah';
		}
		mysqli_close($con);
	}
?>