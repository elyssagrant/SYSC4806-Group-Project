import { NavLink } from 'react-router-dom';

export default function Banner() {
    return (
    <div className="topbar">
        <img className="banner-logo" alt="The logo of Waveform - a blue wave with an aqua-coloured title reading Waveform" src='/title-image.png'></img>
        <nav className="tabs">
            <NavLink className="tab" to="/">Home</NavLink>
            <NavLink className="tab" to="/createform">Create Form</NavLink>
            <NavLink className="tab" to="/viewform" >View Form</NavLink>
            <NavLink className="tab" to="/metrics" >Metrics</NavLink>
        </nav>
    </div>
    )
}