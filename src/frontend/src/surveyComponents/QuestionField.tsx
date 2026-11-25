/**
 * Renders the right survey component for a question's type, in either edit or answer mode. 
 * Used as a wrapper for the actual components.
 */

import { Question } from "../types"
import { FieldProps } from "./componentTypes"
import MultipleChoiceField from "./MultipleChoiceField"
import NumberRangeField from "./NumberRangeField"
import TextField from "./TextField"

export default function QuestionField(props: FieldProps<Question>) {
    const q = props.question

    if (props.mode === 'edit') {
        const { onChange } = props

        switch (q.type) {
            case 'Text':
                return <TextField mode="edit" question={q} onChange={onChange} />
            case 'NumberRange':
                return <NumberRangeField mode="edit" question={q} onChange={onChange} />
            case 'MultipleChoice':
                return <MultipleChoiceField mode="edit" question={q} onChange={onChange} />
        }
    } else {

        const { value, onAnswer } = props

        switch (q.type) {
            case 'Text':
                return <TextField mode="answer" question={q} value={value} onAnswer={onAnswer} />
            case 'NumberRange':
                return <NumberRangeField mode="answer" question={q} value={value} onAnswer={onAnswer} />
            case 'MultipleChoice':
                return <MultipleChoiceField mode="answer" question={q} value={value} onAnswer={onAnswer} />
        }
    }


}