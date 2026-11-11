import React from 'react';
import logo from './logo.svg';
import { BrowserRouter, Routes, Route, NavLink, Navigate } from 'react-router-dom'
import CreateFormPage from './CreateFormPage'
import ViewForm from './ViewForm'
import './App.css';
import Sidebar from './otherComponents/Sidebar';

function App() {
  return (
    <BrowserRouter>
      <Sidebar/>

      <div className="App">
        <Routes>
          <Route path='/'></Route>
          <Route path='/createform' element={<CreateFormPage/>}></Route>
          <Route path='/viewform' element={<ViewForm/>}></Route>
          <Route path='*' element={<h1>Not Found</h1>}></Route>
        </Routes>

        <header className="App-header">
        </header>
      </div>
    </BrowserRouter>
  );
}

export default App;
