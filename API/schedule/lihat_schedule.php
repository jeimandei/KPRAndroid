<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query

	$id = $_GET['id_emp'];

	// perlu revisi dari shared preferenced, get daya by id_emp
	$sql = "SELECT * FROM tb_janji_temu j
		JOIN tb_nasabah n ON j.id_nsb = n.id_nsb
		JOIN tb_employee e ON n.id_emp = e.id_emp
		WHERE e.id_emp = '$id'";
	
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
            "tgl_jantem"=>$row['tgl_jantem'],
			"id_emp" => $row['id_emp']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>