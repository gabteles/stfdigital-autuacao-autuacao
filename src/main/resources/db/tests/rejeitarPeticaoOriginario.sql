SELECT SET(@proc_def_id_, ID_) FROM PUBLIC.ACT_RE_PROCDEF WHERE NAME_ = 'originarios';

INSERT INTO AUTUACAO.PROCESSO(SEQ_PROCESSO, SEQ_PROTOCOLO, SIG_CLASSE, TIP_STATUS, NUM_PROCESSO, SIG_AUTUADOR, DAT_AUTUACAO, QTD_RECURSO, TIP_MEIO_TRAMITACAO, TIP_SIGILO, FLG_PROCESSO_APTO, TXT_ANALISE_PRESSUPOSTO_FORMAL, TXT_ANALISE_REPERCUSSAO_GERAL, TIP_PROCESSO) VALUES
(9003, 9001, 'AP', 'AUTUACAO', NULL, NULL, NULL, NULL, 'ELETRONICO', 'PUBLICO', NULL, NULL, NULL, 'ORIGINARIO');               

INSERT INTO PUBLIC.ACT_GE_BYTEARRAY(ID_, REV_, NAME_, DEPLOYMENT_ID_, BYTES_, GENERATED_) VALUES
('-26', 1, 'var-informationId', NULL, X'aced00057372002a62722e6a75732e7374662e636f72652e7368617265642e70726f636573736f2e50726f636573736f496400000000000000010200014c000269647400104c6a6176612f6c616e672f4c6f6e673b78707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000003', NULL),
('-28', 1, 'hist.var-informationId', NULL, X'aced00057372002a62722e6a75732e7374662e636f72652e7368617265642e70726f636573736f2e50726f636573736f496400000000000000010200014c000269647400104c6a6176612f6c616e672f4c6f6e673b78707372000e6a6176612e6c616e672e4c6f6e673b8be490cc8f23df0200014a000576616c7565787200106a6176612e6c616e672e4e756d62657286ac951d0b94e08b02000078700000000000000003', NULL);        

INSERT INTO PUBLIC.ACT_RU_EXECUTION(ID_, REV_, PROC_INST_ID_, BUSINESS_KEY_, PARENT_ID_, PROC_DEF_ID_, SUPER_EXEC_, ACT_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, SUSPENSION_STATE_, CACHED_ENT_STATE_, TENANT_ID_, NAME_, LOCK_TIME_) VALUES
('-23', 1, '-23', 'OR:9003', NULL, @proc_def_id_, NULL, 'autuar-originario', TRUE, FALSE, TRUE, FALSE, 1, 2, NULL, NULL, NULL);           

INSERT INTO PUBLIC.ACT_RU_TASK(ID_, REV_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, TASK_DEF_KEY_, OWNER_, ASSIGNEE_, DELEGATION_, PRIORITY_, CREATE_TIME_, DUE_DATE_, CATEGORY_, SUSPENSION_STATE_, TENANT_ID_, FORM_KEY_) VALUES
('-30', 1, '-23', '-23', @proc_def_id_, STRINGDECODE('Autuar Processo Origin\u00e1rio'), NULL, 'AUTUACAO', 'autuar-originario', NULL, NULL, NULL, 50, TIMESTAMP '2016-06-02 15:15:40.625', NULL, NULL, 1, NULL, NULL);      

INSERT INTO PUBLIC.ACT_RU_IDENTITYLINK(ID_, REV_, GROUP_ID_, TYPE_, USER_ID_, TASK_ID_, PROC_INST_ID_, PROC_DEF_ID_) VALUES
('-31', 1, 'autuador', 'candidate', NULL, '-30', NULL, NULL);      

INSERT INTO PUBLIC.ACT_RU_VARIABLE(ID_, REV_, TYPE_, NAME_, EXECUTION_ID_, PROC_INST_ID_, TASK_ID_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_) VALUES
('-25', 1, 'string', 'transition', '-23', '-23', NULL, NULL, NULL, NULL, NULL, NULL),
('-27', 1, 'serializable', 'informationId', '-23', '-23', NULL, '-26', NULL, NULL, NULL, NULL);          

INSERT INTO PUBLIC.ACT_HI_PROCINST(ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, DELETE_REASON_, TENANT_ID_, NAME_) VALUES
('-23', '-23', 'OR:9003', @proc_def_id_, TIMESTAMP '2016-06-02 15:15:40.624', NULL, NULL, NULL, 'inicio', NULL, NULL, NULL, NULL, NULL);             

INSERT INTO PUBLIC.ACT_HI_ACTINST(ID_, PROC_DEF_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, TENANT_ID_) VALUES
('-24', @proc_def_id_, '-23', '-23', 'inicio', NULL, NULL, STRINGDECODE('Enviar Peti\u00e7\u00e3o Digital'), 'startEvent', NULL, TIMESTAMP '2016-06-02 15:15:40.624', TIMESTAMP '2016-06-02 15:15:40.625', 1, NULL),
('-29', @proc_def_id_, '-23', '-23', 'autuar-originario', '-30', NULL, STRINGDECODE('Autuar Processo Origin\u00e1rio'), 'userTask', NULL, TIMESTAMP '2016-06-02 15:15:40.625', NULL, NULL, NULL);               

INSERT INTO PUBLIC.ACT_HI_TASKINST(ID_, PROC_DEF_ID_, TASK_DEF_KEY_, PROC_INST_ID_, EXECUTION_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, CLAIM_TIME_, END_TIME_, DURATION_, DELETE_REASON_, PRIORITY_, DUE_DATE_, FORM_KEY_, CATEGORY_, TENANT_ID_) VALUES
('-30', @proc_def_id_, 'autuar-originario', '-23', '-23', STRINGDECODE('Autuar Processo Origin\u00e1rio'), NULL, 'AUTUACAO', NULL, NULL, TIMESTAMP '2016-06-02 15:15:40.625', NULL, NULL, NULL, NULL, 50, NULL, NULL, NULL, NULL);  

INSERT INTO PUBLIC.ACT_HI_VARINST(ID_, PROC_INST_ID_, EXECUTION_ID_, TASK_ID_, NAME_, VAR_TYPE_, REV_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, CREATE_TIME_, LAST_UPDATED_TIME_) VALUES
('-25', '-23', '-23', NULL, 'transition', 'string', 0, NULL, NULL, NULL, NULL, NULL, TIMESTAMP '2016-06-02 15:15:40.624', TIMESTAMP '2016-06-02 15:15:40.624'),
('-27', '-23', '-23', NULL, 'informationId', 'serializable', 0, '-28', NULL, NULL, NULL, NULL, TIMESTAMP '2016-06-02 15:15:40.625', TIMESTAMP '2016-06-02 15:15:40.625');          

INSERT INTO PUBLIC.ACT_HI_IDENTITYLINK(ID_, GROUP_ID_, TYPE_, USER_ID_, TASK_ID_, PROC_INST_ID_) VALUES
('-31', 'autuador', 'candidate', NULL, '-30', NULL);     