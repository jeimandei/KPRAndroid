<?php 
	session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id_dok = $_POST['id_dok'];
		$nama_dok = $_POST['nama_dok'];
		$tipe_dok = $_POST['tipe_dok'];
		$status_dok = $_POST['status_dok'];
		
		//import file koneksi database 
		require_once('../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_dokumen` 
		SET `tipe_dok` = '$id_dok', 
		`nama_dok` = '$nama_dok', 
		`status_dok` = '$status_dok' 
		WHERE `tb_dokumen`.`id_dok` = $id_dok";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Dokumen Nasabah';
		}else{
			echo 'Gagal Update Data Dokumen Nasabah';
		}
		
		mysqli_close($con);
	}
?>