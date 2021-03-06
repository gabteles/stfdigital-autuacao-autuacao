SELECT SET(@proc_def_id_, ID_) FROM PUBLIC.ACT_RE_PROCDEF WHERE NAME_ = 'recursais';

INSERT INTO AUTUACAO.PROCESSO(SEQ_PROCESSO, SEQ_PROTOCOLO, SIG_CLASSE, TIP_STATUS, NUM_PROCESSO, SIG_AUTUADOR, DAT_AUTUACAO, QTD_RECURSO, TIP_MEIO_TRAMITACAO, TIP_SIGILO, FLG_PROCESSO_APTO, TXT_ANALISE_PRESSUPOSTO_FORMAL, TXT_ANALISE_REPERCUSSAO_GERAL, TIP_PROCESSO) VALUES
(9008, 9010, 'RE', 'ANALISAR_RG', 503, NULL, NULL, NULL, 'ELETRONICO', 'PUBLICO', 'Y', NULL, NULL, 'RECURSAL');

INSERT INTO PUBLIC.ACT_GE_BYTEARRAY(ID_, REV_, NAME_, DEPLOYMENT_ID_, BYTES_, GENERATED_) VALUES
('-75', 1, 'var-informationId', NULL, X'aced00057372002a62722e6a75732e7374662e636f72652e7368617265642e70726f636573736f2e50726f636573736f496400000000000000010200014c000269647400104c6a6176612f6c616e672f4c6f6e673b78707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000001', NULL),
('-76', 1, 'hist.var-informationId', NULL, X'aced00057372002a62722e6a75732e7374662e636f72652e7368617265642e70726f636573736f2e50726f636573736f496400000000000000010200014c000269647400104c6a6176612f6c616e672f4c6f6e673b78707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000001', NULL); 

INSERT INTO PUBLIC.ACT_RU_EXECUTION(ID_, REV_, PROC_INST_ID_, BUSINESS_KEY_, PARENT_ID_, PROC_DEF_ID_, SUPER_EXEC_, ACT_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, SUSPENSION_STATE_, CACHED_ENT_STATE_, TENANT_ID_, NAME_, LOCK_TIME_) VALUES
('-77', 2, '-77', 'RE:9008', NULL, @proc_def_id_, NULL, 'analisar-repercussao-geral', TRUE, FALSE, TRUE, FALSE, 1, 2, NULL, NULL, NULL);         

INSERT INTO PUBLIC.ACT_RU_TASK(ID_, REV_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, TASK_DEF_KEY_, OWNER_, ASSIGNEE_, DELEGATION_, PRIORITY_, CREATE_TIME_, DUE_DATE_, CATEGORY_, SUSPENSION_STATE_, TENANT_ID_, FORM_KEY_) VALUES
('-78', 1, '-77', '-77', @proc_def_id_, 'Analisar Assunto/RG', NULL, 'ANALISAR_RG', 'analisar-repercussao-geral', NULL, NULL, NULL, 50, TIMESTAMP '2016-07-29 17:49:19.448', NULL, NULL, 1, NULL, NULL);             

INSERT INTO PUBLIC.ACT_RU_IDENTITYLINK(ID_, REV_, GROUP_ID_, TYPE_, USER_ID_, TASK_ID_, PROC_INST_ID_, PROC_DEF_ID_) VALUES
('-79', 1, 'analista-repercussao-geral', 'candidate', NULL, '-78', NULL, NULL);    

INSERT INTO PUBLIC.ACT_RU_VARIABLE(ID_, REV_, TYPE_, NAME_, EXECUTION_ID_, PROC_INST_ID_, TASK_ID_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_) VALUES
('-80', 1, 'serializable', 'informationId', '-77', '-77', NULL, '-75', NULL, NULL, NULL, NULL),
('-81', 2, 'string', 'transition', '-77', '-77', NULL, NULL, NULL, NULL, '', NULL);            

INSERT INTO PUBLIC.ACT_HI_PROCINST(ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, DELETE_REASON_, TENANT_ID_, NAME_) VALUES
('-77', '-77', 'RE:9008', @proc_def_id_, TIMESTAMP '2016-07-29 17:49:19.218', NULL, NULL, NULL, 'inicio', NULL, NULL, NULL, NULL, NULL); 

INSERT INTO PUBLIC.ACT_HI_ACTINST(ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, TENANT_ID_) VALUES
('-82', @proc_def_id_, '-77', '-77', 'inicio', NULL, NULL, 'Enviar Processo Recursal', 'startEvent', NULL, TIMESTAMP '2016-07-29 17:49:19.218', TIMESTAMP '2016-07-29 17:49:19.238', 20, NULL),
('-83', @proc_def_id_, '-77', '-77', 'tem-preferencia-criminal-eleitoral', NULL, NULL, STRINGDECODE('Prefer\u00eancia Criminal /Eleitoral?'), 'exclusiveGateway', NULL, TIMESTAMP '2016-07-29 17:49:19.238', TIMESTAMP '2016-07-29 17:49:19.248', 10, NULL),
('-84', @proc_def_id_, '-77', '-77', 'tem-aptidao', NULL, NULL, STRINGDECODE('Classifica\u00e7\u00e3o?'), 'exclusiveGateway', NULL, TIMESTAMP '2016-07-29 17:49:19.448', TIMESTAMP '2016-07-29 17:49:19.448', 0, NULL),
('-85', @proc_def_id_, '-77', '-77', 'analisar-repercussao-geral', '-78', NULL, 'Analisar Assunto/RG', 'userTask', NULL, TIMESTAMP '2016-07-29 17:49:19.448', NULL, NULL, NULL),
('-86', @proc_def_id_, '-77', '-77', 'analisar-pressupostos-formais', '-87', NULL, 'Analisar Pressupostos Formais', 'userTask', NULL, TIMESTAMP '2016-07-29 17:49:19.248', TIMESTAMP '2016-07-29 17:49:19.448', 200, NULL);         

INSERT INTO PUBLIC.ACT_HI_TASKINST(ID_, PROC_DEF_ID_, TASK_DEF_KEY_, PROC_INST_ID_, EXECUTION_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, CLAIM_TIME_, END_TIME_, DURATION_, DELETE_REASON_, PRIORITY_, DUE_DATE_, FORM_KEY_, CATEGORY_, TENANT_ID_) VALUES
('-78', @proc_def_id_, 'analisar-repercussao-geral', '-77', '-77', 'Analisar Assunto/RG', NULL, 'ANALISAR_RG', NULL, NULL, TIMESTAMP '2016-07-29 17:49:19.448', NULL, NULL, NULL, NULL, 50, NULL, NULL, NULL, NULL),
('-87', @proc_def_id_, 'analisar-pressupostos-formais', '-77', '-77', 'Analisar Pressupostos Formais', NULL, 'ANALISAR_PRESSUPOSTO', NULL, NULL, TIMESTAMP '2016-07-29 17:49:19.248', NULL, TIMESTAMP '2016-07-29 17:49:19.438', 190, 'completed', 50, NULL, NULL, NULL, NULL);   

INSERT INTO PUBLIC.ACT_HI_VARINST(ID_, PROC_INST_ID_, EXECUTION_ID_, TASK_ID_, NAME_, VAR_TYPE_, REV_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, CREATE_TIME_, LAST_UPDATED_TIME_) VALUES
('-80', '-77', '-77', NULL, 'informationId', 'serializable', 1, '-76', NULL, NULL, NULL, NULL, TIMESTAMP '2016-07-29 17:49:19.228', TIMESTAMP '2016-07-29 17:49:19.438'),
('-81', '-77', '-77', NULL, 'transition', 'string', 1, NULL, NULL, NULL, '', NULL, TIMESTAMP '2016-07-29 17:49:19.218', TIMESTAMP '2016-07-29 17:49:19.438');   

INSERT INTO PUBLIC.ACT_HI_IDENTITYLINK(ID_, GROUP_ID_, TYPE_, USER_ID_, TASK_ID_, PROC_INST_ID_) VALUES
('-88', 'analista-pressupostos', 'candidate', NULL, '-87', NULL),
('-79', 'analista-repercussao-geral', 'candidate', NULL, '-78', NULL);        