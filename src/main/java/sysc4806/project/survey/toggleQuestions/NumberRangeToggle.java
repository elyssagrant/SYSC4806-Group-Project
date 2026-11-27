package sysc4806.project.survey.toggleQuestions;

import org.ff4j.aop.Flip;

public interface NumberRangeToggle {

    @Flip(name = "newImplementation", alterBean = "newFeature")
    void execute();
}