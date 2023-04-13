import React from "react";

// Error message
const ErrorMessage = ({ error }) => {
    const errorSlice = error.slice(error.indexOf("("));
    let simpleError;

    switch (errorSlice) {
        case "(auth/invalid-email).":
            simpleError = "Invalid email";
            break;
        case "(auth/wrong-password).":
            simpleError = "Wrong email/password";
            break;
        case "(auth/user-not-found).":
            simpleError = "Wrong email/password";
            break;
        case "(auth/email-already-in-use).":
            simpleError = "User already exists";
            break;
        case "(auth/too-many-requests).":
            simpleError = "Too many failed login attempts. Try again later";
            break;
        default:
            simpleError = "Something went wrong";
    }
    return <p className="m-auto mt-4 text-center w-60">{simpleError}</p>;
};

export default ErrorMessage;
