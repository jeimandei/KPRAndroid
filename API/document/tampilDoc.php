<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id = $_GET['id_dok'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT * FROM `tb_dokumen`
	JOIN tb_nasabah ON tb_dokumen.id_nsb = tb_nasabah.id_nsb
    WHERE id_emp = $id_dok;";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
		array_push($result,array(
			"id_dok"=>$row['id_dok'],
			"id_nsb"=>$row['id_nsb'],
			"tipe_dok"=>$row['tipe_dok'],
			"nama_dok"=>$row['nama_dok'],
			"status_dok"=>$row['status_dok'],
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>