-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 15, 2022 at 11:50 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kpr_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_dokumen`
--

CREATE TABLE `tb_dokumen` (
  `id_dok` int(11) NOT NULL,
  `id_nsb` int(11) NOT NULL,
  `tipe_dok` varchar(15) NOT NULL,
  `nama_dok` varchar(100) NOT NULL,
  `status_dok` enum('approved','rejected','pending') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_dokumen`
--

INSERT INTO `tb_dokumen` (`id_dok`, `id_nsb`, `tipe_dok`, `nama_dok`, `status_dok`) VALUES
(1, 1, 'KTP Suami', 'ktp-suami-rizal.jpg', 'pending'),
(2, 1, 'KTP Istri', 'ktp-istri-rizal.jpg', 'pending'),
(3, 1, 'Kartu Keluarga', 'kk-rizal.jpg', 'pending'),
(4, 1, 'NPWP', 'npwp-rizal.jpg', 'pending'),
(5, 1, 'Rekening Koran', 'rekening-rizal.jpg', 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `tb_employee`
--

CREATE TABLE `tb_employee` (
  `id_emp` int(11) NOT NULL,
  `nama_emp` varchar(50) NOT NULL,
  `role_emp` enum('kpr','manager') NOT NULL,
  `pass_emp` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_employee`
--

INSERT INTO `tb_employee` (`id_emp`, `nama_emp`, `role_emp`, `pass_emp`) VALUES
(1, 'Abdul Rachman', 'kpr', '202cb962ac59075b964b07152d234b70'),
(2, 'Achmad Fadjar', 'manager', '202cb962ac59075b964b07152d234b70'),
(3, 'Ade R. Syarief', 'manager', '202cb962ac59075b964b07152d234b70'),
(4, 'Adi Sumito', 'kpr', '202cb962ac59075b964b07152d234b70'),
(5, 'Adimitra Baratama Nusantara', 'manager', '202cb962ac59075b964b07152d234b70'),
(6, 'Adji Muljo Teguh', 'kpr', '202cb962ac59075b964b07152d234b70'),
(7, 'Adri Achmad Drajat', 'kpr', '202cb962ac59075b964b07152d234b70'),
(8, 'Adryansyah', 'kpr', '202cb962ac59075b964b07152d234b70'),
(9, 'Ago Harlim', 'kpr', '202cb962ac59075b964b07152d234b70'),
(10, 'Agung Salim', 'manager', '202cb962ac59075b964b07152d234b70'),
(11, 'Baldeo Singh', 'manager', '202cb962ac59075b964b07152d234b70'),
(12, 'Bambang Tijoso Tedjokusumo', 'manager', '202cb962ac59075b964b07152d234b70'),
(13, 'Bayu Irianto', 'manager', '202cb962ac59075b964b07152d234b70'),
(14, 'Belinda Natalia Tanoko', 'manager', '202cb962ac59075b964b07152d234b70'),
(15, 'Benny Tenges', 'kpr', '202cb962ac59075b964b07152d234b70'),
(16, 'Bernadette Ruth Irawati', 'kpr', '202cb962ac59075b964b07152d234b70'),
(17, 'Celin Tanardi', 'kpr', '202cb962ac59075b964b07152d234b70'),
(18, 'Chin Chin Chandera', 'manager', '202cb962ac59075b964b07152d234b70'),
(19, 'Christianto', 'manager', '202cb962ac59075b964b07152d234b70'),
(20, 'Dani Ismulyatie', 'manager', '202cb962ac59075b964b07152d234b70');

-- --------------------------------------------------------

--
-- Table structure for table `tb_janji_temu`
--

CREATE TABLE `tb_janji_temu` (
  `id_jantem` int(11) NOT NULL,
  `id_nsb` int(11) NOT NULL,
  `pesan_jantem` text NOT NULL,
  `tgl_jantem` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_janji_temu`
--

INSERT INTO `tb_janji_temu` (`id_jantem`, `id_nsb`, `pesan_jantem`, `tgl_jantem`) VALUES
(1, 1, 'Bertemu untuk ambil data', '2022-02-15'),
(2, 2, 'Bertemu untuk ambil data', '2022-02-16'),
(3, 3, 'Bertemu untuk ambil data', '2022-02-16'),
(4, 4, 'Bertemu untuk ambil data', '2022-02-17'),
(5, 5, 'Bertemu untuk ambil data', '2022-02-18'),
(6, 6, 'Bertemu untuk ambil data', '2022-02-19'),
(7, 7, 'Bertemu untuk ambil data', '2022-02-20'),
(8, 8, 'Bertemu untuk ambil data', '2022-02-21'),
(9, 9, 'Bertemu untuk ambil data', '2022-02-22'),
(10, 10, 'Bertemu untuk ambil data', '2022-02-23'),
(11, 11, 'Bertemu untuk ambil data', '2022-02-24'),
(12, 12, 'Bertemu untuk ambil data', '2022-02-25'),
(13, 13, 'Bertemu untuk ambil data', '2022-02-26'),
(14, 14, 'Bertemu untuk ambil data', '2022-02-27'),
(15, 15, 'Bertemu untuk ambil data', '2022-02-28'),
(16, 16, 'Bertemu untuk ambil data', '2022-02-29'),
(17, 17, 'Bertemu untuk ambil data', '2022-02-30'),
(18, 18, 'Bertemu untuk ambil data', '2022-03-17'),
(19, 19, 'Bertemu untuk ambil data', '2022-03-18'),
(20, 20, 'Bertemu untuk ambil data', '2022-03-19');

-- --------------------------------------------------------

--
-- Table structure for table `tb_nasabah`
--

CREATE TABLE `tb_nasabah` (
  `id_nsb` int(11) NOT NULL,
  `nama_nsb` varchar(50) NOT NULL,
  `ktp_nsb` varchar(17) NOT NULL,
  `tmpt_lahir_nsb` varchar(15) NOT NULL,
  `tgl_lahir_nsb` varchar(15) NOT NULL,
  `hp_nsb` varchar(13) NOT NULL,
  `alamat_nsb` text NOT NULL,
  `id_emp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_nasabah`
--

INSERT INTO `tb_nasabah` (`id_nsb`, `nama_nsb`, `ktp_nsb`, `tmpt_lahir_nsb`, `tgl_lahir_nsb`, `hp_nsb`, `alamat_nsb`, `id_emp`) VALUES
(1, 'Rizal Cerdas', '3321116502910000', 'Jember', '1998-07-15', '08337829933', 'Jakarta', 1),
(2, 'Kia Dzaky', '3321116502910001', 'Jakarta', '1988-07-15', '083378277833', 'Jember', 2),
(3, 'Abi Praya', '3322216502910002', 'Surabaya', '1999-04-14', '08337776933', 'Jakarta', 1),
(4, 'Sepli Ariyanto', '3321116987910000', 'Malang', '1978-03-15', '08365829933', 'Jember', 3),
(5, 'Dendika Dwi', '3365416502910000', 'Jember', '1998-03-25', '08227829933', 'Jakarta', 4),
(6, 'Lutfiah Intan', '3321116502910087', 'Semarang', '1998-03-10', '08338729943', 'Jakarta', 5),
(7, 'Nasrullah', '3321117762910000', 'Jember', '1998-09-16', '08337866533', 'Semarang', 6),
(8, 'Rafiq Alfansa', '3321998502910000', 'Bogor', '1968-07-15', '08366729933', 'Jakarta', 7),
(9, 'Ilham Ahmad', '3321116502765000', 'Bandung', '1998-04-04', '08337678933', 'Surabaya', 8),
(10, 'Ghozie Ikhsan', '1402020701840017', 'Jember', '1988-07-20', '08337775933', 'Malang', 9),
(11, 'Defi Permata', '8762020701840017', 'Jakarta', '1998-03-15', '08110829933', 'Jakarta', 10),
(12, 'Dona Sari', '1987020701840017', 'Bogor', '1999-01-15', '08556729933', 'Jakarta', 11),
(13, 'Sari Putri', '1402020769840017', 'Purwakarta', '1989-07-15', '08377459933', 'Jakarta', 12),
(14, 'Rizka Putri', '1407690701840017', 'Manado', '1978-06-11', '08780829933', 'Jakarta', 13),
(15, 'Coki Muslim', '14076520701840017', 'Medan', '1977-11-15', '08337764933', 'Semarang', 14),
(16, 'Tona Toni', '1402087601840017', 'Madiun', '1978-05-29', '08337885433', 'Surabaya', 15),
(17, 'Bagas Cimay', '1402076501840017', 'Purwakarta', '1988-01-25', '08887629933', 'Bogor', 16),
(18, 'Isna Farih', '1406670701840017', 'Manado', '1978-08-11', '08337877653', 'Bandung', 17),
(19, 'Eta Putri', '1402020701678017', 'Madiun', '1968-07-25', '08388789933', 'Jakarta', 18),
(20, 'Esra Mugi', '1789020701840017', 'Medan', '1978-11-15', '08337878933', 'Bogor', 18);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_dokumen`
--
ALTER TABLE `tb_dokumen`
  ADD PRIMARY KEY (`id_dok`),
  ADD KEY `id_nsb` (`id_nsb`);

--
-- Indexes for table `tb_employee`
--
ALTER TABLE `tb_employee`
  ADD PRIMARY KEY (`id_emp`);

--
-- Indexes for table `tb_janji_temu`
--
ALTER TABLE `tb_janji_temu`
  ADD PRIMARY KEY (`id_jantem`),
  ADD KEY `id_nsb` (`id_nsb`);

--
-- Indexes for table `tb_nasabah`
--
ALTER TABLE `tb_nasabah`
  ADD PRIMARY KEY (`id_nsb`),
  ADD KEY `id_emp` (`id_emp`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_dokumen`
--
ALTER TABLE `tb_dokumen`
  MODIFY `id_dok` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tb_employee`
--
ALTER TABLE `tb_employee`
  MODIFY `id_emp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tb_janji_temu`
--
ALTER TABLE `tb_janji_temu`
  MODIFY `id_jantem` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tb_nasabah`
--
ALTER TABLE `tb_nasabah`
  MODIFY `id_nsb` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_dokumen`
--
ALTER TABLE `tb_dokumen`
  ADD CONSTRAINT `tb_dokumen_ibfk_1` FOREIGN KEY (`id_nsb`) REFERENCES `tb_nasabah` (`id_nsb`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_janji_temu`
--
ALTER TABLE `tb_janji_temu`
  ADD CONSTRAINT `tb_janji_temu_ibfk_1` FOREIGN KEY (`id_nsb`) REFERENCES `tb_nasabah` (`id_nsb`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_nasabah`
--
ALTER TABLE `tb_nasabah`
  ADD CONSTRAINT `tb_nasabah_ibfk_1` FOREIGN KEY (`id_emp`) REFERENCES `tb_employee` (`id_emp`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
