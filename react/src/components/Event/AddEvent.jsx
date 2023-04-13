import React from "react";
import { UserAuth } from "../../context/AuthContext";
import { addEvent } from "../../api/api";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

const AddEvent = () => {
    const { user } = UserAuth();
    const navigate = useNavigate();
    const { register, handleSubmit } = useForm();

    /**
         * Creates/Updates a user
       @param { Object } data - Data submitted by the form
       */
    const add = async (data) => {
        await addEvent(user.accessToken, data);
        navigate("/events");
    };

    return (
        <form onSubmit={handleSubmit(add)}>
            <div>
                <h1 className="text-5xl">Create a new event!</h1>

                <br />

                <label
                    htmlFor="Event name"
                    className="block mb-2 text-white text-2xl"
                >
                    Event name
                </label>
                <input
                    {...register("eventName")}
                    id="eventName"
                    required
                    minLength={3}
                    maxLength={40}
                    type="text"
                    placeholder="Enter an event name"
                    className="w-full border text-black"
                ></input>

                <br />
                <br />

                <label
                    htmlFor="Set start time:"
                    className="block mb-2 text-white text-2xl"
                >
                    Set start time:
                </label>
                <input
                    {...register("startTime")}
                    id="startTime"
                    required
                    type="datetime-local"
                    className="w-full border text-black"
                ></input>

                <br />
                <br />

                <label
                    htmlFor="Set end time:"
                    className="block mb-2 text-white text-2xl"
                >
                    Set end time:
                </label>
                <input
                    {...register("endTime")}
                    id="endTime"
                    required
                    type="datetime-local"
                    className="w-full border text-black"
                ></input>
                <br />
                <br />

                <label
                    htmlFor="description"
                    className="block mb-2 text-white text-2xl"
                >
                    Event description
                </label>
                <textarea
                    {...register("eventDescription")}
                    id="description"
                    required
                    minLength={3}
                    rows="4"
                    className="block p-2.5 w-full text-black "
                    placeholder="Write a description of the event"
                ></textarea>

                <br />

                <input
                    {...register("allowGuests")}
                    type="checkbox"
                    id="publicEvent"
                    className=" w-6 h-6"
                />
                <label htmlFor="public event" className="text-2xl">
                    {" "}
                    Allow guests?
                </label>

                <br />
                <br />
            </div>

            <button
                type="submit"
                className="p-2 bg-grayish rounded text-3xl font-bold hover:bg-green-500"
            >
                Create Event
            </button>
        </form>
    );
};
export default AddEvent;
