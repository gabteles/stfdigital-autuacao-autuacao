create table autuacao.classe (sig_classe varchar2(6) not null, nom_classe varchar2(80) not null, constraint pk_classe primary key (sig_classe));

create table autuacao.preferencia (seq_preferencia number not null, nom_preferencia varchar2(100) not null, constraint pk_preferencia primary key (seq_preferencia));

create table autuacao.classe_preferencia (sig_classe varchar2(6) not null, seq_preferencia number not null, constraint pk_classe_preferencia primary key (sig_classe, seq_preferencia));
alter table autuacao.classe_preferencia add constraint fk_classe_clpr foreign key (sig_classe) references autuacao.classe(sig_classe);
alter table autuacao.classe_preferencia add constraint fk_preferencia_clpr foreign key (seq_preferencia) references autuacao.preferencia(seq_preferencia);

create table autuacao.processo_preferencia (seq_processo number not null, seq_preferencia number not null, constraint pk_processo_preferencia primary key (seq_processo, seq_preferencia));
alter table autuacao.processo_preferencia add constraint fk_processo_prpr foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.processo_preferencia add constraint fk_preferencia_prpr foreign key (seq_preferencia) references autuacao.preferencia(seq_preferencia);

create sequence autuacao.seq_peca increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.tipo_peca (seq_tipo_documento number not null, nom_tipo_documento varchar2(100) not null, constraint pk_tipo_peca primary key (seq_tipo_documento));

create table autuacao.peca (seq_peca number not null, seq_processo number not null, dsc_peca varchar2(100) not null, seq_tipo_peca number not null, seq_documento number not null, constraint pk_peca primary key (seq_peca));
alter table autuacao.peca add constraint fk_processo_peca foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.peca add constraint fk_tipo_peca_peca foreign key (seq_tipo_peca) references autuacao.tipo_peca(seq_tipo_documento);

create sequence autuacao.seq_parte increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.parte (seq_parte number not null, seq_processo number not null, nom_apresentacao varchar2(100) not null, tip_polo varchar2(11) not null, seq_pessoa number, constraint pk_parte primary key (seq_parte));
alter table autuacao.parte add constraint fk_processo_part foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.parte add constraint ck_part_tip_polo check (tip_polo in ('ATIVO', 'PASSIVO', 'INTERESSADO'));

alter table autuacao.processo add column num_processo integer;
alter table autuacao.processo add column sig_autuador varchar2(30);
alter table autuacao.processo add column dat_autuacao date;
alter table autuacao.processo add constraint fk_classe_proc foreign key (sig_classe) references autuacao.classe(sig_classe);
alter table autuacao.processo add constraint uk_proc_sig_classe unique (sig_classe, num_processo);

create table autuacao.rejeicao (seq_processo number not null, dsc_motivo varchar2(1000) not null, constraint pk_rejeicao primary key (seq_processo));
alter table autuacao.rejeicao add constraint fk_processo_reje foreign key (seq_processo) references autuacao.processo(seq_processo);