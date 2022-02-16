<?php 
 //Mendapatkan Nilai ID
 session_start();
 $id = $_GET['id_dok'];
 
 //Import File Koneksi Database
 require_once('../koneksi.php');
 
 //Membuat SQL Query
 $sql = "DELETE FROM `tb_dokumen` WHERE `tb_dokumen`.`id_dok` = $id";

 
 //Menghapus Nilai pada Database 
if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Data Dokumen Nasabah';
 }else{
 echo 'Gagal Menghapus Data Dokumen Nasabah';
 }
 
 mysqli_close($con);
 ?>