import { useState } from "react"
import { newQuestion, QUESTION_TYPES } from "./surveyComponents/componentTypes";
import { Question } from './types';
import QuestionField from "./surveyComponents/QuestionField";
import { addToForm, createForm, getId, storeId } from "./formEndpoints";

export default function CreateFormPage() {
    const [name, setName] = useState('');
    const [selectedType, setSelectedType] = useState<Question['type']>('Text');
    const [questions, setQuestions] = useState<Question[]>([]);
    const [submittedForm, setSubmittedForm] = useState(false);
    const [formSubmitError, setFormSubmitError] = useState(false);
    const [questionErrors, setQuestionErrors] = useState<Record<number, string>>({});
    const [message, setMessage] = useState<string | null>(null);

    function addQuestion() {
        setQuestions([...questions, newQuestion(selectedType)]);
    }

    function updateQuestion(index: number, update: Question) {
        setQuestions(q => q.map((q,i) => (i === index ? update: q)));
    }

    async function submitForm() {
        try {
            const id = await createForm(name);
            storeId(id);
        } catch(err: any) {
            setMessage(err.message);
            setFormSubmitError(true);
            return;
        }
        setSubmittedForm(true);
    }


    function submitFormAddition() {
        try {
            const id = getId();
            if (id) {
                addToForm(id, questions);
            } else {
                setMessage("You have not created a form yet!")
                setFormSubmitError(true)
            }
            setName("");
            setQuestions([])
            setQuestionErrors({});
            setSubmittedForm(false);
            setFormSubmitError(false);
            setQuestionErrors({})
        } catch(err: any) {
            setMessage(err.message);
        }
    }

    return (
        <div className="formNameContainer">
            { !submittedForm ? (
            <div>
                <h1>Form Creation</h1>
                <label>
                    Name
                    <input
                        type="text"
                        value={name}
                        placeholder="Enter name here"
                        onChange={(e => setName(e.target.value))}
                    ></input>
                </label>
                <button type="submit" onClick={submitForm}>Submit</button>
                {formSubmitError && <p>{message}</p>}
            </div>
            ) : ( 
            <div>
                <h1>Form Creation</h1>
                <div>
                    {questions.length === 0 && <p>No questions have been created yet. Please pick a question type and press Add</p>}

                    {questions.map((q, i) => (
                        <div className="singleQuestionContainer">
                            <span className="questionNumber">
                                Question {i + 1}
                            </span>
                            <QuestionField mode="edit" question = {q} onChange={updated => updateQuestion(i, updated)}></QuestionField>
                            {questionErrors[i] && <p>{questionErrors[i]}</p>}
                        </div>

                    ))}
                </div>
                <select value={selectedType} onChange={e => setSelectedType(e.target.value as Question['type'])}>
                        {QUESTION_TYPES.map(t => (
                            <option key={t.value} value={t.value}>
                                {t.label}
                            </option>
                        ))}
                    </select>
                <button type="button" onClick={addQuestion}>Add</button>
                <button type="submit" onClick={submitFormAddition}>Submit</button>
            </div>
            )}
        </div>
    )
}