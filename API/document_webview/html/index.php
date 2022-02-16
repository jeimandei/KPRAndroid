<!DOCTYPE html>
<html dir="ltr" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="../assets/images/favicon.png">
    <title>Adminmart Template - The Ultimate Multipurpose admin template</title>
    <!-- Custom CSS -->
    <link href="../dist/css/style.min.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
    <!-- ============================================================== -->
    <!-- Preloader - style you can find in spinners.css -->
    <!-- ============================================================== -->
    <div class="preloader">
        <div class="lds-ripple">
            <div class="lds-pos"></div>
            <div class="lds-pos"></div>
        </div>
    </div>
    <!-- ============================================================== -->
    <!-- Main wrapper - style you can find in pages.scss -->
    <!-- ============================================================== -->
    <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">
        <!-- ============================================================== -->
        <!-- Topbar header - style you can find in pages.scss -->
        <!-- ============================================================== -->
       
        <!-- ============================================================== -->
        <!-- End Topbar header -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        
        <!-- ============================================================== -->
        <!-- End Left Sidebar - style you can find in sidebar.scss  -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Page wrapper  -->
        <!-- ============================================================== -->
        <div class="page-wrapper" style= "margin-top : -100px"> 
            <!-- ============================================================== -->
            <!-- Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <div class="page-breadcrumb">
                <div class="row">
                    <div class="col-7 align-self-center">
                        
                    </div>
                    <!-- <div class="col-5 align-self-center">
                        <div class="customize-input float-right">
                            <select class="custom-select custom-select-set form-control bg-white border-0 custom-shadow custom-radius">
                                <option selected>Aug 19</option>
                                <option value="1">July 19</option>
                                <option value="2">Jun 19</option>
                            </select>
                        </div>
                    </div> -->
                </div>
            </div>
            <!-- ============================================================== -->
            <!-- End Bread crumb and right sidebar toggle -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- Container fluid  -->
            <!-- ============================================================== -->
            <div class="container-fluid">
                <!-- ============================================================== -->
                <!-- Start Page Content -->
                <!-- ============================================================== -->
                <?php 
                    $link = "http://".$_SERVER['SERVER_NAME'];    
                ?>
                
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">                                
                                <h4 class="card-title">Upload Document</h4>
                                <form id="frmImage" method="POST" onsubmit = "return false">  
                                    <div class="form-body">
                                        <div class="row">
                                            <div class="col-md-11">
                                                <div class="form-group">
                                                    <label>KTP</label>
                                                    <input type="file" class="form-control" id ="ktp_doc" name = "ktp_doc">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                           
                                            <div class="col-md-10">
                                                <div class="form-group">
                                                    <label>Rekening Koran 3 Bulan</label>
                                                    <input type="file" class="form-control" placeholder="No HandPhone" id ="rek_doc" name = "rek_doc" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-2">
                                                <div class="form-group">
                                                    <label>Slip Gaji</label>
                                                    <input type="file" class="form-control" placeholder="Email" id ="slip_gaji_doc" name = "slip_gaji_doc" required>
                                                </div>
                                            </div>
                                        </div>
                                        
                                    <div class="form-actions" style = "margin-top: 30px">
                                        <div class="text-right">
                                            <button type="submit" class="btn btn-info">Submit</button>
                                            <!-- <button class = "btn btn-danger"><a href=<?=$link . "/individual_task/peserta/tabelPeserta.php"?> style ="color:white">Batal</a></button> -->
                                            <button class = "btn btn-danger" onclick = "showAndroidToast('Hello Android')">Batal</button>
                                            <script>
                                                function showAndroidToast(toast) {
                                                    Android.showToast(toast);
                                                }
                                            </script>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                
<!-- Modal -->
<div class="modal fade" id="exampleModal" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header btn-success">
        <h5 class="modal-title " id="exampleModalLabel">Berhasil Tambah Data Dokumen Nasabah</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h3 id = "delete_name">Berhasil Tambah Data Dokumen Nasabah</h3>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal">Yes</button>
      </div>
    </div>
  </div>
</div>
               
                <!-- ============================================================== -->
                <!-- End PAge Content -->
                <!-- ============================================================== -->
                <!-- ============================================================== -->
                <!-- Right sidebar -->
                <!-- ============================================================== -->
                <!-- .right-sidebar -->
                <!-- ============================================================== -->
                <!-- End Right sidebar -->
                <!-- ============================================================== -->
            </div>
            <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-body">                                
                                <h4 class="card-title">Tampilan</h4>
                                <div id="imageView"></div>
                            </div>
                        </div>
                    </div>
                </div>
            <!-- ============================================================== -->
            <!-- End Container fluid  -->
            <!-- ============================================================== -->
            <!-- ============================================================== -->
            <!-- footer -->
            <!-- ============================================================== -->
           
            <!-- ============================================================== -->
            <!-- End footer -->
            <!-- ============================================================== -->
        </div>
        <!-- ============================================================== -->
        <!-- End Page wrapper  -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- End Wrapper -->
    <!-- ============================================================== -->
    <!-- All Jquery -->
    <!-- ============================================================== -->
    <script src="../assets/libs/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="../assets/libs/popper.js/dist/umd/popper.min.js"></script>
    <script src="../assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- apps -->
    <!-- apps -->
    <script src="../dist/js/app-style-switcher.js"></script>
    <script src="../dist/js/feather.min.js"></script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="../assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
    <script src="../assets/extra-libs/sparkline/sparkline.js"></script>
    <!--Wave Effects -->
    <!-- themejs -->
    <!--Menu sidebar -->
    <script src="../dist/js/sidebarmenu.js"></script>
    <!--Custom JavaScript -->
    <script src="../dist/js/custom.min.js"></script>
    
    <script type="text/javascript">
        <?php $id_nsb = $_GET['id_nsb'] ?>
  $(document).ready(function(){
      $.ajax({
          url  : "upload-image.php?id_nsb=<?=$id_nsb?>",
          type : "GET",
          success:function(response){
            $("#imageView").html(response);
            $("#image").val('');
            
          }
        });
      $("#frmImage").on("submit", function(e){
        e.preventDefault();
        var formData = new FormData(this);
        $.ajax({
          url  : "upload-image.php?id_nsb=<?=$id_nsb?>",
          type : "POST",
          cache:false,
          data :formData,
          contentType : false, // you can also use multipart/form-data replace of false
          processData: false,
          success:function(response){
            $("#imageView").html(response);
            $("#image").val('');
            $('#exampleModal').modal('show');
            $("#frmImage").get(0).reset();
          }
        });
      });
  });
</script>
</body>

</html>