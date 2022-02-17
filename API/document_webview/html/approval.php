<?php 

    require('../../koneksi.php');
    $id_nsb = $_GET['id_nsb'];

    if (!empty($_FILES['ktp_doc']['name'])) {

		$fileName_ktpDoc = $_FILES['ktp_doc']['name'];
		$fileName_rekDoc = $_FILES['rek_doc']['name'];
		$fileName_slipGajiDoc = $_FILES['slip_gaji_doc']['name'];
		

        //Upload KTP
        if (move_uploaded_file($_FILES['ktp_doc']['tmp_name'], '../../image/'.$fileName_ktpDoc)) {
		    $sql = "INSERT INTO `tb_dokumen` 
            (`id_dok`, `id_nsb`, `tipe_dok`, `nama_dok`, `status_dok`) 
            VALUES 
            (NULL, '$id_nsb', 'KTP', '$fileName_ktpDoc', 'pending')";
            mysqli_query($con,$sql);
            // echo '<img src="'.$fileName_ktpDoc.'" style="width:320px;height:300px;"/>';            
		}
        // Upload Rekening
        if (move_uploaded_file($_FILES['rek_doc']['tmp_name'],'../../image/'. $fileName_rekDoc)) {
		    $sql = "INSERT INTO `tb_dokumen` 
            (`id_dok`, `id_nsb`, `tipe_dok`, `nama_dok`, `status_dok`) 
            VALUES 
            (NULL, '$id_nsb', 'Rekening Koran', '$fileName_rekDoc', 'pending')";
            mysqli_query($con,$sql);
            // echo '<img src="'.$fileName_rekDoc.'" style="width:320px;height:300px;"/>';            
		}
        //Upload Slip Gaji
        if (move_uploaded_file($_FILES['slip_gaji_doc']['tmp_name'], '../../image/'. $fileName_slipGajiDoc)) {
		    $sql = "INSERT INTO `tb_dokumen` 
            (`id_dok`, `id_nsb`, `tipe_dok`, `nama_dok`, `status_dok`) 
            VALUES 
            (NULL, '$id_nsb', 'Slip Gaji', '$fileName_slipGajiDoc', 'pending')";
            mysqli_query($con,$sql);
            // echo '<img src="'.$fileName_slipGajiDoc.'" style="width:320px;height:300px;"/>';
		}
        
	}

    

    $sql = "SELECT * FROM `tb_dokumen` WHERE id_nsb = '$id_nsb'";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

    while($row = mysqli_fetch_array($r)){
        // echo '<img src="'.$row['nama_dok'].'" style="width:320px;height:300px;"/>';
        if($row['status_dok']=='approved'){
            $color = "green";
        }
        if($row['status_dok']=='rejected' || $row['status_dok']=='pending'){
            $color = "red";
        }
        echo "<div class='row' style = 'margin-left : 2px'>
        <h4>".$row['tipe_dok']."</h4> &nbsp | | &nbsp<span id='status_dok' style = 'color:".$color."'><b> ".$row['status_dok']."</b></span>
        </div>";
        
        echo '<img src="../../image/'.$row['nama_dok'].'" style="max-width: 100%; height: auto;"/>';
        
        echo '<div class="row modal-footer">
            <button class="btn" onclick = "approveDoc('.$row['id_dok'].');" style = "background: #6B8E23; color: white">Approve</button>
            <button class="btn" onclick = "rejectDoc('.$row['id_dok'].');" style = "background: #CD5C5C ; color: white">Reject</button>
        </div>';
        echo '<hr>';
    }


    
?>