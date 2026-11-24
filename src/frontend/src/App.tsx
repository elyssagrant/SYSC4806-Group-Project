import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import CreateFormPage from './CreateFormPage'
import ViewForm from './AnswerForm'
import './App.css';
import Sidebar from './otherComponents/Sidebar';
import LandingPage from "./LandingPage";

function App() {
  return (
    <BrowserRouter>
      <Sidebar/>

      <div className="App">
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
