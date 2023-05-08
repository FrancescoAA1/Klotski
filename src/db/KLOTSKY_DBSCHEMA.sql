CREATE TABLE "DISPOSITIONS"
(
    "disposition_id" INTEGER NOT NULL UNIQUE,
    "schema"         TEXT    NOT NULL UNIQUE,
    "original"       INTEGER NOT NULL,
    PRIMARY KEY ("disposition_id")
);

CREATE TABLE "MATCHES"
(
    "match_id"    INTEGER NOT NULL UNIQUE,
    "name"        TEXT    NOT NULL UNIQUE,
    "disposition" INTEGER NOT NULL,
    "score"       INTEGER NOT NULL,
    "terminated"  INTEGER NOT NULL,
    PRIMARY KEY ("match_id" AUTOINCREMENT),
    FOREIGN KEY ("disposition") REFERENCES "DISPOSITIONS" ("disposition_id")
);
