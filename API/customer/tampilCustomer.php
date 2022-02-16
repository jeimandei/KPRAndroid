<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	
	$id_nsb = $_GET['id_nsb'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	// revisi get data by id_emp dan id_janji temu
	$sql = "SELECT * FROM tb_nasabah WHERE id_nsb = $id_nsb";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id_nsb"=>$row['id_nsb'],
			"nama_nsb"=>$row['nama_nsb'],
            "ktp_nsb"=>$row['ktp_nsb'],
            "tmpt_lahir_nsb"=>$row['tmpt_lahir_nsb'],
            "tgl_lahir_nsb"=>$row['tgl_lahir_nsb'],
            "hp_nsb"=>$row['hp_nsb'],
            "alamat_nsb"=>$row['alamat_nsb'],
            "id_emp"=>$row['id_emp']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>