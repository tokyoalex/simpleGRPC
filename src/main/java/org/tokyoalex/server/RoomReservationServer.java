package org.tokyoalex.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.tokyoalex.server.database.DatabaseFactory;

import java.io.IOException;


public class RoomReservationServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        DatabaseFactory.init();
        Server server = ServerBuilder.forPort(5004)
                .addService(new RoomReservationServiceImpl())
                .build();

        server.start();

        // Server is kept alive for the client to communicate.
        server.awaitTermination();
    }
}
