<?php 
	session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id_emp = $_POST['id_emp'];
		$nama_emp = $_POST['nama_emp'];
		$role_emp = $_POST['role_emp'];
		// $pass_emp =	md5($_POST['pass_emp']);
		
		//import file koneksi database 
		require_once('../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_employee` 
			SET 
			`nama_emp` = '$nama_emp', 
			`role_emp` = '$role_emp'
			WHERE `tb_employee`.`id_emp` = $id_emp";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Pegawai';
		}else{
			echo 'Gagal Update Data Pegawai';
		}
		
		mysqli_close($con);
	}
?>