import { useState } from "react"
import { checkQuestion, newQuestion, QUESTION_TYPES } from "./surveyComponents/componentTypes";
import { Form, Question } from './types';
import QuestionField from "./surveyComponents/QuestionField";
import { saveToForm, createForm, getId, storeId, viewForm, FormRequestError, deleteForm } from "./formEndpoints";

//Edit modes to select for the dropdown.
enum EDIT_TYPES {
    Edit = 'Edit',
    Create = 'Create',
    Delete = 'Delete'
}

export default function CreateFormPage() {
    const [name, setName] = useState('');
    const [id, setId] = useState('');
    const [selectedType, setSelectedType] = useState<Question['type']>('Text');
    const [selectedEditType, setSelectedEditType] = useState<EDIT_TYPES>(EDIT_TYPES.Create)
    const [submittedForm, setSubmittedForm] = useState(false);
    const [formSubmitError, setFormSubmitError] = useState(false);
    const [hasMessage, setHasMessage] = useState(false);
    const [message, setMessage] = useState<string | null>(null);

    // The list of questions on the remote end.
    // compared and used to compile a list of operations when saving
    const [remoteQuestions, setRemoteQuestions] = useState<Question[]>([]);
    const [version, setVersion] = useState(0);
    // Current questions in editing session
    const [questions, setQuestions] = useState<Question[]>([]);
    const [questionErrors, setQuestionErrors] = useState<Record<number, string>>({});

    // For getting an existing form
    async function getOrDeleteForm() {
        if (selectedEditType === EDIT_TYPES.Delete) {
            try {
                await deleteForm(id);
            } catch (err: any) {
                setMessage("There was an error deleting the form: " + err.message)
                setFormSubmitError(true)
                return;
            }
        } else {
            try {
                const form: Form = await viewForm(id); 
                setName(form.name);
                setRemoteQuestions(form.questions);  
                setQuestions(form.questions);
                setVersion(form.version)
                storeId(id);
            } catch (err: any) {
                setMessage(err.message)
                setFormSubmitError(true)
                return;
            }
            setSubmittedForm(true)
        }

    }

    // For creating a new form
    async function submitForm() {
        try {
            const id = await createForm(name);
            storeId(id);
            setId(id);
        } catch(err: any) {
            setMessage(err.message);
            setFormSubmitError(true);
            return;
        }
        setSubmittedForm(true);
    }

    function addQuestion() {
        setQuestions([...questions, newQuestion(selectedType, questions.length)]);
    }

    function updateQuestion(index: number, update: Question) {
        setQuestions(q => q.map((q,i) => (i === index ? update: q)));
    }

    function deleteQuestion(index: number) {
        setQuestions(q => q.filter((_, i) => i !== index));
    }

    async function saveForm() {
        resetErrors();
        // First check all questions in the list
        const errors: Record<number, string> = {};
        questions.forEach((question, index) => {
            let error = checkQuestion(question)
            if (error) {
                errors[question.id] = error;
            }
        })
        if (Object.keys(errors).length > 0) {
            setFormSubmitError(true);
            setQuestionErrors(errors);
            setMessage("Some questions are invalid. Please fix before saving again.");
            return;
        }

        // A list of changes need to be compiled for sending to the user
        // handled in the endpoint function
        try {
            const id = getId();
            if (id) {
                await saveToForm(id, version, questions, remoteQuestions);
            } else {
                setMessage("You have not created a form yet!")
                setFormSubmitError(true)
                return;
            }
            // Because all questions are assigned an id on the back-end. that means we need to refetch the form.
            setSelectedEditType(EDIT_TYPES.Edit);
            await getOrDeleteForm();
            setMessage("Form Alteration Successful.");
            setHasMessage(true);
        } catch(error: any) {
            if (error instanceof FormRequestError) {
                setQuestionErrors(error.questionErrors);
                setMessage("Some questions are invalid. Please fix before saving again.");
                setFormSubmitError(true)

            } else {
                setFormSubmitError(true);
                setMessage(error.message);
            }
        }
    }

    function resetErrors() {
        setQuestionErrors({});
        setFormSubmitError(false);
        setHasMessage(false);
        setMessage("")
    }

    function resetPage() {
        setName("");
        setQuestions([]);
        setSubmittedForm(false);
        setSelectedEditType(EDIT_TYPES.Create);
        setRemoteQuestions([]);
        resetErrors();
    }

    return (
        <div className="formNameContainer">
            { !submittedForm ? (
            <div>
                <h1>Form Creation</h1>
                <p>Enter the name to create a form, or specify the ID of one to edit/delete.</p>
                <select value={selectedEditType} onChange={e => setSelectedEditType(e.target.value as EDIT_TYPES)}>
                    {(Object.values(EDIT_TYPES) as EDIT_TYPES[]).map(t => (
                    <option key={t} value={t}>
                        {EDIT_TYPES[t]}
                    </option>
                    ))}
                </select>
                { selectedEditType === EDIT_TYPES.Create ? (
                <>
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
                </>
                ) : (
                <>
                    <p>Please enter an id.</p>
                    <label>
                        ID
                        <input 
                            type="text"
                            value={id}
                            placeholder={id}
                            onChange={(e) => setId(e.target.value)}
                        />
                    </label>
                    <button type="submit" onClick={getOrDeleteForm}>Submit</button>
                </>
                )
                }
                {formSubmitError && <p>{message}</p>}
                {hasMessage && <p>{message}</p>}
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
                            <button type="button" onClick={() => deleteQuestion(i)}>Delete</button>
                            {questionErrors[q.id] && <p>{questionErrors[q.id]}</p>}
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
                <button type="submit" onClick={saveForm}>Save</button>
                <button type="button" onClick={resetPage}>Reset</button>
                {formSubmitError && <p>{message}</p>}
                {hasMessage && <p>{message}</p>}
            </div>
            )}
        </div>
    )
}