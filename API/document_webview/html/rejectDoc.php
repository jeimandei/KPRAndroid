<?php 
	session_start();
		//Mendapatkan Nilai Dari Variable 
		$id_dok = $_GET['id_dok'];
		
		//import file koneksi database 
		require_once('../../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_dokumen` SET `status_dok` = 'rejected' WHERE `tb_dokumen`.`id_dok` = $id_dok";
		
		//Meng-update Database 
		if(mysqli_query($con,$sql)){
			$_SESSION['modal_reject'] = TRUE;
		}else{
			echo 'Gagal Update Data Dokumen Nasabah';
		}
		
		mysqli_close($con);
?>