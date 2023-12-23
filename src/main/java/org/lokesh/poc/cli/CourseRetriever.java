package org.lokesh.poc.cli;

import org.lokesh.poc.cli.service.CourseRetrievalService;
import org.lokesh.poc.cli.service.PluralsightCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static java.util.function.Predicate.not;

public class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);
    public CourseRetriever() {}
    public static void main(String... args) {
        LOG.info("Course retriever starting");

        if (args.length == 0) {
            LOG.warn("Please provide author name as first argument");
            return;
        }

        try {
            retrieveCources(args[0]);
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }
    }

    private static void retrieveCources(String authorId) throws IOException, InterruptedException {
        LOG.info("Retrieve courses for author '{}'", authorId);
        CourseRetrievalService courseRetrievalService = new CourseRetrievalService();

        List<PluralsightCourse> coursesToStore = courseRetrievalService.getCourseFor(authorId)
                .stream().filter(not(PluralsightCourse::isRetired))
                .toList();
        LOG.info("Retrieved of following {} course '{}'", coursesToStore.size(), coursesToStore);
    }
}
