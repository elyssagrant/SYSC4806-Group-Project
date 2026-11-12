package sysc4806.project.survey.questions;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NumberRangeQuestion.class, name = "NumberRange"),
        @JsonSubTypes.Type(value = TextQuestion.class, name = "Text"),
        @JsonSubTypes.Type(value = MultipleChoiceQuestion.class, name = "MultipleChoice")
})
public interface Question {
    int getId();
    void setId(int id);
}
