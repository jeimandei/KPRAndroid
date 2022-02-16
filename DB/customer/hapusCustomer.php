<?php 
 //Mendapatkan Nilai ID
 session_start();
 $id = $_GET['id'];
 
 //Import File Koneksi Database
 require_once('../koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM `tb_nasabah` WHERE `tb_nasabah`.`id_nsb` = $id";

 
 //Menghapus Nilai pada Database 
if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Data Nasabah';
 }else{
 echo 'Gagal Menghapus Data Nasabah';
 }
 
 mysqli_close($con);
 ?>