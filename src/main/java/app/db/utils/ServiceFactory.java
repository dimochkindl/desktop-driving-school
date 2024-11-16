package app.db.utils;

import app.db.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory implements Factory{

    private final StudentServiceImpl studentService;
    private final CarServiceImpl carService;
    private final PostServiceImpl postService;
    private final EmployeeServiceImpl employeeService;
    private final LessonServiceImpl lessonService;
    private final JournalServiceImpl journalService;

    @Autowired
    public ServiceFactory(StudentServiceImpl studentService, CarServiceImpl carService, PostServiceImpl postService, EmployeeServiceImpl employeeService, LessonServiceImpl lessonService, JournalServiceImpl journalService) {
        this.studentService = studentService;
        this.carService = carService;
        this.postService = postService;
        this.employeeService = employeeService;
        this.lessonService = lessonService;
        this.journalService = journalService;
    }

    @Override
    public <T> T createService(Class<T> serviceClass) {
        if (serviceClass == StudentServiceImpl.class) {
            return (T) studentService;
        } else if (serviceClass == CarServiceImpl.class) {
            return (T) carService;
        } else if (serviceClass == PostServiceImpl.class) {
            return (T) postService;
        }else if (serviceClass == EmployeeServiceImpl.class) {
            return (T) employeeService;
        } else if (serviceClass == LessonServiceImpl.class) {
            return (T) lessonService;
        } else if (serviceClass == JournalServiceImpl.class) {
            return (T) journalService;
        }
        throw new IllegalArgumentException("Unknown service class: " + serviceClass);
    }

    public <T> T createService(String name) {
        String entityName = name.substring(name.lastIndexOf(".") + 1);
        System.out.println("Entity name: " + entityName);
        return switch (entityName) {
            case "Student" -> (T) studentService;
            case "Car" -> (T) carService;
            case "Post" -> (T) postService;
            case "Employee" -> (T) employeeService;
            case "Lesson" -> (T) lessonService;
            case "Journal" -> (T) journalService;
            default -> throw new IllegalArgumentException("Unknown service name: " + name);
        };
    }
}
