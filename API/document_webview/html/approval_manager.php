<?php session_start(); ?>
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
                
<!-- Modal -->
<?php if(isset($_SESSION['modal'])){ ?>
<div class="modal fade" id="exampleModal" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header btn-success">
        <h5 class="modal-title " id="exampleModalLabel">Berhasil Approve Data Dokumen Nasabah</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h3 id = "delete_name">Berhasil Approve Data Dokumen Nasabah</h3>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success" data-dismiss="modal">Yes</button>
      </div>
    </div>
  </div>
</div>
<?php } unset($_SESSION['modal']); ?>
<?php if(isset($_SESSION['modal_reject'])){ ?>
<div class="modal fade" id="exampleModal1" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header btn-danger">
        <h5 class="modal-title " id="exampleModalLabel">Berhasil Reject Data Dokumen Nasabah</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h3 id = "delete_name">Berhasil Reject Data Dokumen Nasabah</h3>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-dismiss="modal">Yes</button>
      </div>
    </div>
  </div>
</div>
<?php } unset($_SESSION['modal_reject']); ?>

<div class="modal fade" id="exampleModal2" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style = "background: #CD5C5C ; color: white">
        <h5 class="modal-title " id="exampleModalLabel">Anda Akan Reject Data Dokumen Nasabah</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form action = "rejectDoc.php " method = "GET" id="formReject">
      <div class="modal-body">
        <h4>Pesan yang akan disampaikan</h4>
        <input type="text" name="id_dok" id="id_dok" hidden>
        <input type="text" name="id_nsb" id="id_nsb" value = "<?=$_GET['id_nsb'];?>" hidden>
        <textarea name="pesan_dok" id="pesan_dok" cols="30" rows="10" class="form-control" required></textarea>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <!-- <button type="button" class="btn btn-danger" >Yes</button> -->
        <input type="submit" class="btn" style = "background: #CD5C5C ; color: white" value = "Reject">
      </div>
      </form>
    </div>
  </div>
</div>

<div class="modal fade" id="exampleModal3" >
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style = "background: #6B8E23 ; color: white">
        <h5 class="modal-title " id="exampleModalLabel">Anda Akan Approve Data Dokumen Nasabah</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form action = "approveDoc.php " method = "GET" id="formReject">
      <div class="modal-body">
        <h4>Anda Akan Approve Data Dokumen Nasabah</h4>
        <input type="text" name="id_dok_approve" id="id_dok_approve" hidden>
        <input type="text" name="id_nsb" id="id_nsb" value = "<?=$_GET['id_nsb']?>" hidden>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <!-- <button type="button" class="btn btn-danger" >Yes</button> -->
        <input type="submit" class="btn" style = "background: #6B8E23 ; color: white" value = "Yes">
      </div>
      </form>
    </div>
  </div>
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
    //   $("#formReject").submit(function(event){
    //     submitForm();
    //     return false;
    // });
      $.ajax({
          url  : "approval.php?id_nsb=<?=$id_nsb?>",
          type : "GET",
          success:function(response){
            $("#imageView").html(response);
            $("#image").val('');
            $('#exampleModal').modal('show');
            $('#exampleModal1').modal('show');
          }
        });
  });
</script>
<script>
    function approveDoc(id_dok) {

      $('#id_dok_approve').val(id_dok);
      $('#exampleModal3').modal('show');
        // $.ajax({
        //   url  : "approveDoc.php?id_dok="+id_dok,
        //   type : "GET",
        //   success:function(response){ 
        //     //   $('#exampleModal').modal('show');
        //       location.reload();
        //   }
        // });
    }
    function rejectDoc(id_dok) {
        $('#id_dok').val(id_dok);
        $('#exampleModal2').modal('show');
    }
    // function submitForm() {
    //   $.ajax({
    //       url  : "rejectDoc.php"
    //       type : "GET",
    //       cache: "false",
    //       data : $('form#formReject').serialize(),
    //       success:function(response){       
    //         //   $('#exampleModal').modal('show');
    //           location.reload();     
    //       }
    //     });
    // }
</script>


</body>

</html>