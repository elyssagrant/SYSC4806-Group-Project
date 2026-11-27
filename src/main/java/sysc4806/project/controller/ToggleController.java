package sysc4806.project.controller;

import org.springframework.web.bind.annotation.*;
import org.ff4j.FF4j;

public class ToggleController {

    private final FF4j ff4j;

    public ToggleController(FF4j ff4j) {
        this.ff4j = ff4j;
    }


    @PostMapping("/multipleChoice/toggle")
    public boolean toggleMultipleChoice() {

        if (ff4j.getFeature("multipleChoice").isEnable()) {
            ff4j.disable("numberRange");
        } else {
            ff4j.enable("numberRange");
        }

        return ff4j.getFeature("numberRange").isEnable();
    }

    @PostMapping("/numberRange/toggle")
    public boolean toggleNumberRange() {

        if (ff4j.getFeature("numberRange").isEnable()) {
            ff4j.disable("numberRange");
        } else {
            ff4j.enable("numberRange");
        }

        return ff4j.getFeature("numberRange").isEnable();
    }

    @PostMapping("/text/toggle")
    public boolean textToggle() {

        if (ff4j.getFeature("text").isEnable()) {
            ff4j.disable("text");
        } else {
            ff4j.enable("text");
        }

        return ff4j.getFeature("text").isEnable();
    }


}
