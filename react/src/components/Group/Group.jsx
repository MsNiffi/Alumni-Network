import React from "react";
import { UserAuth } from "../../context/AuthContext";
import { joinGroup } from "../../api/api";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { MdGroups } from "react-icons/md";

const Group = ({ title, description, gangId, userGangs }) => {
    const { user } = UserAuth();
    const navigate = useNavigate();
    const [joined, setJoined] = useState(false);

    useEffect(() => {
        if (userGangs.includes(gangId)) {
            setJoined(true);
        }
    }, [joined]);

    const addUserToGroup = async () => {
        await joinGroup(gangId, user.accessToken).then((resp) => {
            setJoined(true);
        });
    };

    return (
        <li
            key={gangId}
            className="bg-grayish bg-opacity-10 my-2 mx-12 border-b p-2 border-white hover:border-green-500 flex flex-col justify-between flex-wrap"
        >
            <div className="flex items-center content-center gap-6">
                <MdGroups className="text-4xl mb-auto" />
                <h2
                    className="text-lg md:text-2xl font-bold text-white mb-3 hover:cursor-pointer hover:text-green-500"
                    onClick={() => navigate(`/group/${gangId}`)}
                >
                    {title}
                </h2>
            </div>

            <div className="grid content-center w-full items-center lg:flex lg:flex-row lg:justify-between">
                <p className="card-text object-left-top">{description}</p>
                {!joined ? (
                    <button
                        type="submit"
                        onClick={addUserToGroup}
                        className="px-4 py-2 m-3 bg-gray-100 rounded-full text-black 
                            text-sm font-bold hover:bg-green-500"
                        title={`Join Group`}
                    >
                        JOIN
                    </button>
                ) : (
                    <p className="text-green-500 text-right lg:w-[30%]">
                        You are a member
                    </p>
                )}
            </div>
        </li>
    );
};
export default Group;
