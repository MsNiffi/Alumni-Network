import React from "react";
import { useNavigate } from "react-router-dom";

// Custom avatar. Takes in size, avatar string, and optionally alt and userId
const Avatar = ({ avatar, size, customAlt, userId }) => {
    const navigate = useNavigate();

    return (
        <img
            className={`border-opacity-20 rounded-full h-full shadow-md bg-gray-600 shadow-black ${
                userId && "hover:cursor-pointer"
            }`}
            width={size}
            height={size}
            onClick={() => userId && navigate(`/profile/${userId}`)}
            src={
                "https://api.dicebear.com/5.x/avataaars/svg?seed=" +
                avatar +
                "&size=" +
                size * 2
            }
            alt={customAlt ? customAlt : "Image of profile avatar"}
        />
    );
};

export default Avatar;
