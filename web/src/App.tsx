
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Layout } from './components/layout/layout';
import { LogginAndRegister } from './pages/login/loginAndResister';
import { LOGGIN_AND_REGISTER } from './routes/web';

function App() {
  return (
    <Layout>
      <BrowserRouter>
        <Routes>
          <Route element={<LogginAndRegister/>} path={LOGGIN_AND_REGISTER}/>
          <Route element={<p>Home</p>} path={"home"}/>
        </Routes>
      </BrowserRouter>
    </Layout>
  );
}

export default App;
