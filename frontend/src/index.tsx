import React from 'react';
import ReactDOM from 'react-dom';
import "./index.css";
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import {
    RouterProvider,
} from "react-router-dom";
import router from "./router"


const App: React.FC = () =>
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
;

ReactDOM.render(<App />, document.getElementById('root'));
