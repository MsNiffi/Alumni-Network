import React, { useState } from "react";
import GoogleButton from "react-google-button";
import { useNavigate } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import ErrorMessage from "./ErrorMessage";

// Using firebase functions in the LoginForm
const LoginForm = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const { createUser, googleLogin, emailLogin } = UserAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            await createUser(email, password);
            navigate("/timeline");
        } catch (e) {
            setError(e.message);
        }
    };

    const handleGoogleLogin = async (e) => {
        e.preventDefault();
        setError("");
        try {
            await googleLogin();
            navigate("/timeline");
        } catch (e) {
            setError(e.message);
        }
    };

    const handleEmailLogin = async (e) => {
        e.preventDefault();
        setError("");
        try {
            await emailLogin(email, password);
            navigate("/timeline");
        } catch (e) {
            setError(e.message);
        }
    };

    return (
        <>
            <div className="grid justify-center text-black">
                <div className="mt-5 flex flex-col bg-[#D9D9D9] bg-opacity-60 rounded-2xl gap-5 p-10 lg:p-16 drop-shadow">
                    <input
                        placeholder="username/email"
                        className="rounded h-12 pl-2 -mt-13"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <input
                        type="password"
                        placeholder="password"
                        className="rounded h-12 pl-2"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <button
                        onClick={handleEmailLogin}
                        className="bg-primary rounded h-11 drop-shadow text-white border-secondary border-[1px]"
                    >
                        Log in
                    </button>
                    <GoogleButton onClick={handleGoogleLogin} />
                    <hr className="border-tertiary" />
                    <button
                        className="bg-[#EA084B] h-11 w-2/3 self-center rounded text-white drop-shadow -mb-6"
                        onClick={handleSubmit}
                    >
                        Create account
                    </button>
                    {error && <ErrorMessage error={error} />}
                </div>
            </div>
        </>
    );
};

export default LoginForm;
