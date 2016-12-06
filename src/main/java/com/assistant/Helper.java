package com.assistant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Helper {
    public static <T> T loadFromResource(String path, Class<T> clazz) throws IOException {
        String jsonResource = loadFromResource(path);
        return convert(jsonResource, clazz);
    }

    public static <T> T convert(String jsonData, Class<T> clazz) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        return (T)objectMapper.readValue(jsonData, clazz);
    }

    public static String loadFromResource(String path) throws IOException {
        Resource body = new ClassPathResource(path);
        StringBuilder result = new StringBuilder("");
        File file = body.getFile();
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            return "";
        }

        return result.toString();
    }
}
