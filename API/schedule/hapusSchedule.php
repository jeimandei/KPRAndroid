<?php 
 //Mendapatkan Nilai ID
 session_start();
 $id = $_GET['id'];
 
 //Import File Koneksi Database
 require_once('../koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM `tb_janji_temu` WHERE `tb_janji_temu`.`id_jantem` = $id";

 
 //Menghapus Nilai pada Database 
if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Data Janji Temu';
 }else{
 echo 'Gagal Menghapus Data Janji Temu';
 }
 
 mysqli_close($con);
 ?>