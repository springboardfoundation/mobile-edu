spool AnswerCluster.lst

CREATE TABLE ANS_CLUSTER
(
  ANS_ID INT, /* Internal primary key field for Answer*/
  ANS_CONTENT VARCHAR(250) NOT NULL, /* The actual answer content */
  Q_ID  VARCHAR(5), /* Question ID mapping to the question */
  E_ID VARCHAR(10), /* Expert ID mapping to the expert who has answered */
  ANS_DATE TIMESTAMP NOT NULL, /* Date when the answer was recorded */
  IS_SENT BOOLEAN DEFAULT TRUE /* Delivery status: TRUE-Sent to user, FALSE-Not sent */
)
/

ALTER TABLE ANS_CLUSTER
ADD CONSTRAINT pk_AnsID PRIMARY KEY (ANS_ID)
/

ALTER TABLE ANS_CLUSTER
ADD CONSTRAINT fk_QId_MsgId
FOREIGN KEY (Q_ID)
REFERENCES MESSAGE_ASSET (MSG_ID)
/

ALTER TABLE ANS_CLUSTER
ADD CONSTRAINT fk_EId_ExpId
FOREIGN KEY (E_ID)
REFERENCES EXP_RESOURCE (EXP_ID)
/

COMMIT;

spool off;
