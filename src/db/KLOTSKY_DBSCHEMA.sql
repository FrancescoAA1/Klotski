CREATE TABLE "DISPOSITIONS"
(
    "disposition_id"    INTEGER NOT NULL UNIQUE,
    "schema"            TEXT    NOT NULL,
    "disposition_image" TEXT,
    "original"          INTEGER NOT NULL,
    "original_number"   INTEGER,
    PRIMARY KEY ("disposition_id" AUTOINCREMENT)
);
CREATE TABLE "MATCHES"
(
    "match_id"       INTEGER NOT NULL UNIQUE,
    "disposition_id" INTEGER NOT NULL UNIQUE,
    "name"           TEXT    NOT NULL UNIQUE,
    "score"          INTEGER NOT NULL,
    "terminated"     INTEGER NOT NULL,
    "hints_number"   INTEGER NOT NULL,
    PRIMARY KEY ("match_id" AUTOINCREMENT),
    FOREIGN KEY ("disposition_id") REFERENCES "DISPOSITIONS" ("disposition_id")
);