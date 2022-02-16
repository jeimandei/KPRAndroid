<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id_emp = $_GET['id_emp'];
	$id_jantem = $_GET['id_jantem'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	// revisi get data by id_emp dan id_janji temu
	$sql = "SELECT * FROM tb_janji_temu j
		JOIN tb_nasabah n ON j.id_nsb = n.id_nsb
        JOIN tb_employee e ON n.id_emp = e.id_emp
        WHERE e.id_emp = $id_emp AND id_jantem = $id_jantem";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id_emp"=>$row['id_emp'],
			"nama_emp" => $row['nama_emp'],
			"role_emp" => $row['role_emp'],
			"id_jantem"=>$row['id_jantem'],
			"id_nsb"=>$row['id_nsb'],
			"nama_nsb"=>$row['nama_nsb'],
            "pesan_jantem"=>$row['pesan_jantem'],
            "tgl_jantem"=>$row['tgl_jantem']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>