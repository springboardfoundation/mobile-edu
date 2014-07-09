spool MessageAsset.lst

CREATE TABLE MESSAGE_ASSET
(
  MSG_ID INT, /* Internal primary key field */
  MESSAGE VARCHAR(500) NOT NULL, /* Main content - SMS to be sent */
  MSG_STD VARCHAR(10), /* Is it for 10th, PUC-1, PUC-2 */
  MSG_SUB VARCHAR(10), /* Is it for Science, Maths, Social-Science... */
  INSERT_DATE TIMESTAMP NOT NULL, /* Date when the SMS was inserted */
  SENT_DATE TIMESTAMP, /* Date when the SMS was broadcasted */
  IS_ACTIVE BOOLEAN DEFAULT TRUE /* Added to make it easier to handle SMS that needs to be sent */
)
/

ALTER TABLE MESSAGE_ASSET
ADD CONSTRAINT pk_MsgID PRIMARY KEY (MSG_ID)
/

COMMIT;

spool off;
