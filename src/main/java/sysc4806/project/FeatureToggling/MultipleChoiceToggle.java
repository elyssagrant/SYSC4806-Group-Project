package sysc4806.project.FeatureToggling;

import org.ff4j.aop.Flip;

public interface MultipleChoiceToggle {

    @Flip(name = "multipleChoice", alterBean = "newMultipleChoice")
    void execute();
}