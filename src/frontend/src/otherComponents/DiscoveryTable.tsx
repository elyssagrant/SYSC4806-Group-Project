import { useEffect } from "react";
import { SurveySummary } from "../types";
import DiscoveryRow from "./DiscoveryRow";


interface DiscoveryTableProps {
    pageSize: number // The total number of entries that can exist in the table at a time.
    data: SurveySummary[] // What the table renders
    // We implement the page system on the backend as well. This sends another request with a new page number.
    // When the page number is changed - the component's parent should send the appropriate fetch (owned forms vs discovery) and modify the data prop based on input
    onPageChange: (pageNumber: number) => void 
    // This gets called when the component loads. This is when the initial fetch should run.
    onLoad: () => void
}


/**
 * A table that displays all available forms, paginated
 */
export default function DiscoveryTable(props: DiscoveryTableProps) {
    let { data } = props;
    const { pageSize, onPageChange, onLoad } = props;

    // This function is a react hook that gets called on component load.
    // If you add ,[] to the end of the curly bracket, any variable put in those square brackets that gets changed
    // causes the useEffect function to call again. We're using it to load the initial contents of the table.
    useEffect(() => {
        props.onLoad();
    }, [])

    return (
        <div className="survey-table-div">
            <table className="survey-table">
                <thead>
                    <th>ID</th>
                    <th>Title</th> 
                    <th>Open?</th>
                    <th>Responses</th>
                    <th>Questions</th>
                </thead>
                <tbody>
                    {data.map((SurveySummary) => (
                        <DiscoveryRow data={SurveySummary}></DiscoveryRow>
                    ))}
                </tbody>
            </table>
        </div>
    );
}