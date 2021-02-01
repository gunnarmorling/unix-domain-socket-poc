/**
 *  License: Apache License, Version 2.0
 *  See the LICENSE file in the root directory or <https://www.apache.org/licenses/LICENSE-2.0>.
 */
package dev.morling.demos.uds;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;


public class UnixDomainSocketTest {

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
                    fail(ar.cause().getMessage());
                }

                latch.countDown();
            });

        latch.await();

        client.close();
    }
}
