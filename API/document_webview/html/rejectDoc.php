<?php 
	session_start();
		//Mendapatkan Nilai Dari Variable 
		$id_dok = $_GET['id_dok'];
		$pesan_dok = $_GET['pesan_dok'];
		$id_nsb = $_GET['id_nsb'];
		//import file koneksi database 
		require_once('../../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_dokumen` SET `pesan_dok` = '$pesan_dok', `status_dok` = 'rejected' WHERE `tb_dokumen`.`id_dok` = $id_dok";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			$_SESSION['modal_reject'] = TRUE;
			header('location:approval_manager.php?id_nsb='.$id_nsb);
		}else{
			echo 'Gagal Update Data Dokumen Nasabah';
		}
		
		mysqli_close($con);
?>