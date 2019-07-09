DROP TABLE Door_Type;
create table Door_Type
(
  id SERIAL PRIMARY KEY,
  name  CHARACTER VARYING(100) NOT NULL,
  doorClass INT,
  namePicture  CHARACTER VARYING(100),
  doorLeaf INT NOT NULL,
  nameForPrint CHARACTER VARYING(100),
  nameForPrintInternalOpening CHARACTER VARYING(100),
  daysToRelease INT,
  markUp INT,
  markUpGlassPackage INT,
  DS INT
);