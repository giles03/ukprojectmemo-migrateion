ALTER TABLE PMEMO2.PM_DRAFT_PHYSICAL
ADD (SUPPLEMENTARY_TITLE VARCHAR2(100 BYTE) DEFAULT NULL);

ALTER TABLE PMEMO2.PM_DETAIL_PHYSICAL
ADD (SUPPLEMENTARY_TITLE VARCHAR2(100 BYTE) DEFAULT NULL);

ALTER TABLE PMEMO2.PM_DRAFT_DIGITAL
ADD (SUPPLEMENTARY_TITLE VARCHAR2(100 BYTE) DEFAULT NULL);

ALTER TABLE PMEMO2.PM_DETAIL_DIGITAL
ADD (SUPPLEMENTARY_TITLE VARCHAR2(100 BYTE) DEFAULT NULL);