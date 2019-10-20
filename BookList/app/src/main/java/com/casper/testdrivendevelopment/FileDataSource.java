package com.casper.testdrivendevelopment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileDataSource {
    Context context;

    public FileDataSource(Context context) {
        this.context = context;
    }

    public ArrayList<Book> getBooks() {
        return Books;
    }

    ArrayList<Book> Books=new ArrayList<Book>();

    public void save()
    {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE)
            );
            outputStream.writeObject(Books);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Book> load()
    {
        try{
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            Books = (ArrayList<Book>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Books;
    }
}
