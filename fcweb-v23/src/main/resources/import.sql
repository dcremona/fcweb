--
-- Dump dei dati per la tabella fc_properties
--
insert into fc_properties (key_,value_) VALUES ('ambiente','PRODUZIONE');
insert into fc_properties (key_,value_) VALUES ('mail.smtp.host','localhost');
insert into fc_properties (key_,value_) VALUES ('PATH_OUTPUT_PDF','/pdf/fc1920');

--insert into fc_properties (key_,value_) VALUES ('ambiente','SVILUPPO');
--insert into fc_properties (key_,value_) VALUES ('mail.smtp.host','fclt.hostingtt.de');
--insert into fc_properties (key_,value_) VALUES ('PATH_WEBAPP','C:\\eclipseNeon\\workspace\\fcWeb-Spring\\src\\main\\webapp');
--insert into fc_properties (key_,value_) VALUES ('PATH_OUTPUT_PDF','C:\\Users\\davide\\Documents\\fantacalcio\\fc1617');

insert into fc_properties (key_,value_) VALUES ('mail.user','notifiche@fclt.hostingtt.it');
insert into fc_properties (key_,value_) VALUES ('mail.password','k!OHGW@%');
insert into fc_properties (key_,value_) VALUES ('mail.transport.protocol','smtp');
insert into fc_properties (key_,value_) VALUES ('mail.smtp.port','25');
insert into fc_properties (key_,value_) VALUES ('mail.smtp.auth','true');
insert into fc_properties (key_,value_) VALUES ('mail.debug','true');

insert into fc_properties (key_,value_) VALUES ('from','notifiche@fclt.hostingtt.it');
insert into fc_properties (key_,value_) VALUES ('to','davide.cremona@gmail.com');
--edvskizzo71@gmail.com;carocci.maurizio@libero.it;luisella.dizenzo@gmail.com;stefano.sln@libero.it;marco_diveroli@libero.it;sdibiaggio@gmail.com;davide.cremona@gmail.com;stefamarangon@gmail.com
insert into fc_properties (key_,value_) VALUES ('ACTIVE_MAIL','true');


insert into fc_properties (key_,value_) VALUES ('1_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('1_UFFICIOSI','0');
insert into fc_properties (key_,value_) VALUES ('2_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('2_UFFICIOSI','0');
insert into fc_properties (key_,value_) VALUES ('3_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('3_UFFICIOSI','0');
insert into fc_properties (key_,value_) VALUES ('4_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('4_UFFICIOSI','0');
insert into fc_properties (key_,value_) VALUES ('5_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('5_UFFICIOSI','0');
insert into fc_properties (key_,value_) VALUES ('6_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('6_UFFICIOSI','0');
insert into fc_properties (key_,value_) VALUES ('7_UFFICIALI','0');
insert into fc_properties (key_,value_) VALUES ('7_UFFICIOSI','0');

--insert into fc_properties (key_,value_) VALUES ('HH_UFFICIALI','16');
--insert into fc_properties (key_,value_) VALUES ('MM_UFFICIALI','30');
--insert into fc_properties (key_,value_) VALUES ('HH_UFFICIOSI','7');
--insert into fc_properties (key_,value_) VALUES ('MM_UFFICIOSI','30');

--insert into fc_properties (key_,value_) VALUES ('FUSO_ORARIO','1');
insert into fc_properties (key_,value_) VALUES ('FUSO_ORARIO','2');
insert into fc_properties (key_,value_) VALUES ('DIVISORE','100');

insert into fc_properties (key_,value_) VALUES ('ID_CAMPIONATO','1');
insert into fc_properties (key_,value_) VALUES ('START','1');
insert into fc_properties (key_,value_) VALUES ('END','19');
--insert into fc_properties (key_,value_) VALUES ('ID_CAMPIONATO','2');
--insert into fc_properties (key_,value_) VALUES ('START','20');
--insert into fc_properties (key_,value_) VALUES ('END','38');

--
-- Dump dei dati per la tabella fc_ruolo
--

insert into fc_ruolo (id_ruolo,desc_ruolo) VALUES ('A', 'Attaccante');
insert into fc_ruolo (id_ruolo,desc_ruolo) VALUES ('C', 'Centrocampista');
insert into fc_ruolo (id_ruolo,desc_ruolo) VALUES ('D', 'Difensore');
insert into fc_ruolo (id_ruolo,desc_ruolo) VALUES ('P', 'Portiere');

--
-- Dump dei dati per la tabella fc_stato_giocatore
--

insert into fc_stato_giocatore (id_stato_giocatore, desc_stato_giocatore) VALUES ('N', 'Non convocato');
insert into fc_stato_giocatore (id_stato_giocatore, desc_stato_giocatore) VALUES ('R', 'Riserva');
insert into fc_stato_giocatore (id_stato_giocatore, desc_stato_giocatore) VALUES ('T', 'Titolare');


--
-- Dump dei dati per la tabella fc_campionato
--

INSERT INTO fc_campionato (ID_CAMPIONATO, DESC_CAMPIONATO, DATA_INIZIO, DATA_FINE, ID_WIN_CAMP, ID_WIN_CLAS) VALUES (1, '2019-20 Ap', NULL, NULL, 0, 0);
INSERT INTO fc_campionato (ID_CAMPIONATO, DESC_CAMPIONATO, DATA_INIZIO, DATA_FINE, ID_WIN_CAMP, ID_WIN_CLAS) VALUES (2, '2019-20 Ch', NULL, NULL, 0, 0);



--
-- Dump dei dati per la tabella fc_tipo_giornata
--

INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (0, 'Prima fase');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (1, '1/2');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (2, '3/4');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (3, '5/6');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (4, '7/8');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (5, 'PlayOff');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (6, 'PlayOut');
INSERT INTO fc_tipo_giornata (ID_TIPO_GIORNATA, DESC_TIPO_GIORNATA) VALUES (7, 'Quarti di finale');


--
-- Dump dei dati per la tabella fc_ris_partita
--

INSERT INTO fc_ris_partita (ID_RIS_PARTITA, DESC_RIS_PARTITA) VALUES (0, 'Pareggio');
INSERT INTO fc_ris_partita (ID_RIS_PARTITA, DESC_RIS_PARTITA) VALUES (1, 'Vinta');
INSERT INTO fc_ris_partita (ID_RIS_PARTITA, DESC_RIS_PARTITA) VALUES (2, 'Persa');



--
-- Dump dei dati per la tabella fc_exp_stat
--

INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (-2, '2000-2001', 'Ap', null, null, null, null, null, null, null, 'GREG', null, null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (-1, '2000-2001', 'Ch', null, null, null, null, null, null, null, 'GREG', 'SKIZZO', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (0, '2001-2002', 'Ap', null, null, null, null, null, null, null, 'SKIZZO', 'SKIZZO', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (1, '2001-2002', 'Ch', null, null, null, null, null, null, null, 'LEO', 'MARCO', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (2, '2002-2003', 'Ap', null, null, null, null, null, null, null, 'RINC', 'CAPPELLA', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (3, '2002-2003', 'Ch', null, null, null, null, null, null, null, 'LEO', 'CAPPELLA', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (4, '2003-2004', 'Ap', null, null, null, null, null, null, null, 'SKIZZO', 'SALONE', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (5, '2003-2004', 'Ch', null, null, null, null, null, null, null, 'SKIZZO', 'RINC', null);
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (6, '2004-2005', 'Ap', 'MARVAL', null, null, null, null, null, null, 'SKIZZO', 'CASKO', 'CASKO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (7, '2004-2005', 'Ch', 'SKIZZO', 'MARVAL', 'CASKO', 'SALONE', 'DAVIDE', 'GREG', 'MARCO', 'RINC', 'MARVAL', 'MARVAL');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (8, '2005-2006', 'Ap', 'RINC', 'SKIZZO', 'DAVIDE', 'MARCO', 'GREG', 'MARVAL', 'CASKO', 'SALONE', 'SKIZZO', 'RINC');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (9, '2005-2006', 'Ch', 'RINC', 'DAVIDE', 'CASKO', 'MARVAL', 'SALONE', 'SKIZZO', 'MARCO', 'GREG', 'RINC', 'RINC');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (10, '2006-2007', 'Ap', 'RINC', 'MARVAL', 'SKIZZO', 'SALONE', 'CASKO', 'DAVIDE', 'MARCO', 'GREG', 'SKIZZO', 'RINC');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (11, '2006-2007', 'Ch', 'DAVIDE', 'SKIZZO', 'GREG', 'LUNA', 'MARVAL', 'MARCO', 'RINC', 'SALONE', 'SALONE', 'SALONE');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (12, '2007-2008', 'Ap', 'MARVAL', 'RINC', 'GREG', 'DAVIDE', 'MARCO', 'SKIZZO', 'CASKO', 'SALONE', 'RINC', 'MARVAL');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (13, '2007-2008', 'Ch', 'MARVAL', 'DAVIDE', 'MARCO', 'GREG', 'SKIZZO', 'RINC', 'LEO', 'SALONE', 'SKIZZO', 'SKIZZO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (14, '2008-2009', 'Ap', 'GREG', 'RINC', 'SALONE', 'DAVIDE', 'MARVAL', 'CASKO', 'MARCO', 'SKIZZO', 'SKIZZO', 'MARVAL');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (15, '2008-2009', 'Ch', 'SALONE', 'SKIZZO', 'DAVIDE', 'GREG', 'MARVAL', 'MARCO', 'RINC', 'CASKO', 'DAVIDE', 'DAVIDE');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (16, '2009-2010', 'Ap', 'SALONE', 'GREG', 'MARCO', 'RINC', 'DAVIDE', 'CASKO', 'MARVAL', 'SKIZZO', 'DAVIDE', 'SKIZZO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (17, '2009-2010', 'Ch', 'GREG', 'SALONE', 'DAVIDE', 'RINC', 'SKIZZO', 'CASKO', 'MARVAL', 'MARCO', 'SALONE', 'SALONE');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (18, '2010-2011', 'Ap', 'SALONE', 'MARVAL', 'MAURIZIO', 'MARCO', 'GREG', 'SKIZZO', 'RINC', 'DAVIDE', 'DAVIDE', 'DAVIDE');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (19, '2010-2011', 'Ch', 'SALONE', 'SKIZZO', 'DAVIDE', 'GREG', 'MARCO', 'MARVAL', 'RINC', 'MAURIZIO', 'MAURIZIO', 'SALONE');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (20, '2011-2012', 'Ap', 'SKIZZO', 'FRANCESCA', 'MARCO', 'DAVIDE', 'GREG', 'MARVAL', 'RINC', 'SALONE', 'MARVAL', 'MARVAL');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (21, '2011-2012', 'Ch', 'MARVAL', 'FRANCESCA', 'SKIZZO', 'MARCO', 'MAURIZIO', 'GREG', 'DAVIDE', 'SALONE', 'MARVAL', 'MARVAL');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (22, '2012-2013', 'Ap', 'SKIZZO', 'GREG', 'DAVIDE', 'SALONE', 'MARCO', 'MARVAL', 'SERGIO', 'FRANCESCA', 'DAVIDE', 'DAVIDE');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (23, '2012-2013', 'Ch', 'SALONE', 'GREG', 'DAVIDE', 'SKIZZO', 'MARVAL', 'FRANCESCA', 'SERGIO', 'MARCO', 'GREG', 'MARCO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (24, '2013-2014', 'Ap', 'SALONE', 'MARVAL', 'SERGIO', 'SKIZZO', 'FRANCESCA', 'DAVIDE', 'MARCO', 'GREG', 'MARVAL', 'SERGIO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (25, '2013-2014', 'Ch', 'GREG', 'SKIZZO', 'SERGIO', 'SALONE', 'FRANCESCA', 'DAVIDE', 'MARCO', 'MARVAL', 'MARVAL', 'MARVAL');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (26, '2014-2015', 'Ap', 'GREG', 'DAVIDE', 'MARVAL', 'SKIZZO', 'MARCO', 'SERGIO', 'FRANCESCA', 'SALONE', 'MARVAL', 'GREG');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (27, '2014-2015', 'Ch', 'SKIZZO', 'FRANCESCA', 'GREG', 'SALONE', 'DAVIDE', 'MARCO', 'MARVAL', 'RINC', 'SKIZZO', 'SKIZZO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (28, '2015-2016', 'Ap', 'MARCO', 'DAVIDE', 'SKIZZO', 'FRANCESCA', 'MARVAL', 'GREG', 'RINC', 'SALONE', 'MARCO', 'MARCO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (29, '2015-2016', 'Ch', 'RINC', 'FRANCESCA', 'MARVAL', 'SKIZZO', 'GREG', 'SALONE', 'DAVIDE', 'MARCO', 'SKIZZO', 'RINC');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (30, '2016-2017', 'Ap', 'MARCO' ,'SALONE', 'SKIZZO', 'RINC', 'FRANCESCA', 'DAVIDE', 'MAURIZIO', 'GREG', 'MARCO', 'GREG');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (31, '2016-2017', 'Ch', 'RINC', 'DAVIDE', 'SALONE', 'MARCO', 'GREG', 'FRANCESCA', 'MAURIZIO', 'SKIZZO', 'SALONE', 'SKIZZO');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (32, '2017-2018', 'Ap', 'SALONE', 'MAURIZIO', 'RINC', 'MARCO', 'GREG', 'DAVIDE', 'SKIZZO', 'FRANCESCA', 'FRANCESCA', 'FRANCESCA');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (33, '2017-2018', 'Ch', 'MAURIZIO', 'DAVIDE', 'SALONE', 'SKIZZO', 'RINC', 'FRANCESCA', 'MARCO', 'GREG', 'GREG', 'GREG');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (34, '2018-2019', 'Ap', 'SALONE', 'MARCO', 'ANDREA', 'RINC', 'GREG', 'DAVIDE', 'FRANCESCA', 'SKIZZO', 'RINC', 'RINC');
INSERT INTO fc_exp_stat (id, anno, campionato, p2, p3, p4, p5, p6, p7, p8, scudetto, win_clas_pt, win_clas_reg) VALUES (35, '2018-2019', 'Ch', 'SALONE', 'ANDREA', 'RINC', 'DAVIDE', 'SKIZZO', 'GREG', 'MARCO', 'FRANCESCA', 'RINC', 'DAVIDE');


--
-- Dump dei dati per la tabella fc_attore
--

INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (-5, '1234', 'Carocci', 'MAURIZIO', 'carocci.maurizio@libero.it', 'runo', 'Maurizio', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (-4, '1234', 'BONINI', 'SERGIO', 'sergio.bonini@hotmail.it', 'runo', 'Sergio', false, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (-3, '3283425913', 'Marval', 'MARVAL', 'm.valvoli@farla.it', 'runo', 'Massimo', false, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (-2, '1234', 'Gardosi', 'CASKO', 'fc@lt.it', 'runo', 'Gianluca', false, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (-1, '1234', 'CAPPELLA', 'CAPPELLA', 'fc@lt.it', 'runo', 'CAPPELLA', false, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (0, '1234', 'LEO', 'LEO', 'fc@lt.it', 'runo', 'LEO', false, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (1, '3294475871', 'Diveroli', 'SKIZZO', 'edvskizzo71@gmail.com', 'runo', 'Enrico', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (2, '1234', 'Zazzarino', 'ANDREA', 'zazzadino71@gmail.com', 'runo', 'Roberto', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (3, '3289459844', 'Di Zenzo', 'FRANCESCA', 'luisella.dizenzo@gmail.com', 'runo', 'Luisella', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (4, '3473758201', 'Salone', 'SALONE', 'stefano.sln@libero.it', 'runo', 'Stefano', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (5, '3383347943', 'Diveroli', 'MARCO', 'marco_diveroli@libero.it', 'runo', 'Marco', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (6, '3478855822', 'Di Biaggio', 'GREG', 'sdibiaggio@gmail.com', 'runo', 'Simone', true, '1234');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (7, '3491240146', 'Cremona', 'DAVIDE', 'davide.cremona@gmail.com', 'runo', 'Davide', true, 'da');
INSERT INTO fc_attore (id_attore, cellulare, cognome, desc_attore, email, grafica, nome, notifiche, password) VALUES (8, '1234', 'Marangon', 'RINC', 'stefamarangon719@gmail.com', 'runo', 'Stefano', true, '1234');


--
-- Dump dei dati per la tabella fc_squadra
--

INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (1, 'ATALANTA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (2, 'BOLOGNA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (3, 'BRESCIA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (4, 'CAGLIARI');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (5, 'FIORENTINA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (6, 'GENOA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (7, 'INTER');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (8, 'JUVENTUS');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (9, 'LAZIO');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (10, 'LECCE');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (11, 'MILAN');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (12, 'NAPOLI');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (13, 'PARMA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (14, 'ROMA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (15, 'SAMPDORIA');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (16, 'SASSUOLO');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (17, 'SPAL');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (18, 'TORINO');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (19, 'UDINESE');
INSERT INTO fc_squadra (ID_SQUADRA, NOME_SQUADRA) VALUES (20, 'VERONA');

--
-- Dump dei dati per la tabella fc_giornata_info
--

INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (1,  'Giornata 1 Andata',   '2018-08-19 17:30:00', 1,  'Prima Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (2,  'Giornata 2 Andata',   '2018-08-26 17:30:00', 2,  'Seconda Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (3,  'Giornata 3 Andata',   '2018-09-02 12:00:00', 3,  'Terza Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (4,  'Giornata 4 Andata',   '2018-09-16 12:00:00', 4,  'Quarta Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (5,  'Giornata 5 Andata',   '2018-09-23 20:15:00', 5,  'Quinta Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (6,  'Giornata 6 Andata',   '2018-09-26 12:00:00', 6,  'Sesta Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (7,  'Giornata 7 Andata',   '2018-09-30 12:00:00', 7,  'Settima Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (8,  'Giornata 8 Andata',   '2018-10-07 12:00:00', 8,  'Prima Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (9,  'Giornata 9 Andata',   '2018-10-21 12:00:00', 9,  'Seconda Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (10, 'Giornata 10 Andata',  '2018-10-28 20:15:00', 10, 'Terza Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (11, 'Giornata 11 Andata',  '2018-11-04 13:00:00', 11, 'Quarta Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (12, 'Giornata 12 Andata',  '2018-11-11 12:00:00', 12, 'Quinta Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (13, 'Giornata 13 Andata',  '2018-11-25 12:00:00', 13, 'Sesta Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (14, 'Giornata 14 Andata',  '2018-12-02 14:30:00', 14, 'Settima Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (15, 'Giornata 15 Andata',  '2018-12-09 12:00:00', 15, 'Quarti Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (16, 'Giornata 16 Andata',  '2018-12-16 14:30:00', 16, 'Quarti Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (17, 'Giornata 17 Andata',  '2018-12-22 12:00:00', 17, 'Semifinali Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (18, 'Giornata 18 Andata',  '2018-12-26 14:30:00', 18, 'Semifinali Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (19, 'Giornata 19 Andata',  '2018-12-29 12:00:00', 19, 'Finalissima', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (20, 'Giornata 1 Ritorno',  '2019-01-20 12:00:00', 1,  'Prima Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (21, 'Giornata 2 Ritorno',  '2019-01-27 12:00:00', 2,  'Seconda Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (22, 'Giornata 3 Ritorno',  '2019-02-03 12:00:00', 3,  'Terza Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (23, 'Giornata 4 Ritorno',  '2019-02-10 18:00:00', 4,  'Quarta Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (24, 'Giornata 5 Ritorno',  '2019-02-17 12:00:00', 5,  'Quinta Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (25, 'Giornata 6 Ritorno',  '2019-02-24 14:30:00', 6,  'Sesta Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (26, 'Giornata 7 Ritorno',  '2019-03-03 14:30:00', 7,  'Settima Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (27, 'Giornata 8 Ritorno',  '2019-03-10 12:00:00', 8,  'Prima Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (28, 'Giornata 9 Ritorno',  '2019-03-17 14:30:00', 9,  'Seconda Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (29, 'Giornata 10 Ritorno', '2019-03-31 14:30:00', 10, 'Terza Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (30, 'Giornata 11 Ritorno', '2019-04-03 14:30:00', 11, 'Quarta Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (31, 'Giornata 12 Ritorno', '2019-04-07 12:00:00', 12, 'Quinta Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (32, 'Giornata 13 Ritorno', '2019-04-14 12:00:00', 13, 'Sesta Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (33, 'Giornata 14 Ritorno', '2019-04-20 12:00:00', 14, 'Settima Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (34, 'Giornata 15 Ritorno', '2019-04-28 14:30:00', 15, 'Quarti Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (35, 'Giornata 16 Ritorno', '2019-05-05 14:30:00', 16, 'Quarti Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (36, 'Giornata 17 Ritorno', '2019-05-12 14:30:00', 17, 'Semifinali Andata', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (37, 'Giornata 18 Ritorno', '2019-05-19 14:30:00', 18, 'Semifinali Ritorno', NULL, NULL);
INSERT INTO fc_giornata_info (CODICE_GIORNATA, DESC_GIORNATA, DATA_GIORNATA, ID_GIORNATA_FC, DESC_GIORNATA_FC, DATA_ANTICIPO, DATA_POSTICIPO) VALUES (38, 'Giornata 19 Ritorno', '2019-05-26 14:30:00', 19, 'Finalissima', NULL, NULL);


