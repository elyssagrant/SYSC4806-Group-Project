import { useEffect, useState } from "react"
import { answerForm, getId, viewForm } from "./formEndpoints";
import { Answer, Form, Question } from "./types";
import QuestionField from "./surveyComponents/QuestionField";
import { checkAnswer, convertMCIndexToMCString, convertMCStringToMCIndex, Response } from "./surveyComponents/componentTypes";

export default function ViewForm() {
    const [id, setId] = useState('');
    const [confirmId, setConfirmId] = useState(false);
    const [message, setMessage] = useState("");
    const [questionErrors, setQuestionErrors] = useState<Record<number, string>>({});
    const [formSubmitError, setFormSubmitError] = useState(false);
    const [hasMessage, setHasMessage] = useState(false);
    const [version, setVersion] = useState(0);

    const [name, setName] = useState("");
    const [questions, setQuestions] = useState<Question[]>([]);
    const [answers, setAnswers] = useState<Answer[]>([]);

    async function getForm() {
        try {
            const form: Form = await viewForm(id); 
            setName(form.name);
            setQuestions(form.questions);
            setVersion(form.version)
            setAnswers(form.questions.map(q => ({ id: q.id, type: q.type, response: "" })));
        } catch (err: any) {
            setMessage(err.message)
            setFormSubmitError(true)
            return;
        }
        setConfirmId(true)
    }


    function updateAnswer(index: number, update: Response) {
        if (update === undefined) return;
        setAnswers(old => old.map((a, i) => i === index ? {...a, response: update} : a))
    }

    async function submitAnswer() {
        resetErrors()

        // First go through every question and validate the answer
        const errors: Record<number, string> = {};
        questions.forEach((q, i) => {
                const a = answers[i];
                const message = a ? checkAnswer(q, a) : "No response given";
                if (message) {
                    console.log("An error was found on question " + i + ": " + message);
                    errors[q.id] = message;
                } 
        });

        if (Object.keys(errors).length !== 0) {
            // Set the question errors and return
            setFormSubmitError(true);
            setQuestionErrors(errors);
            setMessage("Some responses are invalid. Please fix before saving again.");
            return;
        }

        // Once answers hvae have been validated locally, move on
        try {
            convertMCStringToMCIndex(questions, answers);
            await answerForm(id, version, Object.values(answers));
            convertMCIndexToMCString(questions, answers);
            setHasMessage(true);
        } catch (error: any) {
            setFormSubmitError(true);
            setMessage("Error: " + error);
        }
    }

    function resetForm() {
        setName("");
        setQuestions([]);
        setMessage("");
        setConfirmId(false);
        setFormSubmitError(false);
        setId("");
        setQuestionErrors({})
        setAnswers([])
        setHasMessage(false);
    }

    function resetErrors() {
        setFormSubmitError(false);
        setHasMessage(false);
        setMessage("");
        setQuestionErrors({})
    }


    useEffect(() => {
        // If the user has created a form previously and saved
        // that id locally, load that form. (for demo)
        const id = getId();
        if (id) {
            setId(id)
        }
    }, []);
    
    return (
        <>
        { confirmId ? (
            <div className="formNameContainer">
            <h1 className="formTitle">{name}</h1>
            {questions.map((q, i) => (
                <div className="singleQuestionContainer">
                    <span className="questionNumber">
                        Question {i + 1}
                    </span>
                    <QuestionField mode="answer" value = {answers[i].response} question = {q} onAnswer={(value: Response) => {
                        updateAnswer(i, value)
                    }}></QuestionField>
                    {questionErrors[q.id] && <p>{questionErrors[q.id]}</p>}
                </div>

            ))}
            {formSubmitError && <p className="errorText">{message}</p>}
            {hasMessage && <p className="messageText">{message}</p>}

            <button type="submit" className="button" onClick={submitAnswer}>Submit</button>
            <button className="button" onClick={resetForm}>Reset</button>

            </div>
        ) : (
            <div className="formNameContainer">
                <h1>View Forms</h1>
                <p className="questionNumber">Please enter an id.</p>
                <label>
                    ID
                    <input 
                        type="text"
                        value={id}
                        placeholder={id}
                        onChange={(e) => setId(e.target.value)}
                    />
                </label>
                <button className="button" onClick={getForm}>Find Form</button>
                {formSubmitError && <p className="errorText">{message}</p>}
                {hasMessage && <p className="messageText">{message}</p>}
            </div>
        )
        }
        </>
    );
}