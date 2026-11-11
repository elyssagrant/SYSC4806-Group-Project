import { useState } from "react"
import { newQuestion, QUESTION_TYPES } from "./surveyComponents/componentTypes";
import { Question } from './types';
import QuestionField from "./surveyComponents/QuestionField";
import { createForm } from "./formEndpoints";

export default function CreateFormPage() {
    const [name, setName] = useState('');
    const [selectedType, setSelectedType] = useState<Question['type']>('Text');
    const [questions, setQuestions] = useState<Question[]>([]);
    const [submittedForm, setSubmittedForm] = useState(false);
    const [formSubmitError, setFormSubmitError] = useState(false);
    const [questionErrors, setQuestionErrors] = useState<Record<number, string>>({})
    const [message, setMessage] = useState<string | null>(null)

    function addQuestion() {
        setQuestions([...questions, newQuestion(selectedType)]);
    }

    function updateQuestion(index: number, update: Question) {
        setQuestions(q => q.map((q,i) => (i == index ? update: q)));
    }

    function submitForm() {
        try {
            const id = createForm(name);
            document.cookie = `id=${id}`;
        } catch(err: any) {
            setMessage(err.message);
            setFormSubmitError(true);
            return;
        }
        setSubmittedForm(true);
    }

    function addToForm() {
    }

    return (
        <div>
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
            </div>
            ) : ( 
            <div>
                <div>
                    {questions.length == 0 && <p>No questions have been created yet. Please pick a question type and press Add</p>}

                    {questions.map((q, i) => (
                        <div>
                            <span>
                                Question {i + 1}
                            </span>
                            <QuestionField mode="edit" question = {q} onChange={updated => updateQuestion(i, updated)}></QuestionField>
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
                    {/* <QuestionField mode="edit" question = {q} onChange={updated => updateQuestion(0, updated)}></QuestionField> */}
                <button type="button" onClick={addQuestion}>Add</button>
                <button type="submit" onClick={addToForm}>Submit</button>
            </div>
            )}
        </div>
    )
}