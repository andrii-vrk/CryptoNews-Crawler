package de.example.crawler.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {

    private static final Logger logger = LoggerFactory.getLogger(Repository.class.getSimpleName());

    private static final String url = "jdbc:postgresql://localhost:5432/crypto-news";
    private static final String user = "postgres";
    private static final String password = "root";

    public Repository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("Couldn't register PostgreSQL Driver");
        }
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Couldn't connect to PostgreSQL db");
        }

        return connection;
    }

}
