import barGraph from './images/barGraph.png'
import Wave from 'react-wavify'

export default function LandingPage() {
    return (
    <>
        <div className="top-div">
            <h1 className="TopHeader">Multiple Choice? Unlimited Choice.</h1>
            <Wave fill='cornflowerblue'
                paused={false}
                style={{ display: 'flex' }}
                options={{
                height: 20,
                amplitude: 50,
                speed: 0.15,
                points: 10
                }}
            />
        </div>
        <h3 className="SubHeader">Create the survey that's right for you</h3>
        <div className="frontPageAdvertising">
            <div>
                <img src={barGraph} alt="A bar histogram of responses"/>
            </div>
            <div className="advertisingTextGroup">
                <h3 className="SubHeader">Ask the world. Get an answer.</h3>
                <p id="customizationAd">Make customizable surveys within minutes.</p>
            </div>
        </div>
    </>
    )
}