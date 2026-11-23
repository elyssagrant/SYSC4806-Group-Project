import { NavLink } from 'react-router-dom';

export default function Banner() {
    return (
    <div className="topbar">
        <img className="banner-logo" src='/title-image.png'></img>
        <nav className="tabs">
            <NavLink className="tab" to="/">Home</NavLink>
            <NavLink className="tab" to="/createform">Create Form</NavLink>
            <NavLink className="tab" to="/viewform" >View Form</NavLink>
        </nav>
    </div>
    )
}