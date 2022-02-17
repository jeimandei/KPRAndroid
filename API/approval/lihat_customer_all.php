<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT * FROM tb_nasabah n JOIN tb_employee e ON n.id_emp=e.id_emp ORDER BY nama_emp";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_nsb"=>$row['id_nsb'],
			"nama_nsb"=>$row['nama_nsb'],
            "ktp_nsb"=>$row['ktp_nsb'],
            "tmpt_lahir_nsb"=>$row['tmpt_lahir_nsb'],
            "tgl_lahir_nsb"=>$row['tgl_lahir_nsb'],
            "hp_nsb"=>$row['hp_nsb'],
            "alamat_nsb"=>$row['alamat_nsb'],
            "id_emp"=>$row['id_emp'],
			"nama_emp"=>$row['nama_emp']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>