cd /opt/PostgreSQL/10/bin;
PGPASSWORD=admin psql -U postgres -c "create database finley;"
PGPASSWORD=admin psql -U postgres -d finley < $1/db.sql