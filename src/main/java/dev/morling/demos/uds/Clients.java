/**
 *  License: Apache License, Version 2.0
 *  See the LICENSE file in the root directory or <https://www.apache.org/licenses/LICENSE-2.0>.
 */
package dev.morling.demos.uds;

import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxFactory;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public class Clients {

    public static PgPool getTcpClient() {
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(5432)
            .setHost("localhost")
            .setDatabase("test_db")
            .setUser("test_user")
            .setPassword("4fafsqfa98()d#er");

        PoolOptions poolOptions = new PoolOptions()
            .setMaxSize(5);

       return PgPool.pool(connectOptions, poolOptions);
    }

    public static PgPool getDomainClientNettyNative() {
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(5432)
            .setHost("/var/run/postgresql")
            .setDatabase("test_db")
            .setUser("test_user")
            .setPassword("4fafsqfa98()d#er");

        PoolOptions poolOptions = new PoolOptions()
            .setMaxSize(5);

       return PgPool.pool(connectOptions, poolOptions);
    }

    public static PgPool getDomainClientJdk16() {
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(5432)
            .setHost("/var/run/postgresql")
            .setDatabase("test_db")
            .setUser("test_user")
            .setPassword("4fafsqfa98()d#er");

        PoolOptions poolOptions = new PoolOptions()
            .setMaxSize(5);

        VertxFactory fv = new VertxFactory();
        fv.transport(new UnixDomainTransport());
        Vertx v = fv.vertx();

        return PgPool.pool(v, connectOptions, poolOptions);
    }
}
