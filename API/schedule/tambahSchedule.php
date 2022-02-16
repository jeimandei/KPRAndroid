<?php
session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		require_once('../koneksi.php');
		//Mendapatkan Nilai Variable

		$id_nsb = $_POST['id_nsb'];
		$pesan_jantem = $_POST['pesan_jantem'];
		$tgl_jantem = $_POST['tgl_jantem'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO `tb_janji_temu` 
		(`id_jantem`, `id_nsb`, `pesan_jantem`, `tgl_jantem`) 
		VALUES 
		(NULL, '$id_nsb', '$pesan_jantem', '$tgl_jantem')";
		

		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Data Janji Temu';
		}else{
			echo 'Gagal Menambahkan Data Janji Temu';
		}
		mysqli_close($con);
	}
?>