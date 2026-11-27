package sysc4806.project.FeatureToggling;

import org.ff4j.aop.Flip;

public interface NumberRangeToggle {

    @Flip(name = "numberRange", alterBean = "newNumberRange")
    void execute();
}