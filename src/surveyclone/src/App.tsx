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
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.tsx</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
      </div>
    </BrowserRouter>
  );
}

export default App;
