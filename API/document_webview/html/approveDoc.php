<?php 
	session_start();
		//Mendapatkan Nilai Dari Variable 
		$id_dok = $_GET['id_dok'];
		
		//import file koneksi database 
		require_once('../../koneksi.php');
		
		//Membuat SQL Query
		$sql = "UPDATE `tb_dokumen` SET `status_dok` = 'approved' WHERE `tb_dokumen`.`id_dok` = $id_dok";
		mysqli_query($con,$sql);
		$_SESSION['modal'] = TRUE;
		
		//Meng-update Database 
		
		mysqli_close($con);
?>