package com.wsmt.server;

import com.wsmt.generated.Event;
import com.wsmt.generated.EventServicePOA;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventServiceImpl extends EventServicePOA {
    public void add(String title, String description, long date) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wsmt", "wsmt", "wsmt");
            Statement statement = connection.createStatement();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringQuery = "INSERT INTO event (id, title, description, date) VALUES (null,'"
                    + title + "','" + description + "','" + formatter.format(new Date(date)) + "');";

            int rows = statement.executeUpdate(stringQuery);

            if (rows == 0) {
                throw new SQLException("Error creating event");
            }

            connection.close();
            System.out.println("Event created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wsmt", "wsmt", "wsmt");
            Statement statement = connection.createStatement();

            String stringQuery = "DELETE FROM event WHERE id = " + id;

            int rows = statement.executeUpdate(stringQuery);

            if (rows == 0) {
                throw new SQLException("Error deleting event");
            }

            connection.close();
            System.out.println("Event deleted " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(long id, String title, String description, long date) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wsmt", "wsmt", "wsmt");
            Statement statement = connection.createStatement();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String stringQuery = "UPDATE event SET title = '" + title +
                    "', description = '" + description +
                    "', date = '" + formatter.format(new Date(date))
                    + "' WHERE id = " + id + ";";

            int rows = statement.executeUpdate(stringQuery);
            if (rows == 0) {
                throw new SQLException("Error updating event");
            }

            connection.close();
            System.out.println("Event updated " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Event[] getAll() {
        List<Event> events = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wsmt", "wsmt", "wsmt");
            Statement statement = connection.createStatement();

            String stringQuery = "SELECT * FROM event ORDER BY date;";

            ResultSet results = statement.executeQuery(stringQuery);

            while (results.next()) {
                long id = results.getLong("id");
                String title = results.getString("title");
                String description = results.getString("description");
                Date date = new Date(results.getTimestamp("date").getTime());

                Event event = new Event(id, title, description, date.getTime());
                events.add(event);
            }
            connection.close();
            System.out.println("Listed all events");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Event[] array = new Event[events.size()];
        return events.toArray(array);
    }

    public Event[] filter(String filter) {
        List<Event> events = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wsmt", "wsmt", "wsmt");
            Statement statement = connection.createStatement();

            String stringQuery = "SELECT * FROM event " +
                    "WHERE title LIKE '%" + filter + "%' " +
                    "OR description LIKE '%" + filter + "%' " +
                    "ORDER BY date;";

            ResultSet results = statement.executeQuery(stringQuery);

            while (results.next()) {
                long id = results.getLong("id");
                String title = results.getString("title");
                String description = results.getString("description");
                Date date = new Date(results.getTimestamp("date").getTime());

                Event event = new Event(id, title, description, date.getTime());
                events.add(event);
            }

            connection.close();
            System.out.println("Listed events filtered by: '" + filter + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Event[] array = new Event[events.size()];
        return events.toArray(array);
    }
}
