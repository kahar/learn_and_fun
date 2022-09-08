package io.github.kahar._transient;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Demo {

    public static void serialize(Book book) throws Exception {
        FileOutputStream file = new FileOutputStream("test");
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(book);
        out.close();
        file.close();
    }

    public static Book deserialize() throws Exception {
        FileInputStream file = new FileInputStream("test");
        ObjectInputStream in = new ObjectInputStream(file);
        Book book = (Book) in.readObject();
        in.close();
        file.close();
        return book;
    }

    @Test
    @SneakyThrows
    public void testTransient() {
        Book book = new Book("Java Reference", "will not be saved", 25);
        book.setCopies(25);

        serialize(book);
        Book deserializedBook = deserialize();

        assertEquals("Java Reference", deserializedBook.getBookName());
        assertNull(deserializedBook.getDescription());
        assertEquals(0, deserializedBook.getCopies());
        assertEquals("Fiction", deserializedBook.getBookCategory());
    }
}
