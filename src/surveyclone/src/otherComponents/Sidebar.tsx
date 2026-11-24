import { NavLink } from 'react-router-dom';

export default function Sidebar() {
    return (      
    <nav className="tabs">
        <NavLink className="tab" to="/">Home</NavLink>
        <NavLink className="tab" to="/createform">Create Form</NavLink>
        <NavLink className="tab" to="/viewform" >View Form</NavLink>
        <NavLink className="tab" to="/metrics" >Metrics</NavLink>
    </nav>)
}