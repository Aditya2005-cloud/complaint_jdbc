Oracle Database 


Table created.

SQL> create table complaints
  (
    id number primary key,
    name varchar2(22),
   description varchar2(200),
    status varchar2(50));

Sequence created.

SQL> create sequence complain_seq start with 1 increment by 1;


Trigger created

SQL> CREATE OR REPLACE TRIGGER CCOMLAIN_TRIGGER

  2  BEFORE INSERT ON complaints
  
  3  FOR EACH ROW
  
  4  BEGIN
  
  5  SELECT complain_seq.nextval INTO :NEW.ID FROM DUAL;
  
  6  END;
  
  7  /
.


Procedure created.


SQL> CREATE OR REPLACE PROCEDURE UPDATE_STATUS_PROC(CID IN NUMBER,NEW_STATUS IN VARCHAR2)

  2  IS
  
  3  BEGIN
  
  4  UPDATE complaints SET STATUS=NEW_STATUS WHERE ID=CID;
  
  5  END;
  
  6  /

