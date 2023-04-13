# Alumni Network

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)
[![Java](https://img.shields.io/badge/-Java-red?logo=java)](https://www.java.com)
[![Spring](https://img.shields.io/badge/-Spring-white?logo=spring)](https://spring.io/)
[![PostgreSQL](https://badgen.net/badge/icon/postgresql?icon=postgresql&label)](https://www.postgresql.org/)
[![JavaScript](https://img.shields.io/badge/--F7DF1E?logo=javascript&logoColor=000)](https://www.javascript.com/)

## Table of Contents

- [Overview](#overview)
- [Technologies](#technologies)
- [User Manual](#user-manual)
- [Implemented Functionality and Technical Requirements](#implemented-functionality-and-technical-requirements)
- [API Documentation](#api-documentation)
- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [License](#license)



## Overview

Introduction
-------------
  We have created a software solution that facilitates communication and event organization between groups and individuals, by viewing and
  creating posts, sending DMs, and organize events.

  We have been using a front end created with React, styled with tailwind, to let the user easaly navigate the application.

  In our Java and Spring backend we put all business logic. 
  The API is implemented using Spring Boot, and documented using Swagger. A request is handled by Spring and is resolved in a controller. The
  controller calls a service to perform some business logic, which involves data access. The result is then mapped to a suitable data
  transfer object (DTO) for display purposes.

  For authentication, we use Google Firebase auth.
  Spring Security 3 is utilized to manage authorization in a stateless fashion using JWTs. 

  We use a PostgreSQL database to store userdata, except log inn credentials. 

  The whole application is deployed to Azure and can be found at the link below:

https://team-alumni.azurewebsites.net

The scope of the assignment:
--------------------------
  "The Experis Academy fullstack developer course with Noroff Accelerate has been run many times and in many different locations since the
  programme first began. Both Experis and Noroff have experienced difficulty in maintaining contact with past candidates to notify them of
  social events and gather data for quality assurance purposes." 


The application's SRS (Software Requirements Specification:
-----------------------
- "A static, single-page, front-end using React."
- "A RESTful API service, through which the front end may interact with the database."
- "A PostgreSQL database."
- "Maintaining contact with past candidates."
- "Notify them of social events."
- "Gather data for quality assurance purposes."

## Technologies
- Java
- Spring
- Spring security
- React
- Tailwind
- Firebase
- Azure
- Docker
- PostgreSQL

## User manual
The user manual for the application can be found in the same folder as this file. It describes how to navigate the application's various functions.

## Implemented Functionality and Technical Requirements
**API Requirements**
---------------- 
    The API must not use query parameters at all.
    Use a database-backed session approach to ensure that the user is authenticated
  **API User**

    GET /user
    GET /user/:user_id
      Returns profile information pertaining to the referenced user. Any secrets should be filtered from the response. Under no circumstances should information 
      pertaining to the user’s authentication be exposed to anyone. 
    PATCH /user/:user_id 
      Makes a partial update to the user object with the options that have been supplied. Accepts appropriate parameters in the request body as application/json.
  
**API Group** 

    GET /group 
      Returns a list of groups.  
      Groups that are marked as private that the requesting user is not a member of should be filtered out. 
    GET /group/:group_id 
      Returns the group corresponding to the provided group_id. 
      If the group is private and the requesting user is not a member then the server should respond with a 403 Forbidden. 
    POST /group 
      Create a new group. Accepts appropriate parameters in the request body as application/json. 
      This endpoint should simultaneously create a group membership record, adding the group’s creator as the first member of that group. 
    POST /group/:group_id/join 
      Create a new group membership record. 
      Without any parameters provided: user_id is taken as being that of the requesting user and group_id is provided in the path. 
      Optionally accepts a user_id parameter which overrides the aforementioned de fault. 
      If the group for which the membership record is being created is private, then only current members of the group may create group member 
      records for that group. Attempts to do so by non-members will result in a 403 Forbidden response. 
  **API Topic**

    GET /topic 
      Returns a list of topics.
    GET /topic/:topic_id 
      Returns the topic corresponding to the provided topic_id.
    POST /topic 
      Create a new topic. Accepts appropriate parameters in the request body as application/json. 
      This endpoint should simultaneously create a topic membership record, adding the topic’s creator as the first subscriber to that topic. 
    POST /topic/:topic_id/join 
      Create a new topic membership record. Accepts no parameters. user_id is taken as being that of the requesting user and topic_id is
      provided in the path. 
  **API Post** 

    GET /post
      Returns a list of posts to groups and topics for which the requesting user is subscribed. Optionally accepts appropriate query parameters
      to search, filter, limit and paginate the number of objects return in the reponse. 
    GET /post/user 
      Returns a list of posts that were sent as direct messages to the requesting user. Op tionally accepts appropriate query parameters to
      search, filter, limit and paginate the number of objects return in the reponse. 
    GET /post/user/:user_id 
      Returns a list of posts that were sent as direct messages to the requesting user from the specific user described by user_id (i.e. a
      particular conversation). Optionally accepts appropriate query parameters to search, filter, limit and paginate the number of objects
      return in the reponse. 
    GET /post/group/:group_id 
      Returns a list of posts that were sent with the group described by group_id as the target audience. Optionally accepts appropriate query
      parameters to search, filter, limit and paginate the number of objects return in the reponse. 
    GET /post/topic/:topic_id
      Returns a list of posts that were sent with the topic described by topic_id as the target audience. Optionally accepts appropriate query
      parameters to search, filter, limit and paginate the number of objects return in the reponse. 
    GET /post/event/:event_id 
      Returns a list of posts that were sent with the event described by event_id as the target audience. Optionally accepts appropriate query
      parameters to search, filter, limit and paginate the number of objects return in the reponse. 
    POST /post
      Create a new post. Accepts appropriate parameters in the request body as application/json. 
      Attempts to post to an audience for which the requesting user is not a member will result in a 403 Forbidden response. 
      The last_updated property of the post should be set automatically by the service and override any value provided by the client. 
    PUT /post/:post_id~ 
      Update an existing post. Accepts appropriate parameters in the request body as application/json. 
      The audience of a post may not be changed after creation. Attempts to do so will result in a 403 Forbidden response and the update aborted (i.e. no partial  
      update should happen). 
      The last_updated property of the post should be updated automatically by the service and override any value provided by the client. 
  **API Event** 

     GET /event 
      Returns a list of events posted to groups and topics for which the requesting user is subscribed. 
      Create a new event. Accepts appropriate parameters in the request body as application/json. Attempts to post an event to an audience for 
      which the requesting user is not a member 11 will result in a 403 Forbidden response. 
      The last_updated property of the event should be set automatically by the service and override any value provided by the client. 
    PUT /event/:event_id 
      Only in backend. Update an existing event. Accepts appropriate parameters in the request body as application/json. 
      Only the event creator may update an event. Requests to update an event by all other users will result in a 403 Forbidden response. 
      The last_updated property of the post should be updated automatically by the service and override any value provided by the client. 
    POST /event/:event_id/invite/group/:group_id 
     Only in backend. Create a new event group invitation for the event and group specified in the path. Accepts no parameters. 
      Only the event creator may create event invitations. Requests to do so from all other users will result in a 403 Forbidden response. 
    DELETE /event/:event_id/invite/group/:group_id 
       Only in backend. Remove an existing event invitation for the event and group specified in the path. Accepts no parameters. 
      Only the event creator may create event invitations. Requests to do so from all other users will result in a 403 Forbidden response. 
      Removal of the event invitation does not automatically remove RSVP records that were authorized by that invitation before it was removed. 
    POST /event/:event_id/invite/topic/:topic_id 
       Only in backend. Create a new event topic invitation for the event and topic specified in the path. Accepts no parameters. 
      Only the event creator may create event invitations. Requests to do so from all other users will result in a 403 Forbidden response. 
    DELETE /event/:event_id/invite/topic/:topic_id 
       Only in backend. Remove an existing event invitation for the event and topic specified in the path. Accepts no parameters. 12
      Only the event creator may create event invitations. Requests to do so from all other users will result in a 403 Forbidden response. 
      Removal of the event invitation does not automatically remove RSVP records that were authorized by that invitation before it was removed. 
    POST /event/:event_id/invite/user/:user_id 
       Only in backend. Create a new event invitation for an individual user for the event and user specified in the path. Accepts no parameters. 
      Only the event creator may create event invitations. Requests to do so from all other users will result in a 403 Forbidden response. 
    DELETE /event/:event_id/invite/user/:user_id 
       Only in backend. Remove an existing event invitation for the event and user specified in the path. Accepts no parameters. 
      Only the event creator may create event invitations. Requests to do so from all other users will result in a 403 Forbidden response. 
      Removal of the event invitation does not automatically remove RSVP records that were authorized by that invitation before it was removed. 
    POST /event/:event_id/rsvp 
       Only in backend. Create a new event rsvp record. Accepts appropriate parameters in the request body as application/json. By default, user_id is taken as
       being that of the
      requesting user and event_id is provided in the path. 
      If the requesting user is not part of an invited group or topic, or has not been invited individually, the request will result in a 403 Forbidden response. 

**API Administration** 
--------------------
      The system should have no administration section and should not require specific ad ministrator intervention to provide base functionality. 

Database Requirements 
-----------------------
  **Complete Storage Solution** 

      All data used by the application must be stored in the database and access to the database must be appropriately controlled. 
  **Backups** 

      The database must have a complete backup at least once per day. Backups should be kept for at least seven days. 
  **Database Design** 

      While a partial ERD is supplied in Appendix A, it is incomplete and is provided to the candidates as a suggestion. Candidates must alter, or
      design from scratch, and document a database schema for use by this solution. 

Security Requirements 
-----------------------
  **User Authentication**

      Users should be authenticated appropriately before being allowed to interact with the system. All API endpoints (unless otherwise marked)
      should require an appropriate bearer token to be supplied in the Authorization header of the request. 2FA should be enforced2. (optional)
      It is recommended to use an external authentication provider (such as Microsoft, Google, or Gitlab) and the OpenID Connect Auth Code Flow 3.
      If desired, a self-hosted identity provider can be quickly deployed using Docker4 and Keycloak5. We used firebase
  **Input Sanitation** 

      All endpoints that accept input from users must ensure that the provided data is appro priately sanitized or escaped so that it cannot be
      used in an XSS or injection attack. 

  **Credential Storage** 

      Care should be taken to ensure that administrative credentials (i.e. database credentials) are not hard coded into the application and are
      instead provided to the application using environment variables. 

 **HTTPS** 

      The final, public facing deployment of the application should enforce communication only over HTTPS. 


## API Documentation
Swagger has been configured to be as complete as needed. This includes:
- API name and description
- API version
- Controller name and description
- Endpoint name and description
- Response types for failures and successes
- All required data structures are represented as DTOs

Swagger documentation is available at this link:

https://team-alumni-backend.azurewebsites.net/swagger-ui/index.html

The configuration for the API details and the controller details was done
with @Info and @Tag:

```java
@OpenAPIDefinition(info = @Info(
        title = "Alumni Network API",
        description = "A REST API to provide access to users, posts and events, and the groups and topics those users, posts and events are associated with. Created by Anette Londal, Adrian Friduson & Marcus Vinje Johansen.",
        version = "1.0"
))
public class AlumniNetworkApplication {}
```

```java
@Tag(name = "Users", description = "Endpoints to interact with users")
public class UserController {}
```



## Install

Open a terminal or powershell window and run:

```sh
npm install
```

## Usage

Open a terminal or powershell window and run:

```sh
npm run dev
```

Further instructions will appear in your console. Leave the window open while in use.

## Maintainers

[Anette Londal (@Ms.Niffi)](https://gitlab.com/Ms.Niffi)

[Adrian Friduson (@HessianThespian)](https://gitlab.com/HessianThespian)

[Marcus Vinje Johansen (@mommotexx)](https://gitlab.com/mommotexx)

## Contributing

PRs accepted.

Small note: If editing the README, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

MIT © 2023 Anette Londal, Adrian Friduson, Marcus Vinje Johansen