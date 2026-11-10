import { TextQuestion } from "../types";
import { FieldProps } from "./QuestionField";


export default function TextField(props: FieldProps<TextQuestion>) {
    const { question } = props;

    if (props.mode == 'edit') {
        return (
            <div className="formfield">
                <label className="questionInput">
                    <input
                    type="text"
                    value={question.questionText}
                    onChange={e => props.onChange({ ...question, questionText: e.target.value })}
                    />
                </label>
            </div>
        )
    } else {
        return (
            <div className="formfield">
                    <p className="questionLabel"></p>
                    <textarea
                    rows={3}
                    value={typeof props.value === 'string' ? props.value : ''}
                    onChange={e => props.onAnswer(e.target.value)}
                    />
            </div>
        ) 
    } 

}