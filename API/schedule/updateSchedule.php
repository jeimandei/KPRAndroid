<?php 
	session_start();
	if($_SERVER['REQUEST_METHOD']=='POST'){
		//Mendapatkan Nilai Dari Variable 
		$id_jantem = $_POST['id_jantem'];
		$id_nsb = $_POST['id_nsb'];
		$pesan_jantem = $_POST['pesan_jantem'];
		$tgl_jantem = $_POST['tgl_jantem'];
		// $pass_emp =	md5($_POST['pass_emp']);
		
		//import file koneksi database 
		require_once('../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_janji_temu` 
		SET `id_nsb` = '$id_nsb', 
		`pesan_jantem` = '$pesan_jantem', 
		`tgl_jantem` = '$tgl_jantem' 
		WHERE `tb_janji_temu`.`id_jantem` = $id_jantem";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Janji Temu';
		}else{
			echo 'Gagal Update Data Janji Temu';
		}
		
		mysqli_close($con);
	}
?>