PRAGMA foreign_keys = OFF;
drop table if exists StateName;
drop table if exists LGA;
drop table if exists HomelessGroup;
drop table if exists Income;
drop view if exists Population2018;
drop view if exists Population2016;
drop view if exists HomelessCount2018;
drop view if exists HomelessCount2016;
PRAGMA foreign_keys = ON;

CREATE TABLE StateName (
    state_code        INTEGER NOT NULL,
    name              INTEGER NOT NULL,
    PRIMARY KEY (state_code)
);

CREATE TABLE LGA (
    state_code        INTEGER NOT NULL,    
    lga_code          INTEGER NOT NULL,
    year              INTEGER NOT NULL,
    lga_name          TEXT NOT NULL,
    lga_type          TEXT,
    area_sqkm         DOUBLE,
    latitude          DOUBLE,
    longitude         DOUBLE,
    population        INTEGER,
    PRIMARY KEY (lga_code, year),
    FOREIGN KEY (state_code) REFERENCES StateName(state_code)
);

CREATE TABLE HomelessGroup (
    lga_code          INTEGER NOT NULL,
    year              INTEGER NOT NULL,
    status            CHAR (10) NOT NULL,
    sex               CHAR (2),
    age_group         CHAR (10),
    count             INTEGER,
    PRIMARY KEY (lga_code, status, year, sex, age_group)
    FOREIGN KEY (lga_code) REFERENCES LGA(lga_code)
);

CREATE TABLE Income (
    lga_code                INTEGER NOT NULL,
    median_weekly_income    INTEGER,
    median_age              INTEGER,
    median_mortgage         INTEGER,
    median_weekly_rent      INTEGER,
    PRIMARY KEY (lga_code)
    FOREIGN KEY (lga_code) REFERENCES LGA(lga_code)
);

CREATE TABLE Persona (
    persona_name     TEXT NOT NULL,
    Persona_age      INTEGER NOT NULL,
    Persona_description TEXT NOT NULL,
    PRIMARY KEY (persona_name)
);

CREATE TABLE Student (
    Student_number     TEXT NOT NULL,
    Student_name      TEXT NOT NULL,
    Student_description TEXT NOT NULL,
    PRIMARY KEY (Student_number)
);

Create VIEW Population2018
AS
SELECT lga_code, lga_name, population AS population2018 
FROM LGA where year = 2018;


Create VIEW Population2016
AS
SELECT lga_code, lga_name, population AS population2016 
FROM LGA where year = 2016;


CREATE VIEW HomelessCount2018
AS
SELECT HomelessGroup.lga_code, lga_name, status, sex, age_group, count
FROM HomelessGroup JOIN LGA on HomelessGroup.lga_code = LGA.lga_code 
WHERE HomelessGroup.year = 2018 and LGA.year = 2018;


CREATE VIEW HomelessCount2016
AS
SELECT HomelessGroup.lga_code, lga_name, status, sex, age_group, count
FROM HomelessGroup JOIN LGA on HomelessGroup.lga_code = LGA.lga_code 
WHERE HomelessGroup.year = 2016 and LGA.year = 2016;