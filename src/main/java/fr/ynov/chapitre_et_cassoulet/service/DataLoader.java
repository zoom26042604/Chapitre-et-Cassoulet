package main.java.fr.ynov.chapitre_et_cassoulet.service;

import main.java.fr.ynov.chapitre_et_cassoulet.exception.ChapterContentException;
import main.java.fr.ynov.chapitre_et_cassoulet.exception.FileOperationException;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Book;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Novel;
import main.java.fr.ynov.chapitre_et_cassoulet.model.Roman;
import main.java.fr.ynov.chapitre_et_cassoulet.model.TextChapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataLoader {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Reads file content as string
     */
    private String readFile(String filePath) throws FileOperationException {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new FileOperationException("Error reading file: " + filePath, e);
        }
    }

    /**
     * Parse chapter information from JSON string
     */
    private TextChapter parseChapter(String json) throws FileOperationException {
        try {
            int id = extractIntField(json, "id");
            String title = extractStringField(json, "title");
            int chapterNumber = extractIntField(json, "chapterNumber");
            String content = extractStringField(json, "contentText");

            if (content == null || content.isEmpty()) {
                throw new ChapterContentException("Chapter content is empty or missing");
            }

            TextChapter chapter = new TextChapter(id, title, chapterNumber);
            chapter.setContentText(content);

            return chapter;
        } catch (ChapterContentException e) {
            throw e;
        } catch (Exception e) {
            throw new FileOperationException("Error parsing chapter: " + e.getMessage(), e);
        }
    }

    /**
     * Extract a string field from JSON
     */
    private String extractStringField(String json, String fieldName) {
        String searchKey = "\"" + fieldName + "\"";
        int fieldIndex = json.indexOf(searchKey);
        if (fieldIndex == -1) {
            return null;
        }

        int valueStartIndex = json.indexOf(':', fieldIndex) + 1;
        while (valueStartIndex < json.length() && Character.isWhitespace(json.charAt(valueStartIndex))) {
            valueStartIndex++;
        }

        if (valueStartIndex >= json.length()) {
            return null;
        }

        char firstChar = json.charAt(valueStartIndex);
        if (firstChar != '"') {
            if (json.substring(valueStartIndex).startsWith("null")) {
                return null;
            }

            int valueEndIndex = -1;
            for (int i = valueStartIndex; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == ',' || c == '}' || c == ']') {
                    valueEndIndex = i;
                    break;
                }
            }

            if (valueEndIndex == -1) {
                return null;
            }

            return json.substring(valueStartIndex, valueEndIndex).trim();
        }

        valueStartIndex++;

        boolean escaped = false;
        int valueEndIndex = -1;
        for (int i = valueStartIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && !escaped) {
                valueEndIndex = i;
                break;
            }
            escaped = (c == '\\' && !escaped);
        }

        if (valueEndIndex == -1) {
            return null;
        }

        String value = json.substring(valueStartIndex, valueEndIndex);
        return value.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\/", "/")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }

    /**
     * Extract an integer field from JSON
     */
    private int extractIntField(String json, String fieldName) {
        String value = extractStringField(json, fieldName);
        if (value == null) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Extract an array field from JSON
     */
    private List<String> extractArrayField(String json, String fieldName) {
        List<String> result = new ArrayList<>();
        String searchKey = "\"" + fieldName + "\"";
        int fieldIndex = json.indexOf(searchKey);
        if (fieldIndex == -1) {
            return result;
        }

        int arrayStartIndex = json.indexOf('[', fieldIndex);
        if (arrayStartIndex == -1) {
            return result;
        }

        int arrayEndIndex = findMatchingClosingBracket(json, arrayStartIndex);
        if (arrayEndIndex == -1) {
            return result;
        }

        String arrayContent = json.substring(arrayStartIndex + 1, arrayEndIndex).trim();
        if (arrayContent.isEmpty()) {
            return result;
        }

        int startIndex = 0;
        boolean inQuotes = false;
        boolean escaped = false;
        List<String> elements = new ArrayList<>();

        for (int i = 0; i < arrayContent.length(); i++) {
            char c = arrayContent.charAt(i);

            if (c == '"' && !escaped) {
                inQuotes = !inQuotes;
            }

            if (c == ',' && !inQuotes) {
                elements.add(arrayContent.substring(startIndex, i).trim());
                startIndex = i + 1;
            }

            escaped = (c == '\\' && !escaped);
        }

        if (startIndex < arrayContent.length()) {
            elements.add(arrayContent.substring(startIndex).trim());
        }

        for (String element : elements) {
            element = element.trim();
            if (element.startsWith("\"") && element.endsWith("\"")) {
                element = element.substring(1, element.length() - 1);
            }
            result.add(element);
        }

        return result;
    }

    /**
     * Finds the matching closing bracket for a given opening bracket position
     */
    private int findMatchingClosingBracket(String json, int openingBracketIndex) {
        int depth = 1;
        boolean inQuotes = false;
        boolean escaped = false;

        for (int i = openingBracketIndex + 1; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"' && !escaped) {
                inQuotes = !inQuotes;
            } else if (!inQuotes) {
                if (c == '[') {
                    depth++;
                } else if (c == ']') {
                    depth--;
                    if (depth == 0) {
                        return i;
                    }
                }
            }

            escaped = (c == '\\' && !escaped);
        }

        return -1;
    }

    public Book loadBookFromFile(String filePath) throws FileOperationException {
        String jsonContent = readFile(filePath);
        return parseBook(jsonContent);
    }

    public TextChapter loadChapterFromFile(String filePath) throws FileOperationException {
        try {
            String jsonContent = readFile(filePath);
            return parseChapter(jsonContent);
        } catch (FileOperationException e) {
            if (e instanceof ChapterContentException) {
                throw e;
            }
            throw new ChapterContentException("Failed to load chapter content from " + filePath, e);
        }
    }

    public Book parseBook(String json) throws FileOperationException {
        String type = extractStringField(json, "type");
        boolean isNovel = type == null || "Novel".equalsIgnoreCase(type);

        return parseBookInfo(json, isNovel);
    }

    /**
     * Parse book information from JSON string
     */
    private Book parseBookInfo(String json, boolean isNovel) throws FileOperationException {
        try {
            int id = extractIntField(json, "id");
            String title = extractStringField(json, "title");
            String description = extractStringField(json, "description");
            String coverImage = extractStringField(json, "coverImage");
            String status = extractStringField(json, "status");
            String artist = extractStringField(json, "artist");
            String dateAddedStr = extractStringField(json, "dateAdded");
            List<String> genres = extractArrayField(json, "genres");

            Book book;
            if (isNovel) {
                Novel novel = new Novel(id, title, description, coverImage);
                novel.setOrigin(extractStringField(json, "origin"));
                novel.setTranslator(extractStringField(json, "translator"));
                book = novel;
            } else {
                Roman roman = new Roman(id, title, description, coverImage);
                roman.setSeries(extractStringField(json, "series"));
                roman.setIllustrator(extractStringField(json, "illustrator"));
                book = roman;
            }

            book.setStatus(status);
            book.setArtist(artist);

            if (dateAddedStr != null && !dateAddedStr.isEmpty()) {
                try {
                    Date dateAdded = dateFormat.parse(dateAddedStr);
                    book.setDateAdded(dateAdded);
                } catch (ParseException e) {
                    System.err.println("Invalid date format: " + dateAddedStr);
                }
            }
            for (String genre : genres) {
                book.addGenre(genre);
            }

            return book;
        } catch (Exception e) {
            throw new FileOperationException("Error parsing book info: " + e.getMessage(), e);
        }
    }
}