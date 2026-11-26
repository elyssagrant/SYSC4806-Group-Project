import { useEffect, useState } from "react"
import { getId, viewForm } from "./formEndpoints";
import { Form, Question, SurveySummary } from "./types";
import QuestionField from "./surveyComponents/QuestionField";
import DiscoveryTable from "./otherComponents/DiscoveryTable";

export default function ViewForm() {
    const [id, setId] = useState('');
    const [confirmId, setConfirmId] = useState(false);
    const [fetchError, setFetchError] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const [name, setName] = useState("");
    const [questions, setQuestions] = useState<Question[]>([]);
    const [pageNumber, setPageNumber] = useState(1);
    const [discoveryData, setDiscoveryData] = useState<SurveySummary[]>([]);

    async function getForm() {
        try {
            const form: Form = await viewForm(id); 
            setName(form.name);
            setQuestions(form.questions);     

        } catch (err: any) {
            setErrorMessage("There was an error retrieving the form: " + err.message)
            setFetchError(true)
            return;
        }
        setConfirmId(true)
    }

    function updateAnswer(index: number) {
        // setQuestions(q => q.map((q,i) => (i == index ? answer: q)));
    }

    // function submitAnswer() {
    // }

    function resetForm() {
        setName("");
        setQuestions([]);
        setErrorMessage("");
        setConfirmId(false);
        setFetchError(false);
        setId("");
    }

    function tableLoad() {

    }

    function onPageChange(pageNum: number) {
        setPageNumber(pageNum)
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
                    <QuestionField mode="answer" question = {q} onAnswer={updated => updateAnswer(i)}></QuestionField>
                </div>

            ))}
            <button type="submit" className="button">Submit</button>
            <button className="button" onClick={resetForm}>Reset</button>

            </div>
        ) : (
            <div className="formNameContainer">
                <h1>View Forms</h1>
                <DiscoveryTable pageSize={10} onLoad={tableLoad} onPageChange={onPageChange} data={discoveryData} ></DiscoveryTable>
                
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
                {fetchError && <p className="errorText">${errorMessage}</p>}
            </div>
        )
        }
        </>
    );
}