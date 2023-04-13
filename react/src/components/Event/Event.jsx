import React from "react";
import { UserAuth } from "../../context/AuthContext";
import { joinEvent } from "../../api/api";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { FaUserFriends } from "react-icons/fa";

const Event = ({ title, description, eventId, userEvents }) => {
    const { user } = UserAuth();
    const navigate = useNavigate();
    const [joined, setJoined] = useState(false);

    // Checks if the user is part of the event or not. Then sets the state
    useEffect(() => {
        if (userEvents?.includes(eventId)) {
            setJoined(true);
        }
    }, [joined, userEvents]);

    // Function to add user to the event
    const addUserToEvent = async () => {
        await joinEvent(eventId, user.accessToken).then((resp) => {
            setJoined(true);
        });
    };

    return (
        <li key={eventId} className="col-xs-1-12 list-none">
            <div
                className="card bg-grayish bg-opacity-10 my-2 mx-12 border-b border-white 
            hover:opacity-100 hover:border-green-500"
            >
                <div className="card-body grid grid-rows-3 grid-flow-col">
                    <div className="row-span-3 col-span-1 ...">
                        <FaUserFriends className="text-4xl" />
                    </div>

                    <div className="col-span-11 ...">
                        <h3
                            className="card-title object-left-top text-2xl font-bold hover:cursor-pointer hover:text-green-500"
                            onClick={() => navigate(`/event/${eventId}`)}
                        >
                            {title}
                        </h3>
                    </div>

                    <div className="row-span-2 col-span-11 ... ">
                        <p className="card-text object-left-top">
                            {description}
                        </p>
                    </div>

                    <div className="row-span-3 col-span-3 ">
                        {!joined ? (
                            <button
                                type="submit"
                                onClick={addUserToEvent}
                                className="px-6 py-3 bg bg-gray-100 rounded-3xl text-black 
                    text-3xl font-bold hover:bg-green-500"
                                title={`Join Event`}
                            >
                                JOIN
                            </button>
                        ) : (
                            <p>You are a member of this event</p>
                        )}
                    </div>
                </div>
            </div>
        </li>
    );
};
export default Event;
