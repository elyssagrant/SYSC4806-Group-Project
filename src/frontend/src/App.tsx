import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import CreateFormPage from './pages/CreateFormPage'
import ViewForm from './pages/AnswerForm'
import './App.css';
import Banner from './otherComponents/Banner';
import MetricsPage from './pages/MetricsPage'
import LandingPage from "./pages/LandingPage";
import { loggedIn } from './formEndpoints';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    async function checkIfLoaded() {

    try {
        setIsLoggedIn(await loggedIn())
      } catch(error: any) {
        console.error(error.message); 
        setIsLoggedIn(false)
      }
    }
    checkIfLoaded()
  }, [isLoggedIn])


  return (
    <BrowserRouter>
      <div className="App">
        <Banner loggedIn={isLoggedIn}/>
        <Routes>
          <Route path='/' element={<LandingPage/>}></Route>
          {isLoggedIn && (<Route path='/createform' element={<CreateFormPage/>}></Route>)}
          <Route path='/viewform' element={<ViewForm/>}></Route>
          {isLoggedIn && (<Route path='/metrics' element={<MetricsPage/>}></Route>)}
          {isLoggedIn && (<Route path='/logout'></Route>)}
          {!isLoggedIn && (<Route path='/login'></Route>)}
          <Route path='*' element={<h1>Not Found</h1>}></Route>
        </Routes>

      </div>
    </BrowserRouter>
  );
}

export default App;
