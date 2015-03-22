spool MessageAsset.lst

CREATE TABLE MESSAGE_ASSET
(
  MSG_ID INT, /* Internal primary key field */
  MESSAGE VARCHAR(500) NOT NULL, /* Main content - SMS to be sent */
  MSG_STD VARCHAR(10), /* Is it for 10th, PUC-1, PUC-2 */
  MSG_SUB VARCHAR(10), /* Is it for Science, Maths, Social-Science... */
  INSERT_DATE TIMESTAMP NOT NULL, /* Date when the SMS was inserted */
  SENT_DATE TIMESTAMP, /* Date when the SMS was broadcasted */
  MSG_TYPE BOOLEAN DEFAULT TRUE, /* if true=message, false =question */ 
  IS_ACTIVE BOOLEAN DEFAULT TRUE, /* Added to make it easier to handle SMS that needs to be sent */
  USER_ID INT /* mapping to context_id of Usercontext */
)
/

ALTER TABLE MESSAGE_ASSET
ADD CONSTRAINT pk_MsgID PRIMARY KEY (MSG_ID)
/

ALTER TABLE MESSAGE_ASSET
ADD CONSTRAINT fk_UsrId_CtxId 
FOREIGN KEY (USER_ID)
REFERENCES USER_CONTEXT (CONTEXT_ID)

COMMIT;

spool off;
