-- phpMyAdmin SQL Dump
-- version 4.4.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 15. Jul 2015 um 19:35
-- Server-Version: 5.6.13
-- PHP-Version: 5.4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `freunde`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `freunde`
--

CREATE TABLE IF NOT EXISTS `freunde` (
  `UUID` text NOT NULL,
  `SpielerName` text NOT NULL,
  `FreundeID` text NOT NULL,
  `FreundschaftsAnfragenID` text NOT NULL,
  `ID` int(255) NOT NULL,
  `einstellungAkzeptieren` int(11) NOT NULL,
  `einstellungPartyNurFreunde` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `freunde`
--
ALTER TABLE `freunde`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `freunde`
--
ALTER TABLE `freunde`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
