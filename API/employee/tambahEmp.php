<?php
session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		require_once('../koneksi.php');
		//Mendapatkan Nilai Variable

		$nama_emp = $_POST['nama_emp'];
		$role_emp = $_POST['role_emp'];
		$pass_emp =	md5($_POST['pass_emp']);
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO `tb_employee` (`id_emp`, `nama_emp`, `role_emp`, `pass_emp`) 
		VALUES (NULL, '$nama_emp', '$role_emp', '$pass_emp')";
		

		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Pegawai';
		}else{
			echo 'Gagal Menambahkan Pegawai';
		}
		mysqli_close($con);
	}
?>