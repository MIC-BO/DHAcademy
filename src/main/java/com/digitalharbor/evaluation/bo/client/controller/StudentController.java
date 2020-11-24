package com.digitalharbor.evaluation.bo.client.controller;

import com.digitalharbor.evaluation.bo.client.model.Assignment;
import com.digitalharbor.evaluation.bo.client.model.Student;
import com.digitalharbor.evaluation.bo.client.service.AssignmentService;
import com.digitalharbor.evaluation.bo.client.service.StudentService;
import com.digitalharbor.evaluation.bo.client.helper.Control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentServices;
    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getStudent(ModelMap model){
        if (model.isEmpty()) {
            return new ModelAndView("student", "student", new Student()).addObject("pageTitle", "New Student");
        }

        return new ModelAndView("student").addObject("pageTitle", "Update Student");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView postStudent(@ModelAttribute("student") Student student){
        String message;
        boolean isANewRecord = student.getStudentID() == null;
        try {
            studentServices.saveStudent(student);
            message = "Your data was saved.";
        }
        catch (Exception e) {
            message = "Your data could NOT be saved.";
        }

        if (isANewRecord){
            return new ModelAndView("student").addObject("student", new Student())
                    .addObject("pageTitle", "New Student").addObject("message", message);
        }

        return new ModelAndView("student").addObject("pageTitle", "Update Student").addObject("message", message);
    }

    @RequestMapping(value = "/findStudent", method = RequestMethod.GET)
    public ModelAndView getFindStudent(ModelMap model){
        if (model.isEmpty()) {
            return new ModelAndView("findStudent", "student", new Student())
                    .addObject("action", "/findStudent");
        }
        return new ModelAndView("findStudent").addObject("action", "/findStudent");
    }

    @RequestMapping(value = "/findStudent", method = RequestMethod.POST)
    public ModelAndView postFindStudent(@ModelAttribute("student") Student student){
        List<Student> students = new ArrayList<>();
        String message;

        try {
            students = studentServices.getStudentsByNames(student.getFirstName(), student.getLastName());
            message = students.size() + " students were found.";
        }
        catch (Exception e) {
            message = "Please enter at least one search criteria.";
        }

        return new ModelAndView("findStudent", "student", student).addObject("controls", controls)
                .addObject("message", message).addObject("students", students);
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    public RedirectView getDeleteStudent(@RequestParam Long studentID, @RequestParam String firstName,
                                         @RequestParam String lastName, RedirectAttributes redir) {
        List<Student> students = new ArrayList<>();
        Student student = Student.builder().build();
        String message;

        try {
            Student studentToDelete = Student.builder().studentID(studentID).build();
            studentServices.deleteStudent(studentToDelete);

            List<Assignment> assignedClasses = assignmentService.getAssignmentsByStudentID(studentID);
            assignedClasses.forEach(assignmentService::deleteAssignment);

            student = Student.builder().firstName(firstName).lastName(lastName).build();
            students = studentServices.getStudentsByNames(student.getFirstName(), student.getLastName());
            message = "The student was deleted.";
        } catch (Exception e) {
            message = "The student deletion could NOT be performed.";
        }

        redir.addFlashAttribute("controls", controls).addFlashAttribute("student", student)
                .addFlashAttribute("message", message).addFlashAttribute("students", students);

        return new RedirectView("/findStudent", true);
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.GET)
    public RedirectView getUpdateStudent(@RequestParam Long studentID, RedirectAttributes redir){
        Student studentToUpdate = studentServices.getStudent(studentID);
        redir.addFlashAttribute("student", studentToUpdate);

        return new RedirectView("/", true);
    }

    //Populate the list of controls to be displayed in the Student list, such as Update and Delete
    private static List<Control> controls = new ArrayList<Control>(Arrays.asList(
            new Control("btn btn-success btn-sm", "updateStudent", false, "Update"),
            new Control("btn btn-danger btn-sm", "deleteStudent", true, "Delete")));
}