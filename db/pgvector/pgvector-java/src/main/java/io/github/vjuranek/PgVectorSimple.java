package io.github.vjuranek;

import com.pgvector.PGvector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PgVectorSimple {
    public static void main(String[] args) throws Exception {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://172.17.0.2:5432/postgres",
                "postgres",
                "password")) {
            PGvector.addVectorType(conn);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM  document_embeddings");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getLong("id") + " -> ");
                System.out.println(((PGvector) rs.getObject("embedding")).getValue());
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }
}
