spool ExpertResource.lst

CREATE TABLE EXP_RESOURCE
(
  EXP_ID INT, /* Internal primary key field */
  EXP_NAME VARCHAR(50) NOT NULL, /* Exp-User name */
  EXP_EMAIL  VARCHAR(50), /* Exp-User email Id */
  EXP_CONTACT  VARCHAR(15), /* Exp-User contact details */
  IS_ACTIVE BOOLEAN DEFAULT TRUE, /* Expert status: TRUE-Active, FALSE-Otherwise; */
  EXP_LOGIN VARCHAR(50), /* Exp-User login Id */
  EXP_PASS VARCHAR(5)  /* Exp-User password */
)
/

ALTER TABLE EXP_RESOURCE
ADD CONSTRAINT pk_ExpID PRIMARY KEY (EXP_ID)
/

ALTER TABLE EXP_RESOURCE
ADD UNIQUE KEY unq_ExpLogin (EXP_LOGIN)
/

ALTER TABLE EXP_RESOURCE
ADD UNIQUE KEY unq_ExpEmail (EXP_EMAIL)
/

COMMIT;

spool off;
