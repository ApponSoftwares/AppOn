package com.appon.miniframework;


import java.util.NoSuchElementException;


/**
 * Class used to implement the queue data structure 
 * 
 * @param <E> 
 */

public class Queue  {
    private int N;         // number of elements on queue
    private Queue.Node first;    // beginning of queue
    private Queue.Node last;     // end of queue

    // helper linked list class
    private class Node {
        private Object item;
        private Queue.Node next;
    }

   /**
     * Create an empty queue.
     */
    public Queue() {
        first = null;
        last  = null;
    }

   /**
     * Is the queue empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

   /**
     * Return the number of items in the queue.
     */
    public int size() {
        return N;     
    }

   /**
     * Return the item least recently added to the queue.
     * Throw an exception if the queue is empty.
     */
    public Object peek() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        return first.item;
    }

   /**
     * Add the item to the queue.
     */
    public void enqueue(Object item) {
        Queue.Node x = new Queue.Node();
        x.item = item;
        if (isEmpty()) { first = x;     last = x; }
        else           { last.next = x; last = x; }
        N++;
    }

   /**
     * Remove and return the item on the queue least recently added.
     * Throw an exception if the queue is empty.
     */
    public Object dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        Object item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;   // to avoid loitering
        return item;
    }

  
    /**
     * Method used to clear the queue
     */
    public void clear()
    {
    	 first = null;
         last  = null;
         N = 0;
    }
   /**
     * Return an iterator that iterates over the items on the queue in FIFO order.
     */
    public FIFOIterator  iterator()  {
        return new Queue.FIFOIterator();  
    }

    /**
     * An iterator, doesn't implement remove() since it's optional
     */
    // an iterator, doesn't implement remove() since it's optional
    private class FIFOIterator {
        private Queue.Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new RuntimeException();  }

        public Object next() {
            if (!hasNext()) throw new NoSuchElementException();
            Object item = current.item;
            current = current.next; 
            return item;
        }
    }
    
}
