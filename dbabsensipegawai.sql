/*
Navicat MySQL Data Transfer

Source Server         : navicat
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : dbabsensipegawai

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2014-01-13 09:49:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(10) NOT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', 'admin');
INSERT INTO `admin` VALUES ('dendi', 'dendi');

-- ----------------------------
-- Table structure for `tblabsen`
-- ----------------------------
DROP TABLE IF EXISTS `tblabsen`;
CREATE TABLE `tblabsen` (
  `no` int(5) NOT NULL AUTO_INCREMENT,
  `idpegawai` varchar(10) NOT NULL,
  `izin` varchar(4) NOT NULL,
  `tanggal` date NOT NULL,
  `keterangan` varchar(100) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `idpegawai` (`idpegawai`),
  CONSTRAINT `idpegawai di absen` FOREIGN KEY (`idpegawai`) REFERENCES `tblpegawai` (`idpegawai`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tblabsen
-- ----------------------------
INSERT INTO `tblabsen` VALUES ('15', 'J3D112081', 'Izin', '2014-01-12', 'mau mati');
INSERT INTO `tblabsen` VALUES ('16', 'J3D112026', 'Izin', '2014-01-13', 'mokat');
INSERT INTO `tblabsen` VALUES ('17', 'J3D112062', 'Izin', '2014-01-15', 'ke Jawa');

-- ----------------------------
-- Table structure for `tbljabatan`
-- ----------------------------
DROP TABLE IF EXISTS `tbljabatan`;
CREATE TABLE `tbljabatan` (
  `idJabatan` varchar(3) NOT NULL,
  `namajabatan` varchar(30) NOT NULL,
  PRIMARY KEY (`idJabatan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbljabatan
-- ----------------------------
INSERT INTO `tbljabatan` VALUES ('B01', 'Manajer Pemasaran');
INSERT INTO `tbljabatan` VALUES ('B02', 'Manajer IT');
INSERT INTO `tbljabatan` VALUES ('B03', 'Manajer Keuangan');
INSERT INTO `tbljabatan` VALUES ('B04', 'Manajer HRD');
INSERT INTO `tbljabatan` VALUES ('B05', 'Manajer Produksi');
INSERT INTO `tbljabatan` VALUES ('C01', 'Staff IT');
INSERT INTO `tbljabatan` VALUES ('C02', 'Staff Keuangan');
INSERT INTO `tbljabatan` VALUES ('C03', 'Staff Pemasaran');
INSERT INTO `tbljabatan` VALUES ('C04', 'Staff HRD');
INSERT INTO `tbljabatan` VALUES ('C05', 'Staff Produksi');
INSERT INTO `tbljabatan` VALUES ('D01', 'Office Boy');
INSERT INTO `tbljabatan` VALUES ('D02', 'Office Girl');

-- ----------------------------
-- Table structure for `tbljam`
-- ----------------------------
DROP TABLE IF EXISTS `tbljam`;
CREATE TABLE `tbljam` (
  `id_jam` varchar(3) NOT NULL,
  `jam_masuk_mulai` time NOT NULL,
  `jam_kerja_mulai` time NOT NULL,
  `jam_kerja_selesai` time NOT NULL,
  `jam_keluar_selesai` time NOT NULL,
  PRIMARY KEY (`id_jam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbljam
-- ----------------------------
INSERT INTO `tbljam` VALUES ('1', '07:30:00', '08:00:00', '15:30:00', '15:59:59');
INSERT INTO `tbljam` VALUES ('2', '15:30:00', '16:00:00', '23:30:00', '23:59:59');
INSERT INTO `tbljam` VALUES ('3', '23:30:00', '00:00:00', '07:30:00', '07:59:59');
INSERT INTO `tbljam` VALUES ('D', '07:00:00', '08:00:00', '17:00:00', '17:59:59');

-- ----------------------------
-- Table structure for `tblkehadiran`
-- ----------------------------
DROP TABLE IF EXISTS `tblkehadiran`;
CREATE TABLE `tblkehadiran` (
  `no_rec` mediumint(10) NOT NULL AUTO_INCREMENT,
  `idpegawai` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `jmasuk` time DEFAULT NULL,
  `jkeluar` time DEFAULT NULL,
  `id_jam` varchar(3) NOT NULL,
  PRIMARY KEY (`no_rec`),
  KEY `id pegawai di tbl kehadiran` (`idpegawai`),
  KEY `id_jam` (`id_jam`),
  CONSTRAINT `id pegawai di tbl kehadiran` FOREIGN KEY (`idpegawai`) REFERENCES `tblpegawai` (`idpegawai`),
  CONSTRAINT `jam di kehadiran` FOREIGN KEY (`id_jam`) REFERENCES `tbljam` (`id_jam`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tblkehadiran
-- ----------------------------
INSERT INTO `tblkehadiran` VALUES ('51', 'J3D112093', '2014-01-11', '07:30:49', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('52', 'J3D112026', '2014-01-11', '07:31:24', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('53', 'J3D112066', '2014-01-11', '07:32:30', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('54', 'J3D112081', '2014-01-11', '07:33:08', '15:59:59', '1');
INSERT INTO `tblkehadiran` VALUES ('55', 'j3d112092', '2014-01-11', '07:35:35', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('56', 'J3D112029', '2014-01-11', '07:35:57', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('57', 'J3D112032', '2014-01-11', '07:37:57', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('58', 'J3D212123', '2014-01-11', '07:39:00', '15:59:59', '1');
INSERT INTO `tblkehadiran` VALUES ('59', 'J3D112062', '2014-01-12', '07:36:24', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('60', 'J3D112093', '2014-01-12', '07:36:47', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('61', 'J3D112029', '2014-01-12', '07:38:18', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('62', 'J3D112026', '2014-01-12', '07:38:22', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('63', 'J3D112002', '2014-01-12', '15:40:51', '23:30:59', '2');
INSERT INTO `tblkehadiran` VALUES ('64', 'J3D112101', '2014-01-12', '15:30:29', '23:31:07', '2');
INSERT INTO `tblkehadiran` VALUES ('65', 'J3D112062', '2014-01-13', '07:25:01', '17:59:59', 'D');
INSERT INTO `tblkehadiran` VALUES ('66', 'J3D112026', '2014-01-13', '07:25:16', '17:29:31', 'D');

-- ----------------------------
-- Table structure for `tblpegawai`
-- ----------------------------
DROP TABLE IF EXISTS `tblpegawai`;
CREATE TABLE `tblpegawai` (
  `idpegawai` varchar(10) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `jabatan` varchar(3) NOT NULL,
  `foto` varchar(20) NOT NULL,
  `id_jam` varchar(3) NOT NULL,
  PRIMARY KEY (`idpegawai`),
  KEY `jabatan` (`jabatan`),
  KEY `id_jam` (`id_jam`),
  CONSTRAINT `tblpegawai_ibfk_1` FOREIGN KEY (`id_jam`) REFERENCES `tbljam` (`id_jam`),
  CONSTRAINT `tblpegawai_ibfk_2` FOREIGN KEY (`jabatan`) REFERENCES `tbljabatan` (`idJabatan`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tblpegawai
-- ----------------------------
INSERT INTO `tblpegawai` VALUES ('J3D112002', 'Ahmad Fauzi', 'D01', 'avatar_09.jpg', '2');
INSERT INTO `tblpegawai` VALUES ('J3D112026', 'Arimbi Rachmi F', 'B03', 'avatar_03.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112029', 'Muhammad Irsyad', 'C02', 'avatar_10.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112032', 'Dewi Oktarina', 'D02', 'avatar_11.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112042', 'Aldi Akbar', 'B01', 'avatar_02.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112062', 'Triwandha Febian R R', 'C03', 'avatar_07.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112066', 'Aditia Anugrah P', 'B04', 'avatar_04.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112081', 'Ivan Kardianta', 'C05', 'ivan.jpg', '1');
INSERT INTO `tblpegawai` VALUES ('J3D112092', 'Amin Shaum S', 'B05', 'avatar_06.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112093', 'Dendi Abdul Rohim', 'B02', 'avatar_01.jpg', 'D');
INSERT INTO `tblpegawai` VALUES ('J3D112101', 'Milla Nurfitriyani P', 'C05', 'mila.jpg', '2');
INSERT INTO `tblpegawai` VALUES ('J3D112105', 'Akbar Siddieq', 'C04', 'avatar_08.jpg', '2');
INSERT INTO `tblpegawai` VALUES ('J3D112114', 'M. Arifin', 'C05', 'avatar_05.jpg', '3');
INSERT INTO `tblpegawai` VALUES ('J3D212123', 'M. Ramdhan Firdaus', 'C01', 'avatar_12.jpg', '1');
