-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 16, 2018 at 02:58 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 5.6.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbabsensipegawai`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `username` varchar(10) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`username`, `password`) VALUES
('admin', 'admin'),
('dendi', 'dendi');

-- --------------------------------------------------------

--
-- Table structure for table `tblabsen`
--

CREATE TABLE `tblabsen` (
  `no` int(5) NOT NULL,
  `idpegawai` varchar(10) NOT NULL,
  `izin` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblabsen`
--

INSERT INTO `tblabsen` (`no`, `idpegawai`, `izin`, `tanggal`, `keterangan`) VALUES
(1, 'J3D112002', 'Izin', '2018-04-23', 'SAKIT'),
(2, 'J3D112026', 'Cuti', '2018-04-24', 'Cuti Jagain Ibu Sakit');

-- --------------------------------------------------------

--
-- Table structure for table `tbljabatan`
--

CREATE TABLE `tbljabatan` (
  `idJabatan` varchar(3) NOT NULL,
  `namajabatan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbljabatan`
--

INSERT INTO `tbljabatan` (`idJabatan`, `namajabatan`) VALUES
('B01', 'Manajer Finance'),
('B02', 'Manajer Akuntansi'),
('B03', 'Manajer Inventory'),
('B04', 'Manajer HRD'),
('B05', 'Manajer Produksi'),
('C01', 'Staff IT'),
('C02', 'Staff Keuangan'),
('C03', 'Staff Pemasaran'),
('C04', 'Staff HRD'),
('C05', 'Staff Produksi'),
('D01', 'Office Boy'),
('D02', 'Office Girl');

-- --------------------------------------------------------

--
-- Table structure for table `tbljam`
--

CREATE TABLE `tbljam` (
  `id_jam` varchar(3) NOT NULL,
  `jam_masuk_mulai` time NOT NULL,
  `jam_kerja_mulai` time NOT NULL,
  `jam_kerja_selesai` time NOT NULL,
  `jam_keluar_selesai` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbljam`
--

INSERT INTO `tbljam` (`id_jam`, `jam_masuk_mulai`, `jam_kerja_mulai`, `jam_kerja_selesai`, `jam_keluar_selesai`) VALUES
('1', '07:30:00', '08:00:00', '15:30:00', '15:59:59'),
('2', '15:30:00', '16:00:00', '23:30:00', '23:59:59'),
('3', '23:30:00', '00:00:00', '07:30:00', '07:59:59'),
('D', '07:00:00', '08:00:00', '17:00:00', '17:59:59');

-- --------------------------------------------------------

--
-- Table structure for table `tblpegawai`
--

CREATE TABLE `tblpegawai` (
  `idpegawai` varchar(10) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `jabatan` varchar(3) NOT NULL,
  `foto` varchar(20) NOT NULL,
  `id_jam` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblpegawai`
--

INSERT INTO `tblpegawai` (`idpegawai`, `nama`, `jabatan`, `foto`, `id_jam`) VALUES
('J3D112002', 'Ahmad Fauzi U', 'C03', '01.jpg', '2'),
('J3D112026', 'Arimbi Rachmi N', 'B03', '02.jpg', 'D'),
('J3D112029', 'Muhammad Irsyad', 'C02', '03.jpg', 'D'),
('J3D112032', 'Dewi Oktarina', 'D02', '04.jpg', 'D'),
('J3D112042', 'Aldi Akbar', 'B01', '05.jpg', 'D'),
('J3D112062', 'Triwandha Febian R R', 'C03', '06.jpg', 'D'),
('J3D112066', 'Aditia Anugrah P', 'B04', '07.jpg', 'D'),
('J3D112081', 'Ivan Kardianta', 'C05', '08.jpg', '1'),
('J3D112092', 'Amin Shaum S', 'B05', '09.jpg', 'D'),
('J3D112093', 'Dendi Abdul Rohim', 'B02', '10.jpg', 'D'),
('J3D112101', 'Milla Nurfitriyani P', 'C05', 'mila.jpg', '2'),
('J3D112105', 'Akbar Siddieq', 'C04', 'avatar_08.jpg', '2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `tblabsen`
--
ALTER TABLE `tblabsen`
  ADD PRIMARY KEY (`no`),
  ADD KEY `idpegawai` (`idpegawai`);

--
-- Indexes for table `tbljabatan`
--
ALTER TABLE `tbljabatan`
  ADD PRIMARY KEY (`idJabatan`);

--
-- Indexes for table `tbljam`
--
ALTER TABLE `tbljam`
  ADD PRIMARY KEY (`id_jam`);

--
-- Indexes for table `tblpegawai`
--
ALTER TABLE `tblpegawai`
  ADD PRIMARY KEY (`idpegawai`),
  ADD KEY `jabatan` (`jabatan`),
  ADD KEY `id_jam` (`id_jam`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblabsen`
--
ALTER TABLE `tblabsen`
  MODIFY `no` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tblabsen`
--
ALTER TABLE `tblabsen`
  ADD CONSTRAINT `idpegawai di absen` FOREIGN KEY (`idpegawai`) REFERENCES `tblpegawai` (`idpegawai`);

--
-- Constraints for table `tblpegawai`
--
ALTER TABLE `tblpegawai`
  ADD CONSTRAINT `tblpegawai_ibfk_1` FOREIGN KEY (`id_jam`) REFERENCES `tbljam` (`id_jam`) ON DELETE CASCADE,
  ADD CONSTRAINT `tblpegawai_ibfk_2` FOREIGN KEY (`jabatan`) REFERENCES `tbljabatan` (`idJabatan`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
