import { NavLink } from 'react-router-dom';
import { loggedIn } from '../formEndpoints';

export interface BannerProps {
    loggedIn: boolean
}

export default function Banner(props: BannerProps) {
    const { loggedIn } = props;
    return (
    <div className="topbar">
        <img className="banner-logo" alt="The logo of Waveform - a blue wave with an aqua-coloured title reading Waveform" src='/title-image.png'></img>
        <nav className="tabs">
            <NavLink className="tab" to="/">Home</NavLink>
            {loggedIn && (<NavLink className="tab" to="/createform">Create Form</NavLink>)}
            <NavLink className="tab" to="/viewform" >View Form</NavLink>
            {loggedIn && (<NavLink className="tab" to="/metrics" >Metrics</NavLink>)}
            {loggedIn && (<NavLink className="tab" to="/logout">Logout</NavLink>)}
            {!loggedIn && (<NavLink className="tab" to="/loginPage">Login</NavLink>)}
        </nav>
    </div>
    )
}