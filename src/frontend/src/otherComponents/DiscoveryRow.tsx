import { SurveySummary } from "../types";

interface DiscoveryRowProps {
    data: SurveySummary
}

export default function DiscoveryRow({ data }: DiscoveryRowProps) {
    return (
        <tr>
            <td>{data.id}</td> 
            <td>{data.title}</td>
            <td>{data.isOpen}</td>
            <td>{data.numberOfResponses}</td>
            <td>{data.numberOfQuestions}</td>
        </tr>
    )
}