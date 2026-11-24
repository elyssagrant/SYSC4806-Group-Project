import { Form } from "../types";

interface DiscoveryRowProps {
    data: Form
}

export default function DiscoveryRow(props: DiscoveryRowProps) {
    let { data } = props;

    return (
        <tr>
            <td>{data.name}</td> 
        </tr>
    )
}