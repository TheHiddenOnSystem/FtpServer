import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Layout } from './components/layout/layout';
import Home from './pages/home/home';
import { LogginAndRegister } from './pages/login/loginAndResister';
import WorkSpace from './pages/workSpace/workSpace';
import { URL_WEB_SHOW_WORKSPACE, URL_WEB_HOME, URL_WEB_LOGGIN_AND_REGISTER } from './routes/web';

function App() {



  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path={URL_WEB_LOGGIN_AND_REGISTER} element={<LogginAndRegister/>}/>
          <Route path={URL_WEB_HOME}  element={<Home/>} />
          <Route path={URL_WEB_SHOW_WORKSPACE} element={<WorkSpace/>}/>
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;


