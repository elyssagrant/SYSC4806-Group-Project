import { NavLink } from 'react-router-dom';

export default function Sidebar() {
    return (      
    <nav className="tabs">
        <NavLink className="tab" to="/"></NavLink>
        <NavLink className="tab" to="/createform"></NavLink>
        <NavLink className="tab" to="/viewform" ></NavLink>
    </nav>)
}