import React from "react";
import Avatar from "../Avatar/Avatar";

// Individual DM, takes in the DM object
const DmList = ({ dm }) => {
    return (
        <article className="flex flex-col items-center mb-8">
            <div className="flex gap-6 justify-between items-center">
                <Avatar avatar={dm.sender.pictureUrl} size={56} />
                <p className="p-6 rounded bg-green-600">{dm.body}</p>
            </div>
            <p className="">{dm.publishedOn}</p>
        </article>
    );
};

export default DmList;
