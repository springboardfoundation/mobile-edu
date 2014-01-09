spool UserContext.lst

CREATE OR REPLACE TABLE USER_CONTEXT
(
  CONTEXT_ID INT, /* Internal primary key field */
  MOBILE_HASH VARCHAR(50) NOT NULL, /* Mobile number hash as received */
  REG_STD  VARCHAR(10), /* Registered for 10th, PUC-1, PUC-2 */
  REG_DATE TIMESTAMP NOT NULL, /* Date when the user record was inserted */
  IS_ACTIVE BOOLEAN DEFAULT TRUE, /* TRUE-Active, FALSE-Otherwise; user request based */
  LOCATION VARCHAR(50), /* Additional info, can be received from the provider */
  PROTOCOL VARCHAR(30)  /* Protocol info of the way registration was done */
)
/

ALTER TABLE USER_CONTEXT
ADD CONSTRAINT pk_ContextID PRIMARY KEY (CONTEXT_ID)
/

COMMIT;

spool off;
