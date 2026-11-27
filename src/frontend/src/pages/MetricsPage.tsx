import { useState } from "react";
import * as ep from "../metricsEndpoints";
import {Result} from "../types";

export default function Metrics() {
    const [id, setId] = useState("");
    const [confirmId, setConfirmId] = useState(false);
    const [fetchError, setFetchError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [results, setResults] = useState<Result[]>([]);

    async function generateMetrics() {
        try {
            const metrics: Result[] = await ep.getMetrics(id);

            setResults(metrics);
            setFetchError(false);
            setErrorMessage("");

        } catch (err: any) {
            setErrorMessage(
                "There was an error retrieving the metrics: " + err.message
            );
            setFetchError(true);
            return;
        }

        setConfirmId(true);
    }

    // function resetMetrics() {
    //     setResults([]);
    //     setErrorMessage("");
    //     setConfirmId(false);
    //     setFetchError(false);
    //     setId("");
    // }

    return (
        <div className="allResultsContainer">
            {confirmId ? (
                <>
                <div className="metricsTitle">
                    Metrics for survey id # {id}
                </div>
                    {results.map((r, i) => (
                        <div className="singleResultContainer">

                        <div className="metricsSubTitle">
                            Results for Question id# {ep.getId(r)}
                        </div>

                            {(ep.isMultipleChoice(r) || ep.isNumberRange(r)) ? (
                                <img
                                    src={`data:image/png;base64,${ep.getChart(r)}`}
                                    alt={`Chart for question ${i + 1} PNG`}
                                    className="metricsChart"
                                />

                            ) : ep.isText(r) ? (
                                <div>
                                <div className="metricsSubTitle">
                                    Question: "{ep.getQuestionText(r)}"
                                </div>

                                {ep.getTextResponses(r).map((text, i) => (
                                    <div className="textResponseBoby">
                                        {text}
                                    </div>
                                ))}
                                </div>
                            ) : null}

                        </div>
                    ))}
                </>
            ) : (
                <div className="formNameContainer">
                    <h1>View Survey Metrics</h1>
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
                    <button className="button" onClick={generateMetrics}>Show Metrics</button>
                    {fetchError && <p className="errorText">${errorMessage}</p>}
                </div>
            )
            }
        </div>
    );
}