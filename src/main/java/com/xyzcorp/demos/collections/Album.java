package com.xyzcorp.demos.collections;

import java.util.Objects;
import java.util.StringJoiner;

public class Album {

    private String title;
    private int year;
    private String name;

    public Album(String title) {
        this.title = title;
    }

    public Album() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return year == album.year &&
                Objects.equals(title, album.title) &&
                Objects.equals(name, album.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Album.class.getSimpleName() + "[", "]")
            .add("title='" + title + "'")
            .add("year=" + year)
            .add("name='" + name + "'")
            .toString();
    }
}
