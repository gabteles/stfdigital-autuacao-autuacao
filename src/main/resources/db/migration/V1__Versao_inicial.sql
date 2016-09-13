create schema autuacao;

create sequence autuacao.seq_evento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create sequence autuacao.seq_processo increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table autuacao.processo (seq_processo number not null, seq_protocolo number, sig_classe varchar2(6) not null, tip_status varchar2(15) not null, constraint pk_processo primary key (seq_processo));
alter table autuacao.processo add constraint ck_proc_tip_status check (tip_status in ('AUTUACAO', 'AUTUADO', 'REJEITADO'));

create table autuacao.evento (seq_evento number not null, nom_evento varchar2(100) not null, dat_criacao date not null, bin_detalhe clob not null, tip_status smallint not null, constraint pk_evento primary key (seq_evento));

create table autuacao.processo_evento (seq_processo number not null, seq_evento number not null, constraint pk_processo_evento primary key (seq_processo, seq_evento));
alter table autuacao.processo_evento add constraint fk_processo_prev foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.processo_evento add constraint fk_evento_prev foreign key (seq_evento) references autuacao.evento(seq_evento);