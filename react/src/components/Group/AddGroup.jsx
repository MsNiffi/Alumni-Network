import React from "react";
import { UserAuth } from "../../context/AuthContext";
import { addGroup } from "../../api/api";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

const AddGroup = () => {
    const { user } = UserAuth();
    const navigate = useNavigate();
    const { register, handleSubmit } = useForm();

    /**
        * Creates/Updates a user
      @param { Object } data - Data submitted by the form
      */
    const add = async (data) => {
        await addGroup(user.accessToken, data);
        navigate("/groups");
    };

    return (
        <form onSubmit={handleSubmit(add)}>
            <div>
                <h1 className="text-5xl">Create a new group!</h1>
                <br />
                <label
                    htmlFor="Group name"
                    className="block mb-2 text-white text-2xl"
                >
                    Group name
                </label>
                <input
                    {...register("gangName")}
                    id="Group name"
                    required
                    minLength={3}
                    maxLength={40}
                    type="text"
                    placeholder="Enter a group name"
                    className="w-full border text-black"
                ></input>
                <br />
                <br />
                <label
                    htmlFor="description"
                    className="block mb-2 text-white text-2xl"
                >
                    Group description
                </label>
                <textarea
                    {...register("gangDescription")}
                    id="description"
                    required
                    minLength={3}
                    rows="4"
                    className="block p-2.5 w-full text-black "
                    placeholder="Write a description of the group"
                ></textarea>
                <br />
                <input
                    {...register("isPrivate")}
                    type="checkbox"
                    id="public group"
                    className=" w-6 h-6"
                />
                <label htmlFor="public group" className="text-2xl">
                    {" "}
                    Private group?
                </label>
                <br />
                <br />
            </div>
            <button
                type="submit"
                className="p-2 bg-grayish rounded text-3xl font-bold hover:bg-green-500"
            >
                Create Group
            </button>
        </form>
    );
};
export default AddGroup;
