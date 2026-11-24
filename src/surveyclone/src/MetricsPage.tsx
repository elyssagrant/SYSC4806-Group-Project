import { useState } from "react";
import { getMetrics } from "./metricsEndpoints";

export default function Metrics() {
    const [id, setId] = useState("");
    const [confirmId, setConfirmId] = useState(false);
    const [fetchError, setFetchError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    const [charts, setCharts] = useState<string[]>([]);

    async function generateMetrics() {
        try {
            const metrics: string[] = await getMetrics(id);

            setCharts(metrics);
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

    function resetMetrics() {
        setCharts([]);
        setErrorMessage("");
        setConfirmId(false);
        setFetchError(false);
        setId("");
    }

    return (
        <>
            {confirmId ? (
                <div className="allChartsContainer">
                    {charts.map((c, i) => (
                        <div className="singleChartContainer">

                        <span className="chartNumber">
                            Chart {i + 1}
                        </span>

                        <img
                            src={`data:image/png;base64,${c}`}
                            alt={`Chart for question ${i + 1} PNG`}
                            className="metricsChart"
                        />
                        </div>
                    ))}
                </div>
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
        </>
    );
}