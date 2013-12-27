spool UserContext.lst

CREATE TABLE USER_CONTEXT
(
  CONTEXT_ID INT, /* Internal primary key field */
  MOBILE_HASH VARCHAR(50) NOT NULL, /* Mobile number hash as received */
  REG_STD  VARCHAR(10), /* Registered for 10th, PUC-1, PUC-2 */
  REG_DATE TIMESTAMP NOT NULL, /* Date when the user record was inserted */
  IS_ACTIVE BOOLEAN DEFAULT TRUE, /* TRUE-Active, FALSE-Otherwise; user request based */
  LOCATION VARCHAR(20) /* Additional info, can be received from the provider */
)
/

ALTER TABLE USERCONTEXT
ADD CONSTRAINT pk_ContextID PRIMARY KEY (CONTEXT_ID)
/

COMMIT;

spool off;
