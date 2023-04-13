import React from "react";
import { useNavigate } from "react-router-dom";
import Avatar from "../Avatar/Avatar";

// List of senders, takes in the sender object.
const UserList = ({ sender }) => {
    const navigate = useNavigate();
    return (
        <li
            key={sender.id}
            className="bg-grayish bg-opacity-10 my-2 mx-12 border-b p-2 border-white hover:border-green-500 flex flex-col justify-between flex-wrap"
        >
            <div className="flex items-center content-center gap-6">
                <Avatar
                    avatar={sender.pictureUrl}
                    size={56}
                    userId={sender.id}
                />
                <h2
                    className="text-lg md:text-2xl font-bold text-white mb-3 hover:cursor-pointer hover:text-green-500"
                    onClick={() => navigate(`/dm/${sender.id}`)}
                >
                    {sender.name}
                </h2>
            </div>
        </li>
    );
};

export default UserList;
