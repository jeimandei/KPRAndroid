<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT j.id_jantem, n.id_nsb, n.nama_nsb, j.pesan_jantem, j.tgl_jantem FROM tb_janji_temu j
		JOIN tb_nasabah n ON j.id_nsb = n.id_nsb";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_jantem"=>$row['id_jantem'],
			"id_nsb"=>$row['id_nsb'],
			"nama_nsb"=>$row['nama_nsb'],
            "pesan_jantem"=>$row['pesan_jantem'],
            "tgl_jantem"=>$row['tgl_jantem']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>