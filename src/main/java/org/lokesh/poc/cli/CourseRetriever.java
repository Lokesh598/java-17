package org.lokesh.poc.cli;

public class CourseRetriever {
    public CourseRetriever() {}
    public static void main(String... args) {
        System.out.println("Course retriever started!!");

        if (args.length == 0) {
            System.out.println("Please provide author name as first argument");
            return;
        }

        try {
            retrieveCources(args[0]);
        } catch (Exception e) {
            System.out.println("Unexpected error");
            e.printStackTrace();
        }
    }

    private static void retrieveCources(String authorId) {
        System.out.println("Retrieve courses for author: " + authorId);
    }
}
