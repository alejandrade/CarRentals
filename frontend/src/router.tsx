import React from "react";
import {createHashRouter} from "react-router-dom";

export default createHashRouter([
    {
        path: "/",
        element: <div>Hello world!</div>,
    },    {
        path: "/test",
        element: <div>Hello world test!</div>,
    },
]);