import React from "react";
import Post from "../Post/Post";
import NoPostFound from "../Post/NoPostFound";

const CommonPostList = ({ posts, parent, search, color, isLoading }) => {
    // If parent is defined and above 0, render the parent first
    const postArray = posts?.filter((post) => {
        if (parent > 0) {
            return post.replyParent == parent || post.postId == parent;
        }

        // Making title body and search to lowercase. This is so I can search for either body or title, and ignoring case
        const lowercaseTitle = post.title?.toLowerCase();
        const lowercaseBody = post.body?.toLowerCase();
        const lowercaseSearch = search?.toLowerCase();

        // If replyParent == null, returns the top level posts, not it's replies.
        return (
            (lowercaseTitle.includes(lowercaseSearch) &&
                post.replyParent == null) ||
            (lowercaseBody.includes(lowercaseSearch) &&
                post.replyParent == null)
        );
    });

    // Goes through the array, and returns Post element for every post
    return postArray?.length > 0 ? (
        // Sorts the posts by id if parent is defined.
        postArray
            ?.sort((a, b) => parent && a.postId - b.postId)
            .map((post) => {
                return (
                    <Post
                        key={post.postId}
                        title={post.title}
                        author={post.sender.name}
                        messageBody={post.body}
                        published={post.publishedOn}
                        profilePic={post.sender.pictureUrl}
                        topic={post.targetTopics}
                        color={parent == post.postId && color}
                        postId={post.postId}
                        replies={post.replies}
                        creatorId={post.sender.id}
                        updated={post.updatedOn}
                    />
                );
            })
    ) : isLoading ? (
        <h1>Loading posts ...</h1>
    ) : (
        <NoPostFound />
    );
};

export default CommonPostList;
