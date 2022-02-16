<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	$id = $_GET['id_emp'];

	//Membuat SQL Query
	$sql = "SELECT * FROM `tb_dokumen` 
	JOIN tb_nasabah ON tb_dokumen.id_nsb = tb_nasabah.id_nsb WHERE id_emp = $id;";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_dok"=>$row['id_dok'],
			"id_nsb"=>$row['id_nsb'],
			"tipe_dok"=>$row['tipe_dok'],
			"nama_dok"=>$row['nama_dok'],
			"status_dok"=>$row['status_dok'],
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>