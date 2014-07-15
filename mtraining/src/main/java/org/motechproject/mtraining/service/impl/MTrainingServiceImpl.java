package org.motechproject.mtraining.service.impl;

import org.motechproject.mtraining.domain.Chapter;
import org.motechproject.mtraining.domain.Course;
import org.motechproject.mtraining.domain.Lesson;
import org.motechproject.mtraining.domain.Quiz;
import org.motechproject.mtraining.repository.ChapterDataService;
import org.motechproject.mtraining.repository.CourseDataService;
import org.motechproject.mtraining.repository.LessonDataService;
import org.motechproject.mtraining.service.MTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for mTraining
 */
@Service("mTrainingService")
public class MTrainingServiceImpl implements MTrainingService {

    @Autowired
    private CourseDataService courseDataService;

    @Autowired
    private ChapterDataService chapterDataService;

    @Autowired
    private LessonDataService lessonDataService;

    /**
     * Create a course with the given structure
     * @param course course object to store
     * @return Course object created in the store
     */
    @Override
    public Course createCourse(Course course) {

        return courseDataService.create(course);
    }

    /**
     * Retrieve a course with the given course id
     * @param courseId id of the course to retrieve
     * @return course with id
     */
    @Override
    public Course getCourseById(long courseId) {

        return courseDataService.findCourseById(courseId);
    }

    /**
     * Get courses that match the name
     * @param courseName name of the course
     * @return list of courses that match the course name
     */
    @Override
    public List<Course> getCourseByName(String courseName) {

        return courseDataService.findCourseByName(courseName);
    }

    /**
     * Update a course with the given structure
     * @param course Course structure to update
     * @return updated version of the course
     */
    @Override
    public Course updateCourse(Course course) {

        return courseDataService.update(course);
    }

    /**
     * Delete the course with the given id
     * @param courseId id of the course
     */
    @Override
    public void deleteCourse(long courseId) {
        Course toDelete = getCourseById(courseId);
        courseDataService.delete(toDelete);
    }

    /**
     * get the quiz for a given chapter
     * @param chapterId chapter id to retrieve quiz for
     * @return Quiz object for the chapter
     */
    @Override
    public Quiz getQuizForChapter(long chapterId) {
        Chapter lookup = chapterDataService.findChapterById(chapterId);
        return lookup.getQuiz();
    }

    /**
     * Chapter CRUD
     */

    /**
     * Create a chapter with the given information
     * @param chapter chapter to create
     * @return chapter object created in the store
     */
    @Override
    public Chapter createChapter(Chapter chapter) {

        return chapterDataService.create(chapter);
    }

    @Override
    public List<Chapter> getChapterByName(String chapterName) {

        return chapterDataService.findChapterByName(chapterName);
    }

    @Override
    public Chapter getChapterById(long chapterId) {

        return chapterDataService.findChapterById(chapterId);
    }

    @Override
    public Chapter updateChapter(Chapter chapter) {

        // TODO: update all top (higher) level objects for versioning
        return chapterDataService.update(chapter);
    }

    @Override
    public void deleteChapter(long chapterId) {
        Chapter toDelete = chapterDataService.findChapterById(chapterId);
        chapterDataService.delete(toDelete);
    }

    /**
     * Lesson CRUD
     */

    @Override
    public Lesson createLesson(Lesson lesson) {

        return lessonDataService.create(lesson);
    }

    @Override
    public List<Lesson> getLessonByName(String lessonName) {

        return lessonDataService.findLessonByName(lessonName);
    }

    @Override
    public Lesson getLessonById(long lessonId) {

        return lessonDataService.findLessonById(lessonId);
    }

    @Override
    public Lesson updateLesson(Lesson lesson) {

        return lessonDataService.update(lesson);
    }

    @Override
    public void deleteLesson(long lessonId) {

        Lesson toDelete = lessonDataService.findLessonById(lessonId);
        lessonDataService.delete(toDelete);
    }
}