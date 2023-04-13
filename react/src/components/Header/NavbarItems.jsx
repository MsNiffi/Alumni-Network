import React from "react";
import { AiFillHome } from "react-icons/ai";
import { CgProfile, CgLogOut } from "react-icons/cg";
import { FaHashtag, FaUserFriends } from "react-icons/fa";
import { MdGroups } from "react-icons/md";
import { BsFillCalendarEventFill } from "react-icons/bs";
import { FiMessageSquare } from "react-icons/fi";
import { UserAuth } from "../../context/AuthContext";

/* 
    Modular design of the navigation menu. Just add/remove items to fill the menu 
    Have access to the user auth. can be used to add admin panels etc in the future
*/
export const NavbarItems = () => {
    const { user } = UserAuth();
    return [
        {
            title: "Timeline",
            path: "/timeline",
            icon: <AiFillHome />,
        },
        {
            title: "Profile",
            path: `/profile/${user?.uid}`,
            icon: <CgProfile />,
        },
        {
            title: "Direct Message",
            path: "/directMessage",
            icon: <FiMessageSquare />,
        },
        {
            title: "Events",
            path: "/events",
            icon: <FaUserFriends />,
        },
        {
            title: "Topics",
            path: "/topics",
            icon: <FaHashtag />,
        },
        {
            title: "Groups",
            path: "/groups",
            icon: <MdGroups />,
        },
        {
            title: "Calendar",
            path: "/calendar",
            icon: <BsFillCalendarEventFill />,
        },
        {
            title: "Logout",
            path: "/logout",
            icon: <CgLogOut />,
        },
    ];
};
