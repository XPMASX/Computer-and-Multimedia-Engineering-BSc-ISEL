-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 12, 2024 at 11:07 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--
CREATE DATABASE IF NOT EXISTS `project` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `project`;

-- --------------------------------------------------------

--
-- Table structure for table `article`
--

CREATE TABLE `article` (
  `idArticle` int(11) NOT NULL,
  `title` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `content` longtext COLLATE utf8_unicode_ci NOT NULL,
  `date` date NOT NULL,
  `name` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `idCategory` int(11) NOT NULL,
  `latitude` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `protected` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `article`
--

INSERT INTO `article` (`idArticle`, `title`, `content`, `date`, `name`, `idCategory`, `latitude`, `longitude`, `protected`) VALUES
(7, 'Batman', '\'\'this is batman\'\'', '2024-06-01', 'user1', 3, '38.8244247', '-9.1767649', 1),
(8, 'Trump', '\'\'\'Donald Jesus Johnald Ronald \'\'The Donald\'\' Drumpf McDrunk Golfing Moronhat, Sr.\'\'\' (born June 14, 1946) is an unevenly tanned real estate developer, reality television personality, American version of [[Silvio Berlusconi]], [[Rapist|serial rapist]], intermittent [[Republican]] candidate, the 45th [[Mr. Garrison|Televised]] [[President of the United States]], and a secret agent from either [[China]], [[India]], [[North Korea]], or [[Russia]]. Some time traveler must have fucked up big time.\r\n[[File:Don’t feed Trump after Midnight.jpeg|thumb|Trump goes through his nightly transformation.]]\r\n\r\nBefore he became Commander-in-chief, Trump dipped his big toe into politics by yelling at [[Obama]] on \'\'[[Fox News|Fox and Friends]]\'\'. He ran for President on a platform condemning the political establishment and their [[Muslim]]–[[Chinese]]–[[Mexican]] vanguards. Trump ultimately won the 2016 election on the tried and true campaign strategy of not being [[Hillary Clinton]]; however, he faltered in the 2020 election against [[Joe Biden]] for the same reason. He later fomented [[UnNews:January 6, 2021|a massive, organized hissy-fit at the Capitol]] prior to leaving office.\r\n\r\nWhen he\'s not grabbing [[Duck]] by the [[pussy]] or building [[wall]]s, Trump writes storybooks for the children of venture capitalists. His most notable work is \'\'Seal the Deal\'\', about a marine mammal that invests in expensive marinas and opens undersea golf courses in [[Scotland]].', '2024-06-01', 'padrola', 3, '38.8244229', '-9.1788412', 0),
(10, 'Test', '\'\'\' we are testing\'\'\'', '2024-06-06', 'padrola', 2, '38.8244229', '-9.1788412', 0),
(13, 'God of War', '\'\'God of WAr\'\'', '2024-06-06', 'padrola', 2, '38.8244229', '-9.1788412', 1),
(14, 'Breaking Bad', '\'\'\'cool breaking bad edit\'\'\'', '2024-06-06', 'padrola', 2, '38.8244229', '-9.1788412', 0),
(15, 'Duck', '\'\'This is a Duck2\'\'', '2024-06-08', 'user1', 4, '38.8244229', '-9.1788412', 1),
(16, 'Testing art up', '[[Trump|Testing art up]] ', '2024-06-11', 'user1', 1, '', '', 0),
(17, 'Ronaldo', '\'\'\'Cristiano Ronaldo dos Santos Aveiro\'\'\' (Funchal, 5 de fevereiro de 1985) é um futebolista português que atua como ponta-esquerda ou ponta de lança. Atualmente joga pelo Al-Nassr, da Arábia Saudita, onde venceu a Liga dos Campeões Árabes de 2023. É capitão pela Seleção Portuguesa, onde conquistou a Eurocopa de 2016 e a Liga das Nações em 2018-19. É o maior artilheiro da história do futebol em jogos oficiais com 893 golos, sendo simultaneamente o jogador com mais golos na história a nível de seleções com 128 golos, bem como a nível de clubes com 765 golos.Escalado no Dream Team da Bola de Ouro, também o jogador com o maior número de nomeações do prêmio Ballon d\'Or.', '2024-06-12', 'Test', 1, '38.8244229', '-9.1788412', 1);

-- --------------------------------------------------------

--
-- Table structure for table `article-images`
--

CREATE TABLE `article-images` (
  `idArticle` int(11) NOT NULL,
  `fileName` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `mimeFileName` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `typeFileName` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `imageFileName` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `imageMimeFileName` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `imageTypeFileName` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `thumbFileName` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `thumbMimeFileName` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `thumbTypeFileName` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `caption` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `article-images`
--

INSERT INTO `article-images` (`idArticle`, `fileName`, `mimeFileName`, `typeFileName`, `imageFileName`, `imageMimeFileName`, `imageTypeFileName`, `thumbFileName`, `thumbMimeFileName`, `thumbTypeFileName`, `caption`) VALUES
(7, '/tmp/upload/contents/batman.jpg', 'image', 'jpeg', '/tmp/upload/contents/batman.jpg', 'image', 'jpeg', '/tmp/upload/contents/thumbs/batman.jpeg', 'image', 'jpeg', 'Dark Knight'),
(8, '/tmp/upload/contents/Trump.mp4', 'video', 'mp4', '/tmp/upload/contents/thumbs/Trump-Large.jpg', 'image', 'jpeg', '/tmp/upload/contents/thumbs/Trump.jpg', 'image', 'jpeg', 'Trump'),
(10, '/tmp/upload/contents/Trump.mp4', 'video', 'mp4', '/tmp/upload/contents/thumbs/Trump-Large.jpg', 'image', 'jpeg', '/tmp/upload/contents/thumbs/Trump.jpg', 'image', 'jpeg', 'test'),
(13, '/tmp/upload/contents/rat-spinning.gif', 'image', 'gif', '/tmp/upload/contents/rat-spinning.gif', 'image', 'gif', '/tmp/upload/contents/thumbs/rat-spinning.gif', 'image', 'gif', 'rat-spinning'),
(14, '/tmp/upload/contents/19700_breaking_bad.jpg', 'image', 'jpeg', '/tmp/upload/contents/19700_breaking_bad.jpg', 'image', 'jpeg', '/tmp/upload/contents/thumbs/19700_breaking_bad.jpeg', 'image', 'jpeg', 'breakingbad'),
(15, '/tmp/upload/contents/tenor.gif', 'image', 'gif', '/tmp/upload/contents/tenor.gif', 'image', 'gif', '/tmp/upload/contents/thumbs/tenor.gif', 'image', 'gif', 'Duck'),
(16, '/tmp/upload/contents/Logo.png', 'image', 'png', '/tmp/upload/contents/Logo.png', 'image', 'png', '/tmp/upload/contents/thumbs/Logo.png', 'image', 'png', 'Logo'),
(17, '/tmp/upload/contents/Ronaldo.jpg', 'image', 'jpeg', '/tmp/upload/contents/Ronaldo.jpg', 'image', 'jpeg', '/tmp/upload/contents/thumbs/Ronaldo.jpeg', 'image', 'jpeg', 'Ronaldo');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `idCategory` int(11) NOT NULL,
  `category` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`idCategory`, `category`) VALUES
(4, 'Animals'),
(3, 'Movies'),
(2, 'Music'),
(1, 'Sports');

-- --------------------------------------------------------

--
-- Table structure for table `email-accounts`
--

CREATE TABLE `email-accounts` (
  `id` int(11) NOT NULL,
  `accountName` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `smtpServer` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `port` int(11) NOT NULL,
  `useSSL` tinyint(4) NOT NULL,
  `timeout` int(11) NOT NULL,
  `loginName` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `displayName` varchar(128) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `email-accounts`
--

INSERT INTO `email-accounts` (`id`, `accountName`, `smtpServer`, `port`, `useSSL`, `timeout`, `loginName`, `password`, `email`, `displayName`) VALUES
(1, 'Gmail - SMI', 'smtp.gmail.com', 465, 1, 30, 'xpmasx@gmail.com', 'regm ntfj oewu hozf', 'xpmasx@gmail.com', 'VELESPEDIA');

-- --------------------------------------------------------

--
-- Table structure for table `images-config`
--

CREATE TABLE `images-config` (
  `id` int(11) NOT NULL,
  `destination` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `maxFileSize` int(11) NOT NULL,
  `thumbType` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `thumbWidth` int(11) NOT NULL,
  `thumbHeight` int(11) NOT NULL,
  `numColls` int(11) NOT NULL,
  `cellspacing` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `images-config`
--

INSERT INTO `images-config` (`id`, `destination`, `maxFileSize`, `thumbType`, `thumbWidth`, `thumbHeight`, `numColls`, `cellspacing`) VALUES
(1, '/tmp/upload/contents', 52428800, 'png', 80, 80, 3, 10);

-- --------------------------------------------------------

--
-- Table structure for table `perfil`
--

CREATE TABLE `perfil` (
  `idUser` int(11) NOT NULL,
  `name` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `registerDate` date NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `perfil`
--

INSERT INTO `perfil` (`idUser`, `name`, `password`, `email`, `registerDate`, `active`) VALUES
(1, 'user1', 'pass1', 'user1@isel.pt', '2024-05-29', 1),
(2, 'Pedro', 'pass1', 'xpmasx@gmail.com', '2024-05-29', 1),
(3, 'padrola', 'pass1', 'pedro.silva.tic@hotmail.com', '2024-06-01', 1),
(5, 'Test', '1234', 'a48965@alunos.isel.pt', '2024-06-12', 1);

-- --------------------------------------------------------

--
-- Table structure for table `perfil-challenge`
--

CREATE TABLE `perfil-challenge` (
  `idUser` int(11) NOT NULL,
  `challenge` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `registerDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `perfil-permissions`
--

CREATE TABLE `perfil-permissions` (
  `idRole` int(11) NOT NULL,
  `idUser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `perfil-permissions`
--

INSERT INTO `perfil-permissions` (`idRole`, `idUser`) VALUES
(1, 1),
(2, 2),
(3, 3),
(3, 5);

-- --------------------------------------------------------

--
-- Table structure for table `perfil-roles`
--

CREATE TABLE `perfil-roles` (
  `idRole` int(11) NOT NULL,
  `friendlyName` varchar(64) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `perfil-roles`
--

INSERT INTO `perfil-roles` (`idRole`, `friendlyName`) VALUES
(1, 'Administrador'),
(2, 'Simpatizante'),
(3, 'Utilizador');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`idArticle`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `article-images`
--
ALTER TABLE `article-images`
  ADD PRIMARY KEY (`idArticle`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`idCategory`),
  ADD UNIQUE KEY `Category` (`category`);

--
-- Indexes for table `email-accounts`
--
ALTER TABLE `email-accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `perfil-challenge`
--
ALTER TABLE `perfil-challenge`
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `perfil-permissions`
--
ALTER TABLE `perfil-permissions`
  ADD KEY `idRole` (`idRole`),
  ADD KEY `idUser` (`idUser`);

--
-- Indexes for table `perfil-roles`
--
ALTER TABLE `perfil-roles`
  ADD PRIMARY KEY (`idRole`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `article`
--
ALTER TABLE `article`
  MODIFY `idArticle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `article-images`
--
ALTER TABLE `article-images`
  MODIFY `idArticle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `idCategory` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `email-accounts`
--
ALTER TABLE `email-accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `perfil`
--
ALTER TABLE `perfil`
  MODIFY `idUser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
