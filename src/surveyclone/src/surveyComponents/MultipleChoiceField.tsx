import { MultipleChoiceQuestion } from '../types';
import { useId } from 'react'
import { FieldProps } from './componentTypes';


export default function MultipleChoiceField( props: FieldProps<MultipleChoiceQuestion>) {
    const { question } = props;
    const groupName = useId();

    if (props.mode == 'edit') {
        const { onChange } = props;

        const updateChoice = (index: number, text: string) => {
            onChange({...question, choices: question.choices.map((choice, i) => (i == index ? text : choice))});
        }

        const addChoice = () =>  {
            onChange({...question, choices: [...question.choices, ""]})
        }

        const removeChoice = (index: number) =>  {
            onChange({ ...question, choices: question.choices.filter((_, i) => i !== index) })
        }

        return (
            <div className="formField">
                <label className="questionInput">
                    <input
                        type="text"
                        value={question.questionText}
                        placeholder="Question goes here"
                        onChange={e => onChange({ ...question, questionText: e.target.value })}
                    />
                    {
                        question.choices.map((choice, i) => (
                            <label className="multipleChoiceOption">
                                <input
                                    type="text"
                                    value={choice}
                                    placeholder={`Option ${i + 1}`}
                                    onChange={(e) => updateChoice(i, e.target.value)}
                                />
                                <button type="button" onClick={(() => removeChoice(i))}>Remove</button>
                            </label>
                        ))
                    }
                </label>

            </div>
        )

    } else {
        const { value, onAnswer } = props;

        return (
            <div className="formField">
                <p className="questionLabel">{question.questionText}</p>
                { question.choices.map((choice, i) => (
                    <label className="multipleChoiceOption">
                        <input
                            type="radio"
                            name={groupName}
                            value={choice}
                            checked={value === choice}
                            onChange={() => onAnswer(choice)}
                        />
                    </label>
                ))
                    
                }
            </div>
        )

    }
}