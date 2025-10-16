import './App.css'
import { BrowserRouter, Route, Routes } from 'react-router'
import Login from './pages/Login'

function App() {


  return (
    <BrowserRouter>
      <Routes>

        <Route path='/login' element={<Login/>}></Route>


      </Routes>
    </BrowserRouter>
  )
}

export default App
