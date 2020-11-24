package com.digitalharbor.evaluation.bo.client.controller;

import com.digitalharbor.evaluation.bo.client.model.AcademyClass;
import com.digitalharbor.evaluation.bo.client.model.Assignment;
import com.digitalharbor.evaluation.bo.client.service.AcademyClassService;
import com.digitalharbor.evaluation.bo.client.helper.Control;
import com.digitalharbor.evaluation.bo.client.service.AssignmentService;
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
public class AcademyClassController {
    @Autowired
    private AcademyClassService academyClassServices;
    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(value = "/academyClass", method = RequestMethod.GET)
    public ModelAndView getAcademyClass(ModelMap model){
        if (model.isEmpty()) {
            return new ModelAndView("academyClass", "academyClass", new AcademyClass())
                    .addObject("pageTitle", "New Class");
        }

        return new ModelAndView("academyClass").addObject("pageTitle", "Update Class");
    }

    @RequestMapping(value = "/academyClass", method = RequestMethod.POST)
    public ModelAndView postAcademyClass(@ModelAttribute("academyClass") AcademyClass academyClass){
        String message;
        boolean isANewRecord = academyClass.getCode() == null;
        try {
            academyClassServices.saveAcademyClass(academyClass);
            message = "Your data was saved.";
        }
        catch (Exception e) {
            message = "Your data could NOT be saved.";
        }

        ModelAndView mv = new ModelAndView("academyClass");
        if (isANewRecord){
            return new ModelAndView("academyClass").addObject("academyClass", new AcademyClass())
                    .addObject("pageTitle", "New Class").addObject("message", message);
        }
        else {
            mv.addObject("pageTitle", "Update Class");
        }

        return new ModelAndView("academyClass").addObject("pageTitle", "Update Class").addObject("message", message);
    }

    @RequestMapping(value = "/findAcademyClass", method = RequestMethod.GET)
    public ModelAndView getFindAcademyClass(ModelMap model){
        if (model.isEmpty()) {
            return new ModelAndView("findAcademyClass", "academyClass", new AcademyClass())
                    .addObject("action", "/findAcademyClass");
        }
        return new ModelAndView("findAcademyClass").addObject("action", "/findAcademyClass");
    }

    @RequestMapping(value = "/findAcademyClass", method = RequestMethod.POST)
    public ModelAndView postFindAcademyClass(@ModelAttribute("academyClass") AcademyClass academyClass){
        List<AcademyClass> academyClasses = new ArrayList<>();
        String message;

        try {
            academyClasses = academyClassServices.getAcademyClassesByTitleAndDescription(
                    academyClass.getTitle(), academyClass.getDescription());
            message = academyClasses.size() + " classes were found.";
        }
        catch (Exception e) {
            message = "Please enter at least one search criteria.";
        }

        return new ModelAndView("findAcademyClass", "academyClass", academyClass).addObject("controls", controls)
                .addObject("message", message).addObject("academyClasses", academyClasses);
    }

    @RequestMapping(value = "/deleteAcademyClass", method = RequestMethod.GET)
    public RedirectView getDeleteAcademyClass(@RequestParam Long code, @RequestParam String title,
                                              @RequestParam String description, RedirectAttributes redir) {
        List<AcademyClass> academyClasses = new ArrayList<>();
        AcademyClass academyClass = AcademyClass.builder().build();
        String message;

        try {
            AcademyClass academyClassToDelete = AcademyClass.builder().code(code).build();
            academyClassServices.deleteAcademyClass(academyClassToDelete);

            List<Assignment> assignedClasses = assignmentService.getAssignmentsByCode(code);
            assignedClasses.forEach(assignmentService::deleteAssignment);

            academyClass = AcademyClass.builder().title(title).description(description).build();
            academyClasses = academyClassServices.getAcademyClassesByTitleAndDescription(
                    academyClass.getTitle(),
                    academyClass.getDescription());

            message = "The class was deleted.";
        } catch (Exception e) {
            message = "The class deletion could NOT be performed.";
        }

        redir.addFlashAttribute("controls", controls).addFlashAttribute("academyClass", academyClass)
                .addFlashAttribute("message", message).addFlashAttribute("academyClasses", academyClasses);

        return new RedirectView("/findAcademyClass", true);
    }

    @RequestMapping(value = "/updateAcademyClass", method = RequestMethod.GET)
    public RedirectView getUpdateAcademyClass(@RequestParam Long code, RedirectAttributes redir){
        AcademyClass academyClassToUpdate = academyClassServices.getAcademyClass(code);
        redir.addFlashAttribute("academyClass", academyClassToUpdate);

        return new RedirectView("/academyClass", true);
    }

    //Populate the list of controls to be displayed in the AcademyClass list, such as Update and Delete
    private static List<Control> controls = new ArrayList<Control>(Arrays.asList(
            new Control("btn btn-success btn-sm", "updateAcademyClass", false, "Update"),
            new Control("btn btn-danger btn-sm", "deleteAcademyClass", true, "Delete")));
}