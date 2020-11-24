package com.digitalharbor.evaluation.bo.client.controller;

import com.digitalharbor.evaluation.bo.client.helper.Control;
import com.digitalharbor.evaluation.bo.client.helper.StudentClassViewModel;
import com.digitalharbor.evaluation.bo.client.model.AcademyClass;
import com.digitalharbor.evaluation.bo.client.model.Assignment;
import com.digitalharbor.evaluation.bo.client.model.Student;
import com.digitalharbor.evaluation.bo.client.service.AcademyClassService;
import com.digitalharbor.evaluation.bo.client.service.AssignmentService;
import com.digitalharbor.evaluation.bo.client.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class AssignmentController {
    @Autowired
    private StudentService studentServices;
    @Autowired
    private AcademyClassService academyClassServices;
    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private HttpSession httpSession;

    @RequestMapping(value = "/findStudentForAssignment", method = RequestMethod.GET)
    public ModelAndView getFindStudent(ModelMap model){
        if (model.isEmpty()) {
            return new ModelAndView("findStudent", "student", new Student())
                    .addObject("action", "/findStudentForAssignment")
                    .addObject("navigationMessage", "Step 1: Select the student.");
        }
        return new ModelAndView("findStudent").addObject("action", "/findStudentForAssignment")
                .addObject("navigationMessage", "Step 1: Select the student");
    }

    @RequestMapping(value = "/findStudentForAssignment", method = RequestMethod.POST)
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

        return new ModelAndView("findStudent", "student", student).addObject("controls", controlsForStudentView)
                .addObject("message", message).addObject("students", students)
                .addObject("navigationMessage", "Step 1: Select the student");
    }

    @RequestMapping(value = "/findAcademyClassForAssignment", method = RequestMethod.GET)
    public ModelAndView getEnrollStudent(@RequestParam Long studentID, ModelMap model){
        httpSession.setAttribute("studentID", studentID);

        if (model.isEmpty()) {
            return new ModelAndView("findAcademyClass", "academyClass", new AcademyClass())
                    .addObject("action", "/findAcademyClassForAssignment")
                    .addObject("navigationMessage", "Step 2: Select the class");
        }

        return new ModelAndView("findAcademyClass").addObject("action", "/findAcademyClassForAssignment")
                .addObject("navigationMessage", "Step 2: Select the class");
    }

    private  List<AcademyClass> getAvailableClasses(AcademyClass academyClass, Long studentID){
        List<AcademyClass> academyClasses = academyClassServices.getAcademyClassesByTitleAndDescription(
                academyClass.getTitle(), academyClass.getDescription());

        List<Assignment> alreadyTakenClasses = assignmentService.getAssignmentsByStudentID(studentID);

        for(Assignment enrolledClass: alreadyTakenClasses) {
            academyClasses.removeIf(cla -> enrolledClass.getCode().equals(cla.getCode()));
        }

        return academyClasses;
    }

    @RequestMapping(value = "/findAcademyClassForAssignment", method = RequestMethod.POST)
    public ModelAndView postEnrollStudent(@ModelAttribute("academyClass") AcademyClass academyClass){
        List<AcademyClass> academyClasses = new ArrayList<>();
        String message;
        Long studentID =  ((Number) httpSession.getAttribute("studentID")).longValue();

        try {
            academyClasses = getAvailableClasses(academyClass, studentID);
            message = academyClasses.size() + " available classes were found.";
        }
        catch (Exception e) {
            message = "Please enter at least one search criteria.";
        }

        return new ModelAndView("findAcademyClass", "academyClass", academyClass)
                .addObject("controls", controlsForAcademyClassView)
                .addObject("message", message)
                .addObject("academyClasses", academyClasses)
                .addObject("action", "/findAcademyClassForAssignment")
                .addObject("navigationMessage", "Step 2: Select the class");
    }

    @RequestMapping(value = "/enrollStudent", method = RequestMethod.GET)
    public RedirectView enrollStudent(@RequestParam Long code, @RequestParam String title,
                                      @RequestParam String description, RedirectAttributes redir) {
        String message;
        Long studentID =  ((Number) httpSession.getAttribute("studentID")).longValue();
        Assignment assignment = Assignment.builder().studentID(studentID).code(code).build();

        try {
            assignmentService.createAssignment(assignment);
            message = "Your enrollment was saved.";
        }
        catch (Exception e) {
            message = "Your enrollment could NOT be saved.";
        }

        httpSession.removeAttribute("studentID");

        AcademyClass academyClass = AcademyClass.builder().title(title).description(description).build();
        List<AcademyClass> academyClasses = getAvailableClasses(academyClass, studentID);

        redir.addFlashAttribute("controls", controlsForAcademyClassView)
                .addFlashAttribute("academyClass", academyClass).addFlashAttribute("message", message)
                .addFlashAttribute("academyClasses", academyClasses);

        return new RedirectView("/findAcademyClassForAssignment?studentID=" + studentID.toString(), true);
    }

    @RequestMapping(value = "/findAssignmentByStudent", method = RequestMethod.GET)
    public ModelAndView getFindAssignmentByStudent(ModelMap model){
        if (model.isEmpty()) {
            return new ModelAndView("findAssignmentByStudent", "student", new Student());
        }
        return new ModelAndView("findAssignmentByStudent");
    }

    private List<StudentClassViewModel> getTakenClasses(Student student){
        List<StudentClassViewModel> assignments = new ArrayList<StudentClassViewModel>();
        List<Student> students = studentServices.getStudentsByNames(student.getFirstName(), student.getLastName());
        for (Student st: students) {
            List<Assignment> currentAssignments = assignmentService.getAssignmentsByStudentID(st.getStudentID());
            for (Assignment assignment: currentAssignments) {
                AcademyClass academyClass = academyClassServices.getAcademyClass(assignment.getCode());
                assignments.add(new StudentClassViewModel(assignment.getId(), st.getFirstName(),
                        st.getLastName(), academyClass.getTitle()));
            }
        }

        return assignments;
    }

    @RequestMapping(value = "/findAssignmentByStudent", method = RequestMethod.POST)
    public ModelAndView postFindAssignmentByStudent(@ModelAttribute("student") Student student){
        String message;
        List<StudentClassViewModel> assignments = new ArrayList<StudentClassViewModel>();

        try {
            assignments = getTakenClasses(student);
            message = assignments.size() + " students were found.";
        }
        catch (Exception e) {
            message = "Please enter at least one search criteria.";
        }

        return new ModelAndView("findAssignmentByStudent", "student", student)
                .addObject("controls", controlsForAssignmentByStudentView)
                .addObject("message", message).addObject("assignments", assignments);
    }

    @RequestMapping(value = "/deleteAssignment", method = RequestMethod.GET)
    public RedirectView getDeleteAssignment(@RequestParam Long id, @RequestParam String firstName,
                                         @RequestParam String lastName, RedirectAttributes redir) {
        String message;
        List<StudentClassViewModel> assignments = new ArrayList<StudentClassViewModel>();
        Student student = Student.builder().firstName(firstName).lastName(lastName).build();

        try {
            Assignment assignmentToDelete = Assignment.builder().id(id).build();
            assignmentService.deleteAssignment(assignmentToDelete);
            assignments = getTakenClasses(student);
            message = assignments.size() + " students were found.";
        }
        catch (Exception e) {
            message = "Please enter at least one search criteria.";
        }

        redir.addFlashAttribute("student", student)
                .addFlashAttribute("controls", controlsForAssignmentByStudentView)
                .addFlashAttribute("message", message).addFlashAttribute("assignments", assignments);

        return new RedirectView("/findAssignmentByStudent", true);
    }















    private static List<Control> controlsForStudentView = new ArrayList<Control>(Arrays.asList(
            new Control("btn btn-success btn-sm", "findAcademyClassForAssignment", false, "Select")));

    private static List<Control> controlsForAcademyClassView = new ArrayList<Control>(Arrays.asList(
            new Control("btn btn-success btn-sm", "enrollStudent", false, "Enroll")));

    private static List<Control> controlsForAssignmentByStudentView = new ArrayList<Control>(Arrays.asList(
            new Control("btn btn-danger btn-sm", "deleteAssignment", true, "Delete")));
}
