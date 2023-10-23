package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Added import

import java.util.ArrayList;

@Controller
@RequestMapping("search")
public class SearchController {

    //This method takes a Model parameter, which is used to pass data to the view. it allows to add attributes
    // that can be accessed in the view template.adds an attribute named "columns" to the model. It gets the column choices
    // from the ListController and adds them to the model. These column choices are used to populate a dropdown menu in the search form.
    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        // specifies the name of the view template ("search.html") to be rendered. When the method returns "search," Spring Framework knows
        // which HTML template to display.
        return "search";
    }

    // Create a handler to process a search request and render the updated search view
    // Use @GetMapping to configure the mapping type and route.
    //This method handles HTTP POST requests with a path of "/results." It is used to process
    // the form submission and display search results.
    @PostMapping(value = "results")
    //method takes a Model parameter for passing data to the view and two request parameters: searchType and searchTerm.
    // searchType represents the selected search category  and searchTerm is the user's search input.
    public String displaySearchResults(Model model, // Take in a Model parameter
                                       @RequestParam String searchType, // Specify the type of search
                                       @RequestParam String searchTerm) { // Take in the search term

        ArrayList<Job> jobs;

        // If the user enters "all" in the search box or leaves it empty, call findAll() from JobData
        //to retrieve all job listings.
        if (searchTerm.isEmpty() || searchType.equals("all")) {
            jobs = JobData.findAll();
        } else {

            // Otherwise, it sends the search information to the findByColumnAndValue
            //        // method in JobData to retrieve filtered job listings.
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        // Store the results in the "jobs" ArrayList
        //adds the "jobs" attribute to the model, which contains the search results.
        // These search results will be displayed in the view
        model.addAttribute("jobs", jobs);

        // adds the "columns" attribute to the model, which is used to populate
        // the dropdown menu in the search form.
        model.addAttribute("columns", ListController.columnChoices);

        //the method returns "search" to specify that the "search.html" template
        // should be rendered with the search results.
        return "search";
    }
}
