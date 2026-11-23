import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import CreateFormPage from './CreateFormPage'
import ViewForm from './ViewForm'
import './App.css';
import Banner from './otherComponents/Banner';
import LandingPage from "./LandingPage";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Banner/>
        <Routes>
          <Route path='/' element={<LandingPage/>}></Route>
          <Route path='/createform' element={<CreateFormPage/>}></Route>
          <Route path='/viewform' element={<ViewForm/>}></Route>
          <Route path='*' element={<h1>Not Found</h1>}></Route>
        </Routes>

      </div>
    </BrowserRouter>
  );
}

export default App;
