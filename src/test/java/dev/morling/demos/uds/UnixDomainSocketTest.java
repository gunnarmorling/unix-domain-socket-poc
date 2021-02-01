/**
 *  License: Apache License, Version 2.0
 *  See the LICENSE file in the root directory or <https://www.apache.org/licenses/LICENSE-2.0>.
 */
package dev.morling.demos.uds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;


public class UnixDomainSocketTest {

    @Before
    public void setupDatabase() throws Exception {
        String url = "jdbc:postgresql://localhost/test_db";
        Properties props = new Properties();
        props.setProperty("user", "test_user");
        props.setProperty("password", "4fafsqfa98()d#er");

        Connection conn = DriverManager.getConnection(url, props);

        var ddl = """
            create table customer.Customer (
                id int8 not null,
                name varchar(255),
                open_limit int8 not null,
                primary key (id)
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("drop table if exists customer.customer");
            stmt.executeUpdate("drop schema if exists customer");
            stmt.executeUpdate("create schema customer");
            stmt.executeUpdate(ddl);
            stmt.executeUpdate("insert into customer.customer (id, name, open_limit) values (1, 'Bob', 40000)");
        }
            catch (SQLException e) {
                throw new RuntimeException(e);
        }

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM customer.customer");) {

            while (rs.next()) {
                System.out.print("Column 1 returned ");
                System.out.println(rs.getString(1));
            }
        }
    }

    @Test
    public void shouldObtainResult() throws Exception {
        var client = Clients.getDomainClientJdk16();

        CountDownLatch latch = new CountDownLatch(1);
        client.query("SELECT name FROM customer.customer")
            .execute(ar -> {
                if (ar.succeeded()) {
                    RowSet<Row> result = ar.result();
                    assertEquals(1, result.size());
                    var it = result.iterator();
                    it.forEachRemaining(
                         row -> { assertEquals("Bob", row.getString(0));}
                     );
                }
                else {
                    ar.cause().printStackTrace();

                    fail(ar.cause().getMessage());
                }

                latch.countDown();
            });

        latch.await();

        client.close();
    }
}
