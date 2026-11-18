package sysc4806.project.survey.questions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
// This is supposed to allow querying from the Question type.
// It makes a single table with all of the child types of question, with the entity_type parameter disciminating
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type")
// Below is for serializing + deserializing from the json message - distinguishes types by the "type" property I specified in the schema
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
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public abstract Integer getId();
    public abstract void setId(Integer id);
    public abstract void copy(Question question);

}
