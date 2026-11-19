package sysc4806.project.survey.responses;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = NumberRangeResponse.class, name = "NumberRange"),
        @JsonSubTypes.Type(value = TextResponse.class, name = "Text"),
        @JsonSubTypes.Type(value = TextResponse.class, name = "MultipleChoice")
})
public interface Response {
    Integer getId();
    void setId(Integer id);
}
