package com.github.tachesimazzoca.java.examples.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DDL {
    public static void convertJsonToSQL(InputStream input, OutputStream output)
            throws IOException {
        JsonFactory factory = new JsonFactory();
        writeSQL(factory.createParser(input), output);
    }

    public static void convertYAMLToSQL(InputStream input, OutputStream output)
            throws IOException {
        YAMLFactory factory = new YAMLFactory();
        writeSQL(factory.createParser(input), output);
    }

    private static void writeSQL(JsonParser parser, OutputStream output)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(parser);

        PrintWriter writer = new PrintWriter(output);
        writer.print("-- ");
        writer.println(rootNode.path("name").asText());
        writer.println();

        for (JsonNode tableNode : rootNode.path("tables")) {
            String tableName = tableNode.path("name").asText();
            writer.print("DROP TABLE IF EXISTS ");
            writer.print(tableName);
            writer.println(";");
            writer.print("CREATE TABLE ");
            writer.print(tableName);
            writer.println(" (");
            for (JsonNode columnNode : tableNode.path("columns")) {
                writer.print("  ");
                writer.print(columnNode.path(0).asText());
                writer.print(" ");
                writer.print(columnNode.path(1).asText());
                writer.print(",");
                if (columnNode.size() > 2) {
                    writer.println();
                    writer.print("    -- ");
                    writer.print(columnNode.path(2).asText());
                }
                writer.println();
            }
            writer.print("  ");
            writer.print(tableNode.path("indexes").path(0).asText());
            writer.println(",");
            writer.print("  ");
            writer.print(tableNode.path("indexes").path(1).asText());
            writer.println();
            writer.print(") ");
            writer.print(tableNode.path("definition").asText());
            writer.println(";");
            writer.println();
        }
        writer.close();
    }
}
