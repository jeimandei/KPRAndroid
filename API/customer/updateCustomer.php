<?php 
	session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id = $_POST['id_nsb'];
		$nama_nsb = $_POST['nama_nsb'];
		$ktp_nsb = $_POST['ktp_nsb'];
		$tmpt_lahir_nsb = $_POST['tmp_lahir_nsb'];
		$tgl_lahir_nsb = $_POST['tgl_lahir_nsb'];
		$hp_nsb = $_POST['hp_nsb'];
		$alamat_nsb = $_POST['alamat_nsb'];
		
		
		//import file koneksi database 
		require_once('../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_nasabah` SET 
		`nama_nsb` = '$nama_nsb', 
		`ktp_nsb` = '$ktp_nsb', 
		`tmpt_lahir_nsb` = '$tmpt_lahir_nsb', 
		`tgl_lahir_nsb` = '$tgl_lahir_nsb', 
		`hp_nsb` = '$hp_nsb', 
		`alamat_nsb` = '$alamat_nsb'
		WHERE `tb_nasabah`.`id_nsb` = $id";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Nasabah';
		}else{
			echo 'Gagal Update Data Nasabah';
		}
		
		mysqli_close($con);
	}
?>