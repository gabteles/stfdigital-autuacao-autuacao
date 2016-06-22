alter table autuacao.classe add column tip_processo varchar2(10) not null;
alter table autuacao.classe add constraint ck_clas_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));

create table autuacao.pais (seq_pais number not null, nom_pais varchar2(72) not null, constraint pk_pais primary key (seq_pais));

create table autuacao.unidade_federacao (seq_unidade_federacao number not null, nom_unidade_federacao varchar2(109) not null, sig_unidade_federacao varchar2(2) not null, seq_pais number not null, constraint pk_unidade_federacao primary key (seq_unidade_federacao));
alter table autuacao.unidade_federacao add constraint fk_pais_unfe foreign key (seq_pais) references autuacao.pais(seq_pais);

create table autuacao.assunto (cod_assunto varchar2(9) not null, dsc_assunto varchar2(255) not null, cod_assunto_pai varchar2(9), constraint pk_assunto primary key (cod_assunto));
alter table autuacao.assunto add constraint fk_assunto_assu foreign key (cod_assunto_pai) references autuacao.assunto(cod_assunto);

create table autuacao.tese (seq_tese number not null, dsc_tese varchar2(4000) not null, tip_tese varchar2(17) not null, num_tese number not null, constraint pk_tese primary key (seq_tese));
alter table autuacao.tese add constraint ck_tese_tip_tese check (tip_tese in ('CONTROVERSIA', 'PRE_TEMA', 'REPERCUSSAO_GERAL'));

create table autuacao.tese_assunto (seq_tese number not null, cod_assunto varchar2(9), constraint pk_tese_assunto primary key (seq_tese, cod_assunto));
alter table autuacao.tese_assunto add constraint fk_tese_teas foreign key (seq_tese) references autuacao.tese(seq_tese);
alter table autuacao.tese_assunto add constraint fk_assunto_teas foreign key (cod_assunto) references autuacao.assunto(cod_assunto);

create table autuacao.tribunal_juizo (cod_tribunal_juizo number not null, nom_tribunal_juizo varchar2(200) not null, constraint pk_tribunal_juizo primary key (cod_tribunal_juizo));

create table autuacao.tribunal_juizo_atuacao (cod_tribunal_juizo number not null, seq_unidade_federacao number not null, constraint pk_tribunal_juizo_atuacao primary key (cod_tribunal_juizo, seq_unidade_federacao));
alter table autuacao.tribunal_juizo_atuacao add constraint fk_unidade_federacao_tjat foreign key (seq_unidade_federacao) references autuacao.unidade_federacao(seq_unidade_federacao);
alter table autuacao.tribunal_juizo_atuacao add constraint fk_tribunal_juizo_tjat foreign key (cod_tribunal_juizo) references autuacao.tribunal_juizo(cod_tribunal_juizo);

create table autuacao.motivo_inaptidao (cod_motivo_inaptidao number not null, dsc_motivo_inaptidao varchar2(500) not null, constraint pk_motivo_inaptidao primary key (cod_motivo_inaptidao));
alter table autuacao.motivo_inaptidao add constraint uk_moin_dsc_motivo_inaptidao unique (dsc_motivo_inaptidao);

create table autuacao.processo_assunto (seq_processo number not null, cod_assunto varchar2(9) not null, constraint pk_processo_assunto primary key (seq_processo, cod_assunto));
alter table autuacao.processo_assunto add constraint fk_assunto_pras foreign key (cod_assunto) references autuacao.assunto(cod_assunto);
alter table autuacao.processo_assunto add constraint fk_processo_pras foreign key (seq_processo) references autuacao.processo(seq_processo);

create table autuacao.processo_tese (seq_processo number not null, seq_tese number not null, constraint pk_processo_tese primary key (seq_processo, seq_tese));
alter table autuacao.processo_tese add constraint fk_tese_prte foreign key (seq_tese) references autuacao.tese(seq_tese);
alter table autuacao.processo_tese add constraint fk_processo_prte foreign key (seq_processo) references autuacao.processo(seq_processo);

create table autuacao.motivacao_inaptidao (seq_processo number not null, cod_motivo_inaptidao number not null, constraint pk_motivacao_inaptidao primary key (seq_processo, cod_motivo_inaptidao));
alter table autuacao.motivacao_inaptidao add constraint fk_processo_moin foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.motivacao_inaptidao add constraint fk_motivo_inaptidao_moin foreign key (cod_motivo_inaptidao) references autuacao.motivo_inaptidao(cod_motivo_inaptidao);


create sequence autuacao.seq_processo_origem increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;
create table autuacao.processo_origem (seq_processo_origem number not null, seq_processo number not null, cod_tribunal_juizo number not null, seq_unidade_federacao number not null, num_processo number not null, flg_principal varchar2(1) default 'N' not null, constraint pk_processo_origem primary key (seq_processo_origem));
alter table autuacao.processo_origem add constraint uk_pror_seq_processo unique (seq_processo, cod_tribunal_juizo, seq_unidade_federacao, num_processo);
alter table autuacao.processo_origem add constraint fk_processo_pror foreign key (seq_processo) references autuacao.processo(seq_processo);
alter table autuacao.processo_origem add constraint fk_tribunal_juizo_pror foreign key (cod_tribunal_juizo) references autuacao.tribunal_juizo(cod_tribunal_juizo);
alter table autuacao.processo_origem add constraint fk_unidade_federacao_pror foreign key (seq_unidade_federacao) references autuacao.unidade_federacao(seq_unidade_federacao);
alter table autuacao.processo_origem add constraint ck_pror_flg_principal check (flg_principal in ('Y', 'N'));

alter table autuacao.processo add column qtd_recurso integer;
alter table autuacao.processo add tip_meio_tramitacao varchar2(10) not null;
alter table autuacao.processo add constraint ck_proc_tip_meio_tramitacao check (tip_meio_tramitacao in ('ELETRONICO', 'FISICO'));
alter table autuacao.processo add tip_sigilo varchar2(15) not null;
alter table autuacao.processo add constraint ck_proc_tip_sigilo check (tip_sigilo in ('PUBLICO', 'SEGREDO_JUSTICA'));
alter table autuacao.processo add flg_analise_apta varchar2(1);
alter table autuacao.processo add txt_analise_pressuposto_formal varchar2(1000);
alter table autuacao.processo add constraint ck_proc_flg_analise_apta check (flg_analise_apta is null or flg_analise_apta in ('Y', 'N'));
alter table autuacao.processo add txt_analise_repercussao_geral varchar2(1000);
alter table autuacao.processo add column tip_processo varchar2(10) not null;
alter table autuacao.processo add constraint ck_proc_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));
alter table autuacao.processo alter column tip_status varchar(20) not null;
alter table autuacao.processo drop constraint ck_proc_tip_status;
alter table autuacao.processo add constraint ck_proc_tip_status check (tip_status in ('AUTUACAO', 'AUTUADO', 'REJEITADO', 'ANALISAR_PRESSUPOSTO', 'REVISAR_PRESSUPOSTO', 'ANALISAR_RG', 'REVISAR_RG'));