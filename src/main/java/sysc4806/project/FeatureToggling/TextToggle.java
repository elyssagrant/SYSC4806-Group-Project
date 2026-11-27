package sysc4806.project.FeatureToggling;

import org.ff4j.aop.Flip;

public interface TextToggle {

    @Flip(name = "text", alterBean = "newText")
    void execute();
}