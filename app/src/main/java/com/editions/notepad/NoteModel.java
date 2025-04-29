package com.editions.notepad;

public class NoteModel {
    String title, message;
    int id;


    //Constructor
    public NoteModel(String title, String message, int id) {
        this.title = title;
        this.message = message;
        this.id = id;
    }




    //Getter and Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
