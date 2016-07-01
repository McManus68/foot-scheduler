CREATE TABLE `global_setings` (
  `id` int(11) NOT NULL,
  `adress` varchar(100) NOT NULL,
  `event_day` varchar(20) NOT NULL,
  `event_hour` int(2) NOT NULL,
  `max_players` int(2) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `foot_event` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `player_settings` (
  `id` int(11) NOT NULL,
  `notify_new_event` bit(1) DEFAULT NULL,
  `notify_team_composition` bit(1) DEFAULT NULL,
  `notify_to_main_list` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `player` (
  `id` int(11) NOT NULL,
  `mail` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  `username` varchar(30) NOT NULL,
  `settings_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Key_Player_Setttings` (`settings_id`),
  CONSTRAINT `FK_Player_Setttings` FOREIGN KEY (`settings_id`) REFERENCES `player_settings` (`id`)
);

CREATE TABLE `foot_event_player` (
  `id` int(11) NOT NULL,
  `registration_date` timestamp NOT NULL,
  `foot_event_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Key_Foot_Event_Event` (`foot_event_id`),
  KEY `Key_Foot_Event_PLAYER` (`player_id`),
  CONSTRAINT `FK_Foot_Event_Event` FOREIGN KEY (`foot_event_id`) REFERENCES `foot_event` (`id`),
  CONSTRAINT `FK_Foot_Event_PLAYER` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
);
