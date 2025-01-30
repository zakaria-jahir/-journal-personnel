package com.example.journalpersonnel;

import java.util.List;

public class JournalEntry {
    private String title;
    private String content;
    private String date;
    private List<String> tags;

    public JournalEntry(String title, String content, String date, List<String> tags) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return date + " - " + title + " - " + tags;
    }
}