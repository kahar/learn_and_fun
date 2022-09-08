package io.github.kahar.reference;

import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class Demo {

    @Test
    public void differentReferences() {
        /*So in brief: Soft references try to keep the reference. Weak references don’t try to keep the reference.
        Phantom references don’t free the reference until cleared.*/
        /*To reuse (and stretch) one last time our restaurant metaphor: a SoftReference is like a customer
        that say: I’ll leave my table only when there are no other tables avalaible. A WeakReference is like someone
        ready to leave as soon as a new customer arrives. A PhantomReference is like someone ready to leave as soon as
        a new customer arrives, but actually not leaving until the manager gives him permission.*/

        /*Strong reference*/
        User user = new User();
        /*weak reference, if operation user=null will occur, then Weakreference value can be removed by Garbage collector
        but only when JVM need memory
        * These references are used in real time applications while establishing a DBConnection which might be
        * cleaned up by Garbage Collector when the application using the database gets closed.*/
        WeakReference<User> weakref = new WeakReference<User>(user);

        /*soft reference , if operation user=null will occur, then Weakreference value can be removed by Garbage collector
        but only when JVM need memory BADLY (object will be removed before OutOfMemoryError will be thrown)*/
        SoftReference<User> softref = new SoftReference<User>(user);

        /*Phantom References: The objects which are being referenced by phantom references are eligible for garbage
        collection. But, before removing them from the memory, JVM puts them in a queue called 'reference queue' .
        They are put in a reference queue after calling finalize() method on them.To create such references
        java.lang.ref.PhantomReference class is used.*/
        ReferenceQueue<User> refQueue = new ReferenceQueue<User>();
        PhantomReference<User> phantomRef = new PhantomReference<User>(user, refQueue);
        ;
    }

    class User {
        String name;
    }
}
