package org.tokyoalex.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository    {


    public static List<HotelEntity> getAllHotelsAvailability()  {
        List<HotelEntity> hotels = new ArrayList<>();
        try {
            Connection c = DatabaseFactory.getConnection();
            Statement stat = c.createStatement();
            ResultSet rs = stat.executeQuery("Select ID, NAME, LOCATION, ROOMS_AVAILABLE, MAX_CAPACITY from HOTEL");

            while (rs.next()) {
                HotelEntity h = new HotelEntity();
                h.setId(rs.getLong("ID"));
                h.setName(rs.getString("NAME"));
                h.setLocation(rs.getString("LOCATION"));
                h.setRooms_available(rs.getInt("ROOMS_AVAILABLE"));
                h.setMax_capacity(rs.getInt("MAX_CAPACITY"));
                hotels.add(h);
            }
            rs.close();
        }
        catch(Exception e)  {
            System.out.println(e.getMessage());
        }
        return hotels;
    }

    public static HotelEntity getHotelAvailability(Long hotelId)  {
        HotelEntity h = new HotelEntity("test", 100, 1000, "tok");
        try {
            Connection c = DatabaseFactory.getConnection();

            PreparedStatement statement = c.prepareStatement("SELECT ID, NAME, ROOMS_AVAILABLE FROM HOTEL WHERE ID= ?");
            statement.setLong(1,hotelId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("ID");
                h.setName(rs.getString("NAME")); // Assuming there is a column called name.
                h.setRooms_available(rs.getInt("ROOMS_AVAILABLE"));
                System.out.println(h.getName());
            }
            rs.close();
        }
        catch(Exception e)  {
            System.out.println(e.getMessage());
        }
        return h;
    }


    // TODO change from bool to enum with error codes
    public static Boolean makeReservation(Long hotelId)    {

        try {
            Connection c = DatabaseFactory.getConnection();

            PreparedStatement statement = c.prepareStatement("SELECT ROOMS_AVAILABLE, MAX_CAPACITY FROM HOTEL WHERE ID= ?");
            statement.setLong(1,hotelId);
            ResultSet rs = statement.executeQuery();
            Integer rooms = 0;
            Integer cap = 0;
            while (rs.next()) {
                rooms = rs.getInt("ROOMS_AVAILABLE");
                cap = rs.getInt("MAX_CAPACITY");
            }
            rs.close();

            if(rooms < 1) {
                return false; // no free rooms
            }

            PreparedStatement pStatement = c.prepareStatement("UPDATE HOTEL h SET h.ROOMS_AVAILABLE = ? WHERE h.ID= ?");
            pStatement.setInt(1,rooms - 1);
            pStatement.setLong(2,hotelId);

            pStatement.executeUpdate();
            rs.close();

            statement = c.prepareStatement("SELECT NAME, ROOMS_AVAILABLE FROM HOTEL WHERE ID= ?");
            statement.setLong(1,hotelId);
            rs = statement.executeQuery();
            Integer roomsNew = 0;

            while (rs.next()) {
                roomsNew = rs.getInt("ROOMS_AVAILABLE");
                String name = rs.getString("NAME");
                System.out.println("\n" +name+ "Prev room count: " + rooms + " new count: " + roomsNew);
            }
            rs.close();
        }
        catch(Exception e)  {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public static Boolean cancelReservation(Long hotelId)    {
        try {
            Connection c = DatabaseFactory.getConnection();

            PreparedStatement statement = c.prepareStatement("SELECT ROOMS_AVAILABLE, MAX_CAPACITY FROM HOTEL WHERE ID= ?");
            statement.setLong(1,hotelId);
            ResultSet rs = statement.executeQuery();
            Integer rooms = 0;
            Integer cap = 0;
            while (rs.next()) {
                rooms = rs.getInt("ROOMS_AVAILABLE");
                cap = rs.getInt("MAX_CAPACITY");
            }
            rs.close();

            if(rooms >= cap - 1) {
                return false;   // already at capacity, nothing to cancel
            }

            PreparedStatement pStatement = c.prepareStatement("UPDATE HOTEL h SET h.ROOMS_AVAILABLE = ? WHERE h.ID= ?");
            pStatement.setInt(1,rooms + 1);
            pStatement.setLong(2,hotelId);

            pStatement.executeUpdate();
            rs.close();

            statement = c.prepareStatement("SELECT NAME, ROOMS_AVAILABLE FROM HOTEL WHERE ID= ?");
            statement.setLong(1,hotelId);
            rs = statement.executeQuery();
            Integer roomsNew = 0;

            while (rs.next()) {
                roomsNew = rs.getInt("ROOMS_AVAILABLE");
                String name = rs.getString("NAME");
                System.out.println("\n" +name+ "Prev room count: " + rooms + " new count: " + roomsNew);
            }
            rs.close();
        }
        catch(Exception e)  {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}

