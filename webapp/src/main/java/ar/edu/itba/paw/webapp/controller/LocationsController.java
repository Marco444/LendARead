package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LocationsController {

    private final LocationsService locationsService;
    private final UserService userService;
    private final static String VIEW_NAME = "views/locations";

    @Autowired
    public LocationsController(LocationsService locationsService, UserService userService) {
        this.locationsService = locationsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userLocations", method = RequestMethod.GET)
    public ModelAndView manageLocations() throws UserNotFoundException {
        List<LocationImpl> locations = locationsService.getLocations(userService.getUser(userService.getCurrentUser()));
        return new ModelAndView(VIEW_NAME).addObject("locations", locations);
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public ModelAndView editLocation(@RequestParam("id") int id,
                                     @RequestParam("name") String name,
                                     @RequestParam("locality") String locality,
                                     @RequestParam("province") String province,
                                     @RequestParam("country") String country,
                                     @RequestParam("zipcode") String zipcode) throws UserNotFoundException {

        if(id == -1) {
            LocationImpl newLocation = new LocationImpl(name, zipcode, locality, province, country, userService.getUser(userService.getCurrentUser()));
            locationsService.addLocation(newLocation);
        } else {
            LocationImpl newLocation = new LocationImpl(id, name, zipcode, locality, province, country, userService.getUser(userService.getCurrentUser()));
            locationsService.editLocation(newLocation);
        }

        return new ModelAndView("redirect:/" + VIEW_NAME);
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.POST)
    public ModelAndView deleteLocation(@RequestParam("id") int id) throws UserNotFoundException {
        locationsService.deleteLocationById(id);
        return new ModelAndView("redirect:/" + VIEW_NAME);
    }

}
