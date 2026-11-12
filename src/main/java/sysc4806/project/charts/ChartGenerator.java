package sysc4806.project.charts;

public abstract class ChartGenerator {
   public static final int CHART_HEIGHT = 600;
   public static final int CHART_WIDTH = 600;

    public abstract byte[] generateChart(Integer surveyId, Integer questionId);
}
