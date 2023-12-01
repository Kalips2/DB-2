CREATE TABLE "users"
(
    "UserID"        int PRIMARY KEY,
    "Role"          varchar,
    "FirstName"     varchar,
    "LastName"      varchar,
    "Gender"        varchar,
    "DateOfBirth"   date,
    "BloodType"     int,
    "ContactNumber" varchar,
    "Email"         varchar
);

alter table users
    owner to postgres;

CREATE TABLE "favorite_centers"
(
    "UserID"        int,
    "BloodCenterId" int
);

alter table favorite_centers
    owner to postgres;

CREATE TABLE "user_restrictions"
(
    "RestrictionID" int PRIMARY KEY,
    "UserID"        int,
    "Reason"        text,
    "ExpiryDate"    date
);

alter table user_restrictions
    owner to postgres;

CREATE TABLE "user_documents"
(
    "documentID"  int PRIMARY KEY,
    "UserID"      int,
    "Description" text,
    "File"        varchar
);

alter table user_documents
    owner to postgres;

CREATE TABLE "blood_centers"
(
    "CenterId"      int PRIMARY KEY,
    "Name"          varchar,
    "Location"      varchar,
    "ContactNumber" varchar,
    "Email"         varchar
);

alter table blood_centers
    owner to postgres;


CREATE UNIQUE INDEX ON "favorite_centers" ("UserID", "BloodCenterId");

ALTER TABLE "favorite_centers"
    ADD FOREIGN KEY ("UserID") REFERENCES "users" ("UserID");

ALTER TABLE "user_restrictions"
    ADD FOREIGN KEY ("UserID") REFERENCES "users" ("UserID");

ALTER TABLE "user_documents"
    ADD FOREIGN KEY ("UserID") REFERENCES "users" ("UserID");

ALTER TABLE "favorite_centers"
    ADD FOREIGN KEY ("BloodCenterId") REFERENCES "blood_centers" ("CenterId");



