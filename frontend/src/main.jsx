import "./styles/globals.css";
import React from "react";
import ReactDOM from "react-dom/client";
import { RouterProvider } from "react-router-dom";
import router from "./routes";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.Fragment>
        <RouterProvider router={router} />
    </React.Fragment>
);
