import React from "react";
import { useNavigate } from "react-router-dom";

// Takes in the location and message, and render a button with label +. Message is shown for screen readers, and when you hover the button
const CreateButton = ({ location, message }) => {
    const navigate = useNavigate();
    return (
        <div className="flex justify-center">
            <button
                title={`Create ${message}`}
                aria-label={`Create ${message}`}
                className="rounded-2xl bg-primary self-center text-5xl px-4 font-bold hover:bg-green-500"
                onClick={() => location && navigate(location)}
            >
                <div className="p-3">+</div>
            </button>
        </div>
    );
};

export default CreateButton;
