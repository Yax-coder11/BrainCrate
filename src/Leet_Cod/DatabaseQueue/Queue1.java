package Leet_Cod.DatabaseQueue;

public class Queue1 {
    private int[] queue;
    private int front, rear, capacity;

    public Queue1(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
        front = -1;
        rear = -1;
    }

    // Add element (enqueue)
    public void enqueue(int value) {
        // Condition for full queue
        if ((front == 0 && rear == capacity - 1) || (rear + 1) % capacity == front) {
            System.out.println("Queue is full!");
            return;
        }

        // If queue is empty
        if (front == -1) {
            front = 0;
        }

        // Circular increment of rear
        rear = (rear + 1) % capacity;
        queue[rear] = value;
        System.out.println(value + " enqueued");
    }

    // Remove element (dequeue)
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }

        int item = queue[front];

        // If only one element was present
        if (front == rear) {
            front = rear = -1;
        } else {
            // Circular increment of front
            front = (front + 1) % capacity;
        }

        return item;
    }

    // Peek front element
    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return;
        }

        System.out.print("Queue: ");
        int i = front;
        while (true) {
            System.out.print(queue[i] + " ");
            if (i == rear) break;
            i = (i + 1) % capacity;
        }
        System.out.println();
    }

    public boolean contains1(int value) {
        if (isEmpty()) {
            return false;
        }
        int i = front;
        while (true) {
            if (queue[i] == value) {
                return true;   // ✅ found
            }
            if (i == rear) {
                break;
            }
            i = (i + 1) % queue.length;
        }
        return false;  // not found
    }

}
