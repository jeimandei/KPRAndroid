<?php
    $id = $_GET['id'];
    $pass_emp = $_GET['pass_emp'];
   
	require "../koneksi.php";
	
    $query = "SELECT * from tb_employee where id_emp = '$id' AND pass_emp = MD5('$pass_emp')";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_array($result);
 
    if (mysqli_num_rows($result) > 0) {
        $result = array();
        $code = "login_true";
        $message = "Welcome to Apps My Application.";
        array_push($result, array("code"=>$code, "pesan"=>$message, "id_emp"=>$row['id_emp']));
        echo json_encode(array("result"=>$result));
    }else{
        $response = array();
        $code = "login_false";
        $message = "Maaf data anda belum terdaftar..!";
        array_push($response, array("code"=>$code, "pesan"=>$message));
        echo json_encode(array("Server"=>$response));
    }
?>