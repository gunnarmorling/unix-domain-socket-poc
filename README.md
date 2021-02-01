# Java 16 Unix Domain Socket PoC

A proof-of-concept for using Java 16's Unix domain socket support (JEP 380) with the Vert.x Postgres driver.
See [this blog post](https://www.morling.dev/blog/talking-to-postgres-through-java-16-unix-domain-socket-channels/) for some background.

## Build

This project requires OpenJDK 16 or later for its build.
Apache Maven is used for the build.
Run the following to build the project
(requires Postgres to be running locally,
with a database `test_db`, and a user `test_user` with password `4fafsqfa98()d#er`;
see [here](https://www.postgresql.org/download/linux/redhat/) and [here](https://computingforgeeks.com/how-to-install-postgresql-on-fedora/) for installation instructions for Fedora):

```shell
mvn clean verify
```

## License

This code base is available under the Apache License, version 2.
